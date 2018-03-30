package com.codedleaf.sylveryte.hamletangel;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.accounts.AccountManager.KEY_ERROR_MESSAGE;

/**
 * A login screen that offers login via email/password.
 */
public class AuthenticatorActivity extends AppCompatActivity{
    private AccountManager mAccountManager;
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    Button mEmailSignInButton;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountManager=AccountManager.get(this);

        setContentView(R.layout.activity_account_authenticator);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            mEmailSignInButton.setVisibility(View.GONE);
            mProgressView.setVisibility(View.VISIBLE);

            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length()>0;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<String, Void, Intent> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Intent doInBackground(String... strings) {
            // Simulate network access.
            String[] result = Postmaster.userSignIn(mEmail,mPassword);
            Bundle data = new Bundle();
            final Intent res = new Intent();
            if(result[0].equals("true"))
            {
                data.putString(AccountManager.KEY_ACCOUNT_TYPE,PostOffice.ACCOUNT_TYPE);
                data.putString(AccountManager.KEY_ACCOUNT_NAME, mEmail);
                data.putString(AccountManager.KEY_PASSWORD, mPassword);

                data.putString(PostOffice.AUTH_ID, result[1]);
                data.putString(PostOffice.AUTH_TOKEN, result[2]);
                res.putExtras(data);
                return res;
            }
            else {
                data.putString(KEY_ERROR_MESSAGE,result[1]);
                res.putExtras(data);
                return res;
            }
        }

        @Override
        protected void onPostExecute(Intent intent) {
            if(intent!=null)
            {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(),"Wrong Credentials!!", Toast.LENGTH_LONG).show();
                    mEmailSignInButton.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                    mPasswordView.requestFocus();
                    mAuthTask=null;
                } else {
                    finishLogin(intent);
                }
            }
        }
    }

    private void finishLogin(Intent intent) {

        Bundle bundle=intent.getExtras();

        String accountName = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
        String password = bundle.getString(AccountManager.KEY_PASSWORD);
        String accountKey = bundle.getString(PostOffice.AUTH_ID);
        String accountToken = bundle.getString(PostOffice.AUTH_TOKEN);

        final Account account = new Account(accountName, PostOffice.ACCOUNT_TYPE);

        if (getIntent().getBooleanExtra(PostOffice.ARG_IS_ADDING_NEW_ACCOUNT, false)) {

            mAccountManager.addAccountExplicitly(account, password,null);

            mAccountManager.setAuthToken(account, PostOffice.AUTH_ID, accountKey);
            mAccountManager.setAuthToken(account, PostOffice.AUTH_TOKEN, accountToken);

        } else {
            mAccountManager.setPassword(account, password);
        }
        finish();
    }
}

