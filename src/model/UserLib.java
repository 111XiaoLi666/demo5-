package model;

import java.io.*;
import java.util.ArrayList;

public class UserLib {

    ArrayList<User> users;
    private static String file = "data/UserLib/users.ser";


    public UserLib() {
        // 自动创建目录
        File dir = new File("data/UserLib");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!new File(file).exists()) {
            users = new ArrayList<>();
        } else {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(file)));
                users = (ArrayList<User>) in.readObject();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;
        public String username;
        public String password;
        public boolean isGuest;

        public User(String username, String password,boolean isGuest) {
            this.username = username;
            this.password = password;
            this.isGuest = isGuest;
        }
        private User() {
            this.username = null;
            this.password = null;
            this.isGuest  = true;
        }

        // 方便游客构造器
        public static User guestUser() {
            return new User();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof User user) {
                return user.username.equals(username) &&
                        user.password.equals(password) &&
                        user.isGuest == isGuest;
            }
            return false;
        }

        public String getUsername() {
            return username;
        }
    }

    public boolean addUser(User user) {
        for (User value : users) {
            if (value.username.equals(user.username)) {
                System.out.println("用户已存在");
                return false;//此处要在controller里编辑，使界面里出现“用户名已被占用”
            }
        }
        users.add(user);
        save();
        System.out.println("用户添加成功");//这里做一个欢迎界面
        return true;
    }
    //这里没有设置密码？

    public boolean removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            save();//做一个用户删除成功的提示
            return true;
        }
        return false;//做一个用户名不存在的提示
    }

    public boolean has(User user) {
        for (User value : users) {
            if (value.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(file)));
            out.writeObject(users);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println(username+"登录成功");
                return user; // 返回登录的用户对象
            }
        }
        System.out.println("用户名或密码错误");
        return null; // 登录失败返回 null
    }
    public User loginAsGuest() {
        System.out.println("游客模式：登录成功");
        return User.guestUser();
    }
}

