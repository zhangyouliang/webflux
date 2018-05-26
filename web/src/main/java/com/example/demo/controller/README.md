#### POST 参数请求


Spring 获取参数 [参考](https://blog.csdn.net/ye1992/article/details/49998511)

    # 例如将数据插入到 User 模型当中
    curl -s -X POST http://127.0.0.1:8080/user --data '[{"id":1,"name":"abc"},{"id":2,"name":"abcdef"},{"id":3,"name":"f"},{"id":4,"name":"abdddcdef"},{"id":5,"name":"abcdefffffff"}]' -H 'Content-Type:application/json;charset=utf-8'  | python -m json.tool

    
