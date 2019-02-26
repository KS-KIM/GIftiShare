package com.example.giftishare.view.sellcoupons;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giftishare.databinding.FragmentSellCouponsBinding;

public class SellCouponsFragment extends Fragment {

    private SellCouponsViewModel mSellCouponsViewModel;

    private FragmentSellCouponsBinding mFragmentSellCouponsBinding;

    private SellCouponsAdapter mSellCouponsAdapter;

    public SellCouponsFragment() {
        // Require empty public constructor
    }

    public static SellCouponsFragment newInstance() {
        return new SellCouponsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentSellCouponsBinding = mFragmentSellCouponsBinding.inflate(inflater, container, false);
        mSellCouponsViewModel = SellCouponsActivity.obtainViewModel(getActivity());
        mFragmentSellCouponsBinding.setSellCouponsViewModel(mSellCouponsViewModel);
        mFragmentSellCouponsBinding.setLifecycleOwner(getActivity());
        setupListAdapter();
        return mFragmentSellCouponsBinding.getRoot();
    }
    private void setupListAdapter() {
        mSellCouponsAdapter = new SellCouponsAdapter(mSellCouponsViewModel);
        mFragmentSellCouponsBinding.couponsListRecyclerView.setAdapter(mSellCouponsAdapter);
        mFragmentSellCouponsBinding.couponsListRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        //mSellCouponsViewModel.start(getContext());
    }
}
