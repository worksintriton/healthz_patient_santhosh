package com.triton.healthz.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.triton.healthz.R;
import com.triton.healthz.api.APIClient;
import com.triton.healthz.api.RestApiInterface;
import com.triton.healthz.appUtils.ApplicationData;
import com.triton.healthz.requestpojo.EmailOTPRequest;
import com.triton.healthz.responsepojo.EmailOTPResponse;
import com.triton.healthz.utils.ConnectionDetector;
import com.triton.healthz.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyEmailOtpActivity extends AppCompatActivity implements View.OnClickListener {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_verifyotp)
    Button btn_verifyotp;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.otp_view)
    OtpTextView otp_view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_resend)
    TextView txt_resend;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_verifyphnno)
    TextView txt_lbl_verifyphnno;

    private final String TAG = "VerifyEmailOtpActivity";
    private CountDownTimer timer;

    private ApplicationData applicationData;
    private String phonenumber;
    private int otp;
    Dialog alertDialog;
    private String autoOTP;

    private String userstatus;
    private String fromactivity;


    private boolean isOTPExpired ;
    private String userid;
    private String token = "";
    private String firstname,lastname,useremail;
    private String UserType;
    private int UserTypeValue;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llresendotp)
    LinearLayout llresendotp;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_timer_count)
    TextView txt_timer_count;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_verifyphnno2)
    TextView txt_lbl_verifyphnno2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_editno)
    LinearLayout ll_editno;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        applicationData = (ApplicationData) getApplication();

        ButterKnife.bind(this);

        txt_lbl_verifyphnno2.setText("Edit Email");

        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            otp = extras.getInt("otp");
            UserType = extras.getString("UserType");
            UserTypeValue = extras.getInt("UserTypeValue");

            userid = extras.getString("userid");
            userstatus = extras.getString("userstatus");
            firstname = extras.getString("firstname");
            lastname = extras.getString("lastname");
            useremail = extras.getString("useremail");
            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"Bundle "+" phonenumber : "+phonenumber+" otp :"+otp+" UserType : "+UserType+" userstatus : "+userstatus+ " userid : "+userid+" fromactivity : "+fromactivity);
        }

        txt_lbl_verifyphnno.setText("Email Verification");
        btn_verifyotp.setOnClickListener(this);
        txt_resend.setOnClickListener(this);

        startTimer();
        ll_editno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void startTimer() {
        isOTPExpired = false;
        long timer_milliseconds = 120000;
        timer = new CountDownTimer(timer_milliseconds, 1000) {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onTick(long millisUntilFinished) {
                llresendotp.setVisibility(View.GONE);
                txt_timer_count.setVisibility(View.VISIBLE);

                applicationData.setTimer_milliseconds(millisUntilFinished);
                txt_timer_count.setText(getResources().getString(R.string.resendotp)+" " + String.format("%02d : %02d ",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            }

            @Override
            public void onFinish() {
                isOTPExpired = true;
                txt_timer_count.setVisibility(View.GONE);
                llresendotp.setVisibility(View.VISIBLE);
                timer.cancel();
            }
        };
        timer.start();
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verifyotp:
                verifyValidator();
                break;

                case R.id.txt_resend:
                    if (new ConnectionDetector(VerifyEmailOtpActivity.this).isNetworkAvailable(VerifyEmailOtpActivity.this)) {
                        if(useremail != null){
                            resendOtpResponseCall();
                        }

                    }
                break;


        }


    }
    public void verifyValidator() {
        boolean can_proceed = true;
        String enteredotp = otp_view.getOTP();
        String responseotp = String.valueOf(otp);
        if (enteredotp.trim().equals("")) {

            can_proceed = false;

            Toasty.warning(getApplicationContext(), "Please Enter OTP", Toast.LENGTH_SHORT, true).show();

        } else if (!responseotp.equalsIgnoreCase(enteredotp)) {

            can_proceed = false;

            Toasty.warning(getApplicationContext(), "Incorrect OTP", Toast.LENGTH_SHORT, true).show();
        }else if(enteredotp.equalsIgnoreCase(responseotp)){
            can_proceed = true;
        }



        if (can_proceed) {
            Toasty.success(getApplicationContext(),"Your email verified successfully", Toast.LENGTH_SHORT, true).show();

            Intent intent = new Intent(VerifyEmailOtpActivity.this,SignUpActivity.class);
             intent.putExtra("verified","verified");
             intent.putExtra("useremail",useremail);
             intent.putExtra("firstname",firstname);
             intent.putExtra("lastname",lastname);
             intent.putExtra("UserType",UserType);
             intent.putExtra("UserTypeValue",UserTypeValue);
             startActivity(intent);





        }

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if(fromactivity!=null && fromactivity.equalsIgnoreCase("SignUpActivity")){
                Intent intent = new Intent(VerifyEmailOtpActivity.this,SignUpActivity.class);
                intent.putExtra("verified","verified");
                intent.putExtra("useremail",useremail);
                intent.putExtra("firstname",firstname);
                intent.putExtra("lastname",lastname);
                intent.putExtra("UserType",UserType);
                intent.putExtra("UserTypeValue",UserTypeValue);
                startActivity(intent);

        }else{
            Intent intent = new Intent(VerifyEmailOtpActivity.this,SignUpActivity.class);
            intent.putExtra("verified","verified");
            intent.putExtra("useremail",useremail);
            intent.putExtra("firstname",firstname);
            intent.putExtra("lastname",lastname);
            intent.putExtra("UserType",UserType);
            intent.putExtra("UserTypeValue",UserTypeValue);
            startActivity(intent);
        }

    }

    @SuppressLint("LogNotTimber")
    private void resendOtpResponseCall() {
        llresendotp.setVisibility(View.GONE);
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<EmailOTPResponse> call = apiInterface.emailOTPResponseCall(RestUtils.getContentType(), emailOTPRequest());
        Log.w(TAG,"ResendOTPResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<EmailOTPResponse>() {
            @Override
            public void onResponse(@NonNull Call<EmailOTPResponse> call, @NonNull Response<EmailOTPResponse> response) {
                  avi_indicator.smoothToHide();
                Log.w(TAG,"ResendOTPResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        otp_view.setOTP("");
                        startTimer();
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if(response.body().getData().getOtp() !=0){
                            otp = response.body().getData().getOtp();
                        }


                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<EmailOTPResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("ResendOTPResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private EmailOTPRequest emailOTPRequest() {

        /* * user_email : mohammedimthi2395@gmail.com*/

        EmailOTPRequest emailOTPRequest = new EmailOTPRequest();
        emailOTPRequest.setUser_email(useremail);
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

    @Override
    protected void onPause() {
        super.onPause();

    }


}