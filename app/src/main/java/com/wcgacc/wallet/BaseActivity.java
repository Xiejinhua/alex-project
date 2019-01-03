package com.wcgacc.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Alex
 * @since 2018/12/13
 */
public class BaseActivity extends AppCompatActivity {
    private TextView mTitle;
    private RelativeLayout mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SysApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //设置标题栏
    public void setupToolbar(String title) {
        mTitle = (TextView) findViewById(R.id.second_bar_title);
        mTitle.setText(title);
        mBack = (RelativeLayout) findViewById(R.id.second_bar_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setupToolbar(String title, final View.OnClickListener listener) {
        mTitle = (TextView) findViewById(R.id.second_bar_title);
        mTitle.setText(title);
        mBack = (RelativeLayout) findViewById(R.id.second_bar_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });

    }


    //刷新数据
    public void refreshData() {

    }


}
