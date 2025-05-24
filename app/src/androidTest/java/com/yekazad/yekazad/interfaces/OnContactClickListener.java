// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.interfaces;


import com.rovas.forgram.yekazad.models.Following;

import java.io.Serializable;

/**
 * Created by Mohamed El Sayed
 */
public interface OnContactClickListener extends Serializable {
    void onContactClicked(Following contact, int position);
}
