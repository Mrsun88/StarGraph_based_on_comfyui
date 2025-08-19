package cn.jiege.starGraph.core.utils;

import cn.jiege.starGraph.core.pojo.User;

public class UserUtils {

    static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static User getUser(){
        return userThreadLocal.get();
    }

    public static void setUser(User user){
        userThreadLocal.set(user);
    }

    public static void removeUser(){
        userThreadLocal.remove();
    }
}
