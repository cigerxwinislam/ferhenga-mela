// Bismillahirrahmannirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yekazad.yekazad.Utils.SpinKit.SpinKitView;
import com.yekazad.yekazad.Utils.helper.LoadHelper;
import com.yekazad.yekazad.controllers.activities_main.SignInActivity;
import com.yekazad.yekazad.controllers.activities_main.SetupActivity;
import com.yekazad.yekazad.controllers.activities_main.HomeActivity;
import com.yekazad.yekazad.managers.UserWiazrd;
import com.yekazad.yekazad.models.Notification;
import com.yekazad.yekazad.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static int TIME_OUT = 3000;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private List<Notification> notificationList;
    private String current_user_id;
    private SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_start);
        Log.d(TAG, "onCreate: starting..");
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        notificationList = new ArrayList<>();
        spinKitView = findViewById(R.id.spin_kit);
        ThreeBounce threeBounce = new ThreeBounce();
        spinKitView.setIndeterminateDrawable(threeBounce);
    }

    public void sendtoLogin() {
        Intent loginIntent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void sendtoHome() {
        if (UserWiazrd.getInstance().getTempUser().getUsername() != null &&
                UserWiazrd.getInstance().getTempUser().getThumb_image() != null) {
            Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(loginIntent);
            finish();
        } else {
            LoadHelper.Load();
            new Handler().postDelayed(this::sendtoHome, TIME_OUT);
        }
    }

    public void sendtoSetup() {
        Intent loginIntent = new Intent(MainActivity.this, SetupActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void Load_ChatGroup() {
        fStore.collection("users").whereEqualTo("user_id", current_user_id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                String groupID = doc.getDocument().getId();
                                User sGroup = doc.getDocument().toObject(User.class).withid(groupID);
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (CurrentUser == null) {
            new Handler().postDelayed(this::sendtoLogin, TIME_OUT);
        } else {
            LoadHelper.Load();
            new Handler().postDelayed(this::sendtoHome, TIME_OUT);
        }
    }
}