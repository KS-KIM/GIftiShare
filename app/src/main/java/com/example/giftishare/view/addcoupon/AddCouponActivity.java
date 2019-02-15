package com.example.giftishare.view.addcoupon;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.giftishare.Event;
import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.databinding.ActivityAddCouponBinding;
import com.example.giftishare.view.main.MainActivity;
import android.databinding.DataBindingUtil;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class AddCouponActivity extends AppCompatActivity {
    final Calendar cal = Calendar.getInstance();

    private ActivityAddCouponBinding mBinding;

    private AddCouponViewModel mAddCouponViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        mAddCouponViewModel = obtainViewModel(this);

        mBinding = DataBindingUtil.setContentView(AddCouponActivity.this, R.layout.activity_add_coupon);
        mBinding.setLifecycleOwner(this);
        mBinding.setAddCouponViewModel(mAddCouponViewModel);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            ListPopupWindow window = (ListPopupWindow)popup.get(mBinding.couponCategory);
            window.setHeight(550); //pixel
        } catch (Exception e) {
            e.printStackTrace();
        }

        //스피너 어댑터 설정
        final ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(this, R.array.category_name, android.R.layout.simple_spinner_item);
        spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.couponCategory.setAdapter(spin_adapter);

        //스피너 이벤트 발생
        mBinding.couponCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //각 항목 클릭시 포지션값을 토스트에 띄운다.
                //Toast.makeText(AddCouponActivity.this, spin_adapter.getItem(position), Toast.LENGTH_LONG).show();
                mAddCouponViewModel.setCouponCategory(spin_adapter.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mBinding.dateBtn.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(AddCouponActivity.this, (datePicker, year, month, date) -> {
                mBinding.dateBtn.setText(String.format("%d년 %d월 %d일", year, month + 1, date));
                mAddCouponViewModel.setDeadLine(new GregorianCalendar(year, month, date).getTime());
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

            //dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
            dialog.getDatePicker().setMinDate(new Date().getTime() - 24);
            dialog.show();
        });

        mBinding.backBtn.setOnClickListener(view -> onBackPressed());

        setCreateCouponButton();
    }

    private AddCouponViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        AddCouponViewModel addCouponViewModel = ViewModelProviders.of(this, factory).get(AddCouponViewModel.class);
        return addCouponViewModel;

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setCreateCouponButton() {
        mBinding.createBtn.setOnClickListener((View v) -> {
            /*if(!mAddCouponViewModel.isBarCodeValueCheck()){
                mBinding.barCode.setError("바코드를 작성 해주세요");
                mBinding.barCode.requestFocus();
                return;
            }else if(!mAddCouponViewModel.isCompanyNameValueCheck()){
                mBinding.companyName.setError("상호명을 작성 해주세요");
                mBinding.companyName.requestFocus();
                return;
            }else if(!mAddCouponViewModel.isSalePriceValueCheck()){
                mBinding.salePrice.setError("판매금액을 작성 해주세요");
                mBinding.salePrice.requestFocus();
                return;
            }else if(!mAddCouponViewModel.isDeadLineCheck()){
                mBinding.dateBtn.requestFocus();
                return;
            }*/
            mAddCouponViewModel.createCouponValueCheck();
        });

        mAddCouponViewModel.getAddCouponErrorEvent().observe(this,
                (@Nullable Event<Object> event) -> isErrorCheck());
    }

    public void isErrorCheck() {
        if (mAddCouponViewModel.getErrorBl()) {
            mAddCouponViewModel.createCoupon();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            String errorId = mAddCouponViewModel.getErrorId();
            String errorType = mAddCouponViewModel.getErrorType();
            if (errorType == "vt") {
                textCommonIsError(errorId);
                return;
            }else if (errorType == "bt") {
                buttonCommonIsError(errorId);
                return;
            }else if (errorType == "vtint") {
                textCommonTypeError(errorId);
                return;
            }
        }
    }

    public void textCommonIsError(String errorId) {
        if (errorId.equals("barCode")) {
            mBinding.barCode.setError("바코드를 입력 해주세요");
            mBinding.barCode.requestFocus();
        } else if (errorId.equals("companyName")) {
            mBinding.companyName.setError("상호명을 입력 해주세요");
            mBinding.companyName.requestFocus();
        } else if (errorId.equals("salePrice")) {
            mBinding.salePrice.setError("판매금액을 입력 해주세요");
            mBinding.salePrice.requestFocus();
        } else if (errorId.equals("couponName")) {
            mBinding.couponName.setError("쿠폰명을 입력 해주세요");
            mBinding.couponName.requestFocus();
        }
    }

    public void textCommonTypeError(String errorId){
        if (errorId.equals("barCode")) {
            mBinding.barCode.setError("바코드를 숫자형태로 입력해주세요");
            mBinding.barCode.requestFocus();
        }else if (errorId.equals("salePrice")) {
            mBinding.salePrice.setError("판매금액을 정확히 입력해주세요");
            mBinding.salePrice.requestFocus();
        }
    }

    public void buttonCommonIsError(String errorId) {
        if (errorId == "dateBtn") {
            mBinding.dateBtn.requestFocus();
            Toast.makeText(getApplicationContext(), "유효기간을 선택 해주세요.", Toast.LENGTH_SHORT).show();

        }else if (errorId == "couponCaterogy") {
            mBinding.couponCategory.requestFocus();
            Toast.makeText(getApplicationContext(), "메뉴를 선택 해주세요.", Toast.LENGTH_SHORT).show();

        }
    }
}