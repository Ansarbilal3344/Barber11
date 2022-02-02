package com.developer.barber_user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.developer.barber_user.models.All_Booking_list;
import com.developer.barber_user.models.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class All_booking_Adapter extends RecyclerView.Adapter<All_booking_Adapter.Holder> {
    Context context;
    ArrayList<All_Booking_list> arrayList;
    User user;

    public All_booking_Adapter(Context c, ArrayList<All_Booking_list> arry, User user) {
        context = c;
        arrayList = arry;
        this.user = user;

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_all_booking_list, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final All_Booking_list model = arrayList.get(i);
        holder.slot_timing.setText(model.getSlot());

        if (model.getStatus().equals("booked")) {
            holder.btn_book.setText("Booked");
            holder.btn_book.setTextColor(context.getResources().getColor(R.color.white));
            holder.btn_book.setBackgroundColor(Color.GRAY);

        } else {
            holder.btn_book.setText("Book Slot");
            holder.btn_book.setTextColor(context.getResources().getColor(R.color.white));
            holder.btn_book.setBackgroundColor(context.getResources().getColor(R.color.app_color));
        }


        holder.btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date currentTime = Calendar.getInstance().getTime();
                String time = currentTime.toString();
                String times[] = time.split(" ");
                String date_3 = times[5];

                String url = "https://barber-bookings-c1932-default-rtdb.firebaseio.com";
                if (model.getStatus().equals("available")) {
                    String booking_id = getAlphaNumericString();
                    DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("bookings").child(booking_id);
                    db.child("booked by").setValue(user.getName());
                    db.child("booked user_id").setValue(user.getUser_id());
                    db.child("slot").setValue(model.getSlot());
                    db.child("phone").setValue(user.getPhone());
                    db.child("status").setValue("booked");
                    db.child("date").setValue(date_3);
                    alert();
                } else if (holder.btn_book.getText().equals("Cancel Booking")) {
                    alert_Cancel(model.getB_id(), url);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView slot_timing, btn_book;

        public Holder(@NonNull View itemView) {
            super(itemView);
            slot_timing = itemView.findViewById(R.id.slot_name_reycler);
            btn_book = itemView.findViewById(R.id.btn_book_reycler);

        }
    }

    private String getAlphaNumericString() {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public void alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Your Slot is Booked Successfully. You can cancel it till before and hour");
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


    public void alert_Cancel(String booking_id, String url) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are you sure to cancel the booking ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("bookings").child(booking_id);
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

}
