package com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.smartwallet.R;

public class ProfileCardBackFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static ProfileCardBackFragment newInstance(int position) {
        ProfileCardBackFragment f = new ProfileCardBackFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.wallets_kids_fragment_profile_card_back, container, false); //Contains empty RelativeLayout
        return view;
    }


}