package com.apps.dount.uis.activity_home.fragments_home_navigaion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.apps.dount.R;
import com.apps.dount.adapter.Department2Adapter;
import com.apps.dount.adapter.DepartmentAdapter;
import com.apps.dount.databinding.FragmentDepartmentBinding;
import com.apps.dount.model.DepartmentModel;
import com.apps.dount.mvvm.FragmentDepartmentMvvm;
import com.apps.dount.mvvm.FragmentHomeMvvm;
import com.apps.dount.uis.activity_base.BaseFragment;
import com.apps.dount.uis.activity_category_detials.CategoryDetialsActivity;
import com.apps.dount.uis.activity_home.HomeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;


public class FragmentDepartment extends BaseFragment {
    private FragmentDepartmentBinding binding;
    private HomeActivity activity;
    private Department2Adapter departmentAdapter;
    private FragmentDepartmentMvvm fragmentDepartmentMvvm;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;
    public static FragmentDepartment newInstance() {
        FragmentDepartment fragment = new FragmentDepartment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 2 && result.getResultCode() == Activity.RESULT_OK) {
                activity.updateCartCount();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_department, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        fragmentDepartmentMvvm = ViewModelProviders.of(this).get(FragmentDepartmentMvvm.class);

        fragmentDepartmentMvvm.getIsLoading().observe(activity, isLoading -> {
            if (isLoading) {

                binding.cardNoData.setVisibility(View.VISIBLE);

            }
            binding.swipeRefresh.setRefreshing(isLoading);
        });
        fragmentDepartmentMvvm.getCategoryData().observe(activity, new androidx.lifecycle.Observer<List<DepartmentModel>>() {
            @Override
            public void onChanged(List<DepartmentModel> departmentModels) {
                if (departmentModels.size() > 0) {

                    departmentAdapter.updateList(departmentModels);
                    binding.cardNoData.setVisibility(View.GONE);

                    //binding.cardNoData.setVisibility(View.GONE);
                } else {

                    binding.cardNoData.setVisibility(View.VISIBLE);

                    //binding.cardNoData.setVisibility(View.VISIBLE);

                }
            }
        });
        departmentAdapter = new Department2Adapter(activity, this);
        binding.recViewHall.setLayoutManager(new GridLayoutManager(activity, 2));
        binding.recViewHall.setAdapter(departmentAdapter);
        fragmentDepartmentMvvm.getDepartment(getLang());
    }


    public void showcategory(DepartmentModel departmentModel) {

        Intent intent = new Intent(activity, CategoryDetialsActivity.class);
        intent.putExtra("catid", departmentModel.getId());
        launcher.launch(intent);
    }

}