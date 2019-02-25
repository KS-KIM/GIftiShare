package com.example.giftishare.view.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentMainBinding.walletAddress.setOnClickListener((view) -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, mFragmentMainBinding.walletAddress.getText());
            Intent chooser = Intent.createChooser(intent, "지갑 주소 공유하기");
            startActivity(chooser);
        });

        mMainViewModel.getWalletBalance().observe(this,
                walletBalance -> mFragmentMainBinding.walletBalance.setText(walletBalance));

        mMainViewModel.getSendEtherCompleteEvent().observe(this,
                stringEvent -> showMessage(stringEvent.getContentIfNotHandled()));

        mFragmentMainBinding.btnSendEther.setOnClickListener((view) -> {
            final EditText address = new EditText(getContext());
            final EditText sendValue = new EditText(getContext());
            sendValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("받으실 분의 지갑 주소를 입력하세요")
                    .setView(address)
                    .setPositiveButton("확인", (DialogInterface dialog, int which) -> {
                        builder.setTitle("보내실 금액을 입력하세요")
                                .setView(sendValue)
                                .setPositiveButton("확인",
                                        (DialogInterface ethDialog, int ethWhich) -> mMainViewModel.sendEther(address.getText().toString(), sendValue.getText().toString()))
                                .setNegativeButton("취소",
                                        (DialogInterface ethDialog, int ethWhich) -> ethDialog.dismiss())
                                .show();
                    })
                    .setNegativeButton("취소", (DialogInterface dialog, int which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainViewModel.start();
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
