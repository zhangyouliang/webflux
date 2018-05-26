### demo


构建命令

    mvn -Dmaven.test.skip -U clean package
    or
    ./mvnw clean package
    or
    mvn clean compile package
    

运行
    
     java -jar web/target/web-0.0.1-SNAPSHOT.jar
     or
     java -jar web/target/web-0.0.1-SNAPSHOT.war
     
     
### Spring 5 的 WebFlux 开发反应式 Web 应用

参考地址:

[Spring 5 的 WebFlux 开发反应式 Web 应用](https://blog.csdn.net/moonpure/article/details/78400344),
[spring5.0 函数式web框架 webflux](https://blog.csdn.net/qq_34438958/article/details/78539234),
[反应式编程（Reactive Programming）](https://blog.csdn.net/simple_chao/article/details/73648238)


实践类: com.example.demo.FluxTest



### 参考

项目起始缘由: [视频](https://www.imooc.com/learn/933)