package com.example.demo.routes;

import com.example.demo.domain.User;
import com.example.demo.handlers.CalculatorHandler;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 *
 * @author youliangzhang
 * @date 2018/5/25  上午12:32
 **/
@Configuration
public class RouterFunctionConfiguration {

    /**
     * @todo http://localhost:8080/user/all
     * Servlet
     *  请求接口: ServletRequest 或者 HttpServlet
     *  响应接口: ServletResponse 或者 HttpResponse
     *
     *  Spring 5.0 重新定义了服务请求和响应接口
     *  请求接口: ServerRequest
     *  响应接口: ServerResponse
     *  即可支持 Servlet 规范,也可以支持自定义,比如 Netty (Web Server )
     *
     *  Flux 是 0 - N 个对象集合
     *  Mono 是 0 -1 个对象集合
     *  Reactive 中 Flux 或者 Mono 它是异步处理 (非阻塞)
     *  集合对象基本上是同步处理 (阻塞)
     */
    @Bean
    @Autowired
    public RouterFunction<ServerResponse>  presonAll(UserRepository repository){

       return RouterFunctions.route(RequestPredicates.GET("/user/all"),serverRequest -> {
            Collection<User> users = repository.findAll();
            Flux<User> userFlux = Flux.fromIterable(users);
            return ServerResponse.ok().body(userFlux,User.class);
        });

    }

    @Bean
    /**
     *  todo http://127.0.0.1:8080/calculator?operator=add&v1=10&v2=11
     * 函数式编程模型
     */
    @Autowired
    public RouterFunction<ServerResponse>routerFunction(final CalculatorHandler calculatorHandler) {

        return RouterFunctions.route(RequestPredicates.path("/calculator"), request ->

                request.queryParam("operator").map(operator ->

                        Mono.justOrEmpty(ReflectionUtils.findMethod(CalculatorHandler.class, operator, ServerRequest.class))

                                .flatMap(method -> (Mono<ServerResponse>) ReflectionUtils.invokeMethod(method, calculatorHandler, request))

                                .switchIfEmpty(ServerResponse.badRequest().build())

                                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()))

                        .orElse(ServerResponse.badRequest().build()));

    }


}
