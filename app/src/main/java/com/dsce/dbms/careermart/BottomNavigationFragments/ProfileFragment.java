package com.dsce.dbms.careermart.BottomNavigationFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsce.dbms.careermart.R;
import com.dsce.dbms.careermart.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
        private ImageView profilePic;
        private TextView profileId, profileTaken, profileCompleted;
        private FirebaseAuth mAuth;
        private FirebaseDatabase firebaseDatabase;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        profilePic=(ImageView)v.findViewById(R.id.imageView2pro);
        profileId=(TextView)v.findViewById(R.id.textViewEmail);
        profileTaken=(TextView)v.findViewById(R.id.textView2taken);
        profileCompleted=(TextView)v.findViewById(R.id.textView3complete);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();


        DatabaseReference databaseReference= firebaseDatabase.getReference(String.valueOf(mAuth.getCurrentUser()));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile= dataSnapshot.getValue(UserProfile.class);
                profileId.setText(userProfile.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





   return v;
    }

}
