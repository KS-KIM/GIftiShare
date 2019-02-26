package com.example.giftishare.view.buycoupons;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.giftishare.BR;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.databinding.ItemBuyCouponBinding;

import java.util.ArrayList;
import java.util.List;

public class BuyCouponsAdapter extends RecyclerView.Adapter<BuyCouponsAdapter.CouponViewHolder> {

    private final BuyCouponsViewModel mBuyCouponsViewModel;

    private List<Coupon> mCoupons;

    public BuyCouponsAdapter(BuyCouponsViewModel buyCouponsViewModel) {
        this.mCoupons = new ArrayList<>();
        this.mBuyCouponsViewModel = buyCouponsViewModel;
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBuyCouponBinding binding = ItemBuyCouponBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CouponViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return mCoupons != null ? mCoupons.size() : 0;
    }

    public class CouponViewHolder extends RecyclerView.ViewHolder {
        ItemBuyCouponBinding binding;

        public CouponViewHolder(@NonNull ItemBuyCouponBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Coupon coupon) {
            binding.setVariable(BR.coupon, coupon);
        }
    }
}
