package com.dsce.dbms.careermart.BottomNavigationFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dsce.dbms.careermart.ClickInterface;
import com.dsce.dbms.careermart.Course;
import com.dsce.dbms.careermart.CourseAdapter;
import com.dsce.dbms.careermart.Courseinfo;
import com.dsce.dbms.careermart.HomeActivity;
import com.dsce.dbms.careermart.R;
import com.dsce.dbms.careermart.SearchAdatpter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recView;
    ImageButton searchbtn;
    DatabaseReference dref;
    FirebaseUser fuser;
    ArrayList<String>  keysList;
    ArrayList<Course> courseList;
    CourseAdapter courseadap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        recView = (RecyclerView)v.findViewById(R.id.recview);
        dref = FirebaseDatabase.getInstance().getReference();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        keysList = new ArrayList<>();
        courseList= new ArrayList<>();

        keysList.clear();
        recView.removeAllViews();

        dref.child("users").child(fuser.getUid().toString()).child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                keysList.clear();
                courseList= new ArrayList<>();
                recView.removeAllViews();

                for(DataSnapshot snap: dataSnapshot.getChildren())
                {

                    dref.child("Courses").child(snap.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Course courses = dataSnapshot.getValue(Course.class);
                            courseList.add(courses);
                            setAdap();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    keysList.add(snap.getKey());

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ClickInterface listener = new ClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(HomeFragment.this.getContext(), keysList.get(position), Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(getActivity(), Courseinfo.class);
                i.putExtra("STRING_I_NEED", keysList.get(position));
                startActivity(i);*/

            }
        };

        Log.i("HomeAct","CourseList"+courseList);

        courseadap = new CourseAdapter(getActivity(),courseList, listener);
        recView.setAdapter(courseadap);

        return v;
    }

    void setAdap(){

        ClickInterface listener = new ClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(HomeFragment.this.getContext(), keysList.get(position), Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(getActivity(), Courseinfo.class);
                i.putExtra("STRING_I_NEED", keysList.get(position));
                startActivity(i);*/

            }
        };
        courseadap = new CourseAdapter(getActivity(),courseList, listener);
        recView.setAdapter(courseadap);
    }


}
