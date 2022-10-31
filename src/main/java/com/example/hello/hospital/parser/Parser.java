package com.example.hello.hospital.parser;

public interface Parser<T> {
    T parse(String str);
}
