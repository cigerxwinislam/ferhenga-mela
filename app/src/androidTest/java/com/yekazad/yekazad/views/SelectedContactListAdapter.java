// Bismillahirrahmannirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rovas.forgram.yekazad.R;
import com.rovas.forgram.yekazad.base.AbstractRecyclerAdapter;
import com.rovas.forgram.yekazad.interfaces.OnRemoveClickListener;
import com.rovas.forgram.yekazad.models.Following;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohamed El Sayed
 */
public class SelectedContactListAdapter extends AbstractRecyclerAdapter<Following,
        SelectedContactListAdapter.SelectedContactViewHolder> {

    private OnRemoveClickListener onRemoveClickListener;

    public SelectedContactListAdapter(Context context, List<Following> mList) {
        super(context, mList);
    }

    public OnRemoveClickListener getOnRemoveClickListener() {
        return onRemoveClickListener;
    }

    // set a listener called when the "remove" button is pressed
    public void setOnRemoveClickListener(OnRemoveClickListener onRemoveClickListener) {
        this.onRemoveClickListener = onRemoveClickListener;
    }

    @Override
    public SelectedContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.t_row_selected_contact_list, parent, false);
        return new SelectedContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectedContactViewHolder holder, final int position) {

        Following contact = getItem(position);
        holder.bind(contact, position, getOnRemoveClickListener());
    }

    class SelectedContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView contact;
        private final CircleImageView profilePicture;
//        private final ImageView remove;

        SelectedContactViewHolder(View itemView) {
            super(itemView);
            contact = (TextView) itemView.findViewById(R.id.username);
            profilePicture = (CircleImageView) itemView.findViewById(R.id.profile_picture);
//            remove = (ImageView) itemView.findViewById(R.id.remove);
        }

        public void bind(Following contact, int position, OnRemoveClickListener callback) {
            setDisplayName(contact.getUsername());
            loadProfileImage(contact);
            onRemoveClickListener(position, callback);
        }

        private void setDisplayName(String displayName) {
            contact.setText(displayName);
        }

        private void loadProfileImage(Following contact) {

            String url = contact.getThumb_image();

            Glide.with(itemView.getContext())
                    .load(url)
                    .into(profilePicture);

            //TODO Glide getProfilePictureUrl
        }

        private void onRemoveClickListener(final int position, final OnRemoveClickListener callback) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int returnedPosition = 0;
//                    if (position > 0) {
//                        returnedPosition = position;
//                    }

                    callback.onRemoveClickListener(position);
                }
            });
        }
    }
}