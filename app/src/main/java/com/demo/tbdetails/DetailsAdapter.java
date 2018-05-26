package com.demo.tbdetails;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;


/**
 * Created by ：方燚
 * time       ：2018/2/5.
 * description:地址列表
 */

public class DetailsAdapter extends ListBaseAdapter<DetailsBean> {
    private int layoutID;
    private String url;
    private int height=0;

    public DetailsAdapter(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public int getItemViewType(int position) {
        int type = mDataList.get(position).getType();
        if (type == 1) {
//            商品
            layoutID = R.layout.item_details1;
            return 1001;
        } else if (type == 2) {
//            详情
            layoutID = R.layout.item_details2;
            return 1002;
        } else if (type == 3) {
//            评价
            layoutID = R.layout.item_details3;
            return 1003;
        } else if (type == 4) {
//            评价
            layoutID = R.layout.item_details4;
            return 1004;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getLayoutId() {
        return layoutID;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == 1001) {
            final LinearLayout item = holder.getView(R.id.item);
            getMeasureHeight(item,type);
        }
        if (type == 1003) {
            final LinearLayout item = holder.getView(R.id.item);
            getMeasureHeight(item,type);
        }
        if (type == 1004) {
            final LinearLayout item = holder.getView(R.id.item);
            getMeasureHeight(item,type);
        }
        if (type == 1002) {
            WebView webView = holder.getView(R.id.webView);
            WebSettings mSetting = webView.getSettings();
            mSetting.setJavaScriptEnabled(true);
            mSetting.setBlockNetworkImage(false);//解决图片不显示
            mSetting.setBuiltInZoomControls(true); // 显示放大缩小
            mSetting.setSupportZoom(false);
            url = url.replaceAll("<img", "<img style='width:100%'");
            webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
            getMeasureHeight(webView,type);

        }
    }

    /**
     * 获取每个item的高度
     * @param view  item的跟布局
     * @param type  用于判断是那个item的高度
     */
    public void getMeasureHeight(final View view, final int type) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener!=null){
                    if(type==1003||type==1004){
                        if(height!=0){
                            height+=view.getHeight();
                            listener.setOnItemHeightListener(height,type);
                        }else{
                            height=view.getHeight();
                        }
                    }else{
                        listener.setOnItemHeightListener(view.getHeight(),type);
                    }

                }
            }
        });
    }
    public interface OnItemHeightListener{
        void setOnItemHeightListener(int height, int type);
    }
    private OnItemHeightListener listener;
    public void setListener(OnItemHeightListener listener){
        this.listener=listener;
    }
}
