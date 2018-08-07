package noone.pro.mrvull.sampleapp.apihelper

import noone.pro.mrvull.sampleapp.config.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        var retrofit = Retrofit.Builder()
                .baseUrl(Constant.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()!!
    }
}