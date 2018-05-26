package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author youliangzhang
 * @date 2018/5/25  上午12:49
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository repository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    /**
     * @param name
     * @return
     * @todo http://localhost:8080/user/save?name=1121
     */
    @RequestMapping("/save")
    public User save(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        if (repository.save(user)) {
            System.out.println("用户保存对象成功\n" + user);
        }
        return user;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void notFound() {
    }

    /**
     * todo http://127.0.0.1:8080/user
     *
     * @return
     */
    @GetMapping("")
    public Flux<User> list() {
        return this.userService.list();
    }

    /**
     * todo http://127.0.0.1:8080/user/1
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable("id") final Integer id) {
        return this.userService.getById(id);
    }

    /**
     * curl -s -X POST http://127.0.0.1:8080/user --data '[{"id":1,"name":"abc"},{"id":2,"name":"abcdef"},{"id":3,"name":"f"},{"id":4,"name":"abdddcdef"},{"id":5,"name":"abcdefffffff"}]' -H 'Content-Type:application/json;charset=utf-8'  | python -m json.tool
     * todo http://127.0.0.1:8080/user
     * @param users
     * @return
     */
    @PostMapping("")
    public Flux<User> create(@RequestBody final Flux<User> users) {

        return this.userService.createOrUpdate(users);

    }
    @PostMapping("/createOne")
    public Mono<User> createOne(@RequestBody final User user) {

        return this.userService.createOrUpdate(user);
    }


    /**
     * todo http://127.0.0.1:8080/user/1
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Mono<User> update(@PathVariable("id") final Integer id, @RequestBody final User user) {

        Objects.requireNonNull(user);

        user.setId(id);

        return this.userService.createOrUpdate(user);

    }


    /**
     * todo http://127.0.0.1:8080/user/1
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<User> delete(@PathVariable("id") final Integer id) {

        return this.userService.delete(id);

    }

}
