package com.istech.lighthouse.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.model.CommonResponse
import com.istech.lighthouse.model.login.LoginModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import java.util.HashMap

class LoginVM : ViewModel() {
    private val disposable: CompositeDisposable = CompositeDisposable()
    var loginResponse: MutableLiveData<LoginModel> = MutableLiveData<LoginModel>()
    var forgotPasswordResponse: MutableLiveData<CommonResponse> = MutableLiveData<CommonResponse>()
    fun loginApi(params: HashMap<String, Any>) {
        disposable.add(
            Global.initRetrofit().loginApi(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe { video, throwable ->
                    if (video != null) {
                        loginResponse.setValue(video)
                    }
                })
    }

    fun forgotPasswordApi(params: HashMap<String, Any>) {
        disposable.add(
            Global.initRetrofit().forgotPassword(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe { video, throwable ->
                    if (video != null) {
                        forgotPasswordResponse.setValue(video)
                    }
                })
    }
}