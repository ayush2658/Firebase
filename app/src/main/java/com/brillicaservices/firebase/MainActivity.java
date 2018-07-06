package com.brillicaservices.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Recycler.ListItemClickListener {

    private static final String TAG = MainActivity.class.getName();
    DatabaseReference databaseReference;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    Recycler recycler;
    ArrayList<Student> studentArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.recycler_progress_bar);
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recycler = new Recycler(studentArrayList,this);

        recyclerView.setAdapter(recycler);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("student").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange
                    (DataSnapshot dataSnapshot) {

                Log.d(TAG ,"Child count: " + dataSnapshot);

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    Log.d(TAG, "Student Name is: " + snapshot.child("name").getValue());
//                    Log.d(TAG, "Student College is: " + snapshot.child("college").getValue());
//                    Log.d(TAG, "Student Address is: " + snapshot.child("address").getValue());
//                    Log.d(TAG, "Student Phone is: " + snapshot.child("phone").getValue());

                    String studentName = String.valueOf(snapshot.child("name").getValue());
                    String collegeName = String.valueOf(snapshot.child("college").getValue());
                    String address = String.valueOf(snapshot.child("address").getValue());
                    int phone = Integer.parseInt(snapshot.child("phone").getValue().toString());

                    studentArrayList.add(new Student(studentName, address, collegeName, phone));
                }

                recycler.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled
                    (DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onListItemClickListener(int clickedItemIndex) {
        Toast.makeText(getApplicationContext(), studentArrayList.get(clickedItemIndex).name, Toast.LENGTH_SHORT).show();
    }
}




