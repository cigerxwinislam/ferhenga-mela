// Bismillahirrahmanirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.interfaces;
import com.yekazad.yekazad.models.ChatGroup;
import com.yekazad.yekazad.exception.ChatRuntimeException;


/**
 * Created by Mohamed El Sayed
 */

public interface ChatGroupCreatedListener {
    void onChatGroupCreated(ChatGroup chatGroup, ChatRuntimeException chatException);
}
