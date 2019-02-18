package com.example.giftishare.data.model;

public enum CouponsCategoryType {

    BEAUTY("뷰티", "beauty"),
    BOOK("책", "book"),
    CAFFEE("음료", "caffee"),
    DELIVERY_FOOD("배달음식", "delivery_food"),
    EDUCATION("교육", "education"),
    GIFT_COUPON("상품권", "gift_coupon"),
    LIVING("리빙", "living"),
    MART("편의점/마트", "mart"),
    MOBILE_DATA("모바일데이터", "mobile_data"),
    MOVIE("영화", "movie"),
    RESTAURANT("음식점", "restaurant"),
    TRIP("여행", "trip");

    final private String korName;

    final private String engName;

    private CouponsCategoryType(String korName, String engName) {
        this.korName = korName;
        this.engName = engName;
    }

    public String toKor() {
        return korName;
    }

    public String toEng() {
        return engName;
    }
}
