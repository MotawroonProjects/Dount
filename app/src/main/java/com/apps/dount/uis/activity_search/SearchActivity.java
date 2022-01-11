package com.apps.dount.uis.activity_search;

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
import com.apps.dount.adapter.LatestProductAdapter;
import com.apps.dount.adapter.OfferProductAdapter;
import com.apps.dount.databinding.ActivitySearchBinding;
import com.apps.dount.model.FilterModel;
import com.apps.dount.model.SingleDepartmentDataModel;
import com.apps.dount.model.UserModel;
import com.apps.dount.mvvm.ActivityCategoryDetialsMvvm;
import com.apps.dount.mvvm.ActivitySearchMvvm;
import com.apps.dount.preferences.Preferences;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.apps.dount.uis.activity_filter.FilterActivity;
import com.apps.dount.uis.activity_product_detials.ProductDetialsActivity;

import java.util.logging.Filter;

public class SearchActivity extends BaseActivity {
    private ActivitySearchBinding binding;
    private ActivitySearchMvvm activitySearchMvvm;
    private UserModel userModel;
    private Preferences preferences;
    private String catid;
    private OfferProductAdapter product2Adapter;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;
    private FilterModel filtermodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        catid = intent.getStringExtra("catid");

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        activitySearchMvvm = ViewModelProviders.of(this).get(ActivitySearchMvvm.class);
        activitySearchMvvm.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                // binding.cardNoData.setVisibility(View.GONE);
                binding.progBar.setVisibility(View.VISIBLE);
            }
            // binding.swipeRefresh.setRefreshing(isLoading);
        });
        activitySearchMvvm.getCategoryData().observe(this, new Observer<SingleDepartmentDataModel>() {
            @Override
            public void onChanged(SingleDepartmentDataModel singleDepartmentDataModel) {
                binding.progBar.setVisibility(View.GONE);
                if (singleDepartmentDataModel.getData() != null) {
                    binding.setModel(singleDepartmentDataModel.getData());
                    if (singleDepartmentDataModel.getData().getProducts() != null && singleDepartmentDataModel.getData().getProducts().size() > 0) {
                        product2Adapter.updateList(singleDepartmentDataModel.getData().getProducts());
                        binding.cardNoData.setVisibility(View.GONE);
                    } else {
                        binding.cardNoData.setVisibility(View.VISIBLE);

                    }
                }
            }
        });
        //  setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.white, R.color.black);
        binding.setLang(getLang());

        product2Adapter = new OfferProductAdapter(this,null,userModel);
        binding.recView.setLayoutManager(new GridLayoutManager(this, 1));
        binding.recView.setAdapter(product2Adapter);
        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activitySearchMvvm.getDepartmentDetials(getLang(), catid);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 2 && result.getResultCode() == Activity.RESULT_OK) {
                setResult(RESULT_OK);
            }
            else   if (req == 3 && result.getResultCode() == Activity.RESULT_OK) {
                if(result.getData().getSerializableExtra("data")!=null){
                filtermodel=(FilterModel)result.getData().getSerializableExtra("data");
            }}
        });
        binding.imageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req=3;
                Intent intent = new Intent(SearchActivity.this, FilterActivity.class);
                launcher.launch(intent);
            }
        });
    }


    public void showProductDetials(String productid) {
        req = 2;
        Intent intent = new Intent(this, ProductDetialsActivity.class);
        intent.putExtra("proid", productid);
        launcher.launch(intent);
    }
}