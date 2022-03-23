package com.apps.dount.uis.activity_category_detials;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.apps.dount.R;
import com.apps.dount.adapter.LatestProductAdapter;
import com.apps.dount.databinding.ActivityCategoryDetialsBinding;
import com.apps.dount.model.ProductDataModel;
import com.apps.dount.model.ProductModel;
import com.apps.dount.model.SingleDepartmentDataModel;
import com.apps.dount.model.UserModel;
import com.apps.dount.mvvm.ActivityCategoryDetialsMvvm;
import com.apps.dount.preferences.Preferences;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.apps.dount.uis.activity_product_detials.ProductDetialsActivity;

import java.util.List;

public class CategoryDetialsActivity extends BaseActivity {
    private ActivityCategoryDetialsBinding binding;
    private ActivityCategoryDetialsMvvm categoryDetialsMvvm;
    private UserModel userModel;
    private Preferences preferences;
    private String catid;
    private LatestProductAdapter product2Adapter;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;
    private int layoutPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_detials);
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
        categoryDetialsMvvm = ViewModelProviders.of(this).get(ActivityCategoryDetialsMvvm.class);
        categoryDetialsMvvm.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                // binding.cardNoData.setVisibility(View.GONE);
                binding.progBar.setVisibility(View.VISIBLE);
            }
            // binding.swipeRefresh.setRefreshing(isLoading);
        });
        categoryDetialsMvvm.getFav().observe(this, new androidx.lifecycle.Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    List<ProductModel> productModelList = categoryDetialsMvvm.getCategoryData().getValue().getData();
                    ProductModel productModel = productModelList.get(layoutPosition);
                    if (productModel.isIs_favorite()) {
                        productModel.setIs_favorite(false);
                    } else {
                        productModel.setIs_favorite(true);
                    }
                    productModelList.set(layoutPosition, productModel);
                    product2Adapter.updateList(productModelList, layoutPosition);
                }
            }
        });
        categoryDetialsMvvm.getCategoryData().observe(this, new Observer<ProductDataModel>() {
            @Override
            public void onChanged(ProductDataModel singleDepartmentDataModel) {
                binding.progBar.setVisibility(View.GONE);
                if (singleDepartmentDataModel.getData() != null) {
                    // binding.setModel(singleDepartmentDataModel.getData());
                    if (singleDepartmentDataModel.getData() != null && singleDepartmentDataModel.getData().size() > 0) {
                        product2Adapter.updateList(singleDepartmentDataModel.getData());
                        binding.cardNoData.setVisibility(View.GONE);
                    } else {
                        binding.cardNoData.setVisibility(View.VISIBLE);

                    }
                }
            }
        });
        //  setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.white, R.color.black);
        binding.setLang(getLang());

        product2Adapter = new LatestProductAdapter(this, null, userModel);

        binding.recView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recView.setAdapter(product2Adapter);
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        categoryDetialsMvvm.getDepartmentDetials(getLang(), catid, getUserModel());
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 2 && result.getResultCode() == Activity.RESULT_OK) {
                setResult(RESULT_OK);
            }
        });
    }


    public void showProductDetials(String productid) {
        req = 2;
        Intent intent = new Intent(this, ProductDetialsActivity.class);
        intent.putExtra("proid", productid);
        launcher.launch(intent);
    }

    public void addremovefave(int layoutPosition) {
        this.layoutPosition = layoutPosition;
        categoryDetialsMvvm.addRemoveFavourite(categoryDetialsMvvm.getCategoryData().getValue().getData().get(layoutPosition).getId(), getUserModel());
    }
}