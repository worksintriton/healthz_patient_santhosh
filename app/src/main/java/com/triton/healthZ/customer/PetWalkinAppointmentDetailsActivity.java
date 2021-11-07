package com.triton.healthZ.customer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.healthZ.R;
import com.triton.healthZ.activity.NotificationActivity;
import com.triton.healthZ.adapter.MyCouponsTextAdapter;
import com.triton.healthZ.api.APIClient;
import com.triton.healthZ.api.RestApiInterface;
import com.triton.healthZ.doctor.DoctorWalkinPrescriptionDetailsActivity;
import com.triton.healthZ.interfaces.OnAppointmentSuccessfullyCancel;
import com.triton.healthZ.requestpojo.AddReviewRequest;
import com.triton.healthZ.requestpojo.AppoinmentCancelledRequest;
import com.triton.healthZ.requestpojo.AppointmentDetailsRequest;
import com.triton.healthZ.requestpojo.NotificationSendRequest;
import com.triton.healthZ.requestpojo.PetNewAppointmentDetailsRequest;
import com.triton.healthZ.requestpojo.RefundCouponCreateRequest;
import com.triton.healthZ.requestpojo.SPNotificationSendRequest;
import com.triton.healthZ.responsepojo.AddReviewResponse;
import com.triton.healthZ.responsepojo.AppoinmentCancelledResponse;
import com.triton.healthZ.responsepojo.CouponCodeTextResponse;
import com.triton.healthZ.responsepojo.NotificationSendResponse;
import com.triton.healthZ.responsepojo.PetNewAppointmentDetailsResponse;
import com.triton.healthZ.responsepojo.SPAppointmentDetailsResponse;
import com.triton.healthZ.responsepojo.SuccessResponse;
import com.triton.healthZ.sessionmanager.SessionManager;
import com.triton.healthZ.utils.ConnectionDetector;
import com.triton.healthZ.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetWalkinAppointmentDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnAppointmentSuccessfullyCancel {

    private String TAG = "PetWalkinAppointmentDetailsActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;




    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_user)
    ImageView img_user;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_usrname)
    TextView txt_usrname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_serv_name)
    TextView txt_serv_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_serv_cost)
    TextView txt_serv_cost;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_cancel)
    Button btn_cancel;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_petimg)
    ImageView img_petimg;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_pet_name)
    TextView txt_pet_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_pet_type)
    TextView txt_pet_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_breed)
    TextView txt_breed;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_gender)
    TextView txt_gender;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_color)
    TextView txt_color;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_weight)
    TextView txt_weight;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_age)
    TextView txt_age;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_vaccinated)
    TextView txt_vaccinated;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_date)
    TextView txt_order_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order)
    TextView txt_order_id;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_payment_method)
    TextView txt_payment_method;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_cost)
    TextView txt_order_cost;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_address)
    TextView txt_address;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_videocall)
    ImageView img_videocall;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_add_review)
    Button btn_add_review;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_prescriptiondetails)
    Button btn_prescriptiondetails;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_petlastvacinateddate)
    LinearLayout ll_petlastvacinateddate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_petlastvaccinatedage)
    TextView txt_petlastvaccinatedage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_appointment_date)
    TextView txt_appointment_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_pets_handled)
    TextView txt_dr_pets_handled;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_reschedule_appointment)
    Button btn_reschedule_appointment;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_home_address)
    LinearLayout ll_home_address;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_home_address)
    TextView txt_home_address;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_visit_type)
    TextView txt_visit_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.scrollablContent)
    ScrollView scrollablContent;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_footer)
    View include_petlover_footer;

    BottomNavigationView bottom_navigation_view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_emergency_appointment)
    ImageView img_emergency_appointment;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_allergies)
    LinearLayout ll_allergies;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_allergies)
    TextView txt_allergies;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_comments)
    LinearLayout ll_comments;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_comments)
    TextView txt_comments;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_diagnosis)
    LinearLayout ll_diagnosis;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_diagnosis)
    TextView txt_diagnosis;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_sub_diagnosis)
    LinearLayout ll_sub_diagnosis;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_sub_diagnosis)
    TextView txt_sub_diagnosis;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_doctor_comment)
    LinearLayout ll_doctor_comment;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_doctor_comment)
    TextView txt_doctor_comment;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_pets_handled_details)
    TextView txt_pets_handled_details;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_visit_type)
    LinearLayout ll_visit_type;


    String appointment_id;
    String appoinment_status;
    String start_appointment_status;
    private Dialog dialog;
    private String bookedat;
    private String startappointmentstatus;
    private boolean isVaildDate;
    private String appointmentfor;
    private String spid;
    private String userid;
    private String from;
    private String userrate;
    Dialog alertDialog;
    private String appointmentid;
    private List<PetNewAppointmentDetailsResponse.DataBean.PetIdBean.PetImgBean> pet_image;
    private String petAgeandMonth;

    private String concatenatedStarNames = "";

    /* Petlover Bottom Navigation */

    /* Petlover Bottom Navigation */

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_home)
    RelativeLayout rl_home;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_care)
    RelativeLayout rl_care;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_care)
    TextView title_care;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_care)
    ImageView img_care;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_service)
    RelativeLayout rl_service;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_serv)
    TextView title_serv;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_serv)
    ImageView img_serv;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_shop)
    RelativeLayout rl_shop;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_shop)
    TextView title_shop;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_shop)
    ImageView img_shop;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_comn)
    RelativeLayout rl_comn;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_community)
    TextView title_community;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_community)
    ImageView img_community;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_homes)
    RelativeLayout rl_homes;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_original_price)
    LinearLayout ll_original_price;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_discount_price)
    LinearLayout ll_discount_price;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_original_price)
    TextView txt_original_price;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_discount_price)
    TextView txt_discount_price;


    TextView txt_no_records_coupon;
    RecyclerView rv_successfully_cancelled;
    private List<CouponCodeTextResponse.DataBean> myCouponsTextList;
    private String ServiceCost ="";
    private String Appointmenttype = "";
    private String Paymentmethod;
    private String doctorid;
    private SessionManager session;


    @SuppressLint({"LogNotTimber", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petappointment_details);
        ButterKnife.bind(this);

        scrollablContent.setVisibility(View.GONE);
        ll_home_address.setVisibility(View.GONE);
        img_emergency_appointment.setVisibility(View.GONE);

        ll_allergies.setVisibility(View.GONE);
        ll_comments.setVisibility(View.GONE);
        ll_diagnosis.setVisibility(View.GONE);
        ll_sub_diagnosis.setVisibility(View.GONE);
        ll_doctor_comment.setVisibility(View.GONE);
        txt_diagnosis.setVisibility(View.GONE);
        txt_sub_diagnosis.setVisibility(View.GONE);
        txt_doctor_comment.setVisibility(View.GONE);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appointment_id = extras.getString("appointment_id");
            bookedat = extras.getString("bookedat");
            startappointmentstatus = extras.getString("startappointmentstatus");
            appointmentfor = extras.getString("appointmentfor");
            userrate = extras.getString("userrate");
            from = extras.getString("from");

            Log.w(TAG,"appointmentfor : "+appointmentfor+" from : "+from);

            if(appointmentfor !=null && appointmentfor.equalsIgnoreCase("SP")){
                txt_pets_handled_details.setVisibility(View.GONE);
                ll_visit_type.setVisibility(View.GONE);
            }


        }



        ImageView img_back = include_petlover_header.findViewById(R.id.img_back);
        ImageView img_sos = include_petlover_header.findViewById(R.id.img_sos);
        ImageView img_notification = include_petlover_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_petlover_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_petlover_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_petlover_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Appointment Details");
        img_sos.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);



        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            }
        });
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerProfileScreenActivity.class);
                intent.putExtra("fromactivity",TAG);
                intent.putExtra("appointment_id",appointment_id);
                intent.putExtra("bookedat",bookedat);
                intent.putExtra("startappointmentstatus",startappointmentstatus);
                intent.putExtra("appointmentfor",appointmentfor);
                intent.putExtra("userrate",userrate);
                intent.putExtra("from",from);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(v -> onBackPressed());




        btn_cancel.setVisibility(View.GONE);
        img_videocall.setVisibility(View.GONE);
        btn_add_review.setVisibility(View.GONE);
        btn_prescriptiondetails.setVisibility(View.GONE);
        btn_reschedule_appointment.setVisibility(View.GONE);


//        bottom_navigation_view = include_petlover_footer.findViewById(R.id.bottom_navigation_view);
//        bottom_navigation_view.setItemIconTintList(null);
//        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
//        bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);

       /**/


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        if(bookedat != null){
            compareDatesandTime(currentDateandTime,bookedat);
        }

        if(from != null){
            if(from.equalsIgnoreCase("PetWalkinNewAppointmentAdapter")){
                if(appointmentfor != null){
                    if(appointmentfor.equalsIgnoreCase("Doctor")){
                        img_videocall.setVisibility(View.VISIBLE);
                        btn_cancel.setVisibility(View.VISIBLE);

                        if(isVaildDate){
                            btn_cancel.setVisibility(View.VISIBLE);
                        }else{
                            btn_cancel.setVisibility(View.GONE);
                        }
                        /*if(startappointmentstatus != null && !startappointmentstatus.equalsIgnoreCase("Not Started")) {
                            img_videocall.setBackgroundResource(R.drawable.video_camera_gray);
                            btn_cancel.setVisibility(View.GONE);
                        }else{
                            img_videocall.setBackgroundResource(R.drawable.video_camera_green);
                        }
*/



                    }
                }else{
                    btn_cancel.setVisibility(View.VISIBLE);
                    if(isVaildDate){
                        btn_cancel.setVisibility(View.VISIBLE);
                    }else{
                        btn_cancel.setVisibility(View.GONE);
                    }

                }

            }
             else if(from.equalsIgnoreCase("PetWalkinMissedAppointmentAdapter")){
                btn_cancel.setVisibility(View.GONE);
                img_videocall.setVisibility(View.GONE);
                btn_add_review.setVisibility(View.GONE);
                btn_prescriptiondetails.setVisibility(View.GONE);
                img_videocall.setVisibility(View.GONE);

            }
             else if(from.equalsIgnoreCase("PetWalkinCompletedAppointmentAdapter")) {
                 img_videocall.setVisibility(View.GONE);

                if (userrate != null && userrate.equalsIgnoreCase("0")) {
                    btn_add_review.setVisibility(View.GONE);
                }
                else {
                    btn_add_review.setVisibility(View.GONE);

                }

                if (appointmentfor.equalsIgnoreCase("Doctor")) {
                    btn_prescriptiondetails.setVisibility(View.VISIBLE);
                }else{
                    btn_prescriptiondetails.setVisibility(View.GONE);
                }
            }

        }

        btn_prescriptiondetails.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DoctorWalkinPrescriptionDetailsActivity.class);
            intent.putExtra("id",appointment_id);
            startActivity(intent);
        });

        btn_add_review.setOnClickListener(v -> showAddReview(appointment_id,appointmentfor));

        if(appointmentfor != null){
            if(appointmentfor.equalsIgnoreCase("Doctor")){
                if (new ConnectionDetector(PetWalkinAppointmentDetailsActivity.this).isNetworkAvailable(PetWalkinAppointmentDetailsActivity.this)) {
                    petNewAppointmentResponseCall();
                }
            }
            else if(appointmentfor.equalsIgnoreCase("SP")){
                if (new ConnectionDetector(PetWalkinAppointmentDetailsActivity.this).isNetworkAvailable(PetWalkinAppointmentDetailsActivity.this)) {
                    spAppointmentDetailsResponse();
                }
            }

        }
    }


    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void petNewAppointmentResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PetNewAppointmentDetailsResponse> call = ApiService.petWalkinAppointDetailResponseCall(RestUtils.getContentType(), petNewAppointmentDetailsRequest());
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<PetNewAppointmentDetailsResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<PetNewAppointmentDetailsResponse> call, @NonNull Response<PetNewAppointmentDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "PetNewAppointmentDetailsResponse" + "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        scrollablContent.setVisibility(View.VISIBLE);

                        String vaccinated, addr = null, usrname = null;

                        if(response.body().getData() != null){
                            if(response.body().getData().getCoupon_status() != null && response.body().getData().getCoupon_status().equalsIgnoreCase("Applied")){
                                ll_original_price.setVisibility(View.VISIBLE);
                                ll_discount_price.setVisibility(View.VISIBLE);
                                if(response.body().getData().getOriginal_price() != 0){
                                    txt_original_price.setText("INR "+response.body().getData().getOriginal_price());
                                }
                                if(response.body().getData().getDiscount_price() != 0){
                                    txt_discount_price.setText("INR "+response.body().getData().getDiscount_price());
                                }

                            }else{
                                ll_original_price.setVisibility(View.GONE);
                                ll_discount_price.setVisibility(View.GONE);
                            }
                        }


                        if (response.body().getData() != null) {

                            if(response.body().getData().getAppointment_types() != null && response.body().getData().getAppointment_types().equalsIgnoreCase("Emergency")){
                                img_emergency_appointment.setVisibility(View.VISIBLE);
                            }else{
                                img_emergency_appointment.setVisibility(View.GONE);

                            }

                            if(response.body().getData().getVisit_type() != null && !response.body().getData().getVisit_type().isEmpty() ){
                                txt_visit_type.setText(response.body().getData().getVisit_type());
                            }else{
                                txt_visit_type.setText(response.body().getData().getCommunication_type());
                            }

                           
                            String servname = response.body().getData().getService_name();
                            String pet_name = response.body().getData().getPet_id().getPet_name();
                            pet_image = response.body().getData().getPet_id().getPet_img();
                            String pet_type = response.body().getData().getPet_id().getPet_type();
                            String breed = response.body().getData().getPet_id().getPet_breed();
                            String gender = response.body().getData().getPet_id().getPet_gender();
                            String colour = response.body().getData().getPet_id().getPet_color();
                            double weight = response.body().getData().getPet_id().getPet_weight();
                            String pet_dob = response.body().getData().getPet_id().getPet_dob();
                            String pet_age = response.body().getData().getPet_id().getPet_age();
                            if(pet_dob != null && !pet_dob.isEmpty()){
                                txt_age.setText(pet_dob);
                            }else {
                                txt_age.setText("");
                            }

                            Paymentmethod = response.body().getData().getPayment_method();
                            doctorid = response.body().getData().getDoc_business_info().get(0).getUser_id();

                            /*if(pet_dob != null){
                            String[] separated = pet_dob.split("-");
                            String day = separated[0];
                            String month = separated[1];
                            String year = separated[2];
                            Log.w(TAG,"day : "+day+" month: "+month+" year : "+year);

                            getAge(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
                            }*/


                            if(from != null){
                                if(from.equalsIgnoreCase("PetNewAppointmentAdapter")){
                                    if(appointmentfor != null){
                                        if(appointmentfor.equalsIgnoreCase("Doctor")){
                                            btn_cancel.setVisibility(View.VISIBLE);
                                            if(isVaildDate){
                                                btn_cancel.setVisibility(View.VISIBLE);
                                            }else{
                                                btn_cancel.setVisibility(View.GONE);
                                            }
                                            if(startappointmentstatus != null && !startappointmentstatus.equalsIgnoreCase("Not Started")) {
                                                btn_cancel.setVisibility(View.GONE);
                                            }
                                            if(response.body().getData().getCommunication_type() != null && response.body().getData().getCommunication_type().equalsIgnoreCase("Online")){
                                                img_videocall.setVisibility(View.VISIBLE);
                                            }else{
                                                img_videocall.setVisibility(View.GONE);
                                            }




                                        }else if(appointmentfor.equalsIgnoreCase("SP")){
                                            btn_cancel.setVisibility(View.VISIBLE);
                                            if(userrate != null && userrate.equalsIgnoreCase("0")){
                                                btn_add_review.setVisibility(View.VISIBLE);
                                            }else{
                                                btn_add_review.setVisibility(View.GONE);

                                            }
                                        }
                                    }

                                }
                                else if(from.equalsIgnoreCase("PetMissedAppointmentAdapter")){
                                    btn_cancel.setVisibility(View.GONE);
                                    img_videocall.setVisibility(View.GONE);
                                    btn_add_review.setVisibility(View.GONE);
                                    btn_prescriptiondetails.setVisibility(View.GONE);
                                    img_videocall.setVisibility(View.GONE);

                                }
                                else if(from.equalsIgnoreCase("PetCompletedAppointmentAdapter")) {
                                    img_videocall.setVisibility(View.GONE);

                                    if (userrate != null && userrate.equalsIgnoreCase("0")) {
                                        btn_add_review.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        btn_add_review.setVisibility(View.GONE);

                                    }

                                    if (appointmentfor.equalsIgnoreCase("Doctor")) {
                                        btn_prescriptiondetails.setVisibility(View.VISIBLE);
                                    }else{
                                        btn_prescriptiondetails.setVisibility(View.GONE);
                                    }
                                }

                            }





                            if(response.body().getData().getBooking_date_time() != null){
                                txt_appointment_date.setText(response.body().getData().getBooking_date_time());
                            }
                            if (response.body().getData().getPet_id().isVaccinated()) {
                                vaccinated = "Yes";
                                ll_petlastvacinateddate.setVisibility(View.VISIBLE);
                                if (response.body().getData().getPet_id().getLast_vaccination_date() != null && !response.body().getData().getPet_id().getLast_vaccination_date().isEmpty()) {
                                    txt_petlastvaccinatedage.setText(response.body().getData().getPet_id().getLast_vaccination_date());
                                }

                            }
                            else {
                                ll_petlastvacinateddate.setVisibility(View.GONE);
                                vaccinated = "No";
                            }
                            String order_date = response.body().getData().getDate_and_time();
                            String orderid = response.body().getData().getAppointment_UID();
                            String payment_method = response.body().getData().getPayment_method();
                            String order_cost = response.body().getData().getAmount();
                            String usr_image = null;
                            List<PetNewAppointmentDetailsResponse.DataBean.DocBusinessInfoBean> Address = response.body().getData().getDoc_business_info();
                            for (int i = 0; i < Address.size(); i++) {
                                addr = Address.get(i).getClinic_loc();
                                usrname = Address.get(i).getDr_name();
                                usr_image = Address.get(i).getThumbnail_image();

                            }
                            appoinment_status = response.body().getData().getAppoinment_status();
                            start_appointment_status = response.body().getData().getStart_appointment_status();
                            setView(usrname, usr_image, servname, pet_name, pet_type, breed, gender, colour, weight, order_date, orderid, payment_method, order_cost, vaccinated, addr);

                            if(response.body().getData().getVisit_type() != null &&response.body().getData().getVisit_type().equalsIgnoreCase("Home")){
                                ll_home_address.setVisibility(View.VISIBLE);
                                if(response.body().getAddress() != null){
                                    if(response.body().getAddress().getLocation_address() != null){
                                        txt_home_address.setText(response.body().getAddress().getLocation_address());
                                    }
                                }
                            }
                            else{
                                ll_home_address.setVisibility(View.GONE);
                            }
                        }

                        if(response.body().getData().getAppoinment_status() != null && response.body().getData().getAppoinment_status().equalsIgnoreCase("Incomplete")){
                            Log.w(TAG,"userid : "+response.body().getData().getDoc_business_info().get(0).getUser_id()+" _id  : "+response.body().getData().getDoc_business_info().get(0).get_id());

                            if(response.body().getData().getReshedule_status() != null && response.body().getData().getReshedule_status().isEmpty()){
                                btn_reschedule_appointment.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(PetWalkinAppointmentDetailsActivity.this,PetAppointment_Doctor_Date_Time_Activity.class);
                                        intent.putExtra("doctorid",response.body().getData().getDoc_business_info().get(0).getUser_id());
                                        intent.putExtra("fromactivity",TAG);
                                        intent.putExtra("serviceamount",response.body().getData().getService_amount());
                                        intent.putExtra("communicationtype",response.body().getData().getCommunication_type());
                                        intent.putExtra("bookingdate",response.body().getData().getBooking_date());
                                        intent.putExtra("bookingtime",response.body().getData().getBooking_time());
                                        intent.putExtra("bookingdateandtime",response.body().getData().getBooking_date_time());
                                        intent.putExtra("id",response.body().getData().get_id());
                                        startActivity(intent);
                                        Log.w(TAG,"userid : "+response.body().getData().getDoc_business_info().get(0).getUser_id()+" _id  : "+response.body().getData().getDoc_business_info().get(0).get_id());
                                    }
                                });

                            }
                        }
                        else{
                            btn_reschedule_appointment.setVisibility(View.GONE);

                        }

                        if(response.body().getData().getDoc_business_info().get(0).getPet_handled() != null){
                            for (int i = 0; i < response.body().getData().getDoc_business_info().get(0).getPet_handled().size(); i++) {
                                concatenatedStarNames += response.body().getData().getDoc_business_info().get(0).getPet_handled().get(i).getPet_handled();
                                if (i < response.body().getData().getDoc_business_info().get(0).getPet_handled().size() - 1) concatenatedStarNames += ", ";
                            }
                            txt_dr_pets_handled.setText(concatenatedStarNames);
                            Log.w(TAG," concatenatedStarNames : "+concatenatedStarNames);

                        }

                        if(response.body().getData().getAllergies() != null && !response.body().getData().getAllergies().isEmpty() ){
                            ll_allergies.setVisibility(View.VISIBLE);
                            txt_allergies.setText(response.body().getData().getAllergies());
                        }
                        else{
                            ll_allergies.setVisibility(View.GONE);
                        }
                        if(response.body().getData().getProblem_info() != null && !response.body().getData().getProblem_info().isEmpty() ){
                            ll_comments.setVisibility(View.VISIBLE);
                            txt_comments.setText(response.body().getData().getProblem_info());
                        }
                        else{
                            ll_comments.setVisibility(View.GONE);
                        }

                        if(from != null && from.equalsIgnoreCase("PetCompletedAppointmentAdapter")){
                            if(response.body().getData().getDiagnosis() != null && !response.body().getData().getDiagnosis().isEmpty()){
                                ll_diagnosis.setVisibility(View.VISIBLE);
                                txt_diagnosis.setVisibility(View.VISIBLE);
                                txt_diagnosis.setText(response.body().getData().getDiagnosis());

                            }
                            if(response.body().getData().getSub_diagnosis() != null && !response.body().getData().getSub_diagnosis().isEmpty()){
                                ll_sub_diagnosis.setVisibility(View.VISIBLE);
                                txt_sub_diagnosis.setVisibility(View.VISIBLE);
                                txt_sub_diagnosis.setText(response.body().getData().getSub_diagnosis());
                            }
                            if(response.body().getData().getDoctor_comment() != null && !response.body().getData().getDoctor_comment().isEmpty()){
                                ll_doctor_comment.setVisibility(View.VISIBLE);
                                txt_doctor_comment.setVisibility(View.VISIBLE);
                                txt_doctor_comment.setText(response.body().getData().getDoctor_comment());

                            }






                        }



                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<PetNewAppointmentDetailsResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "PetNewAppointmentDetailsResponse" + "--->" + t.getMessage());
            }
        });

    }


    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private PetNewAppointmentDetailsRequest petNewAppointmentDetailsRequest() {

        PetNewAppointmentDetailsRequest petNewAppointmentDetailsRequest = new PetNewAppointmentDetailsRequest();
        petNewAppointmentDetailsRequest.setApppointment_id(appointment_id);
        Log.w(TAG, "petNewAppointmentDetailsRequest" + "--->" + new Gson().toJson(petNewAppointmentDetailsRequest));
        return petNewAppointmentDetailsRequest;
    }

    @SuppressLint({"SetTextI18n", "LongLogTag", "LogNotTimber"})
    private void setView(String usrname, String usr_image, String servname, String pet_name, String pet_type, String breed, String gender, String colour, double weight,String order_date, String orderid, String payment_method, String order_cost, String vaccinated, String addr) {


        if(usr_image != null && !usr_image.isEmpty()){
            Glide.with(PetWalkinAppointmentDetailsActivity.this)
                    .load(usr_image)
                    .into(img_user);

        }else{
            Glide.with(PetWalkinAppointmentDetailsActivity.this)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(img_user);
        }


        if(usrname != null && !usrname.isEmpty()){

            txt_usrname.setText(usrname);
        }

        if(servname != null && !servname.isEmpty()){
            txt_serv_name.setVisibility(View.VISIBLE);
            txt_serv_name.setText(servname);
        }else{
            txt_serv_name.setVisibility(View.GONE);
        }

        
        if(pet_image != null && pet_image.size()>0){
            String petimage = null;
            for(int i=0;i<pet_image.size();i++){
                petimage = pet_image.get(i).getPet_img();
            }
        
            Glide.with(PetWalkinAppointmentDetailsActivity.this)
                    .load(petimage)
                    .into(img_petimg);
        }else{
            Glide.with(PetWalkinAppointmentDetailsActivity.this)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(img_petimg);
        }


        if(pet_name != null && !pet_name.isEmpty()){
            txt_pet_name.setText(pet_name);
        }

        if(pet_type != null && !pet_type.isEmpty()){

            txt_pet_type.setText(pet_type);
        }

        if(breed != null && !breed.isEmpty()){

            txt_breed.setText(breed);
        }

        if(gender != null && !gender.isEmpty()){

            txt_gender.setText(gender);
        }

        if(colour != null && !colour.isEmpty()){

            txt_color.setText(colour);
        }

        if(weight != 0 ){

            txt_weight.setText(weight+"");
        }

        if(petAgeandMonth != null && !petAgeandMonth.isEmpty()){

            txt_age.setText(petAgeandMonth);
        }
        if(vaccinated != null && !vaccinated.isEmpty()) {
            txt_vaccinated.setText(vaccinated);
        }

        Log.w(TAG,"setview order_date : "+order_date);
        if(order_date != null && !order_date.isEmpty()){
            txt_order_date.setText(order_date);
        }

        if(orderid != null && !orderid.isEmpty()){

            txt_order_id.setText(orderid);
        }

        if(payment_method != null && !payment_method.isEmpty()) {

            txt_payment_method.setText(payment_method);

        }

        if(order_cost != null && !order_cost.isEmpty()){
            txt_order_cost.setText("INR "+order_cost);
            txt_serv_cost.setText("INR "+order_cost);
            ServiceCost = order_cost;
        }

        if(addr != null && !addr.isEmpty()){

            txt_address.setText(addr);
        }


        img_videocall.setOnClickListener(v -> {
            Log.w(TAG,"Start_appointment_status : "+start_appointment_status);
            if(start_appointment_status != null && start_appointment_status.equalsIgnoreCase("Not Started")){
                Toasty.warning(PetWalkinAppointmentDetailsActivity.this,"Doctor is yet to start the Appointment. Please wait for the doctor to initiate the Appointment", Toast.LENGTH_SHORT, true).show();
            }else {
                Intent i = new Intent(PetWalkinAppointmentDetailsActivity.this, VideoCallPetLoverActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", appointment_id);
                Log.w(TAG, "ID-->" + appointment_id);
                startActivity(i);
            }


        });

        btn_cancel.setOnClickListener(v -> showStatusAlert(appointment_id,appointmentfor));

    }

    @SuppressLint("SetTextI18n")
    private void showStatusAlert(String id, String appointmentfor) {
        try {
            dialog = new Dialog(PetWalkinAppointmentDetailsActivity.this);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.cancelappointment);
            Button dialogButtonApprove = dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(view -> {
                dialog.dismiss();
                if(appointmentfor != null) {
                    appoinmentCancelledResponseCall(id);
                   /* if (appointmentfor.equalsIgnoreCase("Doctor")) {
                        Appointmenttype = "1";
                        appoinmentCancelledResponseCall(id);
                    } else if (appointmentfor.equalsIgnoreCase("SP")) {
                        Appointmenttype = "2";
                        spappoinmentCancelledResponseCall(id);

                    }*/
                }



            });
            dialogButtonRejected.setOnClickListener(view -> dialog.dismiss());
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }


    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void appoinmentCancelledResponseCall(String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppoinmentCancelledResponse> call = apiInterface.walkinappoinmentCancelledResponseCall(RestUtils.getContentType(), appoinmentCancelledRequest(id));
        Log.w(TAG,"appoinmentCancelledResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppoinmentCancelledResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Response<AppoinmentCancelledResponse> response) {

                Log.w(TAG,"appoinmentCancelledResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                     //   notificationSendResponseCall();
                        startActivity(new Intent(getApplicationContext(), PetWalkinappointmentsActivity.class));

                    }

                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private AppoinmentCancelledRequest appoinmentCancelledRequest(String id) {

        /*
         * _id : 5fc639ea72fc42044bfa1683
         * missed_at : 23-10-2000 10 : 00 AM
         * doc_feedback : One Emergenecy work i am cancelling this appointment
         * appoinment_status : Missed
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        AppoinmentCancelledRequest appoinmentCancelledRequest = new AppoinmentCancelledRequest();
        appoinmentCancelledRequest.set_id(id);
        appoinmentCancelledRequest.setMissed_at(currentDateandTime);
        appoinmentCancelledRequest.setDoc_feedback("");
        appoinmentCancelledRequest.setAppoint_patient_st("Petowner Cancelled appointment");
        appoinmentCancelledRequest.setAppoinment_status("Missed");
        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(appoinmentCancelledRequest));
        return appoinmentCancelledRequest;
    }


    private void showSuccessfullyCancelled() {
        try {
            dialog = new Dialog(PetWalkinAppointmentDetailsActivity.this);
            dialog.setContentView(R.layout.alert_successfulley_cancelled_layout);
            dialog.setCancelable(false);
            txt_no_records_coupon = dialog.findViewById(R.id.txt_no_records);
            rv_successfully_cancelled = dialog.findViewById(R.id.rv_successfully_cancelled);
            txt_no_records_coupon.setVisibility(View.GONE);
            rv_successfully_cancelled.setVisibility(View.GONE);

            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                CouponCodeTextResponseCall();
            }


            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
//
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.home:
//                callDirections("1");
//                break;
//            case R.id.shop:
//                callDirections("2");
//                break;
//            case R.id.services:
//                callDirections("3");
//                break;
//            case R.id.care:
//                callDirections("4");
//                break;
//            case R.id.community:
//                callDirections("5");
//                break;
//
//            default:
//                return  false;
//        }
//        return true;
//    }


    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), CustomerDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void compareDatesandTime(String currentDateandTime, String bookingDateandTime) {
        try{

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");

            Date currentDate = formatter.parse(currentDateandTime);

            Date responseDate = formatter.parse(bookingDateandTime);

            Log.w(TAG,"compareDatesandTime--->"+"responseDate :"+responseDate+" "+"currentDate :"+currentDate);

            if (currentDate != null) {
                if (responseDate != null) {
                    if (currentDate.compareTo(responseDate)<0 || responseDate.compareTo(currentDate) == 0) {
                        Log.w(TAG,"date is equal");
                        isVaildDate = true;

                    }
                    else{
                        Log.w(TAG,"date is not equal");
                        isVaildDate = false;
                    }
                }
            }


        }catch (ParseException e1){
            e1.printStackTrace();
        }
    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void spAppointmentDetailsResponse() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SPAppointmentDetailsResponse> call = ApiService.spAppointmentDetailsResponse(RestUtils.getContentType(), appointmentDetailsRequest());
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<SPAppointmentDetailsResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<SPAppointmentDetailsResponse> call, @NonNull Response<SPAppointmentDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "SPAppointmentDetailsResponse" + "--->" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        scrollablContent.setVisibility(View.VISIBLE);
                        String vaccinated, addr, usrname;
                        String usr_image = "";
                        if(response.body().getData() != null){
                            if(response.body().getData().getCoupon_status() != null && response.body().getData().getCoupon_status().equalsIgnoreCase("Applied")){
                                ll_original_price.setVisibility(View.VISIBLE);
                                ll_discount_price.setVisibility(View.VISIBLE);
                                if(response.body().getData().getOriginal_price() != 0){
                                    txt_original_price.setText("INR "+response.body().getData().getOriginal_price());
                                }
                                if(response.body().getData().getDiscount_price() != 0){
                                    txt_discount_price.setText("INR "+response.body().getData().getDiscount_price());
                                }

                            }else{
                                ll_original_price.setVisibility(View.GONE);
                                ll_discount_price.setVisibility(View.GONE);
                            }
                        }
                        if (response.body().getData() != null) {

                            spid = response.body().getData().getSp_id().get_id();
                            appointmentid = response.body().getData().getAppointment_UID();
                            userid = response.body().getData().getUser_id().get_id();
                            pet_image = response.body().getData().getPet_id().getPet_img();

                            if(response.body().getData().getBooking_date_time() != null){
                                txt_appointment_date.setText(response.body().getData().getBooking_date_time());
                            }
                            usrname = response.body().getData().getSp_business_info().get(0).getBussiness_name();
                            String servname = response.body().getData().getService_name();
                            String pet_name = response.body().getData().getPet_id().getPet_name();
                            String pet_type = response.body().getData().getPet_id().getPet_type();
                            String breed = response.body().getData().getPet_id().getPet_breed();
                            String gender = response.body().getData().getPet_id().getPet_gender();
                            String colour = response.body().getData().getPet_id().getPet_color();
                            double weight = response.body().getData().getPet_id().getPet_weight();
                            String pet_dob = response.body().getData().getPet_id().getPet_dob();
                            if(pet_dob != null && !pet_dob.isEmpty()){
                                txt_age.setText(pet_dob);
                            }else {
                                txt_age.setText("");
                            }
                            Paymentmethod = response.body().getData().getPayment_method();

                           /* if(pet_dob != null){
                                String[] separated = pet_dob.split("-");
                                String day = separated[0];
                                String month = separated[1];
                                String year = separated[2];
                                Log.w(TAG,"day : "+day+" month: "+month+" year : "+year);

                                getAge(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
                            }*/

                            if (response.body().getData().getPet_id().isVaccinated()) {
                                vaccinated = "Yes";
                                ll_petlastvacinateddate.setVisibility(View.VISIBLE);
                                if (response.body().getData().getPet_id().getLast_vaccination_date() != null) {
                                    txt_petlastvaccinatedage.setText(response.body().getData().getPet_id().getLast_vaccination_date());
                                }

                            }
                            else {
                                ll_petlastvacinateddate.setVisibility(View.GONE);
                                vaccinated = "No";
                            }
                            String order_date = response.body().getData().getDate_and_time();
                            Log.w(TAG,"SPAppointmentDetailsResponse order_date : "+order_date);
                            String orderid = response.body().getData().getAppointment_UID();
                            String payment_method = response.body().getData().getPayment_method();
                            String order_cost = response.body().getData().getService_amount();
                            addr = response.body().getData().getSp_business_info().get(0).getSp_loc();
                            appoinment_status = response.body().getData().getAppoinment_status();
                            start_appointment_status = response.body().getData().getStart_appointment_status();

                            List<SPAppointmentDetailsResponse.DataBean.SpBusinessInfoBean> Address = response.body().getData().getSp_business_info();
                            for (int i = 0; i < Address.size(); i++) {
                                usr_image = Address.get(i).getThumbnail_image();

                            }
                            setView(usrname, usr_image, servname, pet_name, pet_type, breed, gender, colour, weight, order_date, orderid, payment_method, order_cost, vaccinated, addr);


                        }
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<SPAppointmentDetailsResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "PetNewAppointmentDetailsResponse" + "--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private AppointmentDetailsRequest appointmentDetailsRequest() {

        AppointmentDetailsRequest appointmentDetailsRequest = new AppointmentDetailsRequest();
        appointmentDetailsRequest.setApppointment_id(appointment_id);
        Log.w(TAG, "appointmentDetailsRequest" + "--->" + new Gson().toJson(appointmentDetailsRequest));
        return appointmentDetailsRequest;
    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void spappoinmentCancelledResponseCall(String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppoinmentCancelledResponse> call = apiInterface.spappoinmentCancelledResponseCall(RestUtils.getContentType(), appoinmentCancelledRequest(id));
        Log.w(TAG,"appoinmentCancelledResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppoinmentCancelledResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Response<AppoinmentCancelledResponse> response) {

                Log.w(TAG,"appoinmentCancelledResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        spnotificationSendResponseCall();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void spnotificationSendResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<NotificationSendResponse> call = ApiService.spnotificationSendResponseCall(RestUtils.getContentType(),spNotificationSendRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<NotificationSendResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<NotificationSendResponse> call, @NonNull Response<NotificationSendResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"notificationSendResponseCall"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        if(Paymentmethod != null && Paymentmethod.equalsIgnoreCase("Online")){
                            if(ServiceCost != null && !ServiceCost.equalsIgnoreCase("0")){
                                showSuccessfullyCancelled();
                            }else{
                                startActivity(new Intent(PetWalkinAppointmentDetailsActivity.this, PetMyappointmentsActivity.class));

                            }

                        }else{
                            startActivity(new Intent(PetWalkinAppointmentDetailsActivity.this, PetMyappointmentsActivity.class));
                        }

                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<NotificationSendResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"NotificationSendResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private SPNotificationSendRequest spNotificationSendRequest() {

        /*
         * status : Payment Failed
         * date : 23-10-2020 11:00 AM
         * appointment_UID : PET-2923029239123
         * user_id : 601b8ac3204c595ee52582f2
         * sp_id : 601ba9c6270cbe79fd900183
         */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String currentDateandTime = simpleDateFormat.format(new Date());



        SPNotificationSendRequest spNotificationSendRequest = new SPNotificationSendRequest();
        spNotificationSendRequest.setStatus("Patient Appointment Cancelled");
        spNotificationSendRequest.setDate(currentDateandTime);
        spNotificationSendRequest.setAppointment_UID(appointmentid);
        spNotificationSendRequest.setUser_id(userid);
        spNotificationSendRequest.setSp_id(spid);


        Log.w(TAG,"spNotificationSendRequest"+ "--->" + new Gson().toJson(spNotificationSendRequest));
        return spNotificationSendRequest;
    }



    @SuppressLint("LogNotTimber")
    private void notificationSendResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<NotificationSendResponse> call = ApiService.notificationSendResponseCall(RestUtils.getContentType(),notificationSendRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<NotificationSendResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<NotificationSendResponse> call, @NonNull Response<NotificationSendResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"notificationSendResponseCall"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        if(Paymentmethod != null && Paymentmethod.equalsIgnoreCase("Online")){
                            showSuccessfullyCancelled();
                        }else{
                            startActivity(new Intent(PetWalkinAppointmentDetailsActivity.this, PetMyappointmentsActivity.class));
                        }



                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<NotificationSendResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"NotificationSendResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private NotificationSendRequest notificationSendRequest() {

        /*
         * status : Payment Failed
         * date : 23-10-2020 11:00 AM
         * appointment_UID :
         * user_id : 601b8ac3204c595ee52582f2
         * doctor_id :
         */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String currentDateandTime = simpleDateFormat.format(new Date());



        NotificationSendRequest notificationSendRequest = new NotificationSendRequest();
        notificationSendRequest.setStatus("Patient Appointment Cancelled");
        notificationSendRequest.setDate(currentDateandTime);
        notificationSendRequest.setAppointment_UID(appointmentid);
        notificationSendRequest.setUser_id(userid);
        notificationSendRequest.setDoctor_id(doctorid);


        Log.w(TAG,"notificationSendRequest"+ "--->" + new Gson().toJson(notificationSendRequest));
        return notificationSendRequest;
    }



    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void showAddReview(String appointment_id,String appointmentfor) {
        try {

            Dialog dialog = new Dialog(PetWalkinAppointmentDetailsActivity.this);
            dialog.setContentView(R.layout.addreview_popup_layout);
            dialog.setCancelable(true);
            RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
            EditText edt_addreview = dialog.findViewById(R.id.edt_addreview);
            Button btn_addreview = dialog.findViewById(R.id.btn_addreview);

            ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
                userrate = String.valueOf(rating);
                Log.w(TAG,"onRatingChanged userrate : "+userrate);
            });

            btn_addreview.setOnClickListener(view -> {
                if(userrate != null){
                    dialog.dismiss();
                    if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                        if(appointmentfor != null && appointmentfor.equalsIgnoreCase("Doctor")){
                            addReviewResponseCall(appointment_id, edt_addreview.getText().toString(), userrate);

                        }else if(appointmentfor != null && appointmentfor.equalsIgnoreCase("SP")) {
                            spaddReviewResponseCall(appointment_id, edt_addreview.getText().toString(), userrate);
                        }
                    }
                }else{
                    showErrorLoading("Please choose a star.");
                }


            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void addReviewResponseCall(String id, String userfeedback, String userrate) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddReviewResponse> call = apiInterface.addReviewResponseCall(RestUtils.getContentType(), addReviewRequest(id,userfeedback,userrate));
        Log.w(TAG,"addReviewResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AddReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddReviewResponse> call, @NonNull Response<AddReviewResponse> response) {

                Log.w(TAG,"AddReviewResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        showAddReviewSuccess();



                    }
                    else{
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<AddReviewResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"AddReviewResponse flr"+"--->" + t.getMessage());
            }
        });

    } @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void spaddReviewResponseCall(String id, String userfeedback, String userrate) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddReviewResponse> call = apiInterface.spaddReviewResponseCall(RestUtils.getContentType(), addReviewRequest(id,userfeedback,userrate));
        Log.w(TAG,"spaddReviewResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AddReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddReviewResponse> call, @NonNull Response<AddReviewResponse> response) {

                Log.w(TAG,"spaddReviewResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        showAddReviewSuccess();



                    }
                    else{
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<AddReviewResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"spaddReviewResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }



    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private AddReviewRequest addReviewRequest(String id, String userfeedback, String userrate) {

        /*
         * _id : 5fd30a701978e618628c966c
         * user_feedback :
         * user_rate : 0
         */
        AddReviewRequest addReviewRequest = new AddReviewRequest();
        addReviewRequest.set_id(id);
        if(userfeedback != null){
            addReviewRequest.setUser_feedback(userfeedback);

        }else{
            addReviewRequest.setUser_feedback("");

        }if(userrate != null){
            int c = 0;
            try {

                c = Integer.parseInt(userrate);

            }
            catch(NumberFormatException e) {

                double d = Double.parseDouble(userrate);

                c = (int) d;
            }
            addReviewRequest.setUser_rate(c);
        }else{
            addReviewRequest.setUser_rate(0);

        }
        Log.w(TAG,"addReviewRequest"+ "--->" + new Gson().toJson(addReviewRequest));
        return addReviewRequest;
    }
    private void showAddReviewSuccess() {
        try {

            Dialog dialog = new Dialog(PetWalkinAppointmentDetailsActivity.this);
            dialog.setContentView(R.layout.addreview_review_success_layout);
            dialog.setCancelable(false);

            Button btn_back = dialog.findViewById(R.id.btn_back);


            btn_back.setOnClickListener(view -> {
                dialog.dismiss();
                startActivity(new Intent(PetWalkinAppointmentDetailsActivity.this,PetMyappointmentsActivity.class));


            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
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

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void getAge(int year, int month, int day){
        Log.w(TAG,"getAge : year "+year+" month : "+ month+" day : "+day);
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        Log.w(TAG,"age : "+age+" todayyear : "+today.get(Calendar.YEAR)+" dobyear : "+ dob.get(Calendar.YEAR));


        int months = dob.get(Calendar.MONTH) - today.get(Calendar.MONTH);
        int currentmonths = (today.get(Calendar.MONTH))+1;
        Log.w(TAG,"dob months: "+dob.get(Calendar.MONTH)+" currentmonths : "+ currentmonths);

        Log.w(TAG," todayyear : "+today.get(Calendar.YEAR)+" dobyear : "+ dob.get(Calendar.YEAR));

        Log.w(TAG,"Conditions : "+(today.get(Calendar.YEAR) < dob.get(Calendar.YEAR)));
        if(today.get(Calendar.YEAR) < dob.get(Calendar.YEAR)){
            age--;
        }

        Log.w(TAG,"age: "+age+" monthsInt : "+ months);
        String ageS = Integer.toString(age);
        String monthsS = Integer.toString(months);

        Log.w(TAG,"ageS: "+ageS+" months : "+monthsS);

        if(age != 0){
            petAgeandMonth = ageS+" years "+monthsS+" months";
        }else{
            petAgeandMonth = monthsS+" months";

        }



        Log.w(TAG,"ageS: "+ageS+" months : "+monthsS);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_homes:

                callDirections("1");
                break;

            case R.id.rl_home:

                callDirections("1");
                break;

            case R.id.rl_shop:
                callDirections("2");
                break;

            case R.id.rl_service:

                callDirections("3");

                break;


            case R.id.rl_care:

                callDirections("4");

                break;

            case R.id.rl_comn:

                callDirections("5");
                break;

        }
    }


    private void setMargins(RelativeLayout rl_layout, int i, int i1, int i2, int i3) {

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)rl_layout.getLayoutParams();
        params.setMargins(i, i1, i2, i3);
        rl_layout.setLayoutParams(params);
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void CouponCodeTextResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<CouponCodeTextResponse> call = apiInterface.CouponCodeTextResponseCall(RestUtils.getContentType());
        Log.w(TAG,"CouponCodeTextResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<CouponCodeTextResponse>() {
            @SuppressLint({"SetTextI18n", "LogNotTimber", "LongLogTag"})
            @Override
            public void onResponse(@NonNull Call<CouponCodeTextResponse> call, @NonNull Response<CouponCodeTextResponse> response) {

                Log.w(TAG,"CouponCodeTextResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        if(response.body().getData() != null && response.body().getData().size()>0){
                            txt_no_records_coupon.setVisibility(View.GONE);
                            rv_successfully_cancelled.setVisibility(View.VISIBLE);
                            myCouponsTextList = response.body().getData();
                            setViewCouponText();

                        }
                        else{
                            rv_successfully_cancelled.setVisibility(View.GONE);
                            txt_no_records_coupon.setVisibility(View.VISIBLE);
                            txt_no_records_coupon.setText("No data found");

                        }



                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<CouponCodeTextResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"CouponCodeTextResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private void setViewCouponText() {
        rv_successfully_cancelled.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_successfully_cancelled.setItemAnimator(new DefaultItemAnimator());
        MyCouponsTextAdapter myCouponsTextAdapter = new MyCouponsTextAdapter(getApplicationContext(), myCouponsTextList,ServiceCost,this);
        rv_successfully_cancelled.setAdapter(myCouponsTextAdapter);

    }

    @SuppressLint("LongLogTag")
    private void RefundCouponCreateRequestCall(String refund, String cost) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = ApiService.RefundCouponCreateRequestCall(RestUtils.getContentType(),refundCouponCreateRequest(refund,cost));

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"RefundCouponCreateRequestCall"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), PetMyappointmentsActivity.class));

                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"RefundCouponCreateRequestCall flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private RefundCouponCreateRequest refundCouponCreateRequest(String refund, String cost) {

        /*
         * created_by : User
         * coupon_type : 1
         * code : REF100
         * amount : 100
         * user_details : 123123
         * used_status : Not Used
         */


        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMhhmmss");
        String currentDateandTime = simpleDateFormat.format(new Date());



        RefundCouponCreateRequest refundCouponCreateRequest = new RefundCouponCreateRequest();
        refundCouponCreateRequest.setCreated_by("User");
        refundCouponCreateRequest.setCoupon_type(Appointmenttype);
        refundCouponCreateRequest.setCode("REF"+currentDateandTime);
        if(cost != null && !cost.isEmpty()){
            refundCouponCreateRequest.setAmount(Integer.parseInt(cost));
        }else{
            refundCouponCreateRequest.setAmount(0);
        }

        refundCouponCreateRequest.setUser_details(userid);
        refundCouponCreateRequest.setUsed_status("Not Used");
        refundCouponCreateRequest.setMobile_type("Android");


        Log.w(TAG,"refundCouponCreateRequest"+ "--->" + new Gson().toJson(refundCouponCreateRequest));
        return refundCouponCreateRequest;
    }


    private void RefundCouponBankCreateRequestCall(String refund, String cost) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = ApiService.RefundCouponCreateRequestCall(RestUtils.getContentType(),refundCouponCreateRequest1(refund,cost));

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"RefundCouponCreateRequestCall"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), PetMyappointmentsActivity.class));

                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"RefundCouponCreateRequestCall flr"+"--->" + t.getMessage());
            }
        });

    }
    private RefundCouponCreateRequest refundCouponCreateRequest1(String refund, String cost) {

        /*
         * created_by : User
         * coupon_type : 1
         * code : REF100
         * amount : 100
         * user_details : 123123
         * used_status : Not Used
         */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String currentDateandTime = simpleDateFormat.format(new Date());



        RefundCouponCreateRequest refundCouponCreateRequest = new RefundCouponCreateRequest();
        refundCouponCreateRequest.setCreated_by("");
        refundCouponCreateRequest.setCoupon_type(Appointmenttype);
        refundCouponCreateRequest.setCode("Bank");
        refundCouponCreateRequest.setAmount(0);
        refundCouponCreateRequest.setUser_details(appointment_id);
        refundCouponCreateRequest.setUsed_status("");
        refundCouponCreateRequest.setMobile_type("Android");


        Log.w(TAG,"refundCouponCreateRequest"+ "--->" + new Gson().toJson(refundCouponCreateRequest));
        return refundCouponCreateRequest;
    }

    @Override
    public void onAppointmentSuccessfullyCancel(String refund, String cost) {
        Log.w(TAG,"onAppointmentSuccessfullyCancel : "+"refund : "+refund+"cost : "+cost);

       if(cost != null && cost.equalsIgnoreCase("0")){
           startActivity(new Intent(getApplicationContext(),PetWalkinappointmentsActivity.class));
       }else{
           if(refund != null && !refund.isEmpty()){
               RefundCouponCreateRequestCall(refund,cost);
           }else{
               RefundCouponBankCreateRequestCall(refund,cost);

           }
       }


    }
}