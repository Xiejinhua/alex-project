package com.wcgacc.wallet.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wcgacc.wallet.BaseActivity;
import com.wcgacc.wallet.R;
import com.wcgacc.wallet.uitl.LeavePageDialog;
import com.wcgacc.wallet.uitl.MyWebChromeClient;


/**
 * @author Alex
 * @since 2018/12/13
 */
public class WebViewActivity extends BaseActivity implements MyWebChromeClient.ProgressListener {

    String payment_url;

    WebView webView;

    SwipeRefreshLayout swipeLayout;


    Context c;

    private ProgressDialog progDailog;

    ProgressBar mProgressBar;
    private Activity activity;
    ImageView second_bar_right;

    /**
     * 跳转
     *
     * @param payment_url
     * @param activity
     */
    public static void actionStart(String payment_url, Activity activity) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("payment_url", payment_url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        this.c = getBaseContext();
        activity = this;
        Bundle extras = getIntent().getExtras();
        payment_url = extras.getString("payment_url");
        second_bar_right = (ImageView) findViewById(R.id.second_bar_right);
        second_bar_right.setBackgroundResource(R.mipmap.refresh_button);
        second_bar_right.setVisibility(View.VISIBLE);
        second_bar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(payment_url);
            }
        });
        initWebView();
        initButtons();
    }

    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
//        swipeLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }


    public void initButtons() {
        setupToolbar("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeavePageDialog.getInstance().show(activity);
            }
        });
    }


    public void initWebView() {
//        progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
//        progDailog.setCancelable(false);

        webView = (WebView) findViewById(R.id.webview_page_content);
        if (isNetworkAvailable(c)) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true); //不显示webview缩放按钮
            webView.getSettings().setDisplayZoomControls(false);

            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.getSettings().setGeolocationEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setDatabaseEnabled(true);

            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
            webView.setWebChromeClient(new MyWebChromeClient(this) {
                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && mProgressBar.getVisibility() == ProgressBar.GONE) {
                        mProgressBar.setVisibility(ProgressBar.VISIBLE);
                    }

                    mProgressBar.setProgress(progress);
                    if (progress == 100) {
                        mProgressBar.setVisibility(ProgressBar.GONE);
                    }
                }

                @Override
                public void onCloseWindow(WebView window) {
                    super.onCloseWindow(window);
                    finish();
                }
            });
            webView.setWebViewClient(new WebViewClient() {

                @SuppressWarnings("deprecation")
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    progDailog.show();
                    view.loadUrl(url);

                    return true;
                }

                @TargetApi(Build.VERSION_CODES.N)
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                    progDailog.show();
                    String url = request.getUrl().toString();
                    view.loadUrl(url);

                    return true;
                }

                @Override
                public void onPageFinished(WebView view, final String url) {

                    webView.scrollTo(0, 0);
                }
            });

            webView.loadUrl(payment_url);

            swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
            swipeLayout.setEnabled(false);
//            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    webView.reload();
//                    if (swipeLayout.isRefreshing()) {
//                        swipeLayout.setRefreshing(false);
//                    }
//                }
//            });


        } else {
            showWIFIAlertToUser();
        }
    }


    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void showWIFIAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("No Connection");
        alertDialogBuilder.setMessage("Please check your Internet Connection")
                .setCancelable(false);
        alertDialogBuilder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webView.canGoBack()) {
                webView.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                LeavePageDialog.getInstance().show(activity);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUpdateProgress(int progressValue) {
        mProgressBar.setProgress(progressValue);
        if (progressValue == 100) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
