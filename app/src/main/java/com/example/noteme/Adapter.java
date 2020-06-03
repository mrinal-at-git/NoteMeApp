package com.example.noteme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Note> notes;

    public Adapter(Context context,  List<Note> notes) {
        this.layoutInflater = LayoutInflater.from(context);
        this.notes = notes;
    }





    @NonNull
    @Override
    public Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_list_view,parent,false);
        return new myViewHolder(view);
    }







    @Override
    public void onBindViewHolder(@NonNull Adapter.myViewHolder holder, int position) {

        String title =notes.get(position).getTitle();
        String date = notes.get(position).getDate();
        String time = notes.get(position).getTime();

        holder.nTitle.setText(title);
        holder.nDate.setText(date);
        holder.nTime.setText(time);

    }





    @Override
    public int getItemCount() {
        return notes.size();
    }







    public class myViewHolder extends RecyclerView.ViewHolder {


        TextView nTitle, nDate, nTime;

        public myViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), Details.class);
                    i.putExtra("ID", notes.get(getAdapterPosition()).getId());
                    //i.putExtra("tittle", notes.get(getAdapterPosition()).getTitle());
                    //i.putExtra("content", notes.get(getAdapterPosition()).getContent());
                    v.getContext().startActivity(i);

                }
            });

            nTitle = itemView.findViewById(R.id.nTitle);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);

        }
    }
}