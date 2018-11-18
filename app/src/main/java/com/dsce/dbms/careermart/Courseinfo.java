package com.dsce.dbms.careermart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Courseinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseinfo);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Bundle extras = getIntent().getExtras();
        final String newString= extras.getString("STRING_I_NEED");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Courses/"+newString);
        final DatabaseReference dref = database.getReference();
        final FirebaseUser current_user = mAuth.getCurrentUser();



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Course value = dataSnapshot.getValue(Course.class);
                //Log.d(TAG, "Value is: " + value);
                TextView coursename=(TextView)findViewById(R.id.course_name);
                coursename.setText(value.getFullname());
                TextView textView=(TextView)findViewById(R.id.tv_intro);
                textView.setText(value.getIntroduction());
                TextView textdef=(TextView)findViewById(R.id.tv_desc);
                textdef.setText(value.getDescription());
                TextView prereq=(TextView)findViewById(R.id.tv_prereq);
                prereq.setText(value.getPrerequisites());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Button subscribe  = (Button)findViewById(R.id.subscribe);
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dref.child("users").child(current_user.getUid().toString()).child("courses").child(newString).child("Percentage").setValue("0%");
                startActivity(new Intent(Courseinfo.this, HomeActivity.class));
            }
        });
    }
}

