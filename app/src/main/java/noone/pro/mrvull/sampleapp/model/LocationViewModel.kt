package noone.pro.mrvull.sampleapp.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import noone.pro.mrvull.sampleapp.apihelper.APIService
import noone.pro.mrvull.sampleapp.data.NetworkState
import noone.pro.mrvull.sampleapp.data.datasource.LocationDataSource
import noone.pro.mrvull.sampleapp.data.datasource.LocationDataSourceFactory

class LocationViewModel : ViewModel() {

    var userList: LiveData<PagedList<Location>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 5

    private val sourceFactory: LocationDataSourceFactory

    init {
        sourceFactory = LocationDataSourceFactory(compositeDisposable, APIService.getService())
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(false)
                .build()
        userList = LivePagedListBuilder<Long, Location>(sourceFactory, config).build()

    }

    fun retry() {
        sourceFactory.locationDataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        sourceFactory.locationDataSourceLiveData.value!!.invalidate()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<LocationDataSource, NetworkState>(
            sourceFactory.locationDataSourceLiveData) { it.networkState }

    fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap<LocationDataSource, NetworkState>(
            sourceFactory.locationDataSourceLiveData) { it.initialLoad }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}