package com.triton.healthZ.doctor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.IOUtils;
import com.google.gson.Gson;
import com.triton.healthZ.R;
import com.triton.healthZ.api.APIClient;
import com.triton.healthZ.api.RestApiInterface;
import com.triton.healthZ.requestpojo.DoctorUpdateProfileImageRequest;
import com.triton.healthZ.responsepojo.DoctorUpdateProfileImageResponse;
import com.triton.healthZ.responsepojo.FileUploadResponse;
import com.triton.healthZ.sessionmanager.SessionManager;
import com.triton.healthZ.utils.RestUtils;
import com.canhub.cropper.CropImage;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class EditDoctorProfileImageActivity extends AppCompatActivity implements View.OnClickListener {
    private  String TAG = "EditDoctorProfileImageActivity";

    @BindView(R.id.img_back)
    ImageView img_back;




    @BindView(R.id.img_pet_imge)
    ImageView img_pet_imge;


    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.txt_uploadpetimage)
    TextView txt_uploadpetimage;

    @BindView(R.id.btn_continue)
    Button btn_continue;


    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private static final String CAMERA_PERMISSION = CAMERA ;
    private static final String READ_EXTERNAL_STORAGE_PERMISSION = READ_EXTERNAL_STORAGE;
    private static final String WRITE_EXTERNAL_STORAGE_PERMISSION = WRITE_EXTERNAL_STORAGE;







    private static final int REQUEST_READ_CLINIC_PIC_PERMISSION = 786;
    private static final int REQUEST_CLINIC_CAMERA_PERMISSION_CODE = 785 ;




    private static final int SELECT_CLINIC_CAMERA = 1000 ;

    private static final int SELECT_CLINIC_PICTURE = 1001 ;
    private MultipartBody.Part filePart;
    private String userid;
    private String firstname,lastname,useremail;
    private String phonenumber,usertype,userstatus,profileimage;
    private String verifyemailstatus;
    private String refcode;

    int PERMISSION_CLINIC = 1;
    int PERMISSION_CERT = 2;
    int PERMISSION_GOVT = 3;
    int PERMISSION_PHOTO = 4;

    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_profile_image);
        Log.w(TAG,"onCreate ");
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);
        btn_continue.setVisibility(View.GONE);
        img_back.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        txt_uploadpetimage.setOnClickListener(this);
        img_pet_imge.setOnClickListener(this);



        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        firstname = user.get(SessionManager.KEY_FIRST_NAME);
        lastname = user.get(SessionManager.KEY_LAST_NAME);
        useremail = user.get(SessionManager.KEY_EMAIL_ID);
        phonenumber = user.get(SessionManager.KEY_MOBILE);
        userid = user.get(SessionManager.KEY_ID);
        usertype = user.get(SessionManager.KEY_TYPE);
        userstatus = user.get(SessionManager.KEY_PROFILE_STATUS);
        profileimage = user.get(SessionManager.KEY_PROFILE_IMAGE);
        verifyemailstatus = user.get(SessionManager.KEY_VERIFY_EMAIL_STATUS);
        refcode = user.get(SessionManager.KEY_REF_CODE);

        if(profileimage != null && !profileimage.isEmpty()){
            Glide.with(EditDoctorProfileImageActivity.this)
                    .load(profileimage)
                    .into(img_pet_imge);
            txt_uploadpetimage.setText("Change Image");
        }else{
            Glide.with(EditDoctorProfileImageActivity.this)
                    .load(R.drawable.image_thumbnail)
                    .into(img_pet_imge);
            txt_uploadpetimage.setText("Upload Image");

        }




    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;


                case R.id.txt_uploadpetimage:
                   gotoUplodPetImage();
                break;

                case R.id.img_pet_imge:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkMultiplePermissions(REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS, EditDoctorProfileImageActivity.this);
                }else{
                    choosePetImage();

                }
                break;

                case R.id.btn_continue:
                    DoctorUpdateProfileImageResponseCall();
                break;
        }
    }

    private void gotoUplodPetImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkMultiplePermissions(REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS, EditDoctorProfileImageActivity.this);
        }else{
            choosePetImage();

        }
    }

    private void gotoDoctorProfileScreenActivity() {
        Intent intent = new Intent(EditDoctorProfileImageActivity.this, DoctorProfileScreenActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditDoctorProfileImageActivity.this, DoctorProfileScreenActivity.class));
        finish();
    }




    private void choosePetImage() {

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CLINIC);
        }



        else
        {


            CropImage.activity().start(EditDoctorProfileImageActivity.this);

            /*CropImage.activity().start(AddYourPetImageOlduserActivity.this);*/
        }


/*
            final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
            //AlertDialog.Builder alert=new AlertDialog.Builder(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(EditDoctorProfileImageActivity.this);
            builder.setTitle("Choose option");
            builder.setItems(items, (dialog, item) -> {
                if (items[item].equals("Take Photo"))
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(EditDoctorProfileImageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
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

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(EditDoctorProfileImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
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
*/



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUriContent();

                    if (resultUri != null) {

                        Log.w("selectedImageUri", " " + resultUri);

                        String filename = getFileName(resultUri);

                        Log.w("filename", " " + filename);

                        String filePath = getFilePathFromURI(EditDoctorProfileImageActivity.this, resultUri);

                        assert filePath != null;

                        File file = new File(filePath); // initialize file here

                        long length = file.length() / 1024; // Size in KB

                        Log.w("filesize", " " + length);

                        if (length > 2000) {

                            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("File Size")
                                    .setContentText("Please choose file size less than 2 MB ")
                                    .setConfirmText("Ok")
                                    .show();
                        } else {


                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());

                            filePart = MultipartBody.Part.createFormData("sampleFile", userid + currentDateandTime + filename, RequestBody.create(MediaType.parse("image/*"), file));

                            uploadPetImage();

                        }


                    } else {

                        Toasty.warning(EditDoctorProfileImageActivity.this, "Image Error!!Please upload Some other image", Toasty.LENGTH_LONG).show();
                    }


                }
            }


        }
        catch (Exception e){
            Log.w(TAG,"onActivityResult exception"+e.toString());
        }




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

                        profileimage = response.body().getData();
                        btn_continue.setVisibility(View.VISIBLE);

                        Log.w(TAG, "ServerUrlImagePath " + profileimage);

                        if( response.body().getData() != null){
                            Glide.with(EditDoctorProfileImageActivity.this)
                                    .load(profileimage)
                                    .into(img_pet_imge);
                            txt_uploadpetimage.setText("Change Image");
                        }else{
                            Glide.with(EditDoctorProfileImageActivity.this)
                                    .load(R.drawable.image_thumbnail)
                                    .into(img_pet_imge);
                            txt_uploadpetimage.setText("Upload Image");

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



    @SuppressLint("MissingSuperCall")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_CLINIC) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                CropImage.activity().start(EditDoctorProfileImageActivity.this);

            } else {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Permisson Required")
                        .setContentText("Please Allow Permissions for choosing Images from Gallery ")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(sDialog -> {

                            sDialog.dismissWithAnimation();


                            if (!hasPermissions(this, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CLINIC);
                            }


                        })
                        .setCancelButton("Cancel", sDialog -> {
                            sDialog.dismissWithAnimation();

                            showWarning(PERMISSION_CLINIC);
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

                    if (!hasPermissions(this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CLINIC);
                    }


                })
                .setCancelButton("Cancel", SweetAlertDialog::dismissWithAnimation)
                .show();
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        if (!TextUtils.isEmpty(fileName)) {


            String path = context.getFilesDir() + "/" + "MyFirstApp/";
            // Create the parent path
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fullName = path + "mylog";
            File copyFile = new File (fullName);

            /* File copyFile = new File(Environment.DIRECTORY_DOWNLOADS + File.separator + fileName);*/
            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copyStream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @SuppressLint("LogNotTimber")
    private void DoctorUpdateProfileImageResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorUpdateProfileImageResponse> call = apiInterface.DoctorUpdateProfileImageResponseCall(RestUtils.getContentType(), doctorUpdateProfileImageRequest());
        Log.w(TAG,"DoctorUpdateProfileImageResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<DoctorUpdateProfileImageResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<DoctorUpdateProfileImageResponse> call, @NonNull Response<DoctorUpdateProfileImageResponse> response) {

                Log.w(TAG,"DoctorUpdateProfileImageResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        sessionManager.setIsLogin(true);
                        sessionManager.createLoginSession(
                                userid,
                                firstname,
                                lastname,
                                useremail,
                                phonenumber,
                                String.valueOf(usertype),
                                userstatus,
                                profileimage,
                                verifyemailstatus,
                                refcode

                        );
                       onBackPressed();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<DoctorUpdateProfileImageResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"DoctorUpdateProfileImageResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private DoctorUpdateProfileImageRequest doctorUpdateProfileImageRequest() {
        DoctorUpdateProfileImageRequest  doctorUpdateProfileImageRequest = new DoctorUpdateProfileImageRequest();
        doctorUpdateProfileImageRequest.setUser_id(userid);
        if(profileimage != null && !profileimage.isEmpty()){
        doctorUpdateProfileImageRequest.setProfile_img(profileimage);
        }else{
            doctorUpdateProfileImageRequest.setProfile_img(APIClient.PROFILE_IMAGE_URL);

        }

        Log.w(TAG,"doctorUpdateProfileImageRequest"+ "--->" + new Gson().toJson(doctorUpdateProfileImageRequest));
        return doctorUpdateProfileImageRequest;
    }
}