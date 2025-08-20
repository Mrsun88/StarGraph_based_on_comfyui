package cn.jiege.starGraph.core.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.security.Principal;
import java.util.List;

public class WebSocketUserInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // 判断当前请求是建立连接的请求
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if(accessor!= null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 获取clientId
            List<String> clientIds = accessor.getNativeHeader("clientId");
            if(clientIds!=null&& !clientIds.isEmpty()) {
                // 缓存clientId和通道对应关系
                accessor.setUser(() -> clientIds.get(0));
            }
        }
        return message;
    }
}
