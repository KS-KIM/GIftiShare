package com.example.giftishare.view.addcoupon;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.databinding.ActivityAddCouponBinding;

import android.databinding.DataBindingUtil;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCouponActivity extends AppCompatActivity {

    private ActivityAddCouponBinding mBinding;

    private AddCouponViewModel mAddCouponViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAddCouponViewModel = obtainViewModel(this);

        mBinding = DataBindingUtil.setContentView(AddCouponActivity.this, R.layout.activity_add_coupon);
        mBinding.setLifecycleOwner(this);
        mBinding.setAddCouponViewModel(mAddCouponViewModel);
        mBinding.etCouponName.requestFocus();

        setupToolBar();
        setupCategorySpinner();
        setupDateButton();
        setupCreateCouponButton();
    }

    private AddCouponViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        AddCouponViewModel addCouponViewModel = ViewModelProviders.of(this, factory).get(AddCouponViewModel.class);
        return addCouponViewModel;
    }

    @Override
    public void onBackPressed() {
        // @TODO 두 번 눌러 취소하기 구현
        finish();
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBinding.btnBack.setOnClickListener((View v) -> onBackPressed());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupCategorySpinner() {
        final ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.category_name));
        mBinding.couponCategory.setAdapter(spinAdapter);
        mBinding.couponCategory.setKeyListener(null);
        mBinding.couponCategory.setOnTouchListener((View v, MotionEvent event) -> {
            ((AutoCompleteTextView) v).showDropDown();
                return false;
            }
        );
    }

    private void setupDateButton() {
        Calendar calendar = Calendar.getInstance();
        mBinding.btnDeadline.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this,
                    (datePicker, year, month, date) -> {
                        mBinding.btnDeadline.setText(String.format(Locale.getDefault(),
                                "  %d년 %d월 %d일", year, month + 1, date));
                        mAddCouponViewModel.setDeadline(year, month, date);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE)
            );
            dialog.getDatePicker().setMinDate(new Date().getTime() - 24);
            dialog.show();
        });
    }

    // @TODO refactor code
    private void setupCreateCouponButton() {
        mBinding.btnCreateWallet.setOnClickListener((View v) -> {
            clearError();
            if (mAddCouponViewModel.isEmptyField(mBinding.etCouponName.getText().toString())) {
                mBinding.etCouponName.requestFocus();
                mBinding.etCouponName.setError("쿠폰명을 입력해주세요");
            } else if (mAddCouponViewModel.isEmptyField(mBinding.couponCategory.getText().toString())) {
                mBinding.couponCategory.requestFocus();
                mBinding.couponCategory.setError("쿠폰 타입을 선택해주세요");
            } else if (mAddCouponViewModel.isEmptyField(mBinding.etCompanyName.getText().toString())) {
                mBinding.etCompanyName.requestFocus();
                mBinding.etCompanyName.setError("상호명을 입력해주세요");
            } else if (mAddCouponViewModel.isEmptyField(mBinding.etPrice.getText().toString())) {
                mBinding.etPrice.requestFocus();
                mBinding.etPrice.setError("판매하실 가격을 입력해주세요");
            } else if (mAddCouponViewModel.isEmptyField(mBinding.etBarcode.getText().toString())) {
                mBinding.etBarcode.requestFocus();
                mBinding.etBarcode.setError("바코드 번호를 입력해주세요");
            } else if (mBinding.btnDeadline.getText().toString().equals("유효기간")) {
                mBinding.btnDeadline.requestFocus();
                mBinding.btnDeadline.setError("유효기간을 입력해주세요");
                showMessage("유효기간을 입력해주세요");
            } else {
                mAddCouponViewModel.createCoupon();
                finish();
            }
        });
    }

    private void clearError() {
        mBinding.etCouponName.setError(null);
        mBinding.couponCategory.setError(null);
        mBinding.etCompanyName.setError(null);
        mBinding.etPrice.setError(null);
        mBinding.etBarcode.setError(null);
        mBinding.btnDeadline.setError(null);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}