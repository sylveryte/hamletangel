package com.codedleaf.sylveryte.hamletangel;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * Created by sylveryte on 30/3/18.
 * Yay!
 */

public class PostOffice extends AbstractAccountAuthenticator {


    private static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    private static final String ARG_AUTH_TYPE = "AUTH_TYPE";
    static final String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_NEW_ACCOUNT";

    static final String ACCOUNT_TYPE = "com.codedleaf.habitica";
    static final String AUTH_ID = "ACCOUNT_API_KEY";
    static final String AUTH_TOKEN = "ACCOUNT_TOKEN";

    private String mAuthId;
    private String mAuthToken;

    private Context mContext;

    PostOffice(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {

        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {


        AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);

        if (TextUtils.isEmpty(authToken)) {
            String[] tokens = Postmaster.userSignIn(account.name, am.getPassword(account));

            if(authTokenType.equals(AUTH_ID))
                authToken=tokens[1];
            else
                authToken=tokens[2];
        }


        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }

    private AccountManagerFuture<Bundle> getTokenForAccountCreateIfNeeded(String accountType, final String authTokenType, Activity activity) {
        AccountManager accountManager=AccountManager.get(mContext);
        final AccountManagerFuture<Bundle> future = accountManager.getAuthTokenByFeatures(accountType, authTokenType, null, activity, null, null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Bundle bnd;
                        try {
                            bnd = future.getResult();
                            Bundle res=new Bundle();
                            final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);

                            res.putString(AccountManager.KEY_AUTHTOKEN,authtoken);
                            res.putString(ARG_AUTH_TYPE,authTokenType);

                            setToken(res);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                ,null);
        return future;
    }

    private void getTokens(Activity activity)
    {
        getTokenForAccountCreateIfNeeded(ACCOUNT_TYPE,AUTH_ID,activity);
        getTokenForAccountCreateIfNeeded(ACCOUNT_TYPE,AUTH_TOKEN,activity);
    }

    private synchronized void setToken(final Bundle bundle) {
        if (bundle==null||bundle.isEmpty())
            return;
        String key=bundle.getString(AccountManager.KEY_AUTHTOKEN);
        String type=bundle.getString(ARG_AUTH_TYPE);
        assert type != null;
        if(type.equals(AUTH_ID))
            mAuthId=key;
        else
            mAuthToken=key;
    }

    void beginUpload(Activity activity, List<HamletTask> tasks)
    {
        if(mAuthToken==null||mAuthId==null) {
            getTokens(activity);
        }
        new TaskUploader().execute(tasks,null,null);
    }

    class TaskUploader extends AsyncTask<List<HamletTask>,Void,List<HamletTask>>{

        @Override
        protected List<HamletTask> doInBackground(List<HamletTask>[] lists) {
            int waitsec=25;
            while (mAuthId==null&&mAuthToken==null)
            {
                if(waitsec<1)
                    return null;
                waitsec--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //mAuths ready

            if(mAuthId!=null&&mAuthToken!=null)
            {
                if(lists.length>0)
                {

                    for (HamletTask task :
                            lists[0]) {
                        String result=Postmaster.uploadTask(task,mAuthId,mAuthToken);
                        if(result!=null) {
                           task.setUploaded();
                            task.setTaskId(result);
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<HamletTask> hamletTasks) {
            super.onPostExecute(hamletTasks);
            AngelLab.getAngelLab(mContext).updateUis();
        }
    }
}
