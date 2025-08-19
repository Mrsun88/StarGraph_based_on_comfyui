package cn.jiege.starGraph.ollama.client.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "star-graph.ollama.connection")
public class OllamaConfigProperties {
    /**
     * Ollama 服务的基础地址
     */
    private String baseUrl = "http://localhost:11434";

    /**
     * 连接超时时间（秒）
     */
    private int connectTimeout = 30;

    /**
     * 读取超时时间（秒）
     */
    private  int readTimeout = 30;

    /**
     * 是否在连接失败时重试
     */
    private boolean retryOnConnectionFailure = true;



}