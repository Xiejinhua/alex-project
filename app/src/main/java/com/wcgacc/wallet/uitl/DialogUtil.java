package com.wcgacc.wallet.uitl;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.wcgacc.wallet.R;


/**
 * dialog工具类
 *
 * @author davy
 * @since 17/7/28
 */
public class DialogUtil extends Dialog {



    //传true的话是可以返回键关闭dialog的
    public DialogUtil(Context context, View view, boolean isCancel) {
        super(context, R.style.Transparent);

        init(view,isCancel);
    }

    //这里是没有状态栏的初始化
    public DialogUtil(Context context, View view) {

            super(context, R.style.TransparentNoActionBar);

        init(view,false);
    }

    private void init(View view, boolean isCancel) {

        //Dialog无边框
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
       // setCanceledOnTouchOutside(false);
        //设置对话框Dialog透明
        getWindow().setBackgroundDrawableResource(R.mipmap.bg_ranslucent);
        if(!isCancel) {
            setCancelable(false);
        }
        setContentView(view);


    }



}
