package com.example.giftishare.view.sellcoupons;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.giftishare.data.model.Coupon;

import java.util.List;

/**
 * Contains{@link BindingAdapter}s for the {@link Coupon} list.
 */
public class SellCouponsListBindings {
    @BindingAdapter("bind:sellitem")
    public static void bindItems(RecyclerView recyclerView, List<Coupon> items) {
        SellCouponsAdapter adapter = (SellCouponsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceItems(items);
        }
    }
}
