package cn.jiege.starGraph.core.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.jwt.JWT;
import cn.jiege.starGraph.core.pojo.User;

public class JWTUtils {

    static final String key = "jiege-starGraph-2025";
    public static String createJWT(Long id,String name) {
        return JWT.create()
                .setPayload("uid", id)
                .setPayload("uname", name)
                .setExpiresAt(DateTime.now().offsetNew(DateField.YEAR,1))
                .setKey(key.getBytes())
                .sign();
    }

    /**
     * 校验token
     */
    public static boolean verify(String token) {
        try {
            JWT jwt = JWT.of(token);
            jwt.setKey(key.getBytes());
            return jwt.verify();
        } catch (Exception e) {
            return false;
        }
    }

    public static User getUser(String token) {
        try {
            JWT jwt = JWT.of(token).setKey(key.getBytes());
            User user = new User();
            user.setId(jwt.getPayload("uid") == null ? null : NumberUtil.parseLong(jwt.getPayload("uid").toString()));
            user.setUsername(jwt.getPayload("uname").toString());
            return user;
        }catch (Exception e){
            return null;
        }
    }
}