package es.asanchez.elastic.query.service.config;

import es.asanchez.app.config.ElasticQueryWebClientConfigData;
import es.asanchez.app.config.UserConfigData;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final ElasticQueryWebClientConfigData.WebClient configData;
    private final UserConfigData userConfigData;

    @LoadBalanced
    @Bean("webClientBuilder")
    WebClient.Builder webClientBuilder(){
        return WebClient.builder()
                .filter(ExchangeFilterFunctions.basicAuthentication(userConfigData.getUsername(), userConfigData.getPwd()))
                .baseUrl(configData.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE,configData.getContentType())
                .defaultHeader(HttpHeaders.ACCEPT,configData.getAcceptType())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create(getTcpClient())))
                ;
    }

    private ConnectionProvider getTcpClient() {
//        return TcpClient.create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,configData.getConnectTimeout())
//                .doOnConnected(connection -> {
//                    connection.addHandlerLast(
//                            new ReadTimeoutHandler(configData.getReadTimeout(), TimeUnit.MILLISECONDS)
//                    );
//                    connection.addHandlerLast(
//                            new WriteTimeoutHandler(configData.getWriteTimeout(), TimeUnit.MILLISECONDS)
//                    );
//                })
//                ;
        return null;
    }

}
