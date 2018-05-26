package com.example.demo.repository;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class UserRepositoryTest {

    private final static AtomicInteger idGenerator = new AtomicInteger();
    @Test
    public void testAutoInc()
    {
        for (int i = 0; i < 100; i++) {
            Integer id = idGenerator.incrementAndGet();
            System.out.println(id);
        }
    }

}