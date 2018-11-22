package com.dsce.dbms.careermart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePic;
    private TextView profileId, profileTaken, profileCompleted;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private String TAG="abc";
    // private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        profilePic = (ImageView) findViewById(R.id.imageView2pro);
        profileId = (TextView) findViewById(R.id.textViewEmail);
        profileTaken = (TextView) findViewById(R.id.textView2taken);
        profileCompleted = (TextView) findViewById(R.id.textView3complete);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            //String email = user.getEmail();
            String uid = user.getUid();

            Log.i(TAG,uid + "User email id");

        }



      DatabaseReference databaseReference= firebaseDatabase.getReference();
        databaseReference.child("users").child(user.getUid()).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email=dataSnapshot.getValue(String.class);
                // UserProfile userProfile= dataSnapshot.getValue(UserProfile.class);
                //Log.i(TAG,userProfile.toString());
               // profileId.setText(userProfile.getEmail());
                profileId.setText(email);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    }

