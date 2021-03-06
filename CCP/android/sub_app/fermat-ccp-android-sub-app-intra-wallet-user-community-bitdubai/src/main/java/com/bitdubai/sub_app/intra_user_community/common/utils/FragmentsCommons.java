package com.bitdubai.sub_app.intra_user_community.common.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.sub_app.intra_user_community.R;
import com.squareup.picasso.Picasso;

/**
 * @author Created by mati on 2015.11.12..
 * @author Modified byJose Manuel De Sousa 08/12/2015
 */
public class FragmentsCommons {


    public static View setUpHeaderScreen(LayoutInflater inflater, Activity activity, ActiveActorIdentityInformation intraUserLoginIdentity) throws CantGetActiveLoginIdentityException {
        /**
         * Navigation view header
         */
        RelativeLayout relativeLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
        relativeLayout.setLayoutParams(layoutParams);
        View view = inflater.inflate(R.layout.row_navigation_drawer_community_header, relativeLayout, true);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
        if (intraUserLoginIdentity != null) {
            if (intraUserLoginIdentity.getImage() != null) {
                if (intraUserLoginIdentity.getImage().length > 0) {
                    BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView,activity.getResources(),0,false);
                    bitmapWorkerTask.execute(intraUserLoginIdentity.getImage());
                } else
                    Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
            } else
                Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
            FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
            fermatTextView.setText(intraUserLoginIdentity.getAlias());
        }
        return view;
    }
}
