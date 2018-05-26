package com.example.demo;

import org.junit.Ignore;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author youliangzhang
 * @date 2018/5/26  下午8:38
 **/

public class FluxTest {
    @Test
    public void testFluxDemo1() {
        System.out.println("===>");
        Flux.just("hello", "World").subscribe(System.out::println);
        System.out.println("===>");
        Flux.fromArray(new Integer[]{1, 2, 3, 4}).subscribe(System.out::println);
        System.out.println("===>");
        Flux.empty().subscribe(System.out::println);
        System.out.println("===>");
        Flux.range(1, 10).subscribe(System.out::println);
        System.out.println("===>");
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);


        //第一个序列的生成逻辑中通过 next()方法产生一个简单的值，然后通过 complete()方法来结束该序列。如果不调用 complete()方法，所产生的是一个无限序列。第二个序列的生成逻辑中的状态对象是一个 ArrayList 对象。实际产生的值是一个随机数。产生的随机数被添加到 ArrayList 中。当产生了 10 个数时，通过 complete()方法来结束序列。

        System.out.println("************** generate *********************");
        // Generate() 生成 Flux 序列
        System.out.println("===>");
        Flux.generate(sink -> {
            sink.next("Hello");
            //sink.next("Hello11"); only one
            sink.complete();
        }).subscribe(System.out::println);

        System.out.println("===>");
        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);
        System.out.println("************** create *********************");
        // create 方法
        // create 方法与 generate 方法不同之处在于所使用的FluxSink 对象,FluxSink 支持同步和异步的消息产生，并且可以在一次调用中产生多个元素。在代码清单 3 中，在一次调用中就产生了全部的 10 个元素
        Flux.create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
        }).subscribe(System.out::println);

        System.out.println("************** mono *********************");
        Mono.fromSupplier(() -> "hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        //Mono.justOrEmpty(null).subscribe(System.out::println); // empty
        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);


        System.out.println("************** 操作符号 *********************");
        // buffer 相关操作符的使用示例
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        System.out.println("===>");
        Flux.range(1, 100).take(2).toStream().forEach(System.out::println);
        // bufferUntil 会一直收集直到 Predicate 返回为 true
        // bufferWhile 则只有当 Predicate 返回 true 时才会收集
        System.out.println("===>");
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        System.out.println("===>");
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);

        System.out.println("===> filter");
        // 对流中包含的元素进行过滤,只留下满足 Predicate 指定条件的元素
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);
        System.out.println("===> window");
        Flux.range(1, 100).window(20).subscribe(System.out::println);

        System.out.println("************** zipWith *********************");
        // 两个流中包含的元素分别是 a，b 和 c，d。第一个 zipWith 操作符没有使用合并函数，因此结果流中的元素类型为 Tuple2；第二个 zipWith 操作通过合并函数把元素类型变为 String
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"),(s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);

        System.out.println("************** take *********************");
        //take 系列操作符用来从当前流中提取元素。提取的方式可以有很多种。
        Flux.range(1, 1000).take(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);

        System.out.println("************** reduce,reduceWith *********************");
        //reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列
        Flux.range(1,100).reduce((x,y)->x+y).subscribe(System.out::println);
        // reduceWith 初始化为 100
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);

        System.out.println("************** merge 和 mergeSequential *********************");
        //merge 和 mergeSequential 操作符用来把多个流合并成一个 Flux 序列。不同之处在于 merge 按照所有流中元素的实际产生顺序来合并，而 mergeSequential 则按照所有流被订阅的顺序，以流为单位进行合并。

        Flux.merge(Flux.interval(Duration.ofMillis(1)).take(5),Flux.interval(Duration.ofMillis(2)).take(3))
                .toStream()
                .forEach(System.out::println);

        System.out.println("************** flatMap 和 flatMapSequential *********************");
        //flatMap 和 flatMapSequential 操作符把流中的每个元素转换成一个流，再把所有流中的元素进行合并。flatMapSequential 和 flatMap 之间的区别与 mergeSequential 和 merge 之间的区别是一样的。
        Flux.just(5,20)
                .flatMap(x->Flux.interval(Duration.ofMillis(x*10),Duration.ofMillis(100)).take(x))
                .toStream()
                .forEach(System.out::println);
        System.out.println("************** 消息处理 *********************");
        // 出现错误时根据异常类型来选择流
        Flux.just(1,2)
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorResume(e->{
                    if (e instanceof IllegalStateException) {
                        return Mono.just(0);
                    } else if (e instanceof IllegalArgumentException) {
                        return Mono.just(-1);
                    }
                    return  Mono.empty();
                })
                .retry(1) // 重试
                .subscribe(System.out::println);

        System.out.println("************** 日志记录 *********************");
        Flux.range(1,2).log("Range").subscribe(System.out::println);



        // 测试 Jdk8新特性
        //List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        //numbers.forEach(System.out::println);
        //numbers.forEach(x-> System.out.println(x));
    }

}
