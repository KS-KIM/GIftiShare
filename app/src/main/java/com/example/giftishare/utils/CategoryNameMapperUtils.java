package com.example.giftishare.utils;

import java.util.HashMap;
import java.util.Map;

public final class CategoryNameMapperUtils {

    private static Map<String, String> map = new HashMap<String, String>() {
        {
            put("음료", "caffee");
            put("배달음식", "food");
            put("음식점", "restaurant");
            put("편의점", "mart");
            put("뷰티", "beauty");
            put("여행", "trip");
            put("상품권", "gift");
            put("책", "book");
            put("모바일 데이터", "mobile");
            put("영화", "movie");
            put("리빙/가전", "living");
            put("교육", "education");
        }
    };

    public static String toEngName(String korName) {
        return map.get(korName);
    }
}
