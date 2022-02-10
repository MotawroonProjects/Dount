package com.apps.dount.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.dount.model.DepartmentDataModel;
import com.apps.dount.model.DepartmentModel;
import com.apps.dount.model.ProductDataModel;
import com.apps.dount.model.ProductModel;
import com.apps.dount.model.SingleProductDataModel;
import com.apps.dount.model.SliderDataModel;
import com.apps.dount.model.StatusResponse;
import com.apps.dount.model.UserModel;
import com.apps.dount.remote.Api;
import com.apps.dount.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentHomeMvvm extends AndroidViewModel {
    private static final String TAG = "FragmentHomeMvvm";
    private Context context;
    private MutableLiveData<SliderDataModel> sliderDataModelMutableLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoadingLiveData;
    private MutableLiveData<List<DepartmentModel>> departmentLivData;
    private MutableLiveData<List<ProductModel>> offerlistMutableLiveData;
    private MutableLiveData<Boolean> addremove;


    public FragmentHomeMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public MutableLiveData<Boolean> getFav() {
        if (addremove == null) {
            addremove = new MutableLiveData<>();
        }
        return addremove;
    }
    public MutableLiveData<List<ProductModel>> getOfferList() {
        if (offerlistMutableLiveData == null) {
            offerlistMutableLiveData = new MutableLiveData<>();
        }
        return offerlistMutableLiveData;
    }


    public MutableLiveData<SliderDataModel> getSliderDataModelMutableLiveData() {

        if (sliderDataModelMutableLiveData == null) {
            sliderDataModelMutableLiveData = new MutableLiveData<>();
        }
        return sliderDataModelMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLiveData == null) {
            isLoadingLiveData = new MutableLiveData<>();
        }
        return isLoadingLiveData;
    }

    public LiveData<List<DepartmentModel>> getCategoryData() {
        if (departmentLivData == null) {
            departmentLivData = new MutableLiveData<>();

        }
        return departmentLivData;
    }


    public void getSlider() {


        isLoadingLiveData.setValue(true);

        Api.getService(Tags.base_url)
                .getSlider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SliderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SliderDataModel> response) {
                        isLoadingLiveData.postValue(false);

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                sliderDataModelMutableLiveData.postValue(response.body());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }

    public void getDepartment(String lang) {
        isLoadingLiveData.postValue(true);
        Api.getService(Tags.base_url)
                .getDepartments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<Response<DepartmentDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<DepartmentDataModel> response) {
                        isLoadingLiveData.postValue(false);

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                List<DepartmentModel> list = response.body().getData();
                                if (list.size() > 0) {
                                    departmentLivData.setValue(list);
                                }


                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoadingLiveData.postValue(false);
                        Log.e(TAG, "onError: ", e);
                    }
                });

    }

    public void getOffers(String lang, UserModel userModel) {

        String token = null;
        if (userModel != null) {
            token = userModel.getData().getAccess_token();
        }

        isLoadingLiveData.setValue(true);

        Api.getService(Tags.base_url)
                .getProducts(token)
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
                            if (response.body().getStatus() == 200) {
                                // List<ProductModel> list = response.body().getData();
                                offerlistMutableLiveData.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
    public void addRemoveFavourite(String id, UserModel userModel) {
        Api.getService(Tags.base_url)
                .addRemoveFav( userModel.getData().getAccess_token(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        Log.e("lllll",response.body().getStatus()+""+id+" "+userModel.getData().getAccess_token());
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200||response.body().getStatus()==201) {

                                addremove.postValue(true);

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });

    }

}
