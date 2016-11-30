package com.example.getgpslocation;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.getgpslocation.Constant.Constant;
import com.example.getgpslocation.Constant.SessionManager;
import com.example.getgpslocation.helper.WebServicesFunctions;
import com.example.getgpslocation.models.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class LogIn extends AppCompatActivity {

    EditText etUserName, etUserPassword;
    TextInputLayout inputLayoutUserName, inputLayoutPassword;
    Button login;
    String userName, userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initViews();
        assignViews();
        initEventDriven();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            statusBarColor();
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void statusBarColor() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.text_Primary));
    }

    private void initViews() {
        login = (Button) findViewById(R.id.btn_login);
        etUserName = (EditText) findViewById(R.id.user_name_et);
        etUserPassword = (EditText) findViewById(R.id.user_password_et);

        inputLayoutUserName = (TextInputLayout) findViewById(R.id.input_user_name);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_password);
        removeFocuse();

    }

    private void assignViews() {
    }

    private void initEventDriven() {

        etUserName.addTextChangedListener(new MyTextWatcher(etUserName));
        etUserPassword.addTextChangedListener(new MyTextWatcher(etUserPassword));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateUserName() && validatePassword()) {
                    return;
                }

                logIn();

            }
        });
    }






    private void removeFocuse() {
        FrameLayout touchInterceptor = (FrameLayout) findViewById(R.id.touchInterceptor_l);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (etUserName.isFocused() || etUserPassword.isFocused()) {
                        Rect outRect = new Rect();
                        etUserName.getGlobalVisibleRect(outRect);
                        etUserPassword.getGlobalVisibleRect(outRect);

                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            etUserName.clearFocus();
                            etUserPassword.clearFocus();

                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });
    }


    private boolean validateUserName() {
        if (etUserName.getText().toString().trim().isEmpty()) {
            inputLayoutUserName.setErrorEnabled(true);
            //   inputLayoutUserName.setError(getString(R.string.center_name_error));
            requestFocus(etUserName);
            return true;
        } else {
            inputLayoutUserName.setErrorEnabled(false);
            inputLayoutUserName.setError(null);
            userName = etUserName.getText().toString();
            return false;
        }


    }

    private boolean validatePassword() {

        if (etUserPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setErrorEnabled(true);
            // inputLayoutPassword.setError(getString(R.string.center_name_error));
            requestFocus(etUserPassword);
            return true;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
            inputLayoutPassword.setError(null);
            userPassword = etUserPassword.getText().toString();
            return false;
        }


    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.user_password_et:
                    if (etUserPassword.getText().length() >= 1) {
                        validatePassword();
                    }
                    break;
                case R.id.user_name_et:
                    if (etUserName.getText().length() >= 1)
                        validateUserName();
                    break;
            }
        }
    }

    private void logIn() {

        String url = Constant.BASE_URL + Constant.LOG_IN ;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", etUserName.getText().toString());
            jsonObject.put("password",  etUserPassword.getText().toString());

            Log.e("request", jsonObject.toString() + url);

            final ProgressDialog progressDialog = ProgressDialog.show(LogIn.this, "", "Loading");
            new WebServicesFunctions(LogIn.this).sendPostJsonObjectRequestToWs(url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("response1 ", response.toString());
                    progressDialog.cancel();
                    try {
                        int status = response.getInt("status");
                        if (status == 1) {
                            JSONObject data = response.getJSONObject("data");
                            User user = new Gson().fromJson(data.toString(), User.class);
                            if (user != null) {

                                new SessionManager().setUserNamePref(LogIn.this, "user_name_pref", user.getUsername());

                                new SessionManager().setUserPasswordPref(LogIn.this, "user_password_pref", etUserPassword.getText().toString());
                                new SessionManager().setUserTokenPref(LogIn.this, "user_token_pref", user.getToken());
                                new SessionManager().setUserTokenExpirePref(LogIn.this, "user_token_expire_pref", user.getToken_expire());
                                new SessionManager().setUserRolePref(LogIn.this, "user_role_pref", user.getRole());
                                new SessionManager().setUserIdPref(LogIn.this, "user_id_pref", user.getId());

                                new SessionManager().setPreferences(LogIn.this, "status", "1");

                                Log.e("saved data", user.getUsername() + etUserPassword.getText().toString()
                                        + user.getToken()+ user.getToken_expire() + user.getRole());

                                Intent intent = new Intent(LogIn.this, VisitedStores.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();
                                startActivity(intent);

                            }
                        }else{

                        }
                    } catch (JSONException e) {
                        Log.e("json ex", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    WebServicesFunctions.printError(error);
                    Log.e("error", "error");
                    progressDialog.cancel();
                }
            });


        } catch (JSONException e) {
            Log.e("json ex", e.getMessage());
            e.printStackTrace();
        }


    }

}


