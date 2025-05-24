// Bismillahirrahmannirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.Utils.Views;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.yekazad.yekazad.R;

/**
 * Created by Mohamed El Sayed
 * SwipeRefreshLayout compatible with pre Lolly pop android versions
 * (fixed hiding progress view on top of the screen)
 */

public class SwipeRefreshPreLollyPop extends SwipeRefreshLayout {

    public SwipeRefreshPreLollyPop(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshPreLollyPop(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setProgressViewOffset(false,
                (int) getResources().getDimension(R.dimen.refresh_layout_start_offset),
                (int) getResources().getDimension(R.dimen.refresh_layout_end_offset));
    }
}
