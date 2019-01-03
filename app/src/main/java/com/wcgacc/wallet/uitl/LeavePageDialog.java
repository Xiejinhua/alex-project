package com.wcgacc.wallet.uitl;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wcgacc.wallet.R;


/**
 * Created by davy on 2017/7/28.
 */

public class LeavePageDialog {

    private static LeavePageDialog leavePageDialog;
    private DialogUtil dialog;
    public static LeavePageDialog getInstance() {
        leavePageDialog = new LeavePageDialog();
        return leavePageDialog;
    }
    private TextView ok;
    private TextView cancel,leave_content;


    public void show(final Activity activity) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_leave_page, null);
        dialog = new DialogUtil(activity, view,true);
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        ok=(TextView)view.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                activity.finish();
            }
        });
        cancel=(TextView)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();


    }
}
