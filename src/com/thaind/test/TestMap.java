package com.thaind.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestMap {
    public static void main(String[] args) {
        Map map = new HashMap<>();
        map.put("key", "String");
        Iterator<Map.Entry<Object, Object>> ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<Object, Object> entry = ite.next();
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    private static <T> T get(Map<String, T> map, String key){
        return map.get(key);
    }
}
