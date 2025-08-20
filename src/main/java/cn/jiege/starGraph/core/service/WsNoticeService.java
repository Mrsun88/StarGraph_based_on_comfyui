package cn.jiege.starGraph.core.service;

public interface WsNoticeService {
    /**
     * 发送消息给指定用户
     * @param wsClientId
     * @param message
     */
    void sendToUser(String wsClientId, String message);

    /**
     * 发送消息给所有用户
     * @param message
     */
    void sendToAll(String message);
}
