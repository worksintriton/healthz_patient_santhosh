package com.triton.healthz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.healthz.R;
import com.triton.healthz.api.APIClient;
import com.triton.healthz.api.RestApiInterface;
import com.triton.healthz.appUtils.NumericKeyBoardTransformationMethod;
import com.triton.healthz.requestpojo.EmailOTPRequest;
import com.triton.healthz.requestpojo.SignupRequest;
import com.triton.healthz.requestpojo.UserStatusUpdateRequest;
import com.triton.healthz.responsepojo.EmailOTPResponse;
import com.triton.healthz.responsepojo.SignupResponse;
import com.triton.healthz.responsepojo.UserStatusUpdateResponse;
import com.triton.healthz.utils.ConnectionDetector;
import com.triton.healthz.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_createacc)
    Button btn_createacc;

    private String TAG = "SignUpActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_firstname)
    EditText edt_firstname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_lastname)
    EditText edt_lastname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email)
    EditText edt_email;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_phone)
    EditText edt_phone;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_ref_code)
    EditText edt_ref_code;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_verify_email)
    Button btn_verify_email;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_terms)
    CheckBox cb_terms;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_login)
    TextView txt_login;

    Dialog alertDialog;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    String[] permissionString = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECEIVE_SMS,
            "check"};
    private boolean user_email_verification;
    private String firstname,lastname,useremail,userphone,referralcode;
    private String verified;
    private String ref_code = "";

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);
        edt_phone.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        btn_verify_email.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            verified = extras.getString("verified");
            firstname = extras.getString("firstname");
            lastname = extras.getString("lastname");
            useremail = extras.getString("useremail");
            userphone = extras.getString("userphone");
            referralcode = extras.getString("referralcode");
        }


        Log.w(TAG,"firstname : "+firstname+" lastname : "+lastname+" useremail : "+useremail+" user_email_verification : "+user_email_verification);
        if(firstname != null){
            edt_firstname.setText(firstname);
        }if(lastname != null){
            edt_lastname.setText(lastname);
        }if(useremail != null){
            edt_email.setText(useremail);
        }if(userphone != null){
            edt_phone.setText(userphone);
        }if(referralcode != null){
            edt_ref_code.setText(referralcode);
        }

        if(verified != null && verified.equalsIgnoreCase("verified")){
            if(btn_verify_email != null){
                btn_verify_email.setText("Verified");
                btn_verify_email.setBackgroundResource(R.drawable.new_rounded_corner_btn);
                user_email_verification = true;
                btn_verify_email.setEnabled(false);
            }
        }
        else{
            if(btn_verify_email != null){
                user_email_verification = false;
                btn_verify_email.setEnabled(true);
                btn_verify_email.setBackgroundResource(R.drawable.rounded_color_red);
            }

        }

        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString();
                Log.w(TAG,"afterTextChanged email : "+email+" useremail : "+useremail);

                if(!email.equalsIgnoreCase(useremail)){
                    btn_verify_email.setText("Verify email");
                    user_email_verification = false;
                    btn_verify_email.setEnabled(true);
                }else{
                    btn_verify_email.setText("Verified email");
                    user_email_verification = true;
                    btn_verify_email.setEnabled(false);
                }


            }
        });

        btn_createacc.setOnClickListener(this);

        txt_login.setOnClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_createacc:
                signUpValidator();
                break;
            case R.id.img_back:
                 onBackPressed();
                break;
            case R.id.btn_verify_email:
                ValidEmailValidator();
                break;

           case R.id.txt_login:
                gotoLoginActivity();
                break;

           }
        }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, WelcomeScreenActivity.class));
        finish();
    }

    @SuppressLint("LogNotTimber")
    private void signupResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SignupResponse> call = apiInterface.signupResponseCall(RestUtils.getContentType(), signupRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SignupResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SignupResponse> call, @NonNull Response<SignupResponse> response) {
                  avi_indicator.smoothToHide();
                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getData().getUser_details().get_id() != null){
                            userStatusUpdateResponseCall(response.body().getData().getUser_details().get_id() );
                        }



                    } else {

                        Toasty.warning(getApplicationContext(),""+response.body().getMessage(),Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SignupResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private SignupRequest signupRequest() {
      /*   * first_name : mohammed
         * last_name : imthiyas
         * user_email : m@gmail.com
         * user_phone : 987987989
         * user_type : 1
         * date_of_reg : 23/10/2019 12:12:00
         * user_email_verification :true
         * mobile_type : "Android"
         * user_email_verification: true*/


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        ref_code = edt_ref_code.getText().toString();
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirst_name(edt_firstname.getText().toString().trim());
        signupRequest.setLast_name(edt_lastname.getText().toString().trim());
        signupRequest.setUser_email(edt_email.getText().toString());
        signupRequest.setUser_phone(edt_phone.getText().toString());
        signupRequest.setUser_type(1);
        signupRequest.setDate_of_reg(currentDateandTime);
        signupRequest.setMobile_type("Android");
        signupRequest.setUser_email_verification(user_email_verification);
        signupRequest.setRef_code(ref_code);
        Log.w(TAG,"signupRequest "+ new Gson().toJson(signupRequest));
        return signupRequest;
    }


    @SuppressLint("LogNotTimber")
    private void userStatusUpdateResponseCall(String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<UserStatusUpdateResponse> call = apiInterface.userStatusUpdateResponse(RestUtils.getContentType(), userStatusUpdateRequest(id));
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<UserStatusUpdateResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<UserStatusUpdateResponse> call, @NonNull Response<UserStatusUpdateResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"UserStatusUpdateResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                        if(response.body().getData() != null) {
                            Log.w(TAG,"usertype --->"+response.body().getData().getUser_type());
                            Intent intent = new Intent(SignUpActivity.this, VerifyOtpActivity.class);
                            intent.putExtra("phonemumber", response.body().getData().getUser_phone());
                            intent.putExtra("otp", response.body().getData().getOtp());
                            intent.putExtra("usertype",1);
                            intent.putExtra("userid", response.body().getData().get_id());
                            intent.putExtra("userstatus", "Incomplete");
                            intent.putExtra("firstname", response.body().getData().getFirst_name());
                            intent.putExtra("lastname", response.body().getData().getLast_name());
                            intent.putExtra("useremail", response.body().getData().getUser_email());
                            intent.putExtra("fromactivity", TAG);
                            startActivity(intent);
                            finish();
                        }


                    } else {

                        Toasty.warning(getApplicationContext(),""+response.body().getMessage(),Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<UserStatusUpdateResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private UserStatusUpdateRequest userStatusUpdateRequest(String id) {
/*
         * user_id : 5fb6162a211fce241eaf53a9
         * user_status : complete*/


        UserStatusUpdateRequest userStatusUpdateRequest = new UserStatusUpdateRequest();
        userStatusUpdateRequest.setUser_id(id);
        userStatusUpdateRequest.setUser_status("complete");
        Log.w(TAG,"userStatusUpdateRequest "+ new Gson().toJson(userStatusUpdateRequest));
        return userStatusUpdateRequest;
    }


    @SuppressLint("LogNotTimber")
    private void emailOTPResponseCall(String emailid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<EmailOTPResponse> call = apiInterface.emailOTPResponseCall(RestUtils.getContentType(), emailOTPRequest(emailid));
        Log.w(TAG,"EmailOTPResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<EmailOTPResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<EmailOTPResponse> call, @NonNull Response<EmailOTPResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"EmailOTPResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if(response.body().getData() != null) {
                            Intent intent = new Intent(SignUpActivity.this, VerifyEmailOtpActivity.class);
                            intent.putExtra("useremail", response.body().getData().getEmail_id());
                            intent.putExtra("otp", response.body().getData().getOtp());
                            intent.putExtra("firstname", edt_firstname.getText().toString());
                            intent.putExtra("lastname", edt_lastname.getText().toString());
                            intent.putExtra("UserType", 1);
                            intent.putExtra("fromactivity", TAG);
                            startActivity(intent);
                        }


                    } else {

                        Toasty.warning(getApplicationContext(),""+response.body().getMessage(),Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<EmailOTPResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("Email OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private EmailOTPRequest emailOTPRequest(String emailid) {
         /** user_email : mohammedimthi2395@gmail.com*/


        EmailOTPRequest emailOTPRequest = new EmailOTPRequest();
        emailOTPRequest.setUser_email(emailid);
        Log.w(TAG,"EmailOTPRequest "+ new Gson().toJson(emailOTPRequest));
        return emailOTPRequest;
    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());




        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

    public void signUpValidator() {
        boolean can_proceed = true;
        int moblength = edt_phone.getText().toString().trim().length();
        int firstnamelength = edt_firstname.getText().toString().trim().length();
        int lastnamelength = edt_firstname.getText().toString().trim().length();
        String emailAddress = edt_email.getText().toString().trim();
        String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";


        if (Objects.requireNonNull(edt_firstname.getText()).toString().trim().equals("") && Objects.requireNonNull(edt_lastname.getText()).toString().trim().equals("") &&
                Objects.requireNonNull(edt_phone.getText()).toString().trim().equals("")) {
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_firstname.getText().toString().trim().equals("")) {
            edt_firstname.setError("Please enter first name");
            edt_firstname.requestFocus();
            can_proceed = false;
        }else if (firstnamelength > 25) {
            edt_firstname.setError("The maximum length for an first name is 25 characters.");
            edt_firstname.requestFocus();
            can_proceed = false;
        }
        else if (edt_lastname.getText().toString().trim().equals("")) {
            edt_lastname.setError("Please enter last name");
            edt_lastname.requestFocus();
            can_proceed = false;
        }
        else if (lastnamelength > 25) {
            edt_lastname.setError("The maximum length for an last name is 25 characters.");
            edt_lastname.requestFocus();
            can_proceed = false;
        }
        else if (Objects.requireNonNull(edt_phone.getText()).toString().trim().equals("")) {
            edt_phone.setError("Please enter your phone number");
            edt_phone.requestFocus();
            can_proceed = false;
        } else if (moblength <= 9) {
            edt_phone.setError("Please enter valid mobile number");
            edt_phone.requestFocus();
            can_proceed = false;
        }
        else if(!emailAddress.matches(emailPattern)){
            edt_email.setError("Please enter correct email address");
            edt_email.requestFocus();
            can_proceed = false;
        }

        else if(!cb_terms.isChecked()){
            Toasty.warning(getApplicationContext(), "Please tick the agreement", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        }




        if (can_proceed) {
            insertmappermission();


        }

    }
    public void ValidEmailValidator() {
        boolean can_proceed = true;
        String emailAddress = edt_email.getText().toString().trim();
        String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

        if (edt_email.getText().toString().trim().equals("")) {
              edt_email.setError("Please enter the email");
              edt_email.requestFocus();
            can_proceed = false;
        }else if(!emailAddress.matches(emailPattern)){
            edt_email.setError("Please enter correct email address");
            edt_email.requestFocus();
            can_proceed = false;
        }

          if (can_proceed) {
              if (new ConnectionDetector(SignUpActivity.this).isNetworkAvailable(SignUpActivity.this)) {
              emailOTPResponseCall(emailAddress);
              }
          }

    }

    private void insertmappermission() {

        int haslocationpermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            haslocationpermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) | checkSelfPermission(Manifest.permission.RECEIVE_SMS);

            if (haslocationpermission != PackageManager.PERMISSION_GRANTED) {

                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) | !shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)) {

                    requestPermissions(permissionString,
                            REQUEST_CODE_ASK_PERMISSIONS);

                    return;
                }
                requestPermissions(permissionString,
                        REQUEST_CODE_ASK_PERMISSIONS);
            }else{
                if (new ConnectionDetector(SignUpActivity.this).isNetworkAvailable(SignUpActivity.this)) {
                    signupResponseCall();


                }

            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                // Permission Granted
                if (new ConnectionDetector(SignUpActivity.this).isNetworkAvailable(SignUpActivity.this)) {
                    signupResponseCall();


                }



            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void gotoLoginActivity() {

        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        intent.putExtra("fromactivity", TAG);
        startActivity(intent);
    }
}