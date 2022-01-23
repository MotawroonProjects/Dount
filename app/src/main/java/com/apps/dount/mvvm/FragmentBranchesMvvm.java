package com.apps.dount.mvvm;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.dount.model.BranchDataModel;
import com.apps.dount.model.BranchModel;
import com.apps.dount.remote.Api;
import com.apps.dount.tags.Tags;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentBranchesMvvm extends AndroidViewModel  {
    private static final String TAG = "FragmentNearMvvm";
   
    private Context context;


    private MutableLiveData<List<BranchModel>> branchModelMutableLiveData;

    private MutableLiveData<Boolean> isLoadingLivData;

    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentBranchesMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }




    public LiveData<List<BranchModel>> getBranch() {
        if (branchModelMutableLiveData == null) {
            branchModelMutableLiveData = new MutableLiveData<>();
        }
        return branchModelMutableLiveData;
    }





    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLivData == null) {
            isLoadingLivData = new MutableLiveData<>();
        }
        return isLoadingLivData;
    }

    //_________________________hitting api_________________________________

    public void getBranchData() {
        isLoadingLivData.postValue(true);


        Api.getService(Tags.base_url)
                .getBranches()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<Response<BranchDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<BranchDataModel> response) {
                        isLoadingLivData.postValue(false);

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                List<BranchModel> list = response.body().getData();
                                branchModelMutableLiveData.setValue(list);
                                Log.e("size",list.size()+"");
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoadingLivData.setValue(false);
                        Log.e(TAG, "onError: ", e);
                    }
                });

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
