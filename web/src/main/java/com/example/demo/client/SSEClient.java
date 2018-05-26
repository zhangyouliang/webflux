package com.example.demo.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * @author youliangzhang
 * @date 2018/5/26  下午6:27
 **/
public class SSEClient {
    public static void main(final String[] args) {

        final WebClient client = WebClient.create();

        client.get()

                .uri("http://localhost:8080/sse/randomNumbers")

                .accept(MediaType.TEXT_EVENT_STREAM)

                .exchange()

                .flatMapMany(response -> response.body(BodyExtractors.toFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {

                })))

                .filter(sse -> Objects.nonNull(sse.data()))

                .map(ServerSentEvent::data)

                .buffer(10)

                .doOnNext(System.out::println)

                .blockFirst();

    }
}
