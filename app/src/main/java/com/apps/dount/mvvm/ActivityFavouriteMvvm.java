package com.apps.dount.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dount.model.ProductDataModel;
import com.apps.dount.model.ProductModel;
import com.apps.dount.model.StatusResponse;
import com.apps.dount.model.UserModel;
import com.apps.dount.remote.Api;
import com.apps.dount.tags.Tags;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityFavouriteMvvm extends AndroidViewModel {

    private MutableLiveData<List<ProductModel>> listMutableLiveData;

    private MutableLiveData<Boolean> isLoadingLiveData;

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> addremove;

    public ActivityFavouriteMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<ProductModel>> getFavouriteList() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
        return listMutableLiveData;
    }

    public MutableLiveData<Boolean> getFav() {
        if (addremove == null) {
            addremove = new MutableLiveData<>();
        }
        return addremove;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLiveData == null) {
            isLoadingLiveData = new MutableLiveData<>();
        }
        return isLoadingLiveData;
    }

    public void getFavourites(UserModel userModel, String lang) {

        if (userModel == null) {
            isLoadingLiveData.setValue(false);
            listMutableLiveData.setValue(new ArrayList<>());
            return;
        }
        isLoadingLiveData.setValue(true);

        Api.getService(Tags.base_url)
                .getFavourites(lang, userModel.getData().getAccess_token())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ProductDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<ProductDataModel> response) {
                        isLoadingLiveData.postValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            Log.e("ddlldldl", response.body().getStatus() + "");
                            if (response.body().getStatus() == 200) {
                                // List<ProductModel> list = response.body().getData();
                                listMutableLiveData.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }

    public void addRemoveFavourite(String id, UserModel userModel) {
        Api.getService(Tags.base_url)
                .addRemoveFav(userModel.getData().getAccess_token(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        Log.e("lllll", response.body().getStatus() + "" + id + " " + userModel.getData().getAccess_token());
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200 || response.body().getStatus() == 201) {

                                addremove.postValue(true);

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        //Log.e(TAG, "onError: ", e);
                    }
                });

    }

}
