package com.demo.tbdetails;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {
    @Bind(R.id.recycler_view)
    LRecyclerView recyclerView;
    @Bind(R.id.left)
    TextView left;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.right)
    TextView right;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.title_bar)
    RelativeLayout titleBar;
    @Bind(R.id.titlel)
    TextView titlel;
    @Bind(R.id.titler)
    TextView titler;
    @Bind(R.id.button)
    LinearLayout title_button;
    private float totaldy;
    private float mRecyclerFactor;
    private List<DetailsBean> list;
    private int item1 = 0;
    private int item2 = 0;
    private int item3 = 0;
    private LinearLayoutManager manager;
    private Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTitle();
        initListener();
        initData();
    }
    private void initTitle() {
        res=getResources();
        StatusBarUtil.setTranslucentForImageView(this, 0, titleBar);
        left.setBackgroundResource(R.mipmap.back_b);
//        right.setBackgroundResource(R.mipmap.add);
        right.setVisibility(View.GONE);
        icon.setVisibility(View.VISIBLE);
        //        图片的高度-状态栏的高度
        mRecyclerFactor = (DensityUtil.dp2px(this, 180.0F) - DensityUtil.getStatusBarHeight(this));

    }
    private void initListener() {

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item1 != 0) {
                    recyclerView.scrollBy(0, (int) -totaldy);
                }
            }
        });
        titlel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item2 != 0) {
//                    判断滑动距离是否超过商品
                    if (totaldy > item1)

                        recyclerView.scrollBy(0, (int) -(totaldy - item1) + 20);
                    else
                        recyclerView.scrollBy(0, (int) (item1 - totaldy) + 20);

                }
            }
        });
        titler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item3 != 0) {
                    recyclerView.scrollBy(0, item2);
                }
            }
        });
        //        设置渐变的主要代码
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recycler, int dx, int dy) {
                super.onScrolled(recycler, dx, dy);

                //滑动的距离
                totaldy += dy;
                if (item2 != 0 && item1 != 0 && item3 != 0) {
                    if (totaldy < item1) {
                        title.setTextColor(res.getColor(R.color.orange));
                        titlel.setTextColor(res.getColor(R.color.black));
                        titler.setTextColor(res.getColor(R.color.black));
                    } else if (totaldy > item1 && totaldy < item2) {
                        titlel.setTextColor(res.getColor(R.color.orange));
                        title.setTextColor(res.getColor(R.color.black));
                        titler.setTextColor(res.getColor(R.color.black));
                    } else if (totaldy > item2) {
                        titler.setTextColor(res.getColor(R.color.orange));
                        title.setTextColor(res.getColor(R.color.black));
                        titlel.setTextColor(res.getColor(R.color.black));
                    }
                }
                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (totaldy <= mRecyclerFactor) {
//                    如果在显示图片中显示圆图标
//                    算出透明度
                    float scale = (float) totaldy / mRecyclerFactor;
                    float alpha = scale * 255;

                    if (alpha < 160) {
//                        如果透明度小于160设置为顶部是图片
                        title_button.setVisibility(View.GONE);
                        StatusBarUtil.setTranslucentForImageView(DetailsActivity.this, (int) alpha, titleBar);
                    } else {
                        title_button.setVisibility(View.VISIBLE);
                        StatusBarUtil.setColor(DetailsActivity.this, Color.argb((int) alpha, 255, 255, 255));
                    }
                    titleBar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                } else {
//                   已经不显示图片
                    titleBar.setBackgroundColor(Color.parseColor("#ffffff"));
                    title_button.setVisibility(View.VISIBLE);
                }

            }
        });
    }
    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            DetailsBean bean = new DetailsBean();
            bean.setType(i + 1);
            list.add(bean);
        }
        OkHttpEngine okHttpEngine=new OkHttpEngine();
        okHttpEngine.get(this, "http://wx.baixinglg.cn/shop_mobile/item/wapdesc/10599.mobile",
                new HttpListener() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void setParams(Context context, Map<String, Object> value) {

                    }

                    @Override
                    public void onSuccess(String result) {
                        TestBean bean = JsonUtil.getObjectFromString(result, TestBean.class);
                        setAdapter(list, bean.getData());
                    }
                });


    }

    private void setAdapter(List<DetailsBean> list, String url) {
        recyclerView.setNestedScrollingEnabled(false);
        DetailsAdapter adapter = new DetailsAdapter(this, url);
        adapter.setDataList(list);
        LRecyclerViewAdapter adapter1 = new LRecyclerViewAdapter(adapter);
        View headView = View.inflate(this, R.layout.details_head, null);
        adapter1.addHeaderView(headView);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter1);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadMoreEnabled(false);
        adapter.setListener(new DetailsAdapter.OnItemHeightListener() {
            @Override
            public void setOnItemHeightListener(int height, int type) {
                if (height != 0) {
                    if (type == 1001) {
                        item1 = (int) (height + mRecyclerFactor);
                    } else if (type == 1002) {
                        item2 = item1 + (height - DensityUtil.getWidth(DetailsActivity.this));
                    } else {
                        item3 = item2 + height;
                    }
                }

            }
        });

    }



    @OnClick({R.id.left, R.id.icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left:
                finish();
                break;
            case R.id.icon:
                break;
        }
    }
}
