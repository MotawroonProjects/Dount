package com.apps.dount.uis.activity_product_detials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.apps.dount.R;
import com.apps.dount.databinding.ActivityProductDetialsBinding;
import com.apps.dount.model.ProductModel;
import com.apps.dount.model.SingleProductDataModel;
import com.apps.dount.model.UserModel;
import com.apps.dount.mvvm.ActivityProductDetialsMvvm;
import com.apps.dount.preferences.Preferences;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.esotericsoftware.minlog.Log;

public class ProductDetialsActivity extends BaseActivity {
    private ActivityProductDetialsBinding binding;
    private ActivityProductDetialsMvvm activityProductDetialsMvvm;
    private UserModel userModel;
    private Preferences preferences;
    private String proid;
    private String user_id = null;

    private ProductModel productmodel;

    private boolean isDataChanged = false,isfav=false;
    private double price;
    private int amount=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detials);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        proid = intent.getStringExtra("proid");

    }

    private void initView() {
        binding.tvTotal.setText("0");

        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        if (userModel != null) {
            user_id = userModel.getData().getId() + "";
        }
        binding.setUserModel(userModel);
        activityProductDetialsMvvm = ViewModelProviders.of(this).get(ActivityProductDetialsMvvm.class);
        activityProductDetialsMvvm.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                // binding.cardNoData.setVisibility(View.GONE);
                binding.progBar.setVisibility(View.VISIBLE);
                binding.nested.setVisibility(View.GONE);
                binding.flTotal.setVisibility(View.GONE);

            }
            // binding.swipeRefresh.setRefreshing(isLoading);
        });
        activityProductDetialsMvvm.getFav().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    isfav=true;
                    if (productmodel != null) {
                        if (productmodel.getIs_favorite() == null) {
                            productmodel.setIs_favorite("yes");

                        } else {
                            if (productmodel.getIs_favorite().equals("yes")) {
                                productmodel.setIs_favorite("no");
                            } else {
                                productmodel.setIs_favorite("yes");
                            }
                        }
                        binding.setModel(productmodel);

                    }
                }
            }
        });
        activityProductDetialsMvvm.getAmount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer > 0) {
                    isDataChanged = true;

                    Toast.makeText(ProductDetialsActivity.this, getResources().getString(R.string.suc_add_to_cart), Toast.LENGTH_LONG).show();
                }
            }
        });
        activityProductDetialsMvvm.getProductData().observe(this, new Observer<SingleProductDataModel>() {

            @Override
            public void onChanged(SingleProductDataModel singleProductDataModel) {
                binding.progBar.setVisibility(View.GONE);
                binding.nested.setVisibility(View.VISIBLE);
                binding.flTotal.setVisibility(View.VISIBLE);

                if (singleProductDataModel.getData() != null) {
                    ProductDetialsActivity.this.productmodel = singleProductDataModel.getData();
                    binding.setModel(singleProductDataModel.getData());
                        if (singleProductDataModel.getData().getOffer() == null) {
                            price = Double.parseDouble(singleProductDataModel.getData().getPrice());
                            binding.tvTotal.setText(price + "");
                        } else {
                            price = Double.parseDouble(singleProductDataModel.getData().getOffer().getPrice_after());
                            binding.tvTotal.setText(price + "");

                        }
                    }

                }

        });
        //  setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.white, R.color.black);
        binding.setLang(getLang());

        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        binding.imageIncrease.setOnClickListener(view -> {
            amount++;
            binding.tvAmount.setText(String.valueOf(amount));
            binding.tvTotal.setText(((price * amount) ) + "");

        });

        binding.imageDecrease.setOnClickListener(view -> {
            if (amount > 1) {
                amount--;
                binding.tvAmount.setText(String.valueOf(amount));
                binding.tvTotal.setText(((price * amount)) + "");

            }
        });
        binding.flTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addTocart();

                }
        });
        binding.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userModel != null) {
                    activityProductDetialsMvvm.addRemoveFavourite(productmodel.getId(), user_id);
                }
            }
        });
        activityProductDetialsMvvm.getProductDetials(getLang(), proid, user_id);
    }

    private void back() {
        if (isDataChanged||isfav) {
            Log.error("ldldll", String.valueOf(isDataChanged));
            setResult(RESULT_OK);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void addTocart() {
        double total = ((price * amount) );
        ;

        activityProductDetialsMvvm.add_to_cart(productmodel, amount, total, price, this);
    }


}