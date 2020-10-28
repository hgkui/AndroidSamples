package com.shop.mall.app.helper
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.shop.mall.app.MyApplication
import com.shop.mall.app.helper.SharedPreferencesInterface

/**
 *
 * SharedPreferences帮助类
 * @author fang.xc@outlook.com
 * @date 2019/3/1
 */
object PrefsHelper : SharedPreferencesInterface {

    /**
     * baseUrl
     */
    const val KEY_SP_BASE_URL = "baseUrl"

    /**
     * 用户信息
     */
    const val KEY_SP_USER_INFO = "userInfo"

    /**
     * token
     */
    const val KEY_SP_TOKEN = "token"

    /**
     * 是否第一次进入app
     */
    const val KEY_SP_IS_FIRST = "isFirst"

    /**
     * 是否展示隐私告知弹窗
     */
    const val KEY_SP_SHOWN_PRIVACY_NOTIFY = "shownPrivacyNotify"

    /**
     * 是否预热
     */
    const val KEY_SP_IS_PRE_HOT = "preHot"

    private var mSharedPreferences: SharedPreferences
    private val SP_NAME = "config"
    private var mGson: Gson? = null


    init {
        mGson = Gson()
        mSharedPreferences =
            MyApplication.instance!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    @JvmStatic
    fun getPrefsHelper(): PrefsHelper = this


    /**
     * 清除用户信息
     */
    override fun clearUserInfo() {
        mSharedPreferences.edit()
            .remove(KEY_SP_TOKEN)
            .remove(KEY_SP_USER_INFO)
            .apply()
    }


    /**
     * 存放token
     */
    override fun putToken(token: String) {
        mSharedPreferences.edit().putString(KEY_SP_TOKEN, token).apply()
    }

    /**
     * 获取token
     */
    override fun getToken(): String {
        return mSharedPreferences.getString(KEY_SP_TOKEN, "") ?: ""
    }

    /**
     * 是否是第一次进入
     */
    override fun putIsFirst(isFirst: Boolean) {
        mSharedPreferences.edit().putBoolean(KEY_SP_IS_FIRST, isFirst).apply()
    }


    /**
     * 获取当前用户是否是第一次进入
     */
    override fun getIsFirst(): Boolean {
        return mSharedPreferences.getBoolean(KEY_SP_IS_FIRST, true)
    }


//    /**
//     * 获取用户信息
//     */
//    override fun getUserInfo(): LoginResponse? {
//        val str = mSharedPreferences.getString(KEY_SP_USER_INFO, null)
//        return if (TextUtils.isEmpty(str)) null else mGson!!.fromJson<LoginResponse>(str, LoginResponse::class.java)
//    }
//
//    /**
//     * 存入用户信息
//     */
//    override fun putUserInfo(response: LoginResponse) {
//        if (mGson == null) {
//            mGson = Gson()
//        }
//        mSharedPreferences.edit().putString(KEY_SP_USER_INFO, mGson!!.toJson(response)).apply()
//    }

//    /**
//     * 存入baseUrl
//     */
//    override fun putBaseUrl(urlBean: UrlConfigBean) {
//        if (mGson == null) {
//            mGson = Gson()
//        }
//        mSharedPreferences.edit().putString(KEY_SP_BASE_URL, mGson!!.toJson(urlBean)).apply()
//    }

//    /**
//     * 获取Url
//     */
//    override fun getBaseUrl(): UrlConfigBean? {
//        val str = mSharedPreferences.getString(KEY_SP_BASE_URL, "") ?: ""
//        val bean = if (TextUtils.isEmpty(str)) {
//            null
//        } else {
//            try {
//                mGson?.fromJson<UrlConfigBean>(str, UrlConfigBean::class.java)
//            } catch (e: Exception) {
//                null
//            }
//        }
//        return bean
//    }

    /**
     * 存储是否预热
     */
    override fun putIsPreHot(boolean: Boolean) {
        mSharedPreferences.edit().putBoolean(KEY_SP_IS_PRE_HOT, boolean).apply()
    }

    /**
     * 获取是否预热
     */
    override fun getIsPreHot() = mSharedPreferences.getBoolean(KEY_SP_IS_PRE_HOT, false)


    /**
     * 存放是否已经弹过隐私告知弹窗
     */
    override fun putShownPrivacyNotify(shown: Boolean) {
        mSharedPreferences.edit().putBoolean(KEY_SP_SHOWN_PRIVACY_NOTIFY, shown).apply()
    }

    /**
     * 是否已经弹过隐私告知弹窗
     */
    override fun getShownPrivacyNotify(): Boolean =
        mSharedPreferences.getBoolean(KEY_SP_SHOWN_PRIVACY_NOTIFY, false)

}