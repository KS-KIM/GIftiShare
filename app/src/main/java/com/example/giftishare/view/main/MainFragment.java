package com.example.giftishare.view.main;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giftishare.databinding.FragmentMainBinding;
import com.example.giftishare.helper.CustomTabHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.annotations.Nullable;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

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
        mFragmentMainBinding.wallet.walletAddress.setOnClickListener((view) -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, mFragmentMainBinding.wallet.walletAddress.getText());
            Intent chooser = Intent.createChooser(intent, "지갑 주소 공유하기");
            startActivity(chooser);
        });

        mMainViewModel.getWalletBalance().observe(this,
                walletBalance -> mFragmentMainBinding.wallet.walletBalance.setText(walletBalance));

        mMainViewModel.start();

        setupButton();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /* @TODO 코드 분리 및 개선 필요 */
    private void setupButton() {
        mMainViewModel.getShowTransactionEvent().observe(this,
                url -> {
                    Intent customTabIntent = CustomTabHelper.createCustomTabIntent(getContext(),
                            url.getContentIfNotHandled(), Color.parseColor("#11d3bc"));
                    startActivity(customTabIntent);
                });

        mFragmentMainBinding.wallet.btnSendEther.setOnClickListener((view) -> {
            final EditText address = new EditText(getContext());
            final EditText sendValue = new EditText(getContext());
            final TextView clipAddress = new TextView(getContext());
            sendValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            boolean matchFlag = false;

            // 클립보드 최상위 값으로부터 주소를 가져올 수 있으면 가져오기
            ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN)) {
                Pattern addressMatch = Pattern.compile("0x[a-fA-F0-9]{40}");
                String clipText = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
                Matcher matcher = addressMatch.matcher(clipText);
                if (matcher.find()) {
                    matchFlag = true;
                    clipAddress.setText(clipText.substring(matcher.start(), matcher.end()));
                }
            }

            // 금액 입력 다이얼로그 구성
            AlertDialog etherInputDialog = new AlertDialog.Builder(getContext()).setTitle("보내실 금액을 입력하세요")
                    .setView(sendValue)
                    .setPositiveButton("확인", (DialogInterface ethDialog, int ethWhich) -> {
                        if (!sendValue.getText().toString().equals("")) {
                            mMainViewModel.sendEther(address.getText().toString(), sendValue.getText().toString());
                        } else {
                            Toast.makeText(getContext(), "금액을 입력하세요.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("취소", (DialogInterface ethDialog, int ethWhich) -> ethDialog.dismiss())
                    .create();

            // 주소 입력 다이얼로그 구성
            AlertDialog addressInputDialog = new AlertDialog.Builder(getContext()).setTitle("받으실 분의 지갑 주소를 입력하세요")
                    .setView(address)
                    .setPositiveButton("확인", (DialogInterface dialog, int which) -> {
                        if (Pattern.matches("^0x[a-fA-F0-9]{40}$", address.getText().toString())) {
                            etherInputDialog.show();
                        } else {
                            Toast.makeText(getContext(), "잘못된 주소를 입력하셨습니다. 확인 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("취소", (DialogInterface dialog, int which) -> dialog.dismiss())
                    .create();

            // 클립보드 주소 사용여부 확인 다이얼로그
            AlertDialog.Builder clipAddressDialog = new AlertDialog.Builder(getContext()).setTitle("클립보드에 복사된 주소를 사용하시겠습니까?")
                    .setMessage(clipAddress.getText().toString())
                    .setPositiveButton("확인", (DialogInterface ethDialog, int ethWhich) -> {
                        address.setText(clipAddress.getText().toString());
                        etherInputDialog.show();
                    })
                    .setNegativeButton("취소", (DialogInterface ethDialog, int ethWhich) -> addressInputDialog.show());

            // 클립보드로부터 가져온 주소가 일치하는 경우와 아닌 경우를 나누어 출력하는 다이얼로그 변경
            if (matchFlag == true) {
                clipAddressDialog.show();
            } else {
                addressInputDialog.show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
