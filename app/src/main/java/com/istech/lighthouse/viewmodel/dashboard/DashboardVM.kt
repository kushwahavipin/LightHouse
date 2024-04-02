package com.istech.lighthouse.viewmodel.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.model.dashboard.DashboardModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap

class DashboardVM : ViewModel() {
    private val disposable: CompositeDisposable = CompositeDisposable()
    var response: MutableLiveData<DashboardModel> = MutableLiveData<DashboardModel>()

    fun getDashboardApi(params: HashMap<String, Any>) {
        try {
            disposable.add(
                Global.initRetrofit().getDashboardData(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe { dashboardModel, throwable ->
                        if (dashboardModel != null) {
                            response.postValue(dashboardModel)
                        }else{
                            response.postValue(null)
                        }
                    })
        }catch (e:Exception){
            response.postValue(null)
        }

    }


}