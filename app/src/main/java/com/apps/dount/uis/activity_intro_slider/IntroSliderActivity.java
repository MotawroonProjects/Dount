package com.apps.dount.uis.activity_intro_slider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.apps.dount.R;
import com.apps.dount.adapter.IntroAdapter;
import com.apps.dount.databinding.ActivityIntroSliderBinding;
import com.apps.dount.language.Language;
import com.apps.dount.model.UserSettingsModel;
import com.apps.dount.preferences.Preferences;
import com.apps.dount.uis.activity_home.HomeActivity;

import io.paperdb.Paper;

public class IntroSliderActivity extends AppCompatActivity {
    private ActivityIntroSliderBinding binding;
    private IntroAdapter adapter;
    private Preferences preferences;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_slider);
        initView();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        binding.tab.setupWithViewPager(binding.pager);
        adapter = new IntroAdapter(this);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(4);
        for (int i = 0; i < binding.tab.getTabCount(); i++) {
            View tab = ((ViewGroup) binding.tab.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(10, 0, 10, 0);
            tab.requestLayout();

            binding.btnSkip.setPaintFlags(binding.btnSkip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


            binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == adapter.getCount() - 1) {
                        binding.btnStart.setVisibility(View.VISIBLE);
                        binding.btnSkip.setVisibility(View.INVISIBLE);
                        binding.btnNext.setVisibility(View.INVISIBLE);

                    } else {
                        binding.btnStart.setVisibility(View.INVISIBLE);
                        binding.btnSkip.setVisibility(View.VISIBLE);
                        binding.btnNext.setVisibility(View.VISIBLE);
                    }


                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            binding.btnSkip.setOnClickListener(view -> {
                start();
            });

            binding.btnNext.setOnClickListener(view -> binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1));
            binding.btnStart.setOnClickListener(view -> {
                start();
            });

        }
    }

    private void start() {
        UserSettingsModel defaultSettings = preferences.getAppSetting(this);
        if (defaultSettings != null) {
            // Log.e("lsdkdk","dkkdkkd");
            defaultSettings.setShowIntroSlider(false);
            preferences.createUpdateAppSetting(this, defaultSettings);
        } else {
            defaultSettings = new UserSettingsModel();
            defaultSettings.setShowIntroSlider(false);
            preferences.createUpdateAppSetting(this, defaultSettings);
        }

        Intent intent = new Intent(this, HomeActivity.class);

        startActivity(intent);
        finish();


    }

}