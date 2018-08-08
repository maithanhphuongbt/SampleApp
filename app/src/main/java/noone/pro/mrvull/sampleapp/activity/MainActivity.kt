package noone.pro.mrvull.sampleapp.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_network_state.*
import noone.pro.mrvull.sampleapp.R
import noone.pro.mrvull.sampleapp.adapter.LocationAdapter
import noone.pro.mrvull.sampleapp.data.NetworkState
import noone.pro.mrvull.sampleapp.data.Status
import noone.pro.mrvull.sampleapp.model.Location
import noone.pro.mrvull.sampleapp.model.LocationViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var locationViewModel: LocationViewModel

    private lateinit var locationAdapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        locationAdapter = LocationAdapter {
            locationViewModel.retry()
        }
        rcRestaurant.layoutManager = linearLayoutManager
        rcRestaurant.adapter = locationAdapter
        rcRestaurant.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))
        locationViewModel.userList.observe(this, Observer<PagedList<Location>> {
            locationAdapter.submitList(it)
        })
        locationViewModel.getNetworkState().observe(this, Observer<NetworkState> {
            locationAdapter.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh() {
        locationViewModel.getRefreshState().observe(this, Observer { networkState ->
            if (locationAdapter.currentList != null) {
                if (locationAdapter.currentList!!.size > 0) {
                    usersSwipeRefreshLayout.isRefreshing = networkState?.status == NetworkState.LOADING.status
                } else {
                    setInitialLoadingState(networkState)
                }
            } else {
                setInitialLoadingState(networkState)
            }
        })
        usersSwipeRefreshLayout.setOnRefreshListener { locationViewModel.refresh() }
    }

    private fun setInitialLoadingState(networkState: NetworkState?) {
        errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }

        retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        usersSwipeRefreshLayout.isEnabled = networkState?.status == Status.SUCCESS
        retryLoadingButton.setOnClickListener { locationViewModel.retry() }
    }
}
