package com.smart.life.ui;

import com.smart.life.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class VoucherHelpActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_help);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_voucher_help, menu);
        return true;
    }

    
}
