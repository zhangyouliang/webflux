package com.example.demo.exceptions;

/**
 * @author youliangzhang
 * @date 2018/5/26  下午4:22
 **/
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
        super("resource not found");
    }
}
