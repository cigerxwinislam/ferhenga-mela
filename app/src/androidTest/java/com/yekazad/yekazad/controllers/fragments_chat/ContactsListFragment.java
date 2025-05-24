package com.rovas.forgram.yekazad.controllers.fragments_chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.rovas.forgram.yekazad.R;
import com.rovas.forgram.yekazad.views.ContactListAdapter;
import com.rovas.forgram.yekazad.Utils.ItemDecoration;
import com.rovas.forgram.yekazad.Synchronizer;
import com.rovas.forgram.yekazad.interfaces.OnContactClickListener;
import com.rovas.forgram.yekazad.models.Following;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mohamed El Sayed
 */

public class ContactsListFragment extends Fragment {
    private static final String TAG = ContactsListFragment.class.getName();

    //private ContactsSynchronizer contactsSynchronizer;
    private Synchronizer synchronizer;
    private OnContactClickListener onContactClickListener;

    private SearchView searchView;
    private FirebaseFirestore fStore;
    // contacts list recyclerview
    private RecyclerView recyclerViewContacts;
    private LinearLayoutManager lmRvContacts;
    private ContactListAdapter contactsListAdapter;

    // no contacts layout
    private RelativeLayout noContactsLayout;
    private String current_user_id;
    private List<Following> followingList;
    public static Fragment newInstance() {
        Fragment mFragment = new ContactsListFragment();
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "ContactsListFragment.onCreateView");
        View view = inflater.inflate(R.layout.t_fragment_contacts_list, container, false);
        fStore = FirebaseFirestore.getInstance();
        // init RecyclerView
        recyclerViewContacts = view.findViewById(R.id.contacts_list);
        recyclerViewContacts.addItemDecoration(new ItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL,
                getResources().getDrawable(R.drawable.decorator_activity_contact_list)));
        lmRvContacts = new LinearLayoutManager(getActivity());
        recyclerViewContacts.setLayoutManager(lmRvContacts);
        followingList = new ArrayList<>();
        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        noContactsLayout = view.findViewById(R.id.layout_no_contacts);
        //Synchronizer synchronizer = new Synchronizer(current_user_id);
        updateContactListAdapter(followingList);
        //Log.d(TAG, "onCreateView: Size " +   synchronizer.getFollowingList().size());
        Log.d(TAG, "onCreateView: updateContactListAdapter(synchronizer.getFollowingList());");
        //updateContactListAdapter(contactsListAdapter.getFollowing_list_Filtered());
        //TODO Here updateContactListAdapter  || toggleNoContactsLayoutVisibility
        // no contacts layout
        toggleNoContactsLayoutVisibility(contactsListAdapter.getItemCount());
        all_Followers();


        return view;
    }

    public void updateContactListAdapter(List<Following> list) {
        if (contactsListAdapter == null) {
            // init RecyclerView adapter
            contactsListAdapter = new ContactListAdapter(list);
            if (getOnContactClickListener() != null)
                Log.d(TAG, "onCreateView: Size " +   list.size());
                Log.d(TAG, "onCreateView:   if (getOnContactClickListener() != null)");
                contactsListAdapter.setOnContactClickListener(getOnContactClickListener());
            recyclerViewContacts.setAdapter(contactsListAdapter);

        } else {
            Log.d(TAG, "onCreateView:     contactsListAdapter.setList(list);");
            contactsListAdapter.setList(list);
            contactsListAdapter.notifyDataSetChanged();
        }
    }

    // toggle the no contacts layout visibilty.
    // if there are items show the list of item, otherwise show a placeholder layout
    private void toggleNoContactsLayoutVisibility(int itemCount) {
        recyclerViewContacts.setVisibility(View.VISIBLE);
        noContactsLayout.setVisibility(View.GONE);
        /*
        if (itemCount > 0) {
            // show the item list
            recyclerViewContacts.setVisibility(View.VISIBLE);
            noContactsLayout.setVisibility(View.GONE);
        } else {
            // show the placeholder layout
            recyclerViewContacts.setVisibility(View.GONE);
            noContactsLayout.setVisibility(View.VISIBLE);
        }
        */
    }

    public void setOnContactClickListener(OnContactClickListener onContactClickListener) {
        this.onContactClickListener = onContactClickListener;
    }

    public OnContactClickListener getOnContactClickListener() {
        return onContactClickListener;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
        inflater.inflate(R.menu.menu_activity_contacts_list, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        MenuItemCompat.setShowAsAction(item,
                MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW |
                        MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                if (contactsListAdapter != null) contactsListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (contactsListAdapter != null) contactsListAdapter.getFilter().filter(query);
//                Log.d(TAG, "ContactListActivity.OnQueryTextListener.onQueryTextChange:" +
//                        " query == " + query);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater); // attach the activity menu
    }
    private void all_Followers() {
        followingList.clear();
        Query f_query = fStore.collection("users").document(current_user_id).collection("Following");
        f_query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        String followingid = doc.getDocument().getId();
                        Following following = doc.getDocument().toObject(Following.class).withid(followingid);
                        followingList.add(following);
                        contactsListAdapter.notifyDataSetChanged();


                    }
                }
            }
        });
    }
    public void onBackPressed() {
        // close search view on back button pressed
        if (searchView != null && !searchView.isIconified()) {
//            searchView.setIconified(true);
            searchView.onActionViewCollapsed();
            return;
        }
    }
}
