// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.models.ids;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
/**
 * Created by Mohamed El Sayed
 */
public class ReplyCommentID {
    @Exclude
    public String ReplyCommentID;
    public  <T extends ReplyCommentID> T withid (@NonNull final String ID)
    {
        this.ReplyCommentID = ID;
        return (T)this;
    }

}
