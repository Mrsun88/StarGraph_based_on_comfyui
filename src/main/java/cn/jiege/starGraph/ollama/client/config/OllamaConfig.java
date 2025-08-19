package cn.jiege.starGraph.ollama.client.config;

import cn.jiege.starGraph.ollama.client.api.OllamaApi;
import cn.jiege.starGraph.ollama.client.config.property.OllamaConfigProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Configuration
public class OllamaConfig {

    private final OllamaConfigProperties properties;

    @Bean
    public OllamaApi ollamaApi(ObjectMapper objectMapper) throws IOException {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(properties.isRetryOnConnectionFailure())
                .connectTimeout(properties.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(properties.getReadTimeout(), TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.format(properties.getBaseUrl()))
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        OllamaApi ollamaApi = retrofit.create(OllamaApi.class);
        return ollamaApi;
    }

}
