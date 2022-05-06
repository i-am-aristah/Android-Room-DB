package com.example.roomtestdb;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewholder> {

    List<MainData> dataList;
    Activity context;
    RoomDB database;

    public MainAdapter( Activity context, List<MainData> dataList) {
        this.dataList = dataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_item,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        MainData mainData = dataList.get(position);
        //initialise database

        database = RoomDB.getInstance(context);

        //Set Data to TextView

        holder.txtuname.setText(mainData.getUsername());

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //initialise
                MainData data = dataList.get(holder.getAdapterPosition());

                String uname = data.getUsername();
                int IDS = data.getID();

                //Initialise dialogue
                Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.update_user_diaogue);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                //initialise dialogue widgets

                EditText edtxt_update_uname;
                Button btn_update;

                edtxt_update_uname = dialog.findViewById(R.id.edtxt_update);
                btn_update=dialog.findViewById(R.id.btn_update);

                //set text to editText
                edtxt_update_uname.setText(uname);



                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userName = edtxt_update_uname.getText().toString().trim();

                        dialog.dismiss();
                        database.mainDao().update(IDS,userName);
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();


                    }
                });




            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData data = dataList.get(holder.getAdapterPosition());

                database.mainDao().delete(data);

                int position = holder.getAdapterPosition();

                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView txtuname;
        ImageView img_edit, img_delete;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            txtuname=itemView.findViewById(R.id.txt_username);
            img_edit = itemView.findViewById(R.id.img_edit);
            img_delete =itemView.findViewById(R.id.img_delete);
        }
    }
}
