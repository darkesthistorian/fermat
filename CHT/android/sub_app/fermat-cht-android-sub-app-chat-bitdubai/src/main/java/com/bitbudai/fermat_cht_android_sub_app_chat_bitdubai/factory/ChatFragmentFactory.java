package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.factory;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments.ChatFragment;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments.ChatListFragment;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * ChatFragmentFactory
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com)  on 29/12/15.
 * @version 1.0
 */

public class ChatFragmentFactory extends FermatFragmentFactory<ChatSession, SubAppResourcesProviderManager, ChatFragmentsEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(ChatFragmentsEnumType fragments) throws FragmentNotFoundException {
        if (fragments == null) {
            throw createFragmentNotFoundException(null);
        }
        if (fragments.equals(ChatFragmentsEnumType.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT))
            return ChatListFragment.newInstance();
        if (fragments.equals(ChatFragmentsEnumType.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT))
            return ChatFragment.newInstance();

           /* case CHT_CHAT_OPEN_CHAT_DETAIL:
                return OpenChatDetailFragment.newInstance();
            case CHT_CHAT_OPEN_CONTACT_DETAIL:
                return OpenContactDetailFragment.newInstance();
            case CHT_CHAT_EDIT_CONTACT:
                return EditContactFragment.newInstance();
            case CHT_CHAT_OPEN_CONNECTIONLIST:
                return OpenConnectionListFragment.newInstance();*/
        throw createFragmentNotFoundException(fragments);

    }

    @Override
    public ChatFragmentsEnumType getFermatFragmentEnumType(String key) {
        return ChatFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason, context;

        if (fragments == null) {
            possibleReason = "The parameter 'fragments' is NULL";
            context = "Null Value";
        } else {
            possibleReason = "Not found in switch block";
            context = fragments.toString();
        }

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }
}
