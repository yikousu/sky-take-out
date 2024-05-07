package com.sky.task;

import java.util.concurrent.ConcurrentHashMap;

public class HashMapConcurrencyExample {
    public static  void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a",111);
        map.get("a");
        map.remove("a");
    }
}
