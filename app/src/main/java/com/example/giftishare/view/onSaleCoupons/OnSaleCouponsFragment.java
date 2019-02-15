package com.example.giftishare.view.onSaleCoupons;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giftishare.databinding.FragmentOnSaleCouponsBinding;

import io.reactivex.annotations.Nullable;

public class OnSaleCouponsFragment extends Fragment {

    private OnSaleCouponsViewModel mOnSaleCouponsViewModel;

    private FragmentOnSaleCouponsBinding mFragmentOnSaleCouponsBinding;

    private OnSaleCouponsAdapter mOnSaleCouponsAdapter;

    public OnSaleCouponsFragment() {
        // Require empty public constructor
    }

    public static OnSaleCouponsFragment newInstance() {
        return new OnSaleCouponsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentOnSaleCouponsBinding = FragmentOnSaleCouponsBinding.inflate(inflater, container, false);
        mOnSaleCouponsViewModel = OnSaleCouponsActivity.obtainViewModel(getActivity());
        mFragmentOnSaleCouponsBinding.setOnSaleCouponsViewModel(mOnSaleCouponsViewModel);
        mFragmentOnSaleCouponsBinding.setLifecycleOwner(getActivity());
        setupListAdapter();
        return mFragmentOnSaleCouponsBinding.getRoot();
    }

    private void setupListAdapter() {
        mOnSaleCouponsAdapter = new OnSaleCouponsAdapter(mOnSaleCouponsViewModel);
        mFragmentOnSaleCouponsBinding.couponsListRecyclerView.setAdapter(mOnSaleCouponsAdapter);
        mOnSaleCouponsViewModel.start("rrrrr");
    }
}
