package com.istech.lighthouse.viewmodel.statics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.model.statics.StaticsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap

class StaticsVM : ViewModel() {
    private val disposable: CompositeDisposable = CompositeDisposable()
    var response: MutableLiveData<StaticsModel> = MutableLiveData<StaticsModel>()

    fun getStaticsApi(params: HashMap<String, Any>) {
        disposable.add(
            Global.initRetrofit().getStaticsData(params)
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