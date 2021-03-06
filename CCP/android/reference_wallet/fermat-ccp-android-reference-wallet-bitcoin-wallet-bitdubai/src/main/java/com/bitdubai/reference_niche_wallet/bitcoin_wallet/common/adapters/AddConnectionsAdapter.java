package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.View;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.IntraUserInfoViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.AddConnectionCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del CryptoBrokerIdentityListFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class AddConnectionsAdapter extends FermatAdapter<CryptoWalletIntraUserActor, IntraUserInfoViewHolder> {


    private final AddConnectionCallback addConnectionCallback;

    public AddConnectionsAdapter(Context context, ArrayList<CryptoWalletIntraUserActor> dataSet,AddConnectionCallback addConnectionCallback) {
        super(context, dataSet);
        this.addConnectionCallback = addConnectionCallback;
    }

    @Override
    protected IntraUserInfoViewHolder createHolder(View itemView, int type) {
        return new IntraUserInfoViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.intra_user_information_list_item;
    }

    @Override
    protected void bindHolder(final IntraUserInfoViewHolder holder, final CryptoWalletIntraUserActor data, final int position) {
        holder.getName().setText(data.getAlias());
        RoundedBitmapDrawable roundedBitmap = null;
        byte[] profileImage = data.getProfileImage();
        try {
            if (profileImage != null) {
                if (profileImage.length > 0) {
                    roundedBitmap = ImagesUtils.getRoundedBitmap(context.getResources(), profileImage);
                    holder.getThumbnail().setImageDrawable(roundedBitmap);
                } else {
                    Picasso.with(context).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(holder.getThumbnail());
                }
            } else {
                Picasso.with(context).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(holder.getThumbnail());
                //roundedBitmap = ImagesUtils.getRoundedBitmap(context.getResources(), R.drawable.ic_profile_male);
            }
        }catch (Exception e){
            Picasso.with(context).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(holder.getThumbnail());
        }



        holder.getContainer_data().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected =  !data.isSelected();
                data.setSelected(selected);
                addConnectionCallback.setSelected(data, selected);
                if(selected==true) {
//                    ObjectAnimator animator = ObjectAnimator.ofInt(v, "backgroundColor", Color.TRANSPARENT, Color.parseColor("#dcf6f7")).setDuration(1500);
//                    animator.setEvaluator(new ArgbEvaluator());
//                    animator.start();
                    holder.getCheckbox_connection().setChecked(true);
                    FermatAnimationsUtils.showEmpty(context,true,holder.getCheckbox_connection());
                    addConnectionCallback.addMenuEnabled();


                }else {
//                    ObjectAnimator animator = ObjectAnimator.ofInt(v, "backgroundColor", Color.parseColor("#dcf6f7"), Color.TRANSPARENT).setDuration(1500);
//                    animator.setEvaluator(new ArgbEvaluator());
//                    animator.start();
                    holder.getCheckbox_connection().setChecked(false);
                    FermatAnimationsUtils.showEmpty(context, false, holder.getCheckbox_connection());
                    addConnectionCallback.addMenuDisabled();
                }
//                TransitionDrawable transition = (TransitionDrawable) v.getBackground();
//                if(selected==false){
//                    transition.startTransition(300);
//                }else{
//                    transition.reverseTransition(300);
//                }
                //v.setBackground(context.getDrawable(R.drawable.add_connection_rounded_rectangle_shape));
            }
        });
        if(data.isSelected()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.getContainer_data().setBackground(context.getDrawable(R.drawable.add_connection_rounded_rectangle_shape));
            }
        }else{
            holder.getContainer_data().setBackgroundColor(Color.parseColor("#ffffff"));

        }

        holder.getCheckbox_connection().setChecked(false);
        holder.getCheckbox_connection().setEnabled(false);
    }

}
