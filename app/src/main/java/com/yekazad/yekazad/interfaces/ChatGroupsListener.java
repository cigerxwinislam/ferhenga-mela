// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.interfaces;

import com.rovas.forgram.yekazad.models.ChatGroup;
import com.rovas.forgram.yekazad.exception.ChatRuntimeException;


/**
 * Created by Mohamed El Sayed
 */

public interface ChatGroupsListener {

    void onGroupAdded(ChatGroup chatGroup, ChatRuntimeException e);

    void onGroupChanged(ChatGroup chatGroup, ChatRuntimeException e);

    void onGroupRemoved(ChatRuntimeException e);
}
