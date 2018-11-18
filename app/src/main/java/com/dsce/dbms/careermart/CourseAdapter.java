package com.dsce.dbms.careermart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private ClickInterface mListener;
    Context c;
    ArrayList<Course> courseList;

    public CourseAdapter(Context c, ArrayList<Course> courseList, ClickInterface mListener){
        Log.i("Activity",c.toString());
        this.c = c;
        this.courseList = courseList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courselist, parent,false);

        CourseViewHolder courses = new CourseViewHolder(view, mListener);

        return courses;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        //Log.i("Activity","This is "+ fullNames.get(position));
        //holder.tvfullName.setText(fullNames.get(position));
        holder.tvcoursename.setText(courseList.get(position).getFullname());

    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }

    private ClickInterface mlistener;


    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvcoursename, tvmorem;
        ProgressBar courseProgress;


        public CourseViewHolder(View itemView, ClickInterface listener){
            super(itemView);
            mlistener = listener;
            tvcoursename = (TextView) itemView.findViewById(R.id.tv_coursename);
            tvmorem = (TextView) itemView.findViewById(R.id.tv_more);
            courseProgress = (ProgressBar) itemView.findViewById(R.id.determinateBar);
            tvcoursename.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mlistener.onClick(v, getAdapterPosition());
        }
    }





}
