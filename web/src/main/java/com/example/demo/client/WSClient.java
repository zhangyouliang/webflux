package com.example.demo.client;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.Duration;

/**
 * @author youliangzhang
 * @date 2018/5/26  下午6:28
 **/
public class WSClient {
    public static void main(final String[] args) {

        final WebSocketClient client = new ReactorNettyWebSocketClient();

        client.execute(URI.create("ws://localhost:8080/echo"), session ->

                session.send(Flux.just(session.textMessage("Hello")))

                        .thenMany(session.receive().take(1).map(WebSocketMessage::getPayloadAsText))

                        .doOnNext(System.out::println)

                        .then())

                .block(Duration.ofMillis(5000));

    }
}
