/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginAct extends AppCompatActivity implements View.OnClickListener {

    /**
     * email
     */
    private EditText edEmailLogin;
    /**
     * password
     */
    private EditText edPasswordLogin;
    /**
     * login_now
     */
    private Button btnLogin;
    //Firebase
    private FirebaseAuth fAuth;
    private DatabaseReference RDatabase;
    private FirebaseDatabase fDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        RDatabase = fDatabase.getReference().child("clint");
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
        RDatabase.keepSynced(true);

        initView();
    }

    private void initView() {
        edEmailLogin = findViewById(R.id.ed_email_login);
        edPasswordLogin = findViewById(R.id.ed_password_login);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (isOnline()) {
            String strPasswordLogin = edPasswordLogin.getText().toString().trim();
            String strEmailLogin = edEmailLogin.getText().toString().trim();
            if (TextUtils.isEmpty(strEmailLogin)) {
                edEmailLogin.setError("Required Email..");
            }
            if (TextUtils.isEmpty(strPasswordLogin)) {
                edPasswordLogin.setError("Required Password..");
            } else {
                btnLogin.setEnabled(false);
                edPasswordLogin.setEnabled(false);
                edEmailLogin.setEnabled(false);
                fAuth.signInWithEmailAndPassword(strEmailLogin, strPasswordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                            Toast.makeText(getApplicationContext(), getString(R.string.successfully_login), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
//                            btnLogin.setEnabled(true);
                        }
                        btnLogin.setEnabled(true);
                        edPasswordLogin.setEnabled(true);
                        edEmailLogin.setEnabled(true);
                    }
                });
            }
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}