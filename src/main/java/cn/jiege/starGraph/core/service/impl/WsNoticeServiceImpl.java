package cn.jiege.starGraph.core.service.impl;

import cn.jiege.starGraph.core.service.WsNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WsNoticeServiceImpl implements WsNoticeService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public static final String COMFYUI_QUEUE_TOPIC = "/topic/message";

    @Override
    public void sendToUser(String wsClientId, String message) {
        simpMessagingTemplate.convertAndSendToUser(
                wsClientId,
                COMFYUI_QUEUE_TOPIC,
                message
        );
    }

    @Override
    public void sendToAll(String message) {
        simpMessagingTemplate.convertAndSend(
                COMFYUI_QUEUE_TOPIC,
                message
        );
    }
}
