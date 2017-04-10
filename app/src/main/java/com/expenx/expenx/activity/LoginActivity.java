package com.expenx.expenx.activity;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.expenx.expenx.R;
import com.expenx.expenx.core.MessageOutput;
import com.expenx.expenx.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ImageView mCircleAroundE;

    EditText mEmailText;
    EditText mPasswordText;

    Button mLoginButton;

    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    public static User user = null;

    private String TAG = "expenxtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(LoginActivity.this,ExpenxActivity.class));
                    LoginActivity.this.finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


        databaseReference = FirebaseDatabase.getInstance().getReference();

        RotateAnimation rotate = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setDuration(6000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.start();

        mCircleAroundE = (ImageView) findViewById(R.id.circleAroundE);
        mCircleAroundE.setAnimation(rotate);

        mEmailText = (EditText) findViewById(R.id.editTextEmail);
        mPasswordText = (EditText) findViewById(R.id.editTextPassword);

        mLoginButton = (Button) findViewById(R.id.buttonLogin);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signIn(mEmailText.getText().toString().trim(), mPasswordText.getText().toString().trim());
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            MessageOutput.showSnackbarLongDuration(LoginActivity.this, "Email required..!");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        String password = mPasswordText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            MessageOutput.showSnackbarLongDuration(LoginActivity.this, "Password required..!");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        MessageOutput.showProgressDialog(LoginActivity.this, "Logging in...");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (!task.isSuccessful()) {
                                MessageOutput.showSnackbarLongDuration(LoginActivity.this, task.getException().getMessage());
                            }

                            if (task.isSuccessful()) {


                                databaseReference.child("user").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        user = dataSnapshot.getValue(User.class);
                                        user.userUID = dataSnapshot.getKey();

                                        startActivity(new Intent(LoginActivity.this,ExpenxActivity.class));
                                        LoginActivity.this.finish();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        MessageOutput.showSnackbarLongDuration(LoginActivity.this, databaseError.getMessage());
                                    }
                                });
                            }
                        }catch(NullPointerException e) {
                            MessageOutput.showSnackbarLongDuration(LoginActivity.this, "Something went wrong...!");
                        }
                        MessageOutput.dismissProgressDialog();
                    }
                });

    }
}
