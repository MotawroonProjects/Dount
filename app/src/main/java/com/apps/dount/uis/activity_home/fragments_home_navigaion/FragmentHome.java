package com.apps.dount.uis.activity_home.fragments_home_navigaion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.dount.R;

import com.apps.dount.adapter.DepartmentAdapter;
import com.apps.dount.adapter.LatestProductAdapter;
import com.apps.dount.adapter.SliderAdapter;
import com.apps.dount.model.DepartmentModel;
import com.apps.dount.model.ProductModel;
import com.apps.dount.model.SliderDataModel;
import com.apps.dount.mvvm.FragmentHomeMvvm;
import com.apps.dount.uis.activity_base.BaseFragment;
import com.apps.dount.databinding.FragmentHomeBinding;
import com.apps.dount.uis.activity_category_detials.CategoryDetialsActivity;
import com.apps.dount.uis.activity_home.HomeActivity;
import com.apps.dount.uis.activity_product_detials.ProductDetialsActivity;
import com.apps.dount.uis.activity_search.SearchActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FragmentHome extends BaseFragment {
    private static final String TAG = FragmentHome.class.getName();
    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private FragmentHomeMvvm fragmentHomeMvvm;
    private DepartmentAdapter departmentAdapter;
    private LatestProductAdapter latestProductAdapter;
    private SliderAdapter sliderAdapter;
    private List<SliderDataModel.SliderModel> sliderModelList;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Timer timer;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;
    private int layoutPosition;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 2 && result.getResultCode() == Activity.RESULT_OK) {
                activity.updateCartCount();
                fragmentHomeMvvm.getOffers(getLang(), getUserModel());
                if (getUserModel() != null) {
                    activity.updateFirebase();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Observable.timer(130, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        initView();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {
        binding.setLang(getLang());
        sliderModelList = new ArrayList<>();
        fragmentHomeMvvm = ViewModelProviders.of(this).get(FragmentHomeMvvm.class);

        fragmentHomeMvvm.getIsLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.progBarSlider.setVisibility(View.VISIBLE);
                binding.progBarDepartment.setVisibility(View.VISIBLE);
                binding.progBarOffers.setVisibility(View.VISIBLE);


            } else {
                binding.progBarSlider.setVisibility(View.GONE);
                binding.progBarDepartment.setVisibility(View.GONE);
                binding.progBarOffers.setVisibility(View.GONE);
            }
            // binding.swipeRefresh.setRefreshing(isLoading);
        });
        fragmentHomeMvvm.getFav().observe(activity, new androidx.lifecycle.Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    List<ProductModel> productModelList=fragmentHomeMvvm.getOfferList().getValue();
                    ProductModel productModel=productModelList.get(layoutPosition);
                    if(productModel.isIs_favorite()){
                        productModel.setIs_favorite(false);
                    }
                    else {
                        productModel.setIs_favorite(true);
                    }
                    productModelList.set(layoutPosition,productModel);
                    latestProductAdapter.updateList(productModelList,layoutPosition);
                }
            }
        });
        fragmentHomeMvvm.getSliderDataModelMutableLiveData().observe(activity, new androidx.lifecycle.Observer<SliderDataModel>() {
            @Override
            public void onChanged(SliderDataModel sliderDataModel) {

                if (sliderDataModel.getData() != null) {
                    binding.progBarSlider.setVisibility(View.GONE);
                    sliderModelList.clear();
                    sliderModelList.addAll(sliderDataModel.getData());
                    sliderAdapter.notifyDataSetChanged();
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new MyTask(), 3000, 3000);
                }

            }
        });

        fragmentHomeMvvm.getCategoryData().observe(activity, new androidx.lifecycle.Observer<List<DepartmentModel>>() {
            @Override
            public void onChanged(List<DepartmentModel> departmentModels) {
                if (departmentModels.size() > 0) {
                    binding.progBarDepartment.setVisibility(View.GONE);

                    departmentAdapter.updateList(departmentModels);
                    binding.tvNoCategory.setVisibility(View.GONE);

                    //binding.cardNoData.setVisibility(View.GONE);
                } else {
                    binding.progBarDepartment.setVisibility(View.GONE);

                    binding.tvNoCategory.setVisibility(View.VISIBLE);

                    //binding.cardNoData.setVisibility(View.VISIBLE);

                }
            }
        });

        fragmentHomeMvvm.getOfferList().observe(activity, new androidx.lifecycle.Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                if (productModels != null && productModels.size() > 0) {
                    binding.progBarOffers.setVisibility(View.GONE);

                    latestProductAdapter.updateList(productModels);
                    binding.tvNoOffer.setVisibility(View.GONE);

                } else {
                    binding.progBarOffers.setVisibility(View.GONE);

                    binding.tvNoOffer.setVisibility(View.VISIBLE);
                }
            }
        });

        departmentAdapter = new DepartmentAdapter(activity, this);
        binding.recyclerDepartment.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerDepartment.setAdapter(departmentAdapter);

        latestProductAdapter = new LatestProductAdapter(activity, this, getUserModel());
        binding.recyclerOffers.setLayoutManager(new GridLayoutManager(activity, 3));
        binding.recyclerOffers.setAdapter(latestProductAdapter);


        sliderAdapter = new SliderAdapter(sliderModelList, activity);
        binding.pager.setAdapter(sliderAdapter);
        binding.pager.setClipToPadding(false);
        binding.pager.setPadding(80, 0, 80, 10);
        binding.pager.setPageMargin(20);

        fragmentHomeMvvm.getSlider();
        fragmentHomeMvvm.getDepartment(getLang());
        fragmentHomeMvvm.getOffers(getLang(), getUserModel());
        binding.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req = 2;
                Intent intent = new Intent(activity, SearchActivity.class);
                launcher.launch(intent);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }

    public void showcategory(DepartmentModel departmentModel) {
        req = 2;
        Intent intent = new Intent(activity, CategoryDetialsActivity.class);
        intent.putExtra("catid", departmentModel.getId());
        launcher.launch(intent);
    }

    public void showProductDetials(String productid) {
        req = 2;
        Intent intent = new Intent(activity, ProductDetialsActivity.class);
        intent.putExtra("proid", productid);
        launcher.launch(intent);
    }

    public void addremovefave(int layoutPosition) {
        this.layoutPosition=layoutPosition;
        fragmentHomeMvvm.addRemoveFavourite(fragmentHomeMvvm.getOfferList().getValue().get(layoutPosition).getId(), getUserModel());
    }

    public class MyTask extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                int current_page = binding.pager.getCurrentItem();
                if (current_page < sliderAdapter.getCount() - 1) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    binding.pager.setCurrentItem(0);

                }
            });

        }

    }


}