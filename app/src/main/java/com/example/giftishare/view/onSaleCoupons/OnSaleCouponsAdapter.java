package com.example.giftishare.view.onSaleCoupons;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.databinding.ItemOnSaleCouponBinding;
import com.example.giftishare.view.buycoupon.BuyCouponActivity;

import java.util.ArrayList;
import java.util.List;

public class OnSaleCouponsAdapter extends RecyclerView.Adapter<OnSaleCouponsAdapter.CouponViewHolder> {

    public static final String INTENT_BUY_COUPON = "INTENT_BUY_COUPON";

    private final OnSaleCouponsViewModel mOnSaleCouponsViewModel;

    private List<Coupon> mCoupons;

    public OnSaleCouponsAdapter(OnSaleCouponsViewModel onSaleCouponsViewModel) {
        this.mCoupons = new ArrayList<>();
        this.mOnSaleCouponsViewModel = onSaleCouponsViewModel;
    }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOnSaleCouponBinding binding = ItemOnSaleCouponBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CouponViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
        Coupon coupon = mCoupons.get(position);
        holder.bind(coupon);
    }

    public void replaceItems(@NonNull List<Coupon> coupons) {
        setItems(coupons);
    }

    private void setItems(List<Coupon> coupons) {
        mCoupons = coupons;
        notifyDataSetChanged();
    }

    public void addItems(List<Coupon> coupons) {
        mCoupons.addAll(coupons);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCoupons != null ? mCoupons.size() : 0;
    }

    class CouponViewHolder extends RecyclerView.ViewHolder {

        ItemOnSaleCouponBinding binding;

        public CouponViewHolder(ItemOnSaleCouponBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            OnSaleCouponsActionsListener listener = (Coupon coupon) -> {
                Context context = binding.getRoot().getContext();
                Intent intent = new Intent(context, BuyCouponActivity.class);
                intent.putExtra(INTENT_BUY_COUPON, coupon);
                context.startActivity(intent);
            };
            binding.setListener(listener);
        }

        void bind(Coupon coupon) {
            binding.setCoupon(coupon);
        }
    }
}
