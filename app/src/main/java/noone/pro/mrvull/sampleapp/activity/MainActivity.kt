package noone.pro.mrvull.sampleapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import noone.pro.mrvull.sampleapp.R
import noone.pro.mrvull.sampleapp.adapter.RestaurantAdapter
import noone.pro.mrvull.sampleapp.apihelper.APIService
import noone.pro.mrvull.sampleapp.apihelper.RetrofitClient
import noone.pro.mrvull.sampleapp.model.Location
import noone.pro.mrvull.sampleapp.model.NearByResult
import noone.pro.mrvull.sampleapp.util.DialogHelper
import noone.pro.mrvull.sampleapp.util.ProgressDialogHelper
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private var restaurantAdapter: RestaurantAdapter? = null
    private var progressHelper: ProgressDialogHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initProgressHelper()
        initRecyclerView()
        requestData()
    }

    private fun initProgressHelper() {
        progressHelper = ProgressDialogHelper(this)
        progressHelper!!.createProgressDialog(LOADING_MESSAGE)
    }

    private fun initRecyclerView() {
        rcRestaurant.setHasFixedSize(true)
        rcRestaurant.layoutManager = LinearLayoutManager(this)
        rcRestaurant.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))
    }

    private fun requestData() {
        RetrofitClient.retrofit.create(APIService::class.java)
                .getRestaurants()
                .enqueue(object : retrofit2.Callback<NearByResult> {
                    override fun onFailure(call: Call<NearByResult>?, t: Throwable?) {
                        showErrorMessage()
                    }

                    override fun onResponse(call: Call<NearByResult>?, response: Response<NearByResult>?) {
                        if (response!!.isSuccessful) {
                            bindData(response.body()!!.results as ArrayList<Location>)
                        } else {
                            showErrorMessage()
                        }
                    }
                })
    }

    private fun showErrorMessage() {
        DialogHelper(this).showDialog(ERROR_MESSAGE)
    }

    private fun bindData(locations: ArrayList<Location>) {
        restaurantAdapter = RestaurantAdapter(locations)
        rcRestaurant.adapter = restaurantAdapter
        restaurantAdapter!!.notifyDataSetChanged()
        progressHelper!!.destroyProgressDialog()
    }

    companion object {
        const val ERROR_MESSAGE = "Error!!!"
        const val LOADING_MESSAGE = "Loading..."
    }
}
