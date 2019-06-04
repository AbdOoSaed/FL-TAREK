/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.karemhamed.Model.ModelClint;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class AddClintActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int PICK_IMAGE_REQUEST = 1;
    private Uri uriImage;
    private ImageView profileImage;
    private StorageReference storageRef;
    private DatabaseReference databaseReference;
    /**
     * name
     */
    private EditText edClintName;
    /**
     * address
     */
    private EditText edClintAddress;
    /**
     * phone_numper
     */
    private EditText edClintPhone;
    /**
     * add
     */
    private Button btnAddClint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clint);
        storageRef = FirebaseStorage.getInstance().getReference("clintPhoto");
        databaseReference = FirebaseDatabase.getInstance().getReference("clint");
        initView();
    }

    public void imgProfileOnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            Picasso.get().load(uriImage).into(profileImage);
        }
    }

    private void initView() {
        profileImage = findViewById(R.id.profile_image_clint);
        edClintName = findViewById(R.id.ed_clint_name);
        edClintAddress = findViewById(R.id.ed_clint_address);
        edClintPhone = findViewById(R.id.ed_clint_phone);
        btnAddClint = findViewById(R.id.btn_add_clint);
        btnAddClint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (uriImage != null) {

            btnAddClint.setEnabled(false);
            edClintName.setEnabled(false);
            edClintPhone.setEnabled(false);
            edClintAddress.setEnabled(false);
            profileImage.setDrawingCacheEnabled(true);
            profileImage.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) profileImage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 5, baos);
            byte[] data = baos.toByteArray();


            final StorageReference fileReference = storageRef.child(System.currentTimeMillis() + ".WEBP");

            final UploadTask uploadTask = fileReference.putBytes(data);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String str_id = databaseReference.push().getKey();
                        ModelClint upload = new ModelClint(downloadUri.toString(), edClintName.getText().toString()
                                , edClintAddress.getText().toString(), edClintPhone.getText().toString(), str_id);
                        databaseReference.child(str_id).setValue(upload);
                        btnAddClint.setEnabled(true);
                        edClintName.setEnabled(true);
                        edClintPhone.setEnabled(true);
                        edClintAddress.setEnabled(true);
                        finish();
//                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {
                        // Handle failures
                        // ...
                        btnAddClint.setEnabled(true);
                        edClintName.setEnabled(true);
                        edClintPhone.setEnabled(true);
                        edClintAddress.setEnabled(true);
                    }
                }
            });
        }
    }
}
