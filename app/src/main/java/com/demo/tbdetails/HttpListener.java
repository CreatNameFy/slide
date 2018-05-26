package com.demo.tbdetails;

import android.content.Context;
import android.widget.Toast;


import java.util.Map;

/**
 * Created by 方燚
 * time 2017/12/11.
 */
public abstract class HttpListener implements IHttpCallback {
    private Context context;


    @Override
    public void error(Exception e) {
        Toast.makeText(context,"网络连接失败",Toast.LENGTH_SHORT).show();
        onError(e);
    }
    public abstract void onError(Exception e);

}
