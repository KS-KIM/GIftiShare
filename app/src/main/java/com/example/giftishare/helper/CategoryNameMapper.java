package com.example.giftishare.helper;

import com.example.giftishare.R;

import java.util.HashMap;
import java.util.Map;

public final class CategoryNameMapper {

    private static Map<String, String> map = new HashMap<String, String>() {
        {
            put("음료", "caffee");
            put("배달음식", "delivery_food");
            put("음식점", "restaurant");
            put("편의점", "mart");
            put("뷰티", "beauty");
            put("여행", "trip");
            put("상품권", "gift_coupon");
            put("책", "book");
            put("모바일 데이터", "mobile_data");
            put("영화", "movie");
            put("리빙/가전", "living");
            put("교육", "education");
        }
    };

    private static Map<String, Integer> resourceMap = new HashMap<String, Integer>() {
        {
            put("caffee", R.drawable.ic_category_caffee);
            put("delivery_food", R.drawable.ic_category_delivery_foods);
            put("restaurant", R.drawable.ic_category_restaurant);
            put("mart", R.drawable.ic_category_mart);
            put("beauty", R.drawable.ic_category_beauty);
            put("trip", R.drawable.ic_category_trip);
            put("gift_coupon", R.drawable.ic_category_gift_coupon);
            put("book", R.drawable.ic_category_book);
            put("mobile_data", R.drawable.ic_category_mobile_data);
            put("movie", R.drawable.ic_category_movie);
            put("living", R.drawable.ic_category_living);
            put("education", R.drawable.ic_category_education);
        }
    };

    public static String toEngName(String korName) {
        return map.get(korName);
    }

    public static Integer toImageDrawable(String EngName) {
        return resourceMap.get(EngName);
    }
}
