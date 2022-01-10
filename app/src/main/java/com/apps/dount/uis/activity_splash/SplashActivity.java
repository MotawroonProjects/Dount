package com.apps.dount.uis.activity_splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apps.dount.R;
import com.apps.dount.preferences.Preferences;
import com.apps.dount.uis.activity_base.BaseActivity;
import com.apps.dount.uis.activity_home.HomeActivity;
import com.apps.dount.databinding.ActivitySplashBinding;
import com.apps.dount.uis.activity_intro_slider.IntroSliderActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Animation animation;
    private Animation animation1;
    private Animation animation2;
    private Animation animation3;
    private Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();

    }

    private void initView() {
        preferences = Preferences.getInstance();
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        animateMethod();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void animateMethod() {
        animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.lanuch);
        animation1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_up);
        animation2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_up);
        animation3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_up);

        binding.cons.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                binding.tv1.startAnimation(animation1);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.tv1.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.tv2.startAnimation(animation2);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.tv2.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.tv3.startAnimation(animation3);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.tv3.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                navigateToHomeActivity();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    private void navigateToHomeActivity() {
        Intent intent;
        if (preferences.getAppSetting(this) == null || preferences.getAppSetting(SplashActivity.this).isShowIntroSlider()) {
            intent = new Intent(this, IntroSliderActivity.class);
            startActivity(intent);
            finish();
        } else {


            intent = new Intent(this, HomeActivity.class);

            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}

