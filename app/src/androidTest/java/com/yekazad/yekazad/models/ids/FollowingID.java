// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.models.ids;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
/**
 * Created by Mohamed El Sayed
 */
public class FollowingID {
    @Exclude
    public String FollowingID;
    public  <T extends FollowingID> T withid (@NonNull final String ID)
    {
        this.FollowingID = ID;
        return (T)this;
    }

}
