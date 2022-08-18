package com.wtreasur2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public List<Map<String, Object>> returnMap() {

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "joy");
        map1.put("age", "34");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "lucky");
        map2.put("age", "35");

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "우탱");
        map3.put("age", "43");

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        return list;
    }

}