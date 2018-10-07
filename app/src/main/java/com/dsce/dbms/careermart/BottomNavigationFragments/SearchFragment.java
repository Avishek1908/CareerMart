package com.dsce.dbms.careermart.BottomNavigationFragments;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dsce.dbms.careermart.ClickInterface;
import com.dsce.dbms.careermart.Courseinfo;
import com.dsce.dbms.careermart.HomeActivity;
import com.dsce.dbms.careermart.LoginActivity;
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

public class SearchFragment extends Fragment {

    EditText searchField;
    RecyclerView recView;
    ImageButton searchbtn;
    DatabaseReference dref;
    FirebaseUser fuser;
    ArrayList<String> fullNameList, keysList;
    SearchAdatpter searchadap;



    public SearchFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);



        searchField = (EditText)view.findViewById(R.id.etsearch);
        recView = (RecyclerView)view.findViewById(R.id.recycleview);
        dref = FirebaseDatabase.getInstance().getReference();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        fullNameList = new ArrayList<>();
        keysList = new ArrayList<>();



        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    if(!s.toString().isEmpty())
                    {
                        Log.i("Activity","Changeddddddd");
                        setAdapter(s.toString());
                    }
                    else
                    {
                        fullNameList.clear();
                        keysList.clear();
                        ClickInterface listener = new ClickInterface() {
                        @Override
                        public void onClick(View view, int position) {
                            Toast.makeText(SearchFragment.this.getContext(), fullNameList.get(position), Toast.LENGTH_SHORT).show();
                        }
                    };

                        searchadap = new SearchAdatpter(getActivity(),fullNameList,listener);
                        recView.setAdapter(searchadap);
                    }
            }
        });


        return view;
    }

    void setAdapter(final String string)
    {
        fullNameList.clear();
        keysList.clear();
        recView.removeAllViews();
        dref.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fullNameList.clear();
                keysList.clear();
                recView.removeAllViews();
                for(DataSnapshot snap: dataSnapshot.getChildren())
                {

                    String fullName = snap.child("fullname").getValue(String.class);
                    if(fullName.toLowerCase().contains(string.toLowerCase()))
                    {
                        fullNameList.add(fullName);
                        keysList.add(snap.getKey());

                        Log.i("Activity",fullName);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ClickInterface listener = new ClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(SearchFragment.this.getContext(), fullNameList.get(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), Courseinfo.class);
                i.putExtra("STRING_I_NEED", keysList.get(position));
                startActivity(i);

            }
        };
        searchadap = new SearchAdatpter(getActivity(),fullNameList, listener);
        recView.setAdapter(searchadap);
    }


}
