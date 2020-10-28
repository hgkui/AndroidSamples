package com.shop.mall.app.helper

import android.content.Context
import com.shop.mall.BuildConfig
import io.objectbox.BoxStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 * @author fang.xc@outlook.com
 * @date 2019-06-03
 */
object ObjectBox : AnkoLogger {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
//        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()


        if (BuildConfig.DEBUG) {
            debug("Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
        }
    }
}