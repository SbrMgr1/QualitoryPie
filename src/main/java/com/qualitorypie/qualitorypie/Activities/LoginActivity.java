package com.qualitorypie.qualitorypie.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qualitorypie.qualitorypie.R;
import com.qualitorypie.qualitorypie.DataProviders.UserDataProvider;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 5;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "sbr.mgr1@gmail.com:sbr6yaha", "kanchan.mgr1@gmail.com:kanchan6yaha"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mlogin_error;

    private String emailInputValue = null;
    private String passwordInputValue = null;
    private int errors = 0;
    private boolean acc_found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mlogin_error = (TextView) findViewById(R.id.login_error);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate_login_fields() == true){

                    UserDataProvider user_data_provider = new UserDataProvider(LoginActivity.this);
                    user_data_provider.setEmail(emailInputValue);
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    private boolean validate_login_fields(){
//        showProgress();
        this.errors = 0;
        this.emailInputValue = mEmailView.getText().toString().trim();
        if(this.emailInputValue.equals("")){
            this.errors++;
            mEmailView.setError("Email is required.");
        }else if(!this.emailInputValue.contains("@")){
            this.errors++;
            mEmailView.setError("Email is not valid email.");
        }
        this.passwordInputValue = mPasswordView.getText().toString().trim();
        if(this.passwordInputValue.equals("")){
            this.errors++;
            mPasswordView.setError("Password is required.");
        }
        acc_found = false;
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(this.emailInputValue) && pieces[1].equals(this.passwordInputValue)) {
                acc_found = true;
            }
        }
        if(acc_found == false){
            this.errors++;
            mlogin_error.setText("Email or password not matched.");
        }else{
            mlogin_error.setText("");
        }

        if(this.errors>0){
            return false;
        }else{
            return true;
        }

    }
    private void showProgress() {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 200;

            mLoginFormView.setVisibility( View.GONE);
            mProgressView.setVisibility(View.VISIBLE);

//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    Log.d("here","form is going to be visible");
//                    mProgressView.setVisibility(View.GONE);
//
//                }
//            });
            mLoginFormView.setVisibility(View.VISIBLE);

        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(View.VISIBLE);
            mLoginFormView.setVisibility(View.GONE);
        }
    }

}

