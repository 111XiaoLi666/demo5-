package view;
import controller.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Direction;
import model.GameBoard;
import model.Piece;
import model.PieceAndPos;

public class GamePanel extends Pane {
    public Piece selectedPiece;
    private GameBoard gameBoard;
    private final Game game;
    private final GameFrame gameFrame;
    public int unitSize = 100;
    private Timeline animationTimeline;
    private double animationLength=0.15;
    private double xPressed, yPressed, xReleased, yReleased;
    private int selectedBoardX;
    private int selectedBoardY;
    private int selectedPieceW;
    private int selectedPieceH;

    public GamePanel(GameBoard gameBoard, Game game, GameFrame gameFrame) {
        this.gameBoard = gameBoard;
        this.game = game;
        this.gameFrame = gameFrame;

        // 尺寸监听
        ChangeListener<Number> sizeListener = (obs, oldVal, newVal) -> adjustUnitSize();
        widthProperty().addListener(sizeListener);
        heightProperty().addListener(sizeListener);

        setFocusTraversable(true);
        setOnKeyPressed(this::handleKeyPress);
        // 初始选中第一个棋子（如果存在）
        if (!gameBoard.piecesAndPoses.isEmpty()) {
            PieceAndPos first = gameBoard.piecesAndPoses.get(0);
            selectedBoardX = first.w;
            selectedBoardY = first.h;
            selectedPiece = first.piece;
            selectedPieceW = first.w;
            selectedPieceH = first.h;
        }

        adjustUnitSize();
        fresh(null, null, 1.0);
    }

    private Piece findPieceAt(int boardX, int boardY) {
        for (PieceAndPos pap : gameBoard.piecesAndPoses) {
            if (boardX >= pap.w && boardX < pap.w + pap.piece.width &&
                    boardY >= pap.h && boardY < pap.h + pap.piece.height) {
                return pap.piece;
            }
        }
        return null;
    }

    public void update(Piece animatedPiece, Direction direction) {
        if (animationTimeline != null) {
            animationTimeline.stop();
        }

        final long startTime = System.nanoTime();
        final double totalDuration = animationLength * 1_000_000_000.0; // 转换为纳秒

        animationTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), e -> { // 每10ms更新一次
                    long elapsed = System.nanoTime() - startTime;
                    double animatedRate = Math.min(elapsed / totalDuration, 1.0);
                    fresh(animatedPiece, direction, animatedRate);

                    if (animatedRate >= 1.0) {
                        animationTimeline.stop();
                    }
                })
        );
        animationTimeline.setCycleCount(Timeline.INDEFINITE);
        animationTimeline.play();
    }

    public void fresh(Piece animatedPiece, Direction direction, double animatedRate) {
        getChildren().clear();

        for (PieceAndPos pieceAndPos : gameBoard.piecesAndPoses) {
            Piece piece = pieceAndPos.piece;
            Pane pieceNode = new Pane();

            // 设置颜色和样式
            Color color = piece.color;
            pieceNode.setStyle("-fx-background-color: rgba("
                    + (int)(color.getRed()*255) + ","
                    + (int)(color.getGreen()*255) + ","
                    + (int)(color.getBlue()*255) + ","
                    + color.getOpacity() + ");"
                    + "-fx-border-color: black;");

            // 设置位置和大小
            double x = pieceAndPos.w * unitSize;
            double y = pieceAndPos.h * unitSize;
            if (animatedPiece == piece) {
                x -= (1 - animatedRate) * direction.w * unitSize;
                y -= (1 - animatedRate) * direction.h * unitSize;
            }

            pieceNode.setLayoutX(x);
            pieceNode.setLayoutY(y);
            pieceNode.setPrefSize(piece.width * unitSize, piece.height * unitSize);

            // 双击事件
            pieceNode.setOnMouseClicked(event -> {
                selectedPiece = piece;
                pieceNode.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-style: solid;");
            });
            pieceNode.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown()) {
                    xPressed = event.getX();
                    yPressed = event.getY();
                }
            });

            pieceNode.setOnMouseReleased(event -> {
                xReleased = event.getX();
                yReleased = event.getY();

                double xDiff = xReleased - xPressed;
                double yDiff = yReleased - yPressed;

                if(xDiff<0.25&&yDiff<0.25){
                    return;
                }
                if (Math.abs(xDiff) > Math.abs(yDiff)) {
                    if (xDiff > 0) {
                        game.step(piece, Direction.RIGHT);
                    } else {
                        game.step(piece, Direction.LEFT);
                    }
                } else {
                    if (yDiff > 0) {
                        game.step(piece, Direction.DOWN);
                    } else {
                        game.step(piece, Direction.UP);
                    }
                }
            });
            getChildren().add(pieceNode);
        }
        requestLayout();
    }

    private void adjustUnitSize() {
        double panelWidth = getWidth();
        double panelHeight = getHeight();

        if (panelWidth > 0 && panelHeight > 0) {
            int cols = gameBoard.occupancyMap[0].length;
            int rows = gameBoard.occupancyMap.length;

            unitSize = (int) Math.min(panelWidth / cols, panelHeight / rows);
            setPrefSize(unitSize * cols, unitSize * rows);
            fresh(null, null, 1.0);
        }
    }

    // 单独的键盘控制方法


    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code.isArrowKey()) {
            event.consume();
            if (selectedPiece != null) {
                // 移动选中的棋子
                Direction direction = switch (code) {
                    case UP -> Direction.UP;
                    case DOWN -> Direction.DOWN;
                    case LEFT -> Direction.LEFT;
                    case RIGHT -> Direction.RIGHT;
                    default -> null;
                };
                if (direction != null) {
                    try {
                        game.step(selectedPiece, direction);
                    } catch (Exception e) {
                        System.err.println("移动失败: " + e.getMessage());
                    }
                }
            } else {
                // 移动选中框
                int newX = selectedBoardX;
                int newY = selectedBoardY;
                switch (code) {
                    case W -> newY--;
                    case S -> newY++;
                    case A -> newX--;
                    case D -> newX++;
                    default -> { return; }
                }
                int cols = gameBoard.occupancyMap[0].length;
                int rows = gameBoard.occupancyMap.length;
                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows) {
                    selectedBoardX = newX;
                    selectedBoardY = newY;
                    selectedPiece = findPieceAt(selectedBoardX, selectedBoardY);
                    if (selectedPiece != null) {
                        for (PieceAndPos pap : gameBoard.piecesAndPoses) {
                            if (pap.piece == selectedPiece) {
                                selectedPieceW = pap.w;
                                selectedPieceH = pap.h;
                                break;
                            }
                        }
                    }
                    fresh(null, null, 1.0);
                }
            }
        } else if (code == KeyCode.ESCAPE) {
            selectedPiece = null;
            fresh(null, null, 1.0);
        }
    }
}