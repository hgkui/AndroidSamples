package com.shop.mall.app.helper

/**
 * sharePreferences接口类
 *
 * @author fang.xc@outlook.com
 * @date 2019/3/1
 */
interface SharedPreferencesInterface {
    /**
     * 清除用户信息
     */
    fun clearUserInfo()

    /**
     * 是否是第一次进入
     */
    fun putIsFirst(isFirst: Boolean)

    /**
     * 获取当前用户是否是第一次进入
     */
    fun getIsFirst(): Boolean

    /**
     * 存放token
     */
    fun putToken(token: String)

    /**
     * 获取token
     */
    fun getToken(): String

//    /**
//     * 存入用户信息
//     */
//    fun putUserInfo(response: LoginResponse)
//
//    /**
//     * 获取用户信息
//     */
//    fun getUserInfo(): LoginResponse?
//
//    /**
//     * 存入baseUrl
//     */
//    fun putBaseUrl(url: UrlConfigBean)
//
//    /**
//     * 获取Url
//     */
//    fun getBaseUrl(): UrlConfigBean?

    /**
     * 存储是否预热
     */
    fun putIsPreHot(boolean: Boolean)

    /**
     * 获取是否预热
     */
    fun getIsPreHot(): Boolean

    /**
     * 是否已经弹过隐私告知弹窗
     */
    fun getShownPrivacyNotify(): Boolean

    /**
     * 存放是否已经弹过隐私告知弹窗
     */
    fun putShownPrivacyNotify(shown: Boolean)

}