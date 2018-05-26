package com.demo.tbdetails;

import android.content.Context;

import java.util.Map;

/**
 * Created by 方燚
 * time 2017/12/8.
 * 访问网络返回的回调接口
 */
public interface IHttpCallback {
    public void setParams(Context context, Map<String, Object> value);

    public void error(Exception e);

    public void onSuccess(String result);
}
