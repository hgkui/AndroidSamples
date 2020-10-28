package com.shop.mall.app.base

import com.shop.mall.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class BaseUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val oldUrl = request.url()
        val builder = request.newBuilder()
        if (BuildConfig.DEBUG) {
//            val urlConfigBean = PrefsHelper.getPrefsHelper().getBaseUrl()
//            val newUrl = HttpUrl.parse(urlConfigBean!!.url)
            // 重建新的HttpUrl，修改需要修改的url部分
//            val newFullUrl = oldUrl
//                    .newBuilder()
//                    // 更换网络协议
//                    .scheme(newUrl!!.scheme())
//                    // 更换主机名
//                    .host(newUrl.host())
//                    // 更换端口
//                    .port(newUrl.port())
//                    .build()
            // 重建这个request，通过builder.url(newFullUrl).build()；
            // 然后返回一个response至此结束修改
//            return chain.proceed(builder.url(newFullUrl).build())
        }
        return chain.proceed(request)
    }
}