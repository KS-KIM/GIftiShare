package com.example.giftishare.view.onsalecoupons;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.InverseBindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.giftishare.data.model.Coupon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Contains{@link BindingAdapter}s for the {@link Coupon} list.
 */
public class OnSaleCouponsListBindings {

    private static final String TAG = OnSaleCouponsListBindings.class.getSimpleName();

    @BindingAdapter("bind:item")
    public static void bindItems(RecyclerView recyclerView, List<Coupon> items) {
        OnSaleCouponsAdapter adapter = (OnSaleCouponsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceItems(items);
        }
    }

    @BindingConversion
    public static String longToStr(Long value) {
        String view = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            view = dateFormat.format(value);
        } catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return view;
    }

    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
    public static Long captureLongValue(EditText view) {
        long value = 0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date parseData = formatter.parse(view.getText().toString());
            value = parseData.getTime();
        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }
}
