package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author youliangzhang
 * @date 2018/5/26  下午4:18
 **/
@Service
public class UserService {
    private final Map<Integer, User> data = new ConcurrentHashMap<>();

    public Flux<User> list() {
        return Flux.fromIterable(this.data.values());
    }

    public Flux<User> getById(final Flux<Integer> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
    }

    public Mono<User> getById(final Integer id) {

        return Mono.justOrEmpty(this.data.get(id))

                .switchIfEmpty(Mono.error(new ResourceNotFoundException()));

    }

    public Flux<User> createOrUpdate(final Flux<User> users) {

        return users.doOnNext(user -> this.data.put(user.getId(), user));

    }


    public Mono<User> createOrUpdate(final User user) {

        this.data.put(user.getId(), user);

        return Mono.just(user);

    }


    public Mono<User> delete(final Integer id) {

        return Mono.justOrEmpty(this.data.remove(id));

    }

}
