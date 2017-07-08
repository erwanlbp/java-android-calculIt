package com.erwanlbp.calculit.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by ErwanLBP on 07/07/17.
 */

public class BaseActivity extends AppCompatActivity {

    // ===== CONSTANTS =====

    private static int value = 1;

    // ----- Request Codes
    public final static int RQ_GAME = value++;
    public final static int RQ_ANSWER = value++;
    public final static int RQ_USER = value++;
    public final static int RQ_SHOW_HIGH_SCORE = value++;

    // ----- Result Codes
    public static final int RC_WON = value++;
    public static final int RC_LOSE = value++;
    public static final int RC_LOGGED_IN = value++;
    public static final int RC_NOT_LOGGED_IN = value++;

    // ===== END CONSTANTS =====


    public ProgressBar mProgressBar;

    public void showProgressBar() {
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
