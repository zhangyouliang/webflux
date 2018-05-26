## Dependency Scope

- compile (default)

This is the default scope. used if none is specified. 

编译-测试-运行


- provided

打包的时候不需要打进去,例如 servlet 之类的包,Tomcat之类的自带

- runtime

例如 JDBC 只在运行的时候需要

- test

- system

- import (only available in maven 2.0.9 or later)

This scope is only supported on a dependency of type `pom` in the `<dependencyManagement>` section.

 
### Others

- exclusions

排除相关包

- optional

假如你的Project A的某个依赖D添加了<optional>true</optional>，当别人通过pom依赖Project A的时候，D不会被传递依赖进来
    
## Repositories
    
    # 添加第三方jar,添加到本地仓库当中
    mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.3.0 -Dpackaging=jar -Dfile=/driver/ojdbc14.jar
