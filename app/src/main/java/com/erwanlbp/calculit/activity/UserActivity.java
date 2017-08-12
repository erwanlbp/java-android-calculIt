package com.erwanlbp.calculit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.erwanlbp.calculit.R;
import com.erwanlbp.calculit.firebase.FirebaseDB;
import com.erwanlbp.calculit.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class UserActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth auth;
    private GoogleApiClient mGoogleApiClient;

    @BindView(R.id.tvAuthUserPseudo)
    TextView tvPseudo;
    @BindView(R.id.toolbar_user)
    Toolbar toolbarUser;
    @BindView(R.id.sign_in_button)
    SignInButton btnSignIn;
    @BindView(R.id.sign_out_button)
    Button btnSignOut;
    @BindView(R.id.remove_user_button)
    Button btnDisconnect;
    @BindString(R.string.default_web_client_id)
    String dftWebClientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);

        setSupportActionBar(toolbarUser);

        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnDisconnect.setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(dftWebClientID)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        auth = FirebaseAuth.getInstance();

        updateUI(null);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();
        updateUI(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_LOGGED_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                showProgressDialog();
                GoogleSignInAccount acct = result.getSignInAccount();
                firebaseAuthWithGoogle(acct);
            } else {
                Toast.makeText(this, result.getStatus().toString(), Toast.LENGTH_SHORT).show();
                User.getInstance().disconnect();
                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressBar();
                        if (task.isSuccessful()) {
                            FirebaseDB.getFireBaseDB().getUserDatas();
                            backHome();
                        } else {
                            Toast.makeText(UserActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_LOGGED_IN);
    }

    private void signOut() {
        // Firebase sign out
        auth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            User.getInstance().disconnect();
                            updateUI(null);
                        } else {
                            Toast.makeText(UserActivity.this, status.getStatusCode(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void removeAccess() {
        FirebaseDB.getFireBaseDB().deleteAll();

        // Firebase sign out
        auth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            User.getInstance().disconnect();
                            updateUI(null);
                        } else {
                            Toast.makeText(UserActivity.this, status.getStatusCode(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.remove_user_button:
                removeAccess();
                break;
            default:
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            btnSignIn.setVisibility(View.GONE);
            btnDisconnect.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.VISIBLE);
            tvPseudo.setText(user.getDisplayName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            tvPseudo.setText(null);
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            btnDisconnect.setVisibility(View.GONE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        // Can't return home if not logged in
        if (auth.getCurrentUser() == null) {
            this.finishAffinity();
            return;
        }

        super.onBackPressed();
        backHome();
    }

    public void backHome() {
        finish();
    }
}
