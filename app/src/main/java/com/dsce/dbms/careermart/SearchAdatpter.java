package com.dsce.dbms.careermart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchAdatpter extends RecyclerView.Adapter<SearchAdatpter.SearchViewHolder> {
    private ClickInterface mListener;
    Context c;
    ArrayList<String> fullNames;

    public SearchAdatpter(Context c, ArrayList<String> fullNames, ClickInterface mListener){
        Log.i("Activity",c.toString());
        this.c = c;
        this.fullNames = fullNames;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public SearchAdatpter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.samplesearchview, parent,false);

        SearchViewHolder search = new SearchViewHolder(view, mListener);

        return search;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Log.i("Activity","This is "+ fullNames.get(position));
        holder.tvfullName.setText(fullNames.get(position));
    }


    @Override
    public int getItemCount() {
        return fullNames.size();
    }

    private ClickInterface mlistener;


    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvfullName;

        public SearchViewHolder(View itemView, ClickInterface listener){
            super(itemView);
            mlistener = listener;
            tvfullName = (TextView) itemView.findViewById(R.id.tvcourses);
            tvfullName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mlistener.onClick(v, getAdapterPosition());
        }
    }





}
