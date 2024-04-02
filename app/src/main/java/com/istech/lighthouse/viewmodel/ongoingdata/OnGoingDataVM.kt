package com.istech.lighthouse.viewmodel.ongoingdata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.model.ongoingdata.OnGoingReportValues
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap

class OnGoingDataVM : ViewModel() {
    private val disposable: CompositeDisposable = CompositeDisposable()
    var response: MutableLiveData<OnGoingReportValues> = MutableLiveData<OnGoingReportValues>()


    fun getOngoingColumnValuesApi(params: HashMap<String, Any>) {
        disposable.add(
            Global.initRetrofit().getOngoingvalues(params)
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