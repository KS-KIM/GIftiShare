package com.example.giftishare.view.onSaleCoupons;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.giftishare.BR;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.databinding.ItemOnSaleCouponBinding;

import java.util.ArrayList;
import java.util.List;

public class OnSaleCouponsAdapter extends RecyclerView.Adapter<OnSaleCouponsAdapter.CouponViewHolder> {

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

    //    public void addItems(List<Coupon> coupons) {
    //        mCoupons.add(coupons);
    //    }

    @Override
    public int getItemCount() {
        return mCoupons != null ? mCoupons.size() : 0;
    }

    class CouponViewHolder extends RecyclerView.ViewHolder {
        ItemOnSaleCouponBinding binding;

        CouponViewHolder(ItemOnSaleCouponBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Coupon coupon) {
            binding.setVariable(BR.coupon, coupon);
        }
    }

}
