package noone.pro.mrvull.sampleapp.data.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import noone.pro.mrvull.sampleapp.apihelper.APIService
import noone.pro.mrvull.sampleapp.model.Location

class LocationDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                                private val service: APIService)
    : DataSource.Factory<Long, Location>() {

    val locationDataSourceLiveData = MutableLiveData<LocationDataSource>()

    override fun create(): DataSource<Long, Location>? {
        val locationDataSource = LocationDataSource(service, compositeDisposable)
        locationDataSourceLiveData.postValue(locationDataSource)
        return locationDataSource
    }

}
