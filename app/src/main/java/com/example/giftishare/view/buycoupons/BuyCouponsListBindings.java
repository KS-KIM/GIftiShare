package com.example.giftishare.view.buycoupons;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.giftishare.data.model.Coupon;

import java.util.List;

/**
 * Contains{@link BindingAdapter}s for the {@link Coupon} list.
 */
public class BuyCouponsListBindings {

    @BindingAdapter("bind:buyitem")
    public static void bindItems(RecyclerView recyclerView, List<Coupon> items) {
        BuyCouponsAdapter adapter = (BuyCouponsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceItems(items);
        }
    }
}
