// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.models.ids;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
/**
 * Created by Mohamed El Sayed
 */
public class BlogPostID {

    @Exclude
    public String BlogPostID;
    public <T extends BlogPostID> T withid (@NonNull final String id)
    {
        this.BlogPostID = id;
        return (T) this;
    }
}
