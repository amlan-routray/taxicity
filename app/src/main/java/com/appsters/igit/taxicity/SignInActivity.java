package com.appsters.igit.taxicity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.ExceptionHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_SIGN_IN = 9001;
    private CallbackManager callbackManager=null;
    private static String EMAIL,NAME,PICURL,UID,PHONE;
    EditText phone_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_sign_in);
        phone_edit_text = (EditText) findViewById(R.id.phone_edit_text);
        callbackManager=CallbackManager.Factory.create();
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestIdToken(getString(R.string.server_client_id))
                .requestId().requestProfile().build();
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton button=(SignInButton)findViewById(R.id.google_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetConnection.isEnabled(SignInActivity.this))signIn();
                else
                    Toast.makeText(SignInActivity.this, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
            }
        });
        final LoginButton fbButton=(LoginButton)findViewById(R.id.fb_sign_in_button);
        fbButton.setReadPermissions("public_profile");
        fbButton.setReadPermissions("email");
        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String access_token=loginResult.getAccessToken().getToken();
                Log.d("access token",access_token);
                GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("response",response.toString());
                        try {
                            NAME=object.getString("first_name")+ " " +object.getString("last_name");
                            if (response.toString().contains("email")) EMAIL=object.getString("email");
                            else EMAIL="E-Mail Not Available";
                            UID=object.getString("id");
                            PICURL="https://graph.facebook.com/" + UID+ "/picture?type=large";
                            updateAccount();
                        } catch (JSONException e) {
                            Toast.makeText(SignInActivity.this, "Sorry something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }
            @Override
            public void onCancel() {
                Toast.makeText(SignInActivity.this, "Authentication Could Not Be Finished", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignInActivity.this, "Sorry something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
       
    }
        @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(getWindow().getDecorView().getRootView(),"Connection Error",Snackbar.LENGTH_LONG);
    }
    public void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (requestCode== CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode())
        {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Googlesign", result.getStatus().toString());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            assert acct != null;
            UID=acct.getId();
            NAME=acct.getDisplayName();
            PICURL=String.valueOf(acct.getPhotoUrl());
            EMAIL=acct.getEmail();
            updateAccount();
        } else {
            Toast.makeText(this, "Authentication Problem", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateAccount() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.phone_no_overlay);
        relativeLayout.setVisibility(View.VISIBLE);
        Button button = (Button) findViewById(R.id.phone_edit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone_edit_text.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(SignInActivity.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    PHONE = phone_edit_text.getText().toString();
                    serverUpdate();
                }
            }
        });
    }
    private void serverUpdate()
    {
        HashMap<String, String> postData = new HashMap<>();
        postData.put(getString(R.string.AC_PHONE), PHONE);
        postData.put(getString(R.string.AC_NAME), NAME);
        postData.put(getString(R.string.AC_UID), UID);
        postData.put(getString(R.string.AC_EMAIL), EMAIL);
        postData.put(getString(R.string.AC_PIC), PICURL);
        postData.put(getString(R.string.AC_DEV_ID), getSharedPreferences("FCM",MODE_PRIVATE).getString("FCM_TOKEN","Error"));
        PostResponseAsyncTask task = new PostResponseAsyncTask(SignInActivity.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d("server_response",s);
                if (s.contains("Success")) {
                    SharedPreferences pref = getSharedPreferences("Account", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(getString(R.string.AC_NAME), NAME);
                    editor.putString(getString(R.string.AC_UID), UID);
                    editor.putString(getString(R.string.AC_EMAIL), EMAIL);
                    editor.putString(getString(R.string.AC_PHONE), PHONE);
                    editor.putString(getString(R.string.AC_DEV_ID), getSharedPreferences("FCM",MODE_PRIVATE).getString("FCM_TOKEN","Error"));
                    editor.putString(getString(R.string.AC_PIC), PICURL);
                    editor.apply();
                    SharedPreferences pref2 = getSharedPreferences("App_Use" , MODE_PRIVATE);
                    SharedPreferences.Editor editor2=pref2.edit();
                    editor2.putBoolean(getString(R.string.FIRST_USE),false);
                    editor2.apply();
                    startActivity(new Intent(SignInActivity.this,DestinationPicker.class));
                    Log.d("name: ",NAME+UID+EMAIL+PICURL+getSharedPreferences("FCM",MODE_PRIVATE).getString("FCM_TOKEN","Error")+PHONE);
                }
            }
        });
        task.execute("http://taxicity.esy.es/registration.php");
        task.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Sorry something went wrong!", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
