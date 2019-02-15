package com.example.giftishare.utils;

import java.util.HashMap;
import java.util.Map;

public class CategoryNameMapperUtils {
    Map<String,String> map = new HashMap<>();

    public CategoryNameMapperUtils(){
        map.put("음료","caffee");
        map.put("배달음식","food");
        map.put("음식점","restaurant");
        map.put("편의점","mart");
        map.put("뷰티","beauty");
        map.put("여행","trip");
        map.put("상품권","gift");
        map.put("책","book");
        map.put("모바일 데이터","mobile");
        map.put("영화","movie");
        map.put("리빙/가전","living");
        map.put("교육","education");
    }

    public String toEngName(String korName) {
        return map.get(korName).toString();
    }
}
