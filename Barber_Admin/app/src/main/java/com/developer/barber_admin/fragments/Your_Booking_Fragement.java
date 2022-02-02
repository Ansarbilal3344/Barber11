package com.developer.barber_admin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.barber_admin.R;
import com.developer.barber_admin.Shop_Detail;
import com.developer.barber_admin.Your_Booking_Adpater;
import com.developer.barber_admin.models.All_Booking_list;
import com.developer.barber_admin.models.Booking_Detail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Your_Booking_Fragement extends Fragment {
    RecyclerView recyclerView;
    ImageView btn_Add;
    Context context;
    Button btn_shop;
    ProgressDialog progressDialog;
    String url = "https://barber-bookings-c1932-default-rtdb.firebaseio.com";

    public Your_Booking_Fragement(Context context) {
        this.context = context;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_booking,container,false);
        recyclerView = view.findViewById(R.id.recycler_admin_booked);
        btn_shop = view.findViewById(R.id.btn_see_Shop);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        getBookingList();

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Shop_Detail.class));
            }
        });
        return view;
    }

    private void getBookingList() {
        progressDialog.show();

        DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("bookings");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    ArrayList<Booking_Detail> arrayList = new ArrayList<>();
                    int count = 0;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Booking_Detail model = new Booking_Detail();
                        count++;
                        try {
                            model.setBooking_id(child.getKey());
                            model.setBooked_slot(child.child("slot").getValue().toString());
                            model.setStatus(child.child("status").getValue().toString());
                            model.setPhone(child.child("phone").getValue().toString());
                            model.setBooked_by(child.child("booked by").getValue().toString());
                            model.setDate(child.child("date").getValue().toString());
                            model.setBooked_user_id(child.child("booked user_id").getValue().toString());
                            arrayList.add(model);
                        }catch (Exception e){
                            getBookingList();
                        }


                        if (count == dataSnapshot.getChildrenCount()) {
                            progressDialog.dismiss();
                            Your_Booking_Adpater adapters = new Your_Booking_Adpater(getActivity(), arrayList);
                            recyclerView.setAdapter(adapters);
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //norecord.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Log.d("array",databaseError.getMessage());
            }
        });
    }
}

