// Bismillahirrahmannirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.views;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rovas.forgram.yekazad.R;
import com.rovas.forgram.yekazad.controllers.activities_popup.vProfileActivity;
import com.rovas.forgram.yekazad.models.Followers;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by Mohamed El Sayed
 */
public class FollowersRecyclerViewAdapter extends RecyclerView.Adapter<FollowersRecyclerViewAdapter.ViewHolder>{
    private List<Followers> followers_list;
    private Context context;
    public FirebaseAuth mAuth;
    public FirebaseFirestore fStore;
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    public FollowersRecyclerViewAdapter(List<Followers> followers_list) {
        this.followers_list = followers_list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followers_list_item,parent,false);
        context = parent.getContext();
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowersRecyclerViewAdapter.ViewHolder holder, final int position) {

        final String current_user_id = mAuth.getCurrentUser().getUid();
        final String user_id = followers_list.get(position).getUser_id();
        final String user_name = followers_list.get(position).getUsername();
        final String thumb_image = followers_list.get(position).getThumb_image();
        holder.setUserData(user_name , thumb_image);
        if (current_user_id.equals(user_id)) {
            holder.mProfile_follow_btn.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.mProfile_follow_btn.setVisibility(View.VISIBLE);
        }

        holder.mProfile_follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_id.equals(current_user_id)) {
                    Intent profileIntent = new Intent(context, vProfileActivity.class);
                    profileIntent.putExtra("user_id", user_id);
                    context.startActivity(profileIntent);
                }
            }
        });

        fStore.collection("users/" + current_user_id + "/Followers/").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        fStore.collection("users/" + current_user_id + "/Following/").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        holder.mProfile_follow_btn.setText(context.getString(R.string.unfollow));
                                        holder.mCurrent_state = "mutual_followers";
                                    } else {
                                        holder.mProfile_follow_btn.setText(context.getString(R.string.follow_back));
                                        holder.mCurrent_state = "single_followers";
                                    }
                                }
                            }
                        });

                    } else
                        fStore.collection("users/" + current_user_id + "/Following/").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        holder.mProfile_follow_btn.setText(context.getString(R.string.unfollow));
                                        holder.mCurrent_state = "single_following";
                                    } else {
                                        holder.mProfile_follow_btn.setText(context.getString(R.string.follow));
                                        holder.mCurrent_state = "not_followers";
                                    }
                                }
                            }
                        });

                } else {
                    String e = task.getException().getMessage();
                }
            }
        });
        holder.mProfile_follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.SetFollowStatue(current_user_id, user_id);
            }
        });

    }
    @Override
    public int getItemCount() {
        return followers_list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private View view;
        private CircleImageView mProfile_image;
        private TextView mProfile_user_name;
        private TextView mProfile_name;
        private Button mProfile_follow_btn;
        private String mCurrent_state;
        public ViewHolder(View itemView){
            super(itemView);
            view=itemView;
            mProfile_image = (CircleImageView)view.findViewById(R.id.followers_image);
            mProfile_user_name = (TextView)view.findViewById(R.id.followers_username);
            mProfile_name = (TextView)view.findViewById(R.id.followers_name);
            mProfile_follow_btn = (Button) view.findViewById(R.id.followers_btn);
            mCurrent_state = "not_followers";
        }

        public void setUserData(String username , String image){

            mProfile_user_name.setText(username);

            Glide.with(context).load(image).into(mProfile_image);

        }
        private void SetFollowStatue(String current_user_id ,String user_id )
        {
            if(mCurrent_state.equals("not_followers"))
            {
                final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                HashMap<String, Object> followersMap_ = new HashMap<>();
                followersMap_.put("time_stamp",  timestamp.getTime());
                followersMap_.put("user_id", current_user_id);
                //
                HashMap<String, Object> followingMap_ = new HashMap<>();
                followingMap_.put("time_stamp",  timestamp.getTime());
                followingMap_.put("user_id", user_id);
                fStore.collection("users/" + user_id + "/Followers/").document(current_user_id).set(followersMap_);
                fStore.collection("users/" + current_user_id + "/Following/").document(user_id).set(followingMap_);
                mCurrent_state = "single_following";
                mProfile_follow_btn.setText(context.getString(R.string.unfollow));
            }
            // --------------- SINGLE FOLLOWING STATE ------------
            else if(mCurrent_state.equals("single_following"))
            {

                fStore.collection("users/" + current_user_id + "/Following/").document(user_id).delete();
                fStore.collection("users/" + user_id + "/Followers/").document(current_user_id).delete();
                mCurrent_state = "not_followers";
                mProfile_follow_btn.setText(context.getString(R.string.follow));
            }
            // --------------- SINGLE FOLLOWERS STATE ------------
            else if(mCurrent_state.equals("single_followers"))
            {
                final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                HashMap<String, Object> followersMap_ = new HashMap<>();
                followersMap_.put("time_stamp",  timestamp.getTime());
                followersMap_.put("user_id", current_user_id);
                //
                HashMap<String, Object> followingMap_ = new HashMap<>();
                followingMap_.put("time_stamp", timestamp.getTime());
                followingMap_.put("user_id", user_id);
                fStore.collection("users/" + user_id + "/Followers/").document(current_user_id).set(followersMap_);
                fStore.collection("users/" + current_user_id + "/Following/").document(user_id).set(followingMap_);
                mCurrent_state = "mutual_followers";
                mProfile_follow_btn.setText(context.getString(R.string.unfollow));
            }
            // --------------- MUTUAL FOLLOWERS STATE ------------
            else if(mCurrent_state.equals("mutual_followers"))
            {
                fStore.collection("users/" + current_user_id + "/Following/").document(user_id).delete();
                fStore.collection("users/" + user_id + "/Followers/").document(current_user_id).delete();
                mCurrent_state = "single_followers";
                mProfile_follow_btn.setText(context.getString(R.string.follow_back));
            }
        }
    }
}