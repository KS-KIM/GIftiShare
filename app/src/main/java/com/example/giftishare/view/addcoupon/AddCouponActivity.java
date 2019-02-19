package com.example.giftishare.view.addcoupon;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.databinding.ActivityAddCouponBinding;

import android.databinding.DataBindingUtil;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

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

    private void setupCategorySpinner() {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            ListPopupWindow window = (ListPopupWindow) popup.get(mBinding.couponCategory);
            window.setHeight(1000);
        } catch (NoSuchFieldException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
        final ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.category_name, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.couponCategory.setAdapter(spinAdapter);
        mBinding.couponCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = spinAdapter.getItem(position).toString();
                mAddCouponViewModel.mCouponCategory.setValue(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupDateButton() {
        Calendar calendar = Calendar.getInstance();
        mBinding.btnDeadline.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this,
                    (datePicker, year, month, date) -> {
                        mBinding.btnDeadline.setText(String.format("  %d년 %d월 %d일", year, month + 1, date));
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

    private void setupCreateCouponButton() {
        mBinding.btnCreateWallet.setOnClickListener((View v) -> {
            if (mAddCouponViewModel.isEmptyField(mAddCouponViewModel.mCouponName.getValue())) {
                mBinding.etCouponName.requestFocus();
                mBinding.etCouponName.setError("쿠폰명을 입력해주세요");
            } else if (mAddCouponViewModel.isEmptyField(mAddCouponViewModel.mCompanyName.getValue())) {
                mBinding.etCompanyName.requestFocus();
                mBinding.etCompanyName.setError("상호명을 입력해주세요");
            } else if (mAddCouponViewModel.isEmptyField(mAddCouponViewModel.mPrice.getValue())) {
                mBinding.etPrice.requestFocus();
                mBinding.etPrice.setError("판매하실 가격을 입력해주세요");
            } else if (mAddCouponViewModel.isEmptyField(mAddCouponViewModel.mBarcode.getValue())) {
                mBinding.etBarcode.requestFocus();
                mBinding.etBarcode.setError("바코드 번호를 입력해주세요");
            } else if (mAddCouponViewModel.mDeadline.getValue() == null) {
                mBinding.btnDeadline.requestFocus();
                mBinding.btnDeadline.setError("유효기간을 입력해주세요");
            } else if (mAddCouponViewModel.isEmptyField(mAddCouponViewModel.mCouponCategory.getValue())) {
                TextView spinnerTextView = (TextView) mBinding.couponCategory.getSelectedView();
                spinnerTextView.setError("쿠폰 타입을 선택해주세요");
            } else {
                mAddCouponViewModel.createCoupon();
                finish();
            }
        });
    }
}