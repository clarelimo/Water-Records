package com.moringaschool.waterrefillrecords.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moringaschool.waterrefillrecords.Constants;
import com.moringaschool.waterrefillrecords.R;
import com.moringaschool.waterrefillrecords.modules.Sale;
import com.moringaschool.waterrefillrecords.modules.Sales;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSalesActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int GALLERY_CODE = 71;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 11;
    private String mSource;
    private String currentPhotoPath;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    Uri imageUrl = null;
    ProgressDialog progressDialog;

    @BindView(R.id.litresSold)
    EditText mlitresSold;
    @BindView(R.id.bottlesSold)
    EditText mBottlesSold;
    @BindView(R.id.totalSales)
    EditText mTotalSales;
    @BindView(R.id.balance)
    EditText mBalance;
    @BindView(R.id.dateEditText)
    EditText mDate;
    @BindView(R.id.addSalesButton)
    Button mAddSalesButton;
    @BindView(R.id.machineImageView) ImageView imageView;
    @BindView(R.id.machineImageBtn) Button mMachineImageBtn;
    @BindView(R.id.takeImageBtn) Button mCaptureBtn;
    private Sale mSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);
        ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences(Constants.PREFERENCES_SALES_KEY, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child(Constants.PREFERENCES_SALES_KEY);
        mStorage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);

        mMachineImageBtn.setOnClickListener(this);
        mCaptureBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mAddSalesButton) {
            createNewSale();
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference filePath = mStorage.getReference().child("imagePost").child(imageUrl.getLastPathSegment());
            filePath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String t = task.getResult().toString();
                            DatabaseReference ref = mRef.push();
                            ref.setValue(mSale);
                            ref.child("image").setValue(t);
                            progressDialog.dismiss();
                        }
                    });
                }
            });

            Toast.makeText(AddSalesActivity.this, "Record Saved", Toast.LENGTH_SHORT).show();
        }

        if(v == mMachineImageBtn){
            onUpLoadImage();
        }

        if(v == mCaptureBtn){
            onLaunchCamera();
        }
    }

    private void createNewSale() {
        String litresSold = mlitresSold.getText().toString().trim();
        String bottlesSold = mBottlesSold.getText().toString().trim();
        String totalSales = mTotalSales.getText().toString().trim();
        String balance = mBalance.getText().toString().trim();
        String date = mDate.getText().toString().trim();


        mSale = new Sale(date, Integer.parseInt(totalSales), Integer.parseInt(litresSold), Integer.parseInt(bottlesSold), Integer.parseInt(balance));
        mSale.setLitresSold(Integer.parseInt(litresSold));
        mSale.setEmptyBottlesSold(Integer.parseInt(bottlesSold));
        mSale.setTotalSales(Integer.parseInt(totalSales));
        mSale.setBalance(Integer.parseInt(balance));
        mSale.setDate(date);
    }

    private void onUpLoadImage() {
        Intent takePictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        takePictureIntent.setType("image/*");
        startActivityForResult(takePictureIntent,GALLERY_CODE);
    }

    private void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            imageUrl = data.getData();
            imageView.setImageURI(imageUrl);
            Toast.makeText(this, "Image saved!!", Toast.LENGTH_LONG).show();
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageUrl = getImageUri(imageBitmap);
            imageView.setImageURI(imageUrl);
            Toast.makeText(this, "Image saved!!", Toast.LENGTH_LONG).show();
        }
        mAddSalesButton.setOnClickListener(this);
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}