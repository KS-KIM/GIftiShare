package com.example.giftishare.view.buysellcoupons;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giftishare.databinding.FragmentBuySellCouponsBinding;

public class BuySellCouponsFragment extends Fragment {

    public static final String INTENT_IS_SALE = "INTENT_IS_SALE";

    private BuySellCouponsViewModel mBuySellCouponsViewModel;

    private FragmentBuySellCouponsBinding mFragmentBuySellCouponsBinding;

    private BuySellCouponsAdapter mBuySellCouponsAdapter;

    public BuySellCouponsFragment() {
        // Require empty public constructor
    }

    public static BuySellCouponsFragment newInstance() {
        return new BuySellCouponsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentBuySellCouponsBinding = mFragmentBuySellCouponsBinding.inflate(inflater, container, false);
        mBuySellCouponsViewModel = BuySellCouponsActivity.obtainViewModel(getActivity());
        mFragmentBuySellCouponsBinding.setBuySellCouponsViewModel(mBuySellCouponsViewModel);
        mFragmentBuySellCouponsBinding.setLifecycleOwner(getActivity());

        Intent intent = getActivity().getIntent();
        boolean isSale = intent.getBooleanExtra(INTENT_IS_SALE, false);
        mBuySellCouponsViewModel.start(isSale);

        setupListAdapter();
        return mFragmentBuySellCouponsBinding.getRoot();
    }

    private void setupListAdapter() {
        mBuySellCouponsAdapter = new BuySellCouponsAdapter(mBuySellCouponsViewModel);
        mFragmentBuySellCouponsBinding.couponsListRecyclerView.setAdapter(mBuySellCouponsAdapter);
        mFragmentBuySellCouponsBinding.couponsListRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}
