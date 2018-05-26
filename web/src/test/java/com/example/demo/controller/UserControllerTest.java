package com.example.demo.controller;

import com.example.demo.domain.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

public class UserControllerTest {

    private final WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

    // todo http://localhost:8080/user
    @Test
    public void testCreateUser()
    {
        final User user = new User();
        user.setId(1);
        user.setName("test");
        WebTestClient.BodyContentSpec bodyContentSpec = client.post().uri("/user/createOne")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
        // print
        byte[] bytes = bodyContentSpec.returnResult().getResponseBodyContent();
        System.out.println(new String(bytes));

        // assert
        bodyContentSpec.jsonPath("name").isEqualTo("test");

    }

}