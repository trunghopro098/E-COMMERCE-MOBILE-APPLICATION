package com.sict.udn.myshoes;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

        import android.content.Intent;
        import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sict.udn.Model.Users;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private Button btnregister;
    private TextInputEditText txtEmailregist, txtpasswordregist, txtphone,txtaddress,name;
    private TextView  login;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_regist);

        btnregister = findViewById(R.id.btnregister);
        login = findViewById(R.id.txtlogin);
        txtEmailregist = findViewById(R.id.txtemailregister);
        txtpasswordregist = findViewById(R.id.txtpassregister);
        txtphone = findViewById(R.id.txtphone);
        txtaddress = findViewById(R.id.txtaddress);
        name = findViewById(R.id.txtName);
        fAuth = FirebaseAuth.getInstance();


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmailregist.getText().toString().trim();
                String password = txtpasswordregist.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    txtEmailregist.setError("Email is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(password)){
                       txtpasswordregist .setError("Password is required.");
                        return;
                }
                    if(password.length()<= 6){
                        txtpasswordregist.setError("Password Must be >=6 Characters");

                    }
                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Register.this,"User Created.", Toast.LENGTH_SHORT).show();
                                HashMap<String,Object> dangky = new HashMap<>();
                                dangky.put("name",name.getText().toString());
                                dangky.put("email",txtEmailregist.getText().toString());
                                dangky.put("address",txtaddress.getText().toString());
                                dangky.put("phone",txtphone.getText().toString());

                                String id =fAuth.getCurrentUser().getUid();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                ref.child(id).updateChildren(dangky);
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                            }else {
                              Toast.makeText(Register.this, "Error! " + task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

