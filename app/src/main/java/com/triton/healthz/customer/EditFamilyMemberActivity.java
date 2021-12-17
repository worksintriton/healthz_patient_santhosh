package com.triton.healthz.customer;


import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.healthz.R;
import com.triton.healthz.adapter.AddFamilyImageListAdapter;
import com.triton.healthz.adapter.EditFamilyImageListAdapter;
import com.triton.healthz.api.APIClient;
import com.triton.healthz.api.RestApiInterface;
import com.triton.healthz.appUtils.FileUtil;
import com.triton.healthz.requestpojo.FamilyMemberCreateRequest;
import com.triton.healthz.requestpojo.FamilyMembersEditRequest;
import com.triton.healthz.responsepojo.FamilyMemberCreateResponse;
import com.triton.healthz.responsepojo.FamilyMemberListResponse;
import com.triton.healthz.responsepojo.FamilyMembersEditResponse;
import com.triton.healthz.responsepojo.FileUploadResponse;
import com.triton.healthz.responsepojo.GetFamilyMemberResponse;
import com.triton.healthz.responsepojo.PetListResponse;
import com.triton.healthz.sessionmanager.SessionManager;
import com.triton.healthz.utils.ConnectionDetector;
import com.triton.healthz.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFamilyMemberActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditFamilyMemberActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sprrelationtype)
    Spinner sprrelationtype;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sprgender)
    Spinner sprgender;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_continue)
    Button btn_continue;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_name)
    EditText edt_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_pregnant)
    CheckBox cb_pregnant;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_diabetes)
    CheckBox cb_diabetes;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_ocd)
    CheckBox cb_ocd;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_dob)
    EditText edt_dob;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_bio)
    EditText edt_bio;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_weight)
    EditText edt_weight;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cdvw_uploadImage)
    CardView cdvw_uploadImage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_yes)
    CheckBox cb_yes;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_no)
    CheckBox cb_no;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_uploaded_images)
    RecyclerView rv_uploaded_images;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_uploadimage)
    ImageView img_uploadimage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    List<FamilyMembersEditRequest.PicBean> picBeanList = new ArrayList<>();

    List<GetFamilyMemberResponse.DataBean> getfamilymemberslist;

    Dialog alertDialog;

    String userid,strrelationtype="",strhealthissue="",strgendertype="";

    int sprflag = 0;

    private String selectedRadioButton = "Yes";

    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private static final String CAMERA_PERMISSION = CAMERA ;
    private static final String READ_EXTERNAL_STORAGE_PERMISSION = READ_EXTERNAL_STORAGE;
    private static final String WRITE_EXTERNAL_STORAGE_PERMISSION = WRITE_EXTERNAL_STORAGE;





    private String ServerUrlImagePath;

    private static final int REQUEST_READ_CLINIC_PIC_PERMISSION = 786;
    private static final int REQUEST_CLINIC_CAMERA_PERMISSION_CODE = 785 ;




    private static final int SELECT_CLINIC_CAMERA = 1000 ;

    private static final int SELECT_CLINIC_PICTURE = 1001 ;
    private MultipartBody.Part filePart;

    private String petId,tagg;
    private String doctorid;
    private String fromactivity;
    private String fromto;
    private String Payment_id = "";
    private String Doctor_ava_Date = "";
    private String selectedTimeSlot = "";
    private int amount;
    private String communicationtype = "";


    private String spid,catid,from;
    private String spuserid;
    private String selectedServiceTitle;
    private String petcolor,petname,serviceprovidingcompanyname;
    private double petweight;
    private String servicetime;
    private int serviceamount;
    private String petage,doctorname,clinicname;
    private int distance;
    private String SP_ava_Date;

    final Calendar myCalendar = Calendar.getInstance();


    private SessionManager session;
    String name,emailid,phoneNo,active_tag,strdistance;
    private List<PetListResponse.DataBean> petList;
    private Dialog dialog;
    private String profileimage;


    private static final int REQUEST_PHONE_CALL =1 ;
    private String sosPhonenumber;


    private int reviewcount;
    private int Count_value_start;
    private int Count_value_end;
    private String _id;

    ArrayList<Integer> product_idList;
    private int product_id;
    private String orderid;
    private String cancelorder;
    private String latlng,CityName,AddressLine,PostalCode,nickname;

    String locationtype;
    private String pincode,cityname,address;
    private boolean defaultstatus;
    private String id;
    double latitude, longtitude;

    String appointment_id;
    String appoinment_status;
    private String bookedat;
    private String startappointmentstatus;
    private String appointmentfor;
    private String userrate;

    private int year, month, day;
    String SelectedLastVaccinateddate = "";
    private static final int DATE_PICKER_ID = 0 ;
    private static final int PET_DATE_PICKER_ID = 1 ;

    String family_id;
    String user_id;
    String member_name;
    String gender;
    String relation_type;
    String health_issue;
    String dateofbirth;
    String anymedicalinfo;
    String covide_vac;
    String weight;

    List<FamilyMemberListResponse.DataBean.PicBean> petImgBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_family_member);

        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        img_back.setOnClickListener(this);

        btn_continue.setOnClickListener(this);

        img_uploadimage.setOnClickListener(this);

        SessionManager sessionManager = new SessionManager(EditFamilyMemberActivity.this);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        Intent intent = getIntent();

        Bundle args = intent.getBundleExtra("petimage");

        if(args!=null&&!args.isEmpty()){

            petImgBeanList = (ArrayList<FamilyMemberListResponse.DataBean.PicBean>) args.getSerializable("PETLIST");
        }

        if(petImgBeanList!=null&&petImgBeanList.size()>0){

            for(int i=0;i<petImgBeanList.size();i++){

                picBeanList.add(new FamilyMembersEditRequest.PicBean(petImgBeanList.get(i).getImage()));
            }

            if(picBeanList!=null&&picBeanList.size()>0){

                setView(picBeanList);
            }
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            family_id = extras.getString("family_id");
            fromactivity = extras.getString("fromactivity");
            userid = extras.getString("userid");
            member_name = extras.getString("member_name");
            gender = extras.getString("gender");
            relation_type = extras.getString("relation_type");
            health_issue = extras.getString("health_issue");
            dateofbirth = extras.getString("dateofbirth");
            anymedicalinfo = extras.getString("anymedicalinfo");
            covide_vac = extras.getString("covide_vac");
            weight = extras.getString("weight");
        }

        if (new ConnectionDetector(EditFamilyMemberActivity.this).isNetworkAvailable(EditFamilyMemberActivity.this)) {
            getfamilymembersListResponseCall();

        }

        if(member_name!=null){

            edt_name.setText(member_name);
        }

        if(dateofbirth!=null){

            edt_dob.setText(dateofbirth);
        }


        if(anymedicalinfo!=null){

            edt_bio.setText(anymedicalinfo);
        }

        if(weight!=null){

            edt_weight.setText(weight);
        }


        ArrayList<String> familymemberstypeArrayList = new ArrayList<>();

        familymemberstypeArrayList.add("Select Gender");

        familymemberstypeArrayList.add("Male");

        familymemberstypeArrayList.add("Female");

        familymemberstypeArrayList.add("Transgender");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(EditFamilyMemberActivity.this, R.layout.spinner_item, familymemberstypeArrayList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view

        sprgender.setAdapter(spinnerArrayAdapter);


        sprrelationtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {

                if(++sprflag > 1) {

                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                    strrelationtype = sprrelationtype.getSelectedItem().toString();

                    Log.w(TAG,"strrelationtype:"+strrelationtype);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        sprgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {

                if(++sprflag > 1) {

                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                    strgendertype = sprgender.getSelectedItem().toString();

                    Log.w(TAG,"strgendertype:"+strgendertype);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        cb_yes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                selectedRadioButton = "Yes";
                cb_no.setChecked(false);
            }
        });
        cb_no.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                selectedRadioButton = "No";
                cb_yes.setChecked(false);
            }
        });

        cb_pregnant.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                strhealthissue = "Pregnant";
                cb_diabetes.setChecked(false);
                cb_ocd.setChecked(false);
            }
        });
        cb_diabetes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                strhealthissue = "Diabetes";
                cb_pregnant.setChecked(false);
                cb_ocd.setChecked(false);
            }
        });
        cb_ocd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                strhealthissue = "OCD";
                cb_pregnant.setChecked(false);
                cb_diabetes.setChecked(false);
            }
        });



        edt_dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SelectDate();
            }
        });
    }

      private void SelectDate() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        showDialog(DATE_PICKER_ID);

    }

    @SuppressLint("LogNotTimber")
    @Override
    protected Dialog onCreateDialog(int id) {
        Log.w(TAG,"onCreateDialog id : "+id);
        if (id == DATE_PICKER_ID) {
            // open datepicker dialog.
            // set date picker for current date
            // add pickerListener listner to date picker
            // return new DatePickerDialog(this, pickerListener, year, month,day);
            DatePickerDialog dialog = new DatePickerDialog(this, pickerListener, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dialog;
        }
        return null;
    }
    private final DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @SuppressLint("LogNotTimber")
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;



            String strdayOfMonth;
            String strMonth;
            int month1 =(month + 1);
            if(day == 9 || day <9){
                strdayOfMonth = "0"+day;
                Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
            }else{
                strdayOfMonth = String.valueOf(day);
            }

            if(month1 == 9 || month1 <9){
                strMonth = "0"+month1;
                Log.w(TAG,"Selected month1-->"+strMonth);
            }else{
                strMonth = String.valueOf(month1);
            }

            SelectedLastVaccinateddate = strdayOfMonth + "-" + strMonth + "-" + year;

            // Show selected date
            edt_dob.setText(SelectedLastVaccinateddate);

        }
    };

    public void addYourfamilyValidator() {
        boolean can_proceed = true;

        int namelength = edt_name.getText().toString().trim().length();
        int weightlength = edt_weight.getText().toString().trim().length();

        if (edt_name.getText().toString() != null && edt_name.getText().toString().trim().equals("") && edt_weight.getText().toString()!= null && edt_weight.getText().toString().trim().equals("")
        ) {
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_name.getText().toString().trim().equals("")) {
            edt_name.setError("Please enter name");
            edt_name.requestFocus();
            can_proceed = false;
        }else if (namelength > 25) {
            edt_name.setError("The maximum length for anname is 25 characters.");
            edt_name.requestFocus();
            can_proceed = false;
        }
        else if (edt_weight.getText().toString().trim().equals("")) {
            edt_weight.setError("Please enter weight");
            edt_weight.requestFocus();
            can_proceed = false;
        }
        else if (weightlength > 5) {
            edt_weight.setError("The maximum length for an weight is 5 characters.");
            edt_weight.requestFocus();
            can_proceed = false;
        }
        else if (edt_dob.getText().toString().trim().equals("")) {
            edt_dob.setError("Please enter D.O.B");
            edt_dob.requestFocus();
            can_proceed = false;
        }

        else if (edt_bio.getText().toString().trim().equals("")) {
            edt_bio.setError("Please enter bio");
            edt_bio.requestFocus();
            can_proceed = false;
        }
        else if (strrelationtype.trim().equals("")) {
            showErrorLoading("Please select relation type");
            can_proceed = false;
        }
        else if (picBeanList.size()==0) {
            showErrorLoading("Please upload one image of your family member");
            can_proceed = false;
        }

        if (can_proceed) {
            if (new ConnectionDetector(EditFamilyMemberActivity.this).isNetworkAvailable(EditFamilyMemberActivity.this)) {

                familymemberseditResponseCall();
            }

        }

    }



    @SuppressLint("LogNotTimber")
    public void getfamilymembersListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<GetFamilyMemberResponse> call = apiInterface.getfamilymembersListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<GetFamilyMemberResponse>() {
            @SuppressLint({"LogNotTimber", "LongLogTag"})
            @Override
            public void onResponse(@NonNull Call<GetFamilyMemberResponse> call, @NonNull Response<GetFamilyMemberResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"GetFamilyMemberResponse" + new Gson().toJson(response.body()));

                        if(response.body().getData() != null){
                            getfamilymemberslist = response.body().getData();
                        }


                        if(getfamilymemberslist != null && getfamilymemberslist.size()>0){
                            setFView(getfamilymemberslist);
                        }
                    }



                }








            }


            @Override
            public void onFailure(@NonNull Call<GetFamilyMemberResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"GetFamilyMemberResponse flr"+t.getMessage());
            }
        });

    }

    @SuppressLint("LogNotTimber")
    private void setFView(List<GetFamilyMemberResponse.DataBean> getfamilymemberslist) {

        ArrayList<String> familymemberstypeArrayList = new ArrayList<>();
        familymemberstypeArrayList.add("Select Relation Type");
        for (int i = 0; i < getfamilymemberslist.size(); i++) {

            String relationType = getfamilymemberslist.get(i).getName();
            Log.w(TAG, "relationType-->" + relationType);
            familymemberstypeArrayList.add(relationType);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(EditFamilyMemberActivity.this, R.layout.spinner_item, familymemberstypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            sprrelationtype.setAdapter(spinnerArrayAdapter);

        }

    }

    @SuppressLint("LogNotTimber")
    private void familymemberseditResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<FamilyMembersEditResponse> call = apiInterface.familymemberseditResponseCall(RestUtils.getContentType(),FamilyMembersEditRequest());
        Log.w(TAG,"FamilyMembersEditResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FamilyMembersEditResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FamilyMembersEditResponse> call, @NonNull Response<FamilyMembersEditResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"FamilyMembersEditResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {


                            Toasty.success(EditFamilyMemberActivity.this," "+response.body().getMessage(), Toasty.LENGTH_LONG).show();

                            callDirections();
                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<FamilyMembersEditResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private FamilyMembersEditRequest FamilyMembersEditRequest() {

        /*
         * user_id : 618230269dcc2a290e5bae9a
         * name : Mohammed
         * gender : Male
         * relation_type : Son
         * health_issue : No issue
         * dateofbirth : 23-10-2021
         * anymedicalinfo : No Issue
         * covide_vac : Yes
         * weight : 70
         * pic : [{"image":"http://Google.com"}]
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());


        FamilyMembersEditRequest FamilyMembersEditRequest = new FamilyMembersEditRequest();
        FamilyMembersEditRequest.set_id(family_id);
        FamilyMembersEditRequest.setUser_id(userid);
        FamilyMembersEditRequest.setName(edt_name.getText().toString().trim());
        FamilyMembersEditRequest.setGender(strgendertype);
        FamilyMembersEditRequest.setRelation_type(strrelationtype);
        FamilyMembersEditRequest.setHealth_issue(strhealthissue);
        FamilyMembersEditRequest.setDateofbirth(edt_dob.getText().toString().trim());
        FamilyMembersEditRequest.setAnymedicalinfo(edt_bio.getText().toString().trim());
        FamilyMembersEditRequest.setCovide_vac(selectedRadioButton);
        FamilyMembersEditRequest.setWeight(edt_weight.getText().toString().trim());
        FamilyMembersEditRequest.setPic(picBeanList);
        Log.w(TAG,"FamilyMembersEditRequest "+ new Gson().toJson(FamilyMembersEditRequest));
        return FamilyMembersEditRequest;
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

    private void gotoPetloverDashboard() {
        Intent intent = new Intent(EditFamilyMemberActivity.this,CustomerDashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callDirections();
    }

    public void callDirections(){


        Intent intent = new Intent(getApplicationContext(), CustomerProfileScreenActivity.class);

        startActivity(intent);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.txt_skip:
                callDirections();
                break;
            case R.id.btn_continue:
                addYourfamilyValidator();
                break;
            case R.id.img_uploadimage:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkMultiplePermissions(REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS, EditFamilyMemberActivity.this);
                }else{
                    choosePetImage();

                }
                break;

        }

    }

    private void choosePetImage() {


        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        //AlertDialog.Builder alert=new AlertDialog.Builder(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(EditFamilyMemberActivity.this);
        builder.setTitle("Choose option");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo"))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(EditFamilyMemberActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CLINIC_CAMERA_PERMISSION_CODE);
                }
                else
                {


                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, SELECT_CLINIC_CAMERA);
                }

            }

            else if (items[item].equals("Choose from Library"))
            {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(EditFamilyMemberActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_CLINIC_PIC_PERMISSION);
                }

                else{

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_CLINIC_PICTURE);


                }
            }

            else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //	Toast.makeText(getActivity(),"kk",Toast.LENGTH_SHORT).show();
        if(requestCode== SELECT_CLINIC_PICTURE || requestCode == SELECT_CLINIC_CAMERA)
        {

            if(requestCode == SELECT_CLINIC_CAMERA)
            {
                Bitmap photo = (Bitmap) data.getExtras().get("data");

          File file = new File(getFilesDir(), "Healthz1" + ".jpg");

                OutputStream os;
                try {
                    os = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());

                RequestBody requestFile = RequestBody.create(MediaType.parse("image*/"), file);

                filePart = MultipartBody.Part.createFormData("sampleFile",  userid+currentDateandTime+file.getName(), requestFile);

                uploadPetImage();

            }

            else{

                try {
                    if (resultCode == Activity.RESULT_OK)
                    {

                        Log.w("VALUEEEEEEE1111", " " + data);

                        Uri selectedImageUri = data.getData();

                        Log.w("selectedImageUri", " " + selectedImageUri);

                        String filename = getFileName(selectedImageUri);

                        Log.w("filename", " " + filename);

                        String filePath = FileUtil.getPath(EditFamilyMemberActivity.this,selectedImageUri);

                        assert filePath != null;

                        File file = new File(filePath); // initialize file here

                        long length = file.length() / 1024; // Size in KB

                        Log.w("filesize", " " + length);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());

                        filePart = MultipartBody.Part.createFormData("sampleFile", userid+currentDateandTime+file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

                        uploadPetImage();


                    }
                } catch (Exception e) {

                    Log.w("Exception", " " + e);
                }

            }

        }



    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }





    private void uploadPetImage() {

        avi_indicator.show();

        RestApiInterface apiInterface = APIClient.getImageClient().create(RestApiInterface.class);


        Call<FileUploadResponse> call = apiInterface.getImageStroeResponse(filePart);


        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<FileUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<FileUploadResponse> call, @NonNull Response<FileUploadResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"Profpic"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if (200 == response.body().getCode()) {

                        ServerUrlImagePath = response.body().getData();
                        btn_continue.setVisibility(View.VISIBLE);

                        Log.w(TAG, "ServerUrlImagePath " + ServerUrlImagePath);

                        if( response.body().getData() != null)
                        {

                            if(picBeanList.size()>=1){

                                Toasty.warning(EditFamilyMemberActivity.this,"Sorry You can't Upload more than 1", Toasty.LENGTH_LONG).show();

                            }

                            else
                            {
                                if(ServerUrlImagePath != null&&!ServerUrlImagePath.isEmpty())
                                {
                                    picBeanList.add(new FamilyMembersEditRequest.PicBean(ServerUrlImagePath));

                                }
                                else
                                {
                                    picBeanList.add(new FamilyMembersEditRequest.PicBean(APIClient.IMAGE_BASE_URL));

                                }

                                setView(picBeanList);

                            }



                        }

                        else
                        {
                            Toasty.warning(EditFamilyMemberActivity.this,"Failed to Upload", Toasty.LENGTH_LONG).show();

                        }



                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<FileUploadResponse> call, @NonNull Throwable t) {
                // avi_indicator.smoothToHide();
                Log.w(TAG,"ServerUrlImagePath"+ "On failure working"+ t.getMessage());
                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setView(List<FamilyMembersEditRequest.PicBean> picBeanList) {

        rv_uploaded_images.setHasFixedSize(true);

        rv_uploaded_images.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(EditFamilyMemberActivity.this, LinearLayoutManager.HORIZONTAL, false);

        rv_uploaded_images.setLayoutManager(layoutManager);

        EditFamilyImageListAdapter addFamilyImageListAdapter = new EditFamilyImageListAdapter(this, picBeanList);

        rv_uploaded_images.setAdapter(addFamilyImageListAdapter);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CLINIC_PIC_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_CLINIC_PICTURE);

            } else {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Permisson Required")
                        .setContentText("Plz Allow Permissions for choosing Images from Gallery ")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(sDialog -> {

                            sDialog.dismissWithAnimation();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_READ_CLINIC_PIC_PERMISSION);
                            }


                        })
                        .setCancelButton("Cancel", sDialog -> {
                            sDialog.dismissWithAnimation();

                            showWarning(REQUEST_READ_CLINIC_PIC_PERMISSION);
                        })
                        .show();

            }

        } else if (requestCode == REQUEST_CLINIC_CAMERA_PERMISSION_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, SELECT_CLINIC_CAMERA);

            } else {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Permisson Required")
                        .setContentText("Plz Allow Camera for taking picture")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(sDialog -> {

                            sDialog.dismissWithAnimation();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{CAMERA}, REQUEST_CLINIC_CAMERA_PERMISSION_CODE);
                            }


                        })
                        .setCancelButton("Cancel", sDialog -> {
                            sDialog.dismissWithAnimation();

                            showWarning(REQUEST_CLINIC_CAMERA_PERMISSION_CODE);
                        })
                        .show();

            }

        }
    }

    //check for camera and storage access permissions
    @TargetApi(Build.VERSION_CODES.M)
    private void checkMultiplePermissions(int permissionCode, Context context) {

        String[] PERMISSIONS = {CAMERA_PERMISSION, READ_EXTERNAL_STORAGE_PERMISSION, WRITE_EXTERNAL_STORAGE_PERMISSION};
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, permissionCode);
        } else {
            choosePetImage();
            // Open your camera here.
        }
    }
    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWarning(int REQUEST_PERMISSION_CODE) {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Sorry!!")
                .setContentText("You Can't proceed further unless you allow permission")
                .setConfirmText("Ok")
                .setConfirmClickListener(sDialog -> {

                    sDialog.dismissWithAnimation();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
                    }


                })
                .setCancelButton("Cancel", SweetAlertDialog::dismissWithAnimation)
                .show();
    }
}