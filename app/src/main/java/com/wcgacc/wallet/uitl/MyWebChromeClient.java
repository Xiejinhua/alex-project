package com.wcgacc.wallet.uitl;

import android.os.Message;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * @author Alex
 * @since 2018/12/13
 */

public class MyWebChromeClient extends WebChromeClient {
    private ProgressListener mListener;

    public MyWebChromeClient(ProgressListener listener) {
        mListener = listener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mListener.onUpdateProgress(newProgress);
        super.onProgressChanged(view, newProgress);
    }

    public interface ProgressListener {
        public void onUpdateProgress(int progressValue);
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
    }
}
