package com.example.giftishare.view.buycoupons;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giftishare.databinding.FragmentBuyCouponsBinding;

public class BuyCouponsFragment extends Fragment {

    private BuyCouponsViewModel mBuyCouponsViewModel;

    private FragmentBuyCouponsBinding mFragmentBuyCouponsBinding;

    private BuyCouponsAdapter mBuyCouponsAdapter;

    public BuyCouponsFragment() {
        // Require empty public constructor
    }

    public static BuyCouponsFragment newInstance() {
        return new BuyCouponsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentBuyCouponsBinding = mFragmentBuyCouponsBinding.inflate(inflater, container, false);
        mBuyCouponsViewModel = BuyCouponsActivity.obtainViewModel(getActivity());
        mFragmentBuyCouponsBinding.setBuyCouponsViewModel(mBuyCouponsViewModel);
        mFragmentBuyCouponsBinding.setLifecycleOwner(getActivity());
        setupListAdapter();
        return mFragmentBuyCouponsBinding.getRoot();
    }

    private void setupListAdapter() {
        mBuyCouponsAdapter = new BuyCouponsAdapter(mBuyCouponsViewModel);
        mFragmentBuyCouponsBinding.couponsListRecyclerView.setAdapter(mBuyCouponsAdapter);
        mFragmentBuyCouponsBinding.couponsListRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}
