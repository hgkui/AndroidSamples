package com.shop.mall.app.api.coverter;

import com.blankj.utilcode.util.ApiUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.shop.mall.app.Api;
import com.shop.mall.app.base.BaseResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static kotlin.text.Charsets.UTF_8;


/**
 * @author fang.xc@outlook.com
 * @date 2018/5/10
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter adapter;

    public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //将返回的json数据储存在String类型的response中
        String response = value.string();
        BaseResponse httpResult = gson.fromJson(response, BaseResponse.class);
        //服务端设定0为正确的请求，故在此为判断标准
        if (Api.API_CODE_SUCCESS.equals(httpResult.getCode())) {
            //直接解析，正确请求不会导致json解析异常
            MediaType contentType = value.contentType();
            Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
            InputStream inputStream = new ByteArrayInputStream(response.getBytes());
            Reader reader = new InputStreamReader(inputStream, charset);
            JsonReader jsonReader = gson.newJsonReader(reader);

            try {
                return (T) adapter.read(jsonReader);
            } catch (Exception e) {
                //定义错误响应体，并通过抛出自定义异常传递错误码及错误信息
                //定义错误响应体，并通过抛出自定义异常传递错误码及错误信息/
                ApiException myException = new ApiException(httpResult.getCode(), e.getLocalizedMessage());
                LogUtils.e(myException);
                throw myException;
            } finally {
                value.close();
            }
        } else {
            //定义错误响应体，并通过抛出自定义异常传递错误码及错误信息/
            ApiException e = new ApiException(httpResult.getCode(), httpResult.getMsg());
            LogUtils.e(e);
            throw e;
        }
    }
}

