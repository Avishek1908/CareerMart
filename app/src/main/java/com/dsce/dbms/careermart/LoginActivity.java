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

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

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
                                    // Sign in success, update UI with the signed-in user's information
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
                                                        // Sign in success, update UI with the signed-in user's information
                                                        //Log.d(TAG, "createUserWithEmail:success");
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        //updateUI(user);
                                                    } else {
                                                        // If sign in fails, display a message to the user.
                                                        //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                                Toast.LENGTH_SHORT).show();
                                                        //updateUI(null);
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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

}
