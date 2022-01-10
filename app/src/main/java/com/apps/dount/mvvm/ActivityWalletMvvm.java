package com.apps.dount.mvvm;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dount.model.UserModel;
import com.apps.dount.remote.Api;
import com.apps.dount.tags.Tags;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityWalletMvvm extends AndroidViewModel {
    private Context context;

    public MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingLiveData;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityWalletMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();


    }
    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLiveData == null) {
            isLoadingLiveData = new MutableLiveData<>();
        }
        return isLoadingLiveData;
    }



    public void getProfile(String user_id) {
        isLoadingLiveData.postValue(true);
        Api.getService(Tags.base_url).getProfile(user_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new SingleObserver<Response<UserModel>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull Response<UserModel> userModelResponse) {
               isLoadingLiveData.postValue(false);

                if (userModelResponse.isSuccessful()) {
                   // Log.e("status", userModelResponse.body().getStatus() + "");
                    if (userModelResponse.body().getStatus() == 200) {

                        userModelMutableLiveData.postValue(userModelResponse.body());
                    }
                } else {

                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
              isLoadingLiveData.postValue(false);

            }
        });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
