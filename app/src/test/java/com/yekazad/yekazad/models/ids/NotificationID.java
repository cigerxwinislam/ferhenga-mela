// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.models.ids;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
/**
 * Created by Mohamed El Sayed
 */
public class NotificationID {
    @Exclude
    public String NotificationID;
    public  <T extends NotificationID> T withid (@NonNull final String ID)
    {
        this.NotificationID = ID;
        return (T)this;
    }

}
