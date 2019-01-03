package com.wcgacc.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wcgacc.wallet.activity.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Alex
 * @since 2018/12/13
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_visit_main)
    TextView tvVisitMain;
    @BindView(R.id.tv_visit_01)
    TextView tvVisit01;
    @BindView(R.id.tv_visit_02)
    TextView tvVisit02;
    @BindView(R.id.tv_visit_03)
    TextView tvVisit03;
    @BindView(R.id.tv_visit_04)
    TextView tvVisit04;
    @BindView(R.id.tv_visit_05)
    TextView tvVisit05;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(activity);
    }

    @OnClick({R.id.tv_visit_main, R.id.tv_visit_01, R.id.tv_visit_02, R.id.tv_visit_03, R.id.tv_visit_04, R.id.tv_visit_05})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_visit_main:
                WebViewActivity.actionStart(Constant.wcg, activity);
                break;
            case R.id.tv_visit_01:
                WebViewActivity.actionStart(Constant.wcg_n01, activity);
                break;
            case R.id.tv_visit_02:
                WebViewActivity.actionStart(Constant.wcg_n02, activity);
                break;
            case R.id.tv_visit_03:
                WebViewActivity.actionStart(Constant.wcg_n03, activity);
                break;
            case R.id.tv_visit_04:
                WebViewActivity.actionStart(Constant.wcg_n04, activity);
                break;
            case R.id.tv_visit_05:
                WebViewActivity.actionStart(Constant.wcg_n05, activity);
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            SysApplication.getInstance().exit();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
