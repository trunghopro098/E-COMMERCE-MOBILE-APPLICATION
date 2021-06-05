package com.sict.udn.myshoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sict.udn.Model.Users;

public class MainActivity extends AppCompatActivity {
    private Button btnlogin;
    private TextView register;
    private TextInputEditText txtEmail, txtpassword;
    private FirebaseAuth fAuth;
    Users users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        fAuth = FirebaseAuth.getInstance();
        btnlogin = findViewById(R.id.btnlogin);
        register = findViewById(R.id.txtregister);
        txtEmail = findViewById(R.id.txtemaillogin);
        txtpassword = findViewById(R.id.txtpasslogin);



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    txtEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    txtpassword.setError("Password is required.");
                    return;
                }
                if(password.length()< 6){
                    txtpassword.setError("Password Must be >=6 Characters");

                }
//                dang nhap
                fAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Main1Activity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this, "Error  !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
