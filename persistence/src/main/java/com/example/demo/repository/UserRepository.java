package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link User} {@link Repository}
 **/
@Repository
public class UserRepository {

    private final static AtomicInteger idGenerator = new AtomicInteger();
    /**
     * 内存型号的
     */
    private ConcurrentMap<Integer,User> repository = new ConcurrentHashMap<>();
    /**
     * 保存用户对象
     * @param user {@link User} 对象
     * @return 如果保存成功返回 <code>true</code>
     *  否则返回<code>false</code>
     */
    public boolean save(User user)
    {
        Integer id = idGenerator.incrementAndGet();
        user.setId(id);
        return  repository.put(id,user) == null;
    }
    public Collection<User> findAll()
    {
        return  repository.values();
    }

}
