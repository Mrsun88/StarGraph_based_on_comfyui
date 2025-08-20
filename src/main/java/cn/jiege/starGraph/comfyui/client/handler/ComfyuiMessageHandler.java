package cn.jiege.starGraph.comfyui.client.handler;

import cn.jiege.starGraph.comfyui.client.dto.MessageBase;
import cn.jiege.starGraph.core.service.ComfyuiMessageService;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class ComfyuiMessageHandler extends AbstractWebSocketHandler {

    @Autowired
    private ComfyuiMessageService comfyuiMessageService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("收到消息: " + payload);
        MessageBase messageBase = JSON.parseObject(payload, MessageBase.class);
        comfyuiMessageService.handleMessage(messageBase);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("链接成功: " + session.getId());

    }
}
