package com.andriod.weddingbells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andriod.weddingbells.Utils.VolleySingleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private static String TAG = "RegistrationActivity";

    // UI references.
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mPhoneNumberView;
    private RadioGroup mGender;
    private View mRegisterFormView;
    private View mProgressView;
    private String mGenderSelected = "Male";

    //TODO update the string values
    private static String REGISTRATION_SUCCESSFUL = "Successfully Registered";
    private static String INVALID_EMAIL = "Email Not Valid";
    private static String WEAK_PASSWORD = "Password Weak";
    private static String USER_REGISTERED_ALREADY = "Email already Registered";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirstNameView = (EditText) findViewById(R.id.editText);
        mLastNameView = (EditText) findViewById(R.id.editText2);
        mEmailView = (EditText) findViewById(R.id.editText3);
        mPasswordView = (EditText) findViewById(R.id.editText5);
        mPhoneNumberView = (EditText) findViewById(R.id.editText4);

        mGender = (RadioGroup) findViewById(R.id.radioGroup);
        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);
                mGenderSelected = rb.getText().toString();
                Log.e("sharath", "*** button id:" + checkedId + " name:" + mGenderSelected);
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

        Button submit = (Button) findViewById(R.id.signupbutton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });
    }

    /**
     * Attempts to register the account specified by the registration form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual registration attempt is made.
     */
    private void attemptRegistration() {
        showProgress(true);

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mFirstNameView.setError(null);
        mLastNameView.setError(null);
        mPhoneNumberView.setError(null);

        String emailtxt = mEmailView.getText().toString();
        String passwordtxt = mPasswordView.getText().toString();

        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("email", emailtxt);
        stringMap.put("password", passwordtxt);
        stringMap.put("firstname", mFirstNameView.getText().toString());
        stringMap.put("lastname", mLastNameView.getText().toString());
        stringMap.put("phonenumber", mPhoneNumberView.getText().toString());
        stringMap.put("gender",mGenderSelected);

        if (!isEmailValid(emailtxt) || !isPasswordValid(passwordtxt)) {
            return;
        }

        userRegistrationRequest(stringMap);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void userRegistrationRequest(Map<String, String> stringMap) {
        //TODO url format to be updated for user registration
        String url = "http://10.0.2.2:8080/WeddingBell/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG,"*** response success" + response);

                        showProgress(false);
                        /*if(response != null) {
                        if(REGISTRATION_SUCCESSFUL.equals(response) || USER_REGISTERED_ALREADY.equals(response)) {
                            Toast.makeText(getApplication(), response, Toast.LENGTH_LONG).show();
                            Intent I = new Intent(RegistrationActivity.this,LoginActivity.class);
                            startActivity(I);

                            finish();
                        }

                        if(WEAK_PASSWORD.equals(response)) {
                            mPasswordView.setError(getString(R.string.weak_password));
                            mPasswordView.requestFocus();
                        }

                        if(INVALID_EMAIL.equals(response)) {
                            mEmailView.setError(getString(R.string.weak_password));
                            mEmailView.requestFocus();
                        } */
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"*** response failure " + error.getMessage());
                // TODO Auto-generated method stub
                showProgress(false);
                Toast.makeText(getApplicationContext(),"invalid username or password",Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
