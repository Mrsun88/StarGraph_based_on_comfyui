package cn.jiege.starGraph.core.interceptor;

import cn.hutool.json.JSONUtil;
import cn.jiege.starGraph.core.dto.common.Result;
import cn.jiege.starGraph.core.dto.common.ResultCode;
import cn.jiege.starGraph.core.pojo.User;
import cn.jiege.starGraph.core.utils.JWTUtils;
import cn.jiege.starGraph.core.utils.UserUtils;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 从请求头中获取用户信息
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            writeErrMessage(response);
            return false;
        }
        if(!JWTUtils.verify(token)) {
            writeErrMessage(response);
            return false;
        }
        User user = JWTUtils.getUser(token);
        if(user == null) {
            writeErrMessage(response);
            return false;
        }
        // 将用户信息存入ThreadLocal
        UserUtils.setUser(user);
        return true;
    }

    private void writeErrMessage(HttpServletResponse response) {
        // 设置状态码/响应头
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        // 设置响应体
        try (PrintWriter writer = response.getWriter()) {
            Result<Object> result = Result.failed(ResultCode.ACCESS_UNAUTHORIZED);
            writer.print(JSONUtil.toJsonStr(result));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserUtils.removeUser();
    }
}
