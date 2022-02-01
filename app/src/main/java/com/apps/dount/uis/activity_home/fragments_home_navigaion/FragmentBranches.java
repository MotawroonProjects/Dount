package com.apps.dount.uis.activity_home.fragments_home_navigaion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.apps.dount.R;
import com.apps.dount.adapter.BranchAdapter;
import com.apps.dount.databinding.FragmentBranchesBinding;
import com.apps.dount.model.BranchDataModel;
import com.apps.dount.model.BranchModel;
import com.apps.dount.model.LocationModel;
import com.apps.dount.mvvm.FragmentBranchesMvvm;
import com.apps.dount.remote.Api;
import com.apps.dount.tags.Tags;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.apps.dount.uis.activity_base.BaseFragment;
import com.apps.dount.uis.activity_home.HomeActivity;
import com.apps.dount.uis.activity_map.MapActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class FragmentBranches extends BaseFragment implements OnMapReadyCallback {
    private HomeActivity activity;
    private FragmentBranchesBinding binding;
    private GoogleMap mMap;
    private float zoom = 15.0f;
    private ActivityResultLauncher<String> permissionLauncher;
    private CompositeDisposable disposable = new CompositeDisposable();
    private BranchAdapter branchAdapter;
    private FragmentBranchesMvvm fragmentBranchesMvvm;
    private LocationModel locationmodel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                fragmentBranchesMvvm.initGoogleApi();

            } else {
                Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_branches, container, false);
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
        locationmodel = new LocationModel(0, 0, "");
        branchAdapter = new BranchAdapter(activity);
        fragmentBranchesMvvm = ViewModelProviders.of(this).get(FragmentBranchesMvvm.class);
        fragmentBranchesMvvm.setContext(activity);
        // fragmentBranchesMvvm.getBranch().observe(activity, weddingHallModels -> branchAdapter.updateList(fragmentBranchesMvvm.getBranch().getValue()));
        fragmentBranchesMvvm.getBranch();
        fragmentBranchesMvvm.getLocationData().observe(this, locationModel -> {
            // addMarker(locationModel.getLat(), locationModel.getLng());
            FragmentBranches.this.locationmodel = locationModel;
            branchAdapter.updateLocation(locationModel);
        });
        fragmentBranchesMvvm.getIsLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.flLoading.setClickable(true);
                binding.flLoading.setFocusable(true);
                binding.progBar.setVisibility(View.VISIBLE);
                binding.flLoading.setVisibility(View.VISIBLE);
                binding.cardNoData.setVisibility(View.GONE);
                branchAdapter.updateList(null);

            }
        });

        fragmentBranchesMvvm.getBranch().observe(activity, new androidx.lifecycle.Observer<List<BranchModel>>() {
            @Override
            public void onChanged(List<BranchModel> branchModels) {
                if (branchModels.size() > 0) {
                    branchAdapter.updateLocation(FragmentBranches.this.locationmodel);
                    branchAdapter.updateList(fragmentBranchesMvvm.getBranch().getValue());
                    updateMapData(branchModels);
                    binding.cardNoData.setVisibility(View.GONE);
                    binding.flLoading.setVisibility(View.GONE);

                } else {
                    binding.flLoading.setVisibility(View.VISIBLE);
                    binding.progBar.setVisibility(View.GONE);
                    binding.cardNoData.setVisibility(View.VISIBLE);
                    branchAdapter.updateList(null);
                    mMap.clear();
                    binding.flLoading.setClickable(false);
                    binding.flLoading.setFocusable(false);
                }
            }
        });

//        SnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(binding.recView);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.recView.setAdapter(branchAdapter);
        checkPermission();

        updateUI();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(activity, BaseActivity.fineLocPerm) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(BaseActivity.fineLocPerm);
        } else {

            fragmentBranchesMvvm.initGoogleApi();
        }
    }


    private void updateUI() {
        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.map, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);
            fragmentBranchesMvvm.getBranchData();
        }
    }

    private void addMarker(double lat, double lng, String is_delivery) {
        if (is_delivery.equals("yes")) {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.ic_map2)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {

                            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));


                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                        super.onLoadFailed(errorDrawable);

                        }
                    });
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.ic_map)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {

                            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));


                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                        super.onLoadFailed(errorDrawable);

                        }
                    });
        }
    }

    private void updateMapData(List<BranchModel> data) {

        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        for (BranchModel branchModel : data) {
            bounds.include(new LatLng(Double.parseDouble(branchModel.getLatitude()), Double.parseDouble(branchModel.getLongitude())));
            addMarker(Double.parseDouble(branchModel.getLatitude()), Double.parseDouble(branchModel.getLongitude()), branchModel.getIs_delivery());
        }

        if (data.size() >= 2) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100));

        } else if (data.size() == 1) {
            LatLng latLng = new LatLng(Double.parseDouble(data.get(0).getLatitude()), Double.parseDouble(data.get(0).getLongitude()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }
}