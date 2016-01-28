package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.Group;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.User;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by frank on 12/8/15.
 */
public class AssetDeliverySelectGroupsHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;
    private Resources res;

    private FermatTextView nameText;
    private ImageView selectUserButton;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetDeliverySelectGroupsHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        nameText = (FermatTextView) itemView.findViewById(R.id.groupName);
        selectUserButton = (ImageView) itemView.findViewById(R.id.selectGroupButton);
    }

    public void bind(final Group group) {
        nameText.setText(group.getName());
        if (group.isSelected()) {
            selectUserButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
        } else {
            selectUserButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
        }

        selectUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!group.isSelected()) {
                    group.setSelected(true);
                    selectUserButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
                } else {
                    group.setSelected(false);
                    selectUserButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
                }
            }
        });
    }
}
