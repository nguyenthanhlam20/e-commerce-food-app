package vn.edu.fpt.fa24.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.LoginActivity;
import vn.edu.fpt.fa24.MyAccountActivity;
import vn.edu.fpt.fa24.OrderHistoryActivity;
import vn.edu.fpt.fa24.R;

public class ProfileSettingsFragment extends Fragment {
    View view;
    RelativeLayout logoutBtn, accountBtn, historyBtn;
    private SessionHelper sessionHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initializations(inflater, container);
        clickListeners();
        return view;
    }

    private void initializations(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        logoutBtn = view.findViewById(R.id.logout);
        accountBtn = view.findViewById(R.id.myaccount);
        historyBtn = view.findViewById(R.id.orderHistory);
        sessionHelper = new SessionHelper(requireActivity());
    }

    private void clickListeners() {
        logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            sessionHelper.clearSession();
            requireActivity().finish();
        });

        accountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MyAccountActivity.class);
            startActivity(intent);
        });

        historyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), OrderHistoryActivity.class);
            startActivity(intent);
        });
    }
}