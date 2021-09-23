package com.moringaschool.waterrefillrecords.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.waterrefillrecords.Constants;
import com.moringaschool.waterrefillrecords.R;
import com.moringaschool.waterrefillrecords.modules.Sale;
import com.moringaschool.waterrefillrecords.modules.Sales;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSalesActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 11;
    private String mSource;
    private String currentPhotoPath;

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
    @BindView(R.id.machineImageBtn) Button mMachineImageBtn;
    private Sale mSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);
//        setHasOptionsMenu(true);
        ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences(Constants.PREFERENCES_SALES_KEY, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mAddSalesButton.setOnClickListener(this);
        mMachineImageBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mAddSalesButton) {
            createNewSale();
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.PREFERENCES_SALES_KEY);
            restaurantRef.push().setValue(mSale);
            Intent intent = new Intent(AddSalesActivity.this, SavedSalesActivity.class);
            startActivity(intent);
            Toast.makeText(AddSalesActivity.this, "Record Saved", Toast.LENGTH_SHORT).show();
        }
        if(v == mMachineImageBtn){
            onLaunchCamera();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        if (mSource.equals(Constants.SOURCE_SAVED)) {
//            inflater.inflate(R.menu.menu_photo, menu);
//        } else {
//            inflater.inflate(R.menu.menu_main, menu);
//        }
//        return super.onCreateOptionsMenu(menu);
//    }

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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_photo:
//                onLaunchCamera();
//            default:
//                break;
//        }
//        return false;
//    }

    private void onLaunchCamera() {
        Uri photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName()+".provider",
                createImageFile());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        // tell the camera to request write permissions
        takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageLabel.setImageBitmap(imageBitmap);
            //      encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    //On camera Icon Clicked
    public void askCameraPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            onLaunchCamera();
        } else {
            // let's request permission.getContext(),getContext(),
            String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            // we have heard back from our request for camera and write external storage.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                onLaunchCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to use camera", Toast.LENGTH_LONG).show();
            }
        }
    }

    private File createImageFile()  {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Restaurant_JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir,
                imageFileName
                        +  ".jpg"
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        // Log.i(TAG, currentPhotoPath);
        return image;
    }
}