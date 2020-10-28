/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shop.mall.app;

import android.content.Context;
import android.net.ParseException;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import retrofit2.HttpException;
import timber.log.Timber;


public class ResponseErrorListenerImpl implements ResponseErrorListener {

    @Override
    public void handleResponseError(Context context, Throwable t) {
        Timber.tag("Catch-Error").w(t.getMessage());
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        String msg = t.getLocalizedMessage();
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException || t instanceof ConnectException || t instanceof SocketException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException || t instanceof IOException) {
            msg = "网络似乎断开了";
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
            msg = "数据解析错误";
        }
//        else if (t instanceof ApiException) {
//            if (Api.API_CODE_LOGIN_INVALID.equals(((ApiException) t).getCode())) {
//                PrefsHelper.getPrefsHelper().clearUserInfo();
//
//                if (BuildConfig.isBusiness) {
//                    if (!(AppManager.getAppManager().getTopActivity() instanceof LoginActivity)) {
//                        ActivityUtils.finishAllActivities();
//                        ArmsUtils.startActivity(LoginActivity.class);
//                    }
//
//                } else {
//                    if (!(AppManager.getAppManager().getTopActivity() instanceof DriverLoginActivity)) {
//                        ArmsUtils.startActivity(DriverLoginActivity.class);
//                    }
//                }
//                msg = context.getString(R.string.login_invalid);
//            } else {
//                msg = ((ApiException) t).getErrorMsg();
//            }
//        }
        if (!TextUtils.isEmpty(msg)) {
            ToastUtils.showShort(msg);
        }

    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
