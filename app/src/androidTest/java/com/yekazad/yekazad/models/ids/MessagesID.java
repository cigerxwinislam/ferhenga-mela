// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.models.ids;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
/**
 * Created by Mohamed El Sayed
 */
public class MessagesID {
    @Exclude
    public String MessagesID;
    public  <T extends MessagesID> T withid (@NonNull final String ID)
    {
        this.MessagesID = ID;
        return (T)this;
    }

}
