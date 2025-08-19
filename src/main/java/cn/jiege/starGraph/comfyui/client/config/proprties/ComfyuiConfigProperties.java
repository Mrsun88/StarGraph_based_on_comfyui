package cn.jiege.starGraph.comfyui.client.config.proprties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

/**
 * ComfyUI 配置属性类
 * <p>
 * 该类用于获取application.yml中配置的 ComfyUI 的配置属性。
 * </p>
 */
@Data
@ConfigurationProperties("star-graph.comfyui")
public class ComfyuiConfigProperties {
    private String baseUrlAndPort = "http://localhost:8188";
    // 链接超时时间
    private Integer connectionTimeoutSeconds = 30;
    // 读取超时时间
    private Integer readTimeoutSeconds = 30;
    // 是否允许重连
    private Boolean retryOnConnectionFailure = true;
    private ConnectionPoolConfig connectionPool = new ConnectionPoolConfig();

    @Data
    public static class ConnectionPoolConfig {
        private int maxIdleConnections = 5;
        private long keepAliveDuration = 5; // in minutes
    }

}

