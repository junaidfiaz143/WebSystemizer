package com.inventors.jd.keeper.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inventors.jd.keeper.R;
import com.inventors.jd.keeper.models.Post;

public class AddPostActivity extends AppCompatActivity {

    private EditText edtTitle, edtUrl;

    private Button btnSave;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Posts");

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        onGetLastId();

        edtTitle = findViewById(R.id.edtTitle);
        edtUrl = findViewById(R.id.edtUrl);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("" + id).setValue(new Post(id, edtTitle.getText().toString(), edtUrl.getText().toString(), "true"), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(AddPostActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
                            edtTitle.setText("");
                            edtUrl.setText("");
                            finish();
                        } else {
                            Toast.makeText(AddPostActivity.this, "Insertion Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void onGetLastId() {
        ref.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Post post = ds.getValue(Post.class);

                    id = post.getId();
                }
                id++;
//                Toast.makeText(AddPostActivity.this, "" + id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
