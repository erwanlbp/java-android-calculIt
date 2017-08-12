package com.erwanlbp.calculit.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.erwanlbp.calculit.R;

import butterknife.BindView;

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

    @Nullable
    @BindView(R.id.loading)
    public ProgressBar mProgressBar;

    public void showProgressDialog() {
        if (mProgressBar == null) {
            Toast.makeText(this, "Loading ...", Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        if (mProgressBar != null && mProgressBar.isShown()) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressBar();
    }
}
