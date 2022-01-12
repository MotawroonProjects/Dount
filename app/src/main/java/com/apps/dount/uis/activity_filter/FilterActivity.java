package com.apps.dount.uis.activity_filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.apps.dount.R;
import com.apps.dount.adapter.CategoryFilterAdapter;
import com.apps.dount.adapter.LatestProductAdapter;
import com.apps.dount.databinding.ActivityFilterBinding;
import com.apps.dount.model.DepartmentModel;
import com.apps.dount.model.FilterModel;
import com.apps.dount.model.SingleDepartmentDataModel;
import com.apps.dount.model.UserModel;
import com.apps.dount.mvvm.ActivityFilterMvvm;
import com.apps.dount.mvvm.FragmentHomeMvvm;
import com.apps.dount.preferences.Preferences;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.apps.dount.uis.activity_product_detials.ProductDetialsActivity;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends BaseActivity {
    private ActivityFilterBinding binding;
    private ActivityFilterMvvm activityFilterMvvm;
    private UserModel userModel;
    private Preferences preferences;
    private CategoryFilterAdapter categoryFilterAdapter;

    private List<String> departments;
    private List<DepartmentModel> departmentModels;
    private FilterModel filterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        initView();

    }


    private void initView() {
        departments = new ArrayList<>();
        departmentModels = new ArrayList<>();
        filterModel=new FilterModel();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        activityFilterMvvm = ViewModelProviders.of(this).get(ActivityFilterMvvm.class);
        activityFilterMvvm.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                // binding.cardNoData.setVisibility(View.GONE);
                binding.progBar.setVisibility(View.VISIBLE);
                binding.nested.setVisibility(View.GONE);
                binding.ll.setVisibility(View.GONE);
            } else {
                binding.progBar.setVisibility(View.GONE);
                binding.nested.setVisibility(View.VISIBLE);
                binding.ll.setVisibility(View.VISIBLE);

            }
            // binding.swipeRefresh.setRefreshing(isLoading);
        });
        activityFilterMvvm.getCategoryData().observe(this, new androidx.lifecycle.Observer<List<DepartmentModel>>() {


            @Override
            public void onChanged(List<DepartmentModel> departmentModels) {
                if (departmentModels.size() > 0) {
                    binding.progBar.setVisibility(View.GONE);
                    FilterActivity.this.departmentModels.addAll(departmentModels);
                    categoryFilterAdapter.updateList(departmentModels);

                    //binding.cardNoData.setVisibility(View.GONE);
                } else {
                    binding.progBar.setVisibility(View.GONE);


                    //binding.cardNoData.setVisibility(View.VISIBLE);

                }
            }
        });

        //  setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.white, R.color.black);
        binding.setLang(getLang());

        categoryFilterAdapter = new CategoryFilterAdapter(this);
        binding.recView.setLayoutManager(new GridLayoutManager(this, 1));
        binding.recView.setAdapter(categoryFilterAdapter);
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activityFilterMvvm.getDepartment(getLang());
        binding.lldepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.elexpendDepart.isExpanded()) {
                    binding.elexpendDepart.setExpanded(false);
                    if (getLang().equals("en")) {
                        binding.arrow.setRotation(180);
                    } else {
                        binding.arrow.setRotation(0);
                    }
                } else {
                    binding.elexpendDepart.setExpanded(true);
                    binding.arrow.setRotation(-90);
                }
            }
        });
        binding.btnRecet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departments.clear();
                categoryFilterAdapter = new CategoryFilterAdapter(FilterActivity.this);
                binding.recView.setAdapter(categoryFilterAdapter);
                categoryFilterAdapter.updateList(departmentModels);

            }
        });
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterModel.setDepartments(departments);
                Intent intent = getIntent();
                intent.putExtra("data", filterModel);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }


    public void addDepartid(DepartmentModel subCategoryDataModel) {
        if (departments.contains(subCategoryDataModel.getId())) {
            departments.remove(subCategoryDataModel.getId());
        } else {
            departments.add(subCategoryDataModel.getId());
        }
    }
}