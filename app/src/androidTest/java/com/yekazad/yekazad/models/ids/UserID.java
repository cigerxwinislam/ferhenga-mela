// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.models.ids;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
/**
 * Created by Mohamed El Sayed
 */
public class UserID {

    @Exclude
    public String UserID;
    public <T extends UserID> T withid (@NonNull final String id)
    {
        this.UserID = id;
        return (T) this;
    }
}
