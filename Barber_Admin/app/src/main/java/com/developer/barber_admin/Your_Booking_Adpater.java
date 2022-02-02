package com.developer.barber_admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.barber_admin.models.Booking_Detail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Your_Booking_Adpater extends RecyclerView.Adapter<Your_Booking_Adpater.Holder> {
    Context context;
    ArrayList<Booking_Detail> arrayList;

    public Your_Booking_Adpater(Context c, ArrayList<Booking_Detail> arry) {
        context = c;
        arrayList = arry;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_booked_list, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final Booking_Detail model = arrayList.get(i);


        String booked_by = "Booked By: "+ "<b>" + model.getBooked_by() + "</b> ";
        String user_phone = "Phone No: "+ "<b>" + model.getPhone() + "</b> ";

        holder.slot_timing.setText(model.getBooked_slot());
        holder.booked_by.setText(Html.fromHtml(booked_by));
        holder.user_phone.setText(Html.fromHtml(user_phone));
        if (model.getStatus().equals("booked")) {
            holder.btn_book.setText("Cancel Booking");
            holder.btn_book.setTextColor(context.getResources().getColor(R.color.white));
            holder.btn_book.setBackgroundColor(Color.RED);
        }

        String slot = model.getBooked_slot();
        String[] slotsarry = slot.split(":");
        int start_Slot = Integer.parseInt(slotsarry[0]);


        Date currentTime = Calendar.getInstance().getTime();
        String time = currentTime.toString();
        String times[] = time.split(" ");
        String time_1 = times[3];
        String tim[] = time_1.split(":");
        String acutal_time = tim[0] + ":" + tim[1];
        int curent_time_slot = Integer.parseInt(tim[0]);
        if (curent_time_slot > 24) {
            curent_time_slot = curent_time_slot - 12;
        }

        if (start_Slot == curent_time_slot) {
            holder.btn_book.setText("Mark Complete");
            holder.btn_book.setTextColor(context.getResources().getColor(R.color.white));
            holder.btn_book.setBackgroundColor(context.getResources().getColor(R.color.app_color));
        }


        holder.btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://barber-bookings-c1932-default-rtdb.firebaseio.com";
                if (holder.btn_book.getText().equals("Mark Complete")) {
                    DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("bookings").child(model.getBooking_id());
                    db.removeValue();
                    holder.btn_book.setText("Completed");
                    holder.btn_book.setTextColor(context.getResources().getColor(R.color.white));
                    holder.btn_book.setBackgroundColor(Color.GRAY);
                    alert();
                } else if(holder.btn_book.getText().equals("Cancel Booking")){
                    alert_Cancel(model.getBooking_id(), url);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView slot_timing, btn_book,booked_by,user_phone;

        public Holder(@NonNull View itemView) {
            super(itemView);
            slot_timing = itemView.findViewById(R.id.slot_name_reycler);
            btn_book = itemView.findViewById(R.id.btn_book_reycler);
            booked_by = itemView.findViewById(R.id.user_name_reycler);
            user_phone = itemView.findViewById(R.id.user_phone_reycler);

        }
    }


    public void alert_Cancel(String booking_id, String url) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are you sure to cancel the booking ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("bookings").child(booking_id);
                        // db.child("status").setValue("canceled");
                        db.removeValue();
                        Toast.makeText(context, "Booking Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("You slot is mark as completed");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }





}
