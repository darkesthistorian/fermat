package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by memo on 11/01/16.
 */
public class StockDestockViewHolder  extends FermatViewHolder {
    private FermatTextView title;
    private FermatTextView subTitle1;
    private FermatTextView subTitle2;


    public StockDestockViewHolder(View itemView) {
        super(itemView);
        title = (FermatTextView) itemView.findViewById(R.id.cbw_settings_title);
        subTitle1 = (FermatTextView) itemView.findViewById(R.id.cbw_settings_sub_title1);
        subTitle2 = (FermatTextView) itemView.findViewById(R.id.cbw_settings_sub_title2);
    }


    public void bind(CryptoBrokerWalletAssociatedSetting data) {
        System.out.println("data = "+data.getPlatform().getCode()+"    "+data.getWalletPublicKey() + "         "+ data.getMerchandise().getCode());
        subTitle1.setText(getPlatformTitle(data.getPlatform()));
        title.setText(data.getMerchandise().getCode());
        subTitle2.setText("test1");
    }

    private String getPlatformTitle(Platforms platform) {
        if (platform.equals(Platforms.BANKING_PLATFORM))
            return "Bank";
        if (platform.equals(Platforms.CASH_PLATFORM))
            return "Cash";

        return "Crypto";
    }
}
