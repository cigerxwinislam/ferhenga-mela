//Bismillahirrahmanirahim
//Elhamdulillahirabbulalemin
//Esselatu vesselamu alaa seyyidina Muhammedin ve alaa alihi ve sahbihi ecmain

package com.yekazad.yekazad.controllers.activities_notification;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rovas.forgram.yekazad.R;
import com.rovas.forgram.yekazad.Utils.GetTimeAgo;
import com.rovas.forgram.yekazad.Utils.Views.CircularImageView;
import com.rovas.forgram.yekazad.controllers.activities_home.CommentsActivity;
import com.rovas.forgram.yekazad.controllers.activities_main.HomeActivity;
import com.rovas.forgram.yekazad.controllers.activities_popup.vProfileActivity;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by Mohamed El Sayed
 */
public class PhotoForward extends AppCompatActivity {

    private TextView blogUserName;
    private CircularImageView blogUserImage;
    private TextView blogDate;
    private ImageButton more_btn;

    private ImageView blogLikeBtn;
    private ImageView blogCommentBtn;
    private TextView blogLikeCount;
    private TextView blogCommentCount;

    private TextView blogTweet;
    private ImageView blogPhoto;

    //FireBase
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String currentUserId;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item_odrinary_photo);
        //Views
        blogUserName = findViewById(R.id.blog_user_name);
        blogUserImage = findViewById(R.id.blog_user_image);
        blogDate = findViewById(R.id.blog_date);
        more_btn = findViewById(R.id.imageButton_more);
        //Middle_Layout
        blogLikeBtn = findViewById(R.id.blog_like_btn);
        blogLikeCount = findViewById(R.id.blog_like_count);
        blogCommentBtn = findViewById(R.id.blog_comment_icon);
        blogCommentCount = findViewById(R.id.blog_comment_count);
        //Content
        blogTweet = findViewById(R.id.blog_desc);
        blogPhoto = findViewById(R.id.blog_image);
        //FireBase
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        //Intent-Extra
        String from = getIntent().getStringExtra("from");
        final String forward  =   getIntent().getStringExtra("forward");
        String message = getIntent().getStringExtra("message");
        //SharedPreferences
        SharedPreferences prefs_c_p = PreferenceManager.getDefaultSharedPreferences(PhotoForward.this);
        String data_c_p = prefs_c_p.getString("blog_user_icon", ""); //no id: default value
        final String blog_user_icon = data_c_p;
        fStore.collection("Posts").document(forward).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    if (task.getResult().exists()) {


                        //Tweets_Text
                        String desc_post = task.getResult().getString("desc");
                        setDescText_Post(desc_post);
                        //BlogPost
                        String image_url = task.getResult().getString("image_url");
                        setBlogImage(image_url);

                        final String Ownder_id = task.getResult().getString("user_id");
                        fStore.collection("users").document(Ownder_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if(task.isSuccessful()){

                                    String userName = task.getResult().getString("username");
                                    String userImage = task.getResult().getString("thumb_image");
                                    setUserData(userName, userImage);
                                }
                            }
                        });
                        try {
                            long millisecond = task.getResult().getLong("time_stamp");
                            String lastSeenTime = GetTimeAgo.getTimeAgo(millisecond, PhotoForward.this);
                            blogDate.setText(lastSeenTime);
                        } catch (Exception e) {

                            Toast.makeText(PhotoForward.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        //Time_Stamp[Date]
                        ///////////////////////////////////////////////////////////////////////
                        //Likes Feature
                        blogLikeBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //holder.reactionView.show(holder.motionEvent);
                                fStore.collection("Posts/" + forward + "/Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if(!task.getResult().exists()){

                                            Map<String, Object> likesMap = new HashMap<>();
                                            likesMap.put("timestamp", FieldValue.serverTimestamp());

                                            fStore.collection("Posts/" + forward + "/Likes").document(currentUserId).set(likesMap);
                                            if(!currentUserId.equals(Ownder_id)) {
                                                fStore.collection("users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                        if (task.isSuccessful()) {

                                                            String userName = task.getResult().getString("username");
                                                            String thumb_image = task.getResult().getString("thumb_image");
                                                            String message_noti = userName + " Liked your post";
                                                            Map<String, Object> notificatinMap = new HashMap<>();
                                                            notificatinMap.put("message", message_noti);
                                                            notificatinMap.put("from", currentUserId);
                                                            notificatinMap.put("username", userName);
                                                            notificatinMap.put("thumb_image", thumb_image);
                                                            notificatinMap.put("post", "tweet");
                                                            notificatinMap.put("type", "like");
                                                            notificatinMap.put("forward", forward);
                                                            notificatinMap.put("timestamp", FieldValue.serverTimestamp());
                                                            fStore.collection("users/" + Ownder_id + "/Notifications").add(notificatinMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                    if (!task.isSuccessful()) {
                                                                        Toast.makeText(PhotoForward.this, "Error Send Notification : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                                    }


                                                                }
                                                            });


                                                        } else {

                                                            //Firebase Exception

                                                        }

                                                    }
                                                });
                                            }

                                        } else {

                                            fStore.collection("Posts/" + forward + "/Likes").document(currentUserId).delete();

                                        }

                                    }
                                });
                            }
                        });
                        //Get Likes icon
                        fStore.collection("Posts/" + forward + "/Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                                if(documentSnapshot.exists()){

                                    blogLikeBtn.setImageDrawable(ContextCompat.getDrawable(PhotoForward.this, R.mipmap.action_like_accent));

                                } else {

                                    blogLikeBtn.setImageDrawable(ContextCompat.getDrawable(PhotoForward.this ,R.drawable.ic_like));


                                }

                            }
                        });
                        //Comment Feature
                        blogCommentBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent commentIntent = new Intent(PhotoForward.this, CommentsActivity.class);
                                commentIntent.putExtra("blog_post_id", forward);
                                commentIntent.putExtra("owner_id",Ownder_id);
                                startActivity(commentIntent);

                            }
                        });
                        //Get Likes Count
                        fStore.collection("Posts/" + forward + "/Likes").addSnapshotListener( new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                if(!documentSnapshots.isEmpty()){

                                    int count = documentSnapshots.size();

                                    updateLikesCount(count);

                                } else {

                                    updateLikesCount(0);

                                }

                            }
                        });
                        //Comment_Intent
                        blogUserImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent commentIntent = new Intent(PhotoForward.this, vProfileActivity.class);
                                commentIntent.putExtra("user_id", Ownder_id);
                                startActivity(commentIntent);

                            }
                        });
                        //Comments_Count
                        fStore.collection("Posts/" + forward + "/Comments").addSnapshotListener( new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                if(!documentSnapshots.isEmpty()){

                                    int count = documentSnapshots.size();

                                    UpdateCommentCount(count , blog_user_icon);

                                } else {

                                    UpdateCommentCount(0 , blog_user_icon);

                                }

                            }
                        });
                        ///////////////////////////////////////////////////////////////////////
                        more_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Display option menu

                                android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(PhotoForward.this, more_btn);
                                popupMenu.inflate(R.menu.option_menu);
                                popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {

                                        switch (item.getItemId()) {
                                            case R.id.mnu_item_save:
                                                Toast.makeText(PhotoForward.this, "Saved", Toast.LENGTH_LONG).show();
                                                break;
                                            case R.id.mnu_item_delete: {
                                                //Delete item
                                                if (Ownder_id.equals(currentUserId))
                                                {
                                                    fStore.collection("Posts").document(forward).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            //blogRecyclyerAdapter.notifyDataSetChanged();
                                                            Intent Intent = new Intent(PhotoForward.this, HomeActivity.class);
                                                            startActivity(Intent);
                                                            finish();//Don't Return AnyMore TO the last page
                                                            Toast.makeText(PhotoForward.this, "Deleted", Toast.LENGTH_LONG).show();

                                                        }
                                                    });
                                                    break;
                                                }
                                            }
                                            default:
                                                break;
                                        }
                                        return false;
                                    }
                                });
                                popupMenu.show();
                            }
                        });
                        //////////////////////////////////////////////////////////////////////

                    }
                }
            }
        });
    }
    public void setDescText_Post(String descText){

        blogTweet.setText(descText);

    }
    public void setBlogImage(String downloadUri){
        if(downloadUri != null) {
            Glide.with(PhotoForward.this).load(downloadUri).into(blogPhoto);
        }

    }
    public void setTime(String date) {

        blogDate.setText(date);

    }

    public void setUserData(String name, String image){

        blogUserName.setText(name);
        if(image != null) {
            Glide.with(PhotoForward.this).load(image).into(blogUserImage);
        }

    }
    public void updateLikesCount(int count){

        blogLikeCount.setText(count + " " +getString(R.string.likes));

    }
    public void UpdateCommentCount(int count , String image){
            blogCommentCount.setText(count +" " + getString(R.string.comments));
    }

}
