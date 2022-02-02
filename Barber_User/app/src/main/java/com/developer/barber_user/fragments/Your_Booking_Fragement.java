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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.barber_user.All_booking_Adapter;
import com.developer.barber_user.R;
import com.developer.barber_user.Shop_Detail;
import com.developer.barber_user.Your_Booking_Adpater;
import com.developer.barber_user.models.All_Booking_list;
import com.google.firebase.auth.FirebaseAuth;
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
    ProgressDialog progressDialog;
    Button btn_shop;
    String url = "https://barber-bookings-c1932-default-rtdb.firebaseio.com";

    public Your_Booking_Fragement(Context context) {
        this.context = context;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_booking,container,false);
        btn_shop = view.findViewById(R.id.btn_see_Shop);
        recyclerView = view.findViewById(R.id.recycler_user_booked);
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
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("bookings");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    ArrayList<All_Booking_list> arrayList = new ArrayList<>();
                    int count = 0;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        All_Booking_list model = new All_Booking_list();
                        count++;
                        try {
                            String booking_user_id = child.child("booked user_id").getValue().toString();

                            if (booking_user_id.equals(user_id)) {
                                String slot = child.child("slot").getValue().toString();
                                String status = child.child("status").getValue().toString();
                                model.setB_id(child.getKey());
                                model.setSlot(slot);
                                model.setStatus(status);
                                arrayList.add(model);
                            }
                        }catch (Exception e){
                            getBookingList();
                        }


                        if (count == dataSnapshot.getChildrenCount()) {
                            progressDialog.dismiss();
                            Your_Booking_Adpater adapters = new Your_Booking_Adpater(getActivity(), arrayList);
                            recyclerView.setAdapter(adapters);
                            adapters.notifyDataSetChanged();
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
