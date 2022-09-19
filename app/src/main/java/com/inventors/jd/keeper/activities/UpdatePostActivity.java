package com.inventors.jd.keeper.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inventors.jd.keeper.R;
import com.inventors.jd.keeper.models.Post;

public class UpdatePostActivity extends AppCompatActivity {

    private EditText edtTitle, edtUrl;

    private Button btnUpdate;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Posts");

    private int id = 0;
    private String title, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);

        edtTitle = findViewById(R.id.edtTitle);
        edtUrl = findViewById(R.id.edtUrl);

        getId();

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != 0)
                    ref.child("" + id).setValue(new Post(id, edtTitle.getText().toString(), edtUrl.getText().toString(), "true"), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                Toast.makeText(UpdatePostActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                edtTitle.setText("");
                                edtUrl.setText("");
                                finish();
                            } else {
                                Toast.makeText(UpdatePostActivity.this, "Update Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
    }

    private void getId() {
        try {
            id = getIntent().getExtras().getInt("id");
            title = getIntent().getExtras().getString("title");
            url = getIntent().getExtras().getString("url");

            edtTitle.setText(title);
            edtUrl.setText(url);
        } catch (Exception exp) {

        }
    }
}
