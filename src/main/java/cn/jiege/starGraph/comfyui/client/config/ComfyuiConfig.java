package cn.jiege.starGraph.comfyui.client.config;

import cn.jiege.starGraph.comfyui.client.api.ComfyuiApi;
import cn.jiege.starGraph.comfyui.client.config.proprties.ComfyuiConfigProperties;
import cn.jiege.starGraph.comfyui.client.handler.ComfyuiMessageHandler;
import lombok.RequiredArgsConstructor;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class ComfyuiConfig {

    final private ComfyuiConfigProperties comfyuiProperties;
    @Bean
    public ComfyuiApi comfyuiApi() {
        // okHttp 日志拦截器
        Interceptor okHttpInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        ConnectionPool connectionPool = new ConnectionPool(
                comfyuiProperties.getConnectionPool().getMaxIdleConnections(),
                comfyuiProperties.getConnectionPool().getKeepAliveDuration(),
                java.util.concurrent.TimeUnit.MINUTES
        );
        // 配置 OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(okHttpInterceptor)
                .connectionPool(connectionPool)
                .connectTimeout(Duration.ofSeconds(comfyuiProperties.getConnectionTimeoutSeconds()))
                .readTimeout(Duration.ofSeconds(comfyuiProperties.getReadTimeoutSeconds()))
                .retryOnConnectionFailure(comfyuiProperties.getRetryOnConnectionFailure())
                .build();
        // 创建 Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(comfyuiProperties.getBaseUrlAndPort())
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(ComfyuiApi.class);
    }

    /**
     * 消息处理器
     * @return
     */
    @Bean
    public ComfyuiMessageHandler comfyuiMessageHandler() {
        return new ComfyuiMessageHandler();
    }

    @Bean
    public WebSocketConnectionManager webSocketConnectionManager(ComfyuiMessageHandler comfyuiMessageHandler) {
        // 定义websocket客户端
        WebSocketClient client = new StandardWebSocketClient();
        String url = comfyuiProperties.getBaseUrlAndPort().replace("http", "ws") + "/ws?clientId=star-graph";
        // 定义websocket链接管理器
        WebSocketConnectionManager manager  = new WebSocketConnectionManager(client, comfyuiMessageHandler, url);
        manager.start();
        return manager;
    }
}
