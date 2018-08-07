package noone.pro.mrvull.sampleapp.apihelper

import noone.pro.mrvull.sampleapp.config.Constant
import noone.pro.mrvull.sampleapp.model.NearByResult
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET(Constant.GET_RESTAURANTS)
    fun getRestaurants(): Call<NearByResult>
}