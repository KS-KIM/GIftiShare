package com.example.giftishare.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giftishare.databinding.FragmentMainBinding;

import io.reactivex.annotations.Nullable;

public class MainFragment extends Fragment {

    private FragmentMainBinding mFragmentMainBinding;

    private MainViewModel mMainViewModel;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false);
        mMainViewModel = MainActivity.obtainViewModel(getActivity());
        mFragmentMainBinding.setViewmodel(mMainViewModel);
        mFragmentMainBinding.setLifecycleOwner(getActivity());
        setHasOptionsMenu(true);
        return mFragmentMainBinding.getRoot();
    }
}
