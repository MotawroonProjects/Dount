package com.apps.dount.uis.activity_home.fragments_home_navigaion;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apps.dount.R;
import com.apps.dount.databinding.FragmentDepartmentBinding;
import com.apps.dount.databinding.FragmentOfferBinding;
import com.apps.dount.uis.activity_base.BaseFragment;
import com.apps.dount.uis.activity_home.HomeActivity;


public class FragmentOffer extends BaseFragment {
    private FragmentOfferBinding binding;
    private HomeActivity activity;

    public static FragmentOffer newInstance() {
        FragmentOffer fragment = new FragmentOffer();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offer, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {

    }


}