// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.models.ids;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
/**
 * Created by Mohamed El Sayed
 */
public class FollowersID {
    @Exclude
    public String FollowersID;
    public  <T extends FollowersID> T withid (@NonNull final String ID)
    {
        this.FollowersID = ID;
        return (T)this;
    }

}
