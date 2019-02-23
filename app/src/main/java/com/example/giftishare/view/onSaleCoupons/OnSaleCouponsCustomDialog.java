package com.example.giftishare.view.onSaleCoupons;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.giftishare.R;
import com.example.giftishare.data.model.Coupon;

import java.util.List;
import java.util.Objects;

public class OnSaleCouponsCustomDialog extends Dialog {

    private String mtitle;
    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 외부화면흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);

        TextView textView = findViewById(R.id.textView);
        Button yes_bt = findViewById(R.id.yes_bt);
        Button no_bt = findViewById(R.id.no_bt);

         if (mLeftClickListener != null && mRightClickListener != null) {
            yes_bt.setOnClickListener(mLeftClickListener);
            no_bt.setOnClickListener(mRightClickListener);
            textView.setText(mtitle);
        }
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public OnSaleCouponsCustomDialog(View context, View.OnClickListener leftListener, View.OnClickListener rightListener , OnSaleCouponsViewModel viewModel, int pos) {
        super(context.getContext(), android.R.style.Theme_Translucent_NoTitleBar);
        final String[] temp = {""};
        viewModel.getCoupons().observe((LifecycleOwner) context.getContext(), new Observer<List<Coupon>>() {
            @Override
            public void onChanged(@Nullable List<Coupon> coupons) {
                Log.d("aaa",coupons.get(pos).mCompany);
                Log.d("aaa",coupons.get(pos).mBarcode);
                Log.d("aaa",coupons.get(pos).mName);
                Log.d("aaa",coupons.get(pos).mPrice);
//                for(Coupon cou : coupons){
//                    Log.d("aaa", cou.mCompany);
//                    Log.d("aaa", cou.mBarcode);
//                    Log.d("aaa", cou.mName);
//                    Log.d("aaa", cou.mPrice);
//                }
                temp[0] = coupons.get(pos).mCompany + "\n" + coupons.get(pos).mBarcode;
            }
        });
        this.mtitle = temp[0];
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;

    }
}
