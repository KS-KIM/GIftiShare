package com.example.giftishare.view.buysellcoupons;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.helper.CategoryNameMapper;

import java.util.List;

/**
 * Contains{@link BindingAdapter}s for the {@link Coupon} list.
 */
public class BuySellCouponsListBindings {

    @BindingAdapter("bind:buyitem")
    public static void bindItems(RecyclerView recyclerView, List<Coupon> items) {
        BuySellCouponsAdapter adapter = (BuySellCouponsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceItems(items);
        }
    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, String categoryName) {
        imageView.setImageResource(CategoryNameMapper.toImageDrawable(categoryName));
    }
}
