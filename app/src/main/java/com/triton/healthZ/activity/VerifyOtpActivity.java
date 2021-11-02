package com.triton.healthZ.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.triton.healthZ.R;
import com.triton.healthZ.customer.CustomerDashboardActivity;
import com.triton.healthZ.sessionmanager.SessionManager;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import in.aabhasjindal.otptextview.OtpTextView;

public class VerifyOtpActivity extends AppCompatActivity implements View.OnClickListener {
   /* @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;*/
   private final String TAG = "VerifyOtpActivity";

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

/*
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_timer_count)
    TextView txt_timer_count;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llresendotp)
    LinearLayout llresendotp;


    private CountDownTimer timer;

    private ApplicationData applicationData;
    private String phonenumber;
    private int otp;
    Dialog alertDialog;
    private String autoOTP;

    private String userstatus;
    private String fromactivity;
    private int usertype = 0;

    private boolean isOTPExpired ;
    private String userid;
    private String token = "";
    private String firstname,lastname,useremail;
    private String verifyemailstatus = "false";
    private String myrefcode = "";
*/
    String enteredotp , responseotp;
    boolean can_proceed = true;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
       // applicationData = (ApplicationData) getApplication();

        ButterKnife.bind(this);

        Log.w(TAG,"onCreate-->");

       // edt_otp.setTransformationMethod(new NumericKeyBoardTransformationMethod());


        avi_indicator.setVisibility(View.GONE);

        btn_verifyotp.setOnClickListener(this);

     /*   Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phonenumber = extras.getString("phonemumber");
            otp = extras.getInt("otp");
            usertype = extras.getInt("usertype");
            userid = extras.getString("userid");
            userstatus = extras.getString("userstatus");
            firstname = extras.getString("firstname");
            lastname = extras.getString("lastname");
            useremail = extras.getString("useremail");
            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"Bundle "+" phonenumber : "+phonenumber+" otp :"+otp+" usertype : "+usertype+" userstatus : "+userstatus+ " userid : "+userid);
        }


        try{
            // Initialize Firebase
            FirebaseApp.initializeApp(getApplicationContext());
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        Log.w(TAG,"token--->"+ token);



                    });



        }
        catch (Exception e){
            Log.w(TAG,"FCM : "+e.getLocalizedMessage());
            Log.w(TAG,"FCM Message : "+e.getMessage());
            e.printStackTrace();
        }


        if(fromactivity != null && fromactivity.equalsIgnoreCase("VerifyPhoneNumberActivity")){
            img_back.setVisibility(View.VISIBLE);
        }else{
            img_back.setVisibility(View.INVISIBLE);
        }

        img_back.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        txt_resend.setOnClickListener(this);

*//*
        SmsBroadcastListener.bindListener(otpText -> {
            avi_indicator.smoothToHide();
            Log.w(TAG,"extractedOTP--->"+otpText);
            autoOTP = otpText;

            if(autoOTP != null) {
                edt_otp.setText(autoOTP);
            }


        });
*//*




        startTimer();
*/



      /*
        otp_view.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }

            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.


            }


        });
*/

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verifyotp:
                verifyValidator();
                break;
            /*case R.id.img_back:
                onBackPressed();
                break;*/

            case R.id.txt_resend:
                /*if (new ConnectionDetector(VerifyOtpActivity.this).isNetworkAvailable(VerifyOtpActivity.this)) {
                    resendOtpResponseCall();
                }*/
                break;


        }


    }

    public void verifyValidator() {

        enteredotp = otp_view.getOTP();

        Log.w(TAG, "enteredotp : "+enteredotp);

        responseotp = "123456";

        if (enteredotp.trim().equals("")) {

            can_proceed = false;

            Toasty.warning(getApplicationContext(), "Please Enter OTP", Toast.LENGTH_SHORT, true).show();

        } else if (!responseotp.equalsIgnoreCase(enteredotp)) {

            can_proceed = false;

            Toasty.warning(getApplicationContext(), "Incorrect OTP", Toast.LENGTH_SHORT, true).show();
        }

        if (can_proceed) {
            //  Toasty.success(getApplicationContext(),"userid : "+userid+ "fbtoken : "+token, Toast.LENGTH_SHORT, true).show();

             /*   if (new ConnectionDetector(VerifyOtpActivity.this).isNetworkAvailable(VerifyOtpActivity.this)) {
                    if(userid != null){
                        fBTokenUpdateResponseCall();
                    }*/

            Toasty.success(getApplicationContext(), "Success", Toast.LENGTH_SHORT, true).show();

            SessionManager sessionManager = new SessionManager(VerifyOtpActivity.this);
            sessionManager.setIsLogin(true);
            sessionManager.createLoginSession(
                    "60b73c4638e95868d79be9c6",
                    "Santhosh",
                    "Kumar",
                    "santhoshvsk94@gmail.com",
                    "9159207294",
                    String.valueOf(1),
                    "complete",
                    "",
                    "true",
                    "PS2Z0G5"

            );
            sessionManager.createRazorpayDetails(
                    "rzp_test_zioohqmxDjJJtd",
                    String.valueOf(false));

            Intent intent = new Intent(VerifyOtpActivity.this, CustomerDashboardActivity.class);
            startActivity(intent);
        }


    }

    /*private void startTimer() {
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




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(timer != null){
            timer.cancel();
            timer = null;

        }
        if(fromactivity != null && fromactivity.equalsIgnoreCase("VerifyPhoneNumberActivity")) {
            Intent intent = new Intent(VerifyOtpActivity.this, VerifyPhoneNumberActivity.class);
            intent.putExtra("phonemumber", phonenumber);
            startActivity(intent);
            finish();
        }
    }

    private void resendOtpResponseCall() {
        llresendotp.setVisibility(View.GONE);
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ResendOTPResponse> call = apiInterface.resendOTPResponsecall(RestUtils.getContentType(), resendOTPRequest());
        Log.w(TAG,"ResendOTPResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ResendOTPResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResendOTPResponse> call, @NonNull Response<ResendOTPResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"ResendOTPResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        edt_otp.setText("");
                        startTimer();
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if(response.body().getData().getUser_Details() != null) {
                            otp = response.body().getData().getUser_Details().getOtp();
                        }

                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<ResendOTPResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("ResendOTPResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private ResendOTPRequest resendOTPRequest() {
        ResendOTPRequest resendOTPRequest = new ResendOTPRequest();
        resendOTPRequest.setUser_phone(phonenumber);
        Log.w(TAG,"OTP resendOTPRequest"+ new Gson().toJson(resendOTPRequest));
        return resendOTPRequest;
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

    @SuppressLint("LogNotTimber")
    private void fBTokenUpdateResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<FBTokenUpdateResponse> call = apiInterface.fBTokenUpdateResponseCall(RestUtils.getContentType(), fbTokenUpdateRequest());
        Log.w(TAG,"fBTokenUpdateResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FBTokenUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<FBTokenUpdateResponse> call, @NonNull Response<FBTokenUpdateResponse> response) {

                Log.w(TAG,"fBTokenUpdateResponseCall"+ "--->" + new Gson().toJson(response.body()));


                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        if(response.body().getData() != null) {
                            if (response.body().getData().isUser_email_verification()) {
                                verifyemailstatus = "true";
                            }
                            if(response.body().getData().getMy_ref_code() != null){
                                Log.w(TAG,"MyRefCode : "+ response.body().getData().getMy_ref_code());
                                myrefcode = response.body().getData().getMy_ref_code();
                            }else{
                                myrefcode = "";
                            }
                        }




                        SessionManager sessionManager = new SessionManager(VerifyOtpActivity.this);
                        sessionManager.setIsLogin(true);
                        sessionManager.createLoginSession(
                                userid,
                                firstname,
                                lastname,
                                useremail,
                                phonenumber,
                                String.valueOf(usertype),
                                userstatus,
                                response.body().getData().getProfile_img(),
                                verifyemailstatus,
                                myrefcode

                        );
                        sessionManager.createRazorpayDetails(
                                response.body().getPayment_gateway_detail().getRzpkey(),
                                String.valueOf(response.body().getPayment_gateway_detail().isIsproduction()));
                        Log.w(TAG,"ref_code : "+response.body().getData().getRef_code()+" fromactivity : "+fromactivity+" usertype : "+usertype);

                        if(fromactivity != null && fromactivity.equalsIgnoreCase("VerifyPhoneNumberActivity")){
                            if(usertype != 0){
                                if(usertype == 1){
                                    startActivity(new Intent(VerifyOtpActivity.this, CustomerDashboardActivity.class));

                                }else if(usertype == 2 ){
                                    startActivity(new Intent(VerifyOtpActivity.this, ServiceProviderDashboardActivity.class));

                                }else if(usertype == 3 ){
                                    startActivity(new Intent(VerifyOtpActivity.this, VendorDashboardActivity.class));

                                }else if(usertype == 4 ){
                                    startActivity(new Intent(VerifyOtpActivity.this, DoctorDashboardActivity.class));

                                }
                            }
                        } else{
                            if(usertype != 0){
                                if(usertype == 1 ){
                                    startActivity(new Intent(VerifyOtpActivity.this, BasicPetDetailsNewActivity.class));

                                }else if(usertype == 2 ){
                                    startActivity(new Intent(VerifyOtpActivity.this, ServiceProviderRegisterFormActivity.class));

                                }else if(usertype == 3 ){
                                    startActivity(new Intent(VerifyOtpActivity.this, VendorRegisterFormActivity.class));

                                }else if(usertype == 4 ){
                                    startActivity(new Intent(VerifyOtpActivity.this, DoctorBusinessInfoActivity.class));

                                }
                            }
                        }
                    }
                    else{

                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<FBTokenUpdateResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"FBTokenUpdateResponse flr"+"--->" + t.getMessage());
                //Toasty.success(getApplicationContext(),"NotificationUpdateResponse flr : "+t.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        });

    }
    private FBTokenUpdateRequest fbTokenUpdateRequest() {
        FBTokenUpdateRequest fbTokenUpdateRequest = new FBTokenUpdateRequest();
        fbTokenUpdateRequest.setUser_id(userid);
        fbTokenUpdateRequest.setFb_token(token);
        Log.w(TAG,"fbTokenUpdateRequest"+ "--->" + new Gson().toJson(fbTokenUpdateRequest));
        //  Toasty.success(getApplicationContext(),"fbTokenUpdateRequest : "+new Gson().toJson(fbTokenUpdateRequest), Toast.LENGTH_SHORT, true).show();

        return fbTokenUpdateRequest;
    }
*/


}