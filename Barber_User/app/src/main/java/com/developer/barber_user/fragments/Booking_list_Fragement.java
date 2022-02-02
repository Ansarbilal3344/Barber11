package com.developer.barber_user.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.barber_user.Shop_Detail;
import com.developer.barber_user.models.All_Booking_list;
import com.developer.barber_user.All_booking_Adapter;
import com.developer.barber_user.R;
import com.developer.barber_user.models.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Booking_list_Fragement extends Fragment {
    RecyclerView recyclerView;
    Context context;
    ProgressDialog progressDialog;
    ArrayList<String> arrayList_all_slots = new ArrayList<>();
    String url = "https://barber-bookings-c1932-default-rtdb.firebaseio.com";
    User user;
    Button btn_shop;

    public Booking_list_Fragement(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_booking, container, false);
        btn_shop = view.findViewById(R.id.btn_see_Shop);
        recyclerView = view.findViewById(R.id.recycler_user_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        FirebaseApp.initializeApp(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        getuser();
        deleteOld();
        setIntialSlots();

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Shop_Detail.class));
            }
        });
        return view;
    }

    private void getBookingList(ArrayList<String> arrayList_all_slots) {
        progressDialog.show();

        DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("bookings");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    ArrayList<All_Booking_list> arrayList = new ArrayList<>();
                    ArrayList<String> slots = new ArrayList<>();
                    int count = 0;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        count++;
                        try {
                            String slot = child.child("slot").getValue().toString();
                            slots.add(slot);
                        }catch (Exception e){
                            getBookingList(arrayList_all_slots);
                        }


                        if (count == dataSnapshot.getChildrenCount()) {
                            int coont = 0;
                            for (String single_slot :arrayList_all_slots) {
                                coont++;
                                All_Booking_list model = new All_Booking_list();
                                if (slots.contains(single_slot)) {
                                    model.setSlot(single_slot);
                                    model.setStatus("booked");
                                } else {
                                    model.setSlot(single_slot);
                                    model.setStatus("available");
                                }
                                arrayList.add(model);

                                if (coont == arrayList_all_slots.size()) {
                                    progressDialog.dismiss();
                                    All_booking_Adapter adapter = new All_booking_Adapter(getActivity(), arrayList,user);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    progressDialog.dismiss();
                                }
                            }
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    progressDialog.dismiss();
                    ArrayList<All_Booking_list> arrayList2 = new ArrayList<>();
                    int coont = 0;
                    for (String single_slot : arrayList_all_slots) {
                        coont++;
                        All_Booking_list model2 = new All_Booking_list();
                        model2.setSlot(single_slot);
                        model2.setStatus("available");
                        arrayList2.add(model2);

                        if (coont == arrayList_all_slots.size()) {
                            progressDialog.dismiss();
                            All_booking_Adapter adapter = new All_booking_Adapter(getActivity(), arrayList2,user);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
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

    private void setIntialSlots() {
        Date currentTime = Calendar.getInstance().getTime();
        String time = currentTime.toString();
        String times[] = time.split(" ");
        String time_1 = times[3];
        String tim[] = time_1.split(":");
        String acutal_time = tim[0] + ":" + tim[1];
        int current_slot_start = Integer.parseInt(tim[0]) + 1;

        for (int i = 0; i <= 11; i++) {

            if (current_slot_start < 10) {
                current_slot_start = current_slot_start + 9;
            }
            int current_slot_end = current_slot_start + 1;
            String start_slot, end_slot;
            if (current_slot_start <= 11) {
                start_slot = current_slot_start + ":00 AM";
            } else {
                int current_slot_start_mini = current_slot_start;
                if (current_slot_start > 12) {
                    current_slot_start_mini = current_slot_start_mini - 12;
                }
                start_slot = current_slot_start_mini + ":00 PM";
            }
            if (current_slot_end <= 11) {
                end_slot = current_slot_end + ":00 AM";
            } else {
                int current_slot_end_mini = current_slot_end;
                if (current_slot_end > 12) {
                    current_slot_end_mini = current_slot_end_mini - 12;
                }
                end_slot = current_slot_end_mini + ":00 PM";
            }
            String slot = start_slot + " - " + end_slot;
            arrayList_all_slots.add(slot);
            if (current_slot_start < 22) {
                current_slot_start++;
            } else {
                current_slot_start = 1;
            }
            if (arrayList_all_slots.size() > 11) {
                getBookingList(arrayList_all_slots);
            }
        }

        Log.d("array", arrayList_all_slots.toString());
    }

    private void getuser() {
        FirebaseApp.initializeApp(context);
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("user").child(user_id);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    user =new User();
                    user.setUser_id(user_id);
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getKey().equals("name")) {
                            user.setName(child.getValue().toString());
                        }else if (child.getKey().equals("email")) {
                            user.setEmail(child.getValue().toString());
                        }else if (child.getKey().equals("phone")) {
                            user.setPhone(child.getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void deleteOld() {
        progressDialog.show();

        DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("bookings");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    ArrayList<String> slots = new ArrayList<>();
                    int count = 0;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        count++;
                        try {
                            Date currentTime = Calendar.getInstance().getTime();
                            String time = currentTime.toString();
                            String times[] = time.split(" ");
                            String date_3 = times[5];
                            int date = Integer.parseInt(date_3);

                            String booking_Date = child.child("date").getValue().toString();
                            int b_date = Integer.parseInt(booking_Date)+1;

                           if (date>b_date){
                                db.child(child.getKey()).removeValue();
                           }

                        }catch (Exception e){
                            deleteOld();
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
