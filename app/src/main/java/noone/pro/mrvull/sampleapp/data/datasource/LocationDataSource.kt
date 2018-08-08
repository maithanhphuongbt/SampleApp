package noone.pro.mrvull.sampleapp.data.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import noone.pro.mrvull.sampleapp.apihelper.APIService
import noone.pro.mrvull.sampleapp.data.NetworkState
import noone.pro.mrvull.sampleapp.model.Location
import timber.log.Timber

class LocationDataSource(private val githubService: APIService,
                         private val compositeDisposable: CompositeDisposable) : ItemKeyedDataSource<Long, Location>() {

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    private var retryCompletable: Completable? = null

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Timber.e(throwable.message) }))
        }
    }
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Location>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        compositeDisposable.add(githubService.getRestaurants().subscribe({ location ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(location.results as ArrayList<Location>)
        }, { throwable ->
            setRetry(Action { loadInitial(params, callback) })
            val error = NetworkState.error(throwable.message)
            networkState.postValue(error)
            initialLoad.postValue(error)
        }))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Location>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(githubService.getRestaurants().subscribe({ location ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(location.results as ArrayList<Location>)
        }, { throwable ->
            setRetry(Action { loadAfter(params, callback) })
            networkState.postValue(NetworkState.error(throwable.message))
        }))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Location>) {
    }

    override fun getKey(item: Location): Long {
        return 1
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }
}