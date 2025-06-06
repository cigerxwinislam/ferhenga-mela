// Bismillahirrahmannirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah


package com.yekazad.yekazad.Utils;


import android.app.Activity;
import android.net.Uri;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by Mohamed El Sayed
 */
public class Cropper {

    /**
     * @return Intent that will open the crop context with an adjustable bounds for the cropping square.
     * * * */
    public static void startActivity(Activity activity, Uri output){

        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity(output)
                .setAllowFlipping(false)
                .setInitialCropWindowPaddingRatio(0)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);

    }

    /**
     * @return Intent that will open the crop context with an adjustable bounds for the cropping square.
     * * * */
    public static void startSquareActivity(Activity activity, Uri output){
        CropImage.activity(output)
                .setAspectRatio(1,1)
                .setInitialCropWindowPaddingRatio(0)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);
    }

    public static void startCircleActivity(Activity activity, Uri output){
        CropImage.activity(output)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setInitialCropWindowPaddingRatio(0)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);
    }

}
