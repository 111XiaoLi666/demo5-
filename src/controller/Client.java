// Client.java
package controller;

import javafx.stage.Stage;
import model.UserLib;
import model.UserLib.User;   // 如果 User 是静态内部类
import view.LoginFrame;



public class Client {
    private final UserLib lib;
    private  LoginFrame login;

    // 构造器里，把 Main new 的 lib 传进来
    public Client(UserLib lib) {
        this.lib = lib;
    }



    // 供 LoginFrame 调用的回调：校验用户名/密码
    public boolean isMatch(String u,String p) {
        User user=new User(u,p,false);
        return lib.has(user);
    }

    public User login(String u,String p)
    {
        return lib.login(u,p);
    }

    // 供 LoginFrame 的“注册”按钮调用
    public void register(String u,String p) {
        // 再次从界面上拿最新输入

        // new 一个 User 并放到 lib 里
        User user = new User(u, p, false);
        lib.addUser(user);

        // 这里你可以跳转到主界面、提示注册成功之类的
    }
}