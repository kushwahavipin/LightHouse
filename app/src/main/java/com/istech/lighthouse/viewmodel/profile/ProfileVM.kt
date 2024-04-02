package com.istech.lighthouse.viewmodel.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.model.profile.ProfileModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap

class ProfileVM : ViewModel() {
    private val disposable: CompositeDisposable = CompositeDisposable()
    var response: MutableLiveData<ProfileModel> = MutableLiveData<ProfileModel>()

    fun getProfileApi(params: HashMap<String, Any>) {
        disposable.add(
            Global.initRetrofit().getProfileData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe { video, throwable ->
                    if (video != null) {
                        response.setValue(video)
                    }
                })
    }


}