package com.erwanlbp.calculit.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public class BaseActivity extends AppCompatActivity {

    // ===== CONSTANTS =====

    public static final String APPNAME = "com.erwanlbp.calculit";
    public final String TAG = this.getClass().getName();

    // ----- Codes -----
    private static int value = 1;

    // ----- Request Codes
    public final static int RQ_USER = value++;

    // ----- Result Codes
    public static final int RC_LOGGED_IN = value++;
    public static final int RC_NOT_LOGGED_IN = value++;

    // ===== END CONSTANTS =====


    public ProgressBar mProgressBar;

    public void showProgressDialog() {
        if (mProgressBar == null) {
            mProgressBar = new ProgressBar(this);
            mProgressBar.setIndeterminate(true);
        }

        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressDialog() {
        if (mProgressBar != null && mProgressBar.isShown()) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
