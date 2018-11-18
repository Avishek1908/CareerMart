package com.dsce.dbms.careermart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }


        Button button= (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText Email = (EditText) findViewById(R.id.Username);
                final String value = Email.getText().toString();
                EditText Password = (EditText) findViewById(R.id.Password);
                final String value1 = Password.getText().toString();
                mAuth.signInWithEmailAndPassword(value, value1)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(i);

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    mAuth.createUserWithEmailAndPassword(value, value1)
                                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        String Key =user.getUid().toString();
                                                        dref.child("users").child(Key).child("email").setValue(value);
                                                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                                        startActivity(i);

                                                    } else {
                                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                                Toast.LENGTH_SHORT).show();
                                                    }

                                                    // ...
                                                }
                                            });
                                }

                                // ...
                            }
                        });
            }

        });
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}
