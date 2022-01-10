package com.apps.dount.uis.activity_map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.apps.dount.R;
import com.apps.dount.databinding.ActivityMapBinding;
import com.apps.dount.model.LocationModel;
import com.apps.dount.mvvm.ActivitymapMvvm;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.apps.dount.uis.activity_base.FragmentMapTouchListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {
    private ActivityMapBinding binding;
    private String lang;
    private GoogleMap mMap;
    private float zoom = 15.0f;
    private ActivitymapMvvm activitymapMvvm;
    private ActivityResultLauncher<String> permissionLauncher;
    private LocationModel locationmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        initView();

    }


    private void initView() {


        binding.setLang(getLang());

        activitymapMvvm = ViewModelProviders.of(this).get(ActivitymapMvvm.class);
        activitymapMvvm.setContext(this);
        activitymapMvvm.setLang(getLang());
        activitymapMvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.progBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progBar.setVisibility(View.GONE);
                }
            }
        });
        activitymapMvvm.getLocationData().observe(this, locationModel -> {
            addMarker(locationModel.getLat(), locationModel.getLng());
            MapActivity.this.locationmodel = locationModel;
            binding.edtSearch.setText(locationModel.getAddress());
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                activitymapMvvm.initGoogleApi();

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

            }
        });
        binding.flchek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationmodel != null) {
                    Intent intent = getIntent();
                    intent.putExtra("location", locationmodel);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        binding.edtSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String query = binding.edtSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(query)) {
                    Log.e("q", query);
                    activitymapMvvm.Search(query, getLang());
                }
            }
            return false;
        });
        updateUI();
        checkPermission();
    }

    private void updateUI() {
        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.map, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);

        FragmentMapTouchListener fragmentMapTouchListener = (FragmentMapTouchListener) getSupportFragmentManager().findFragmentById(R.id.map);
        fragmentMapTouchListener.setListener(() -> {
            binding.fl.requestDisallowInterceptTouchEvent(true);
        });


    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, BaseActivity.fineLocPerm) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(BaseActivity.fineLocPerm);
        } else {

            activitymapMvvm.initGoogleApi();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            activitymapMvvm.startLocationUpdate();

        }
    }


    @Override
    public void onBackPressed() {
        back();
    }


    public void back() {

        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);
            if (activitymapMvvm.getGoogleMap().getValue() == null) {
                activitymapMvvm.setmMap(mMap);

            }


        }
    }

    private void addMarker(double lat, double lng) {
        if (activitymapMvvm.getGoogleMap().getValue() != null) {
            mMap = activitymapMvvm.getGoogleMap().getValue();
        }
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));

    }


}
