package noone.pro.mrvull.sampleapp.apihelper

import io.reactivex.Single
import noone.pro.mrvull.sampleapp.config.Constant
import noone.pro.mrvull.sampleapp.model.NearByResult
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIService {
    @GET(Constant.GET_RESTAURANTS)
    fun getRestaurants(): Single<NearByResult>

    companion object {
        fun getService(): APIService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(Constant.URL_API)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(APIService::class.java)
        }
    }
}