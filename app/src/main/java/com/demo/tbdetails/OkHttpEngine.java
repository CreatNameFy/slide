package com.demo.tbdetails;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 方燚
 * time 2017/12/8.
 * okhttp访问
 */
public class OkHttpEngine  {
    private static final String TAG="TAG_OkHttpEngine";
    private static OkHttpClient mOkHttpClient;
    private static Handler mHandler=new Handler();
    public void getOkHttp() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            mOkHttpClient = builder.build();
        }
    }

    public void get(Context context, String url, final IHttpCallback callback) {
        getOkHttp();

        Request.Builder requestBuilder = new Request.Builder().url(url).tag(context);
        //可以省略，默认是GET请求
        Request request = requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.error(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(resultJson);
                    }
                });

                Log.e("Get返回结果：", resultJson);
            }
        });
    }






}
