package com.inventors.jd.keeper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inventors.jd.keeper.MyApplication;
import com.inventors.jd.keeper.R;
//import com.inventors.jd.aldeenulkhalis.adapters.PostAdapter;
import com.inventors.jd.keeper.adapters.PostAdapter;
import com.inventors.jd.keeper.models.Post;
import com.inventors.jd.keeper.receivers.ConnectivityReceiver;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private RecyclerView recyclerView;

    private ArrayList<Post> posts = null;

    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Posts");

    private LinearLayout lytLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddPostActivity.class));
            }
        });

        lytLoading = findViewById(R.id.lytLoading);

//        checkConnection();

        onLoadData();
    }

    public void onLoadData() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Post post = ds.getValue(Post.class);


                    Log.d("TAG", "onDataChange: ");

                    assert post != null;
                    if (post.getIs_active().equals("true"))
                        posts.add(post);
                }
                if (posts.size() > 0) {
                    lytLoading.setVisibility(View.GONE);
                    recyclerView.setAdapter(new PostAdapter(MainActivity.this, posts));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();

        Toast.makeText(this, "Network Connected: " + isConnected, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        Toast.makeText(this, "Network Connected: " + isConnected, Toast.LENGTH_SHORT).show();
    }
}
