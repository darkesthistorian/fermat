package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 06/01/16.
 */
public class ChatSupAppModuleManager implements ChatManager {

    private final com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatManager chatManager;

    public ChatSupAppModuleManager(com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatManager chatManager)
    {
        this.chatManager    = chatManager;
    }

    @Override
    public List<Chat> getChats() throws CantGetChatException {
        return chatManager.getChats();
    }

    @Override
    public Chat getChatByChatId(UUID chatId) throws CantGetChatException {
        return chatManager.getChatByChatId(chatId);
    }

    @Override
    public Chat newEmptyInstanceChat() throws CantNewEmptyChatException {
        return chatManager.newEmptyInstanceChat();
    }

    @Override
    public void saveChat(Chat chat) throws CantSaveChatException {
        chatManager.saveChat(chat);
    }

    @Override
    public void deleteChat(Chat chat) throws CantDeleteChatException {
        chatManager.deleteChat(chat);
    }

    @Override
    public List<Message> getMessages() throws CantGetMessageException {
        return chatManager.getMessages();
    }

    @Override
    public Message getMessageByChatId(UUID chatId) throws CantGetMessageException {
        return chatManager.getMessageByChatId(chatId);
    }

    @Override
    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException {
        return chatManager.getMessageByMessageId(messageId);
    }

    @Override
    public Message newEmptyInstanceMessage() throws CantNewEmptyMessageException {
        return chatManager.newEmptyInstanceMessage();
    }

    @Override
    public void saveMessage(Message message) throws CantSaveMessageException {
        chatManager.saveMessage(message);
    }

    @Override
    public void deleteMessage(Message message) throws  CantDeleteMessageException {
        chatManager.deleteMessage(message);
    }

    @Override
    public List<Contact> getContacts() throws CantGetContactException {
        return chatManager.getContacts();
    }

    @Override
    public Contact getContactByContactId(UUID contactId) throws CantGetContactException {
        return chatManager.getContactByContactId(contactId);
    }

    @Override
    public Contact newEmptyInstanceContact() throws CantNewEmptyContactException {
        return newEmptyInstanceContact();
    }

    @Override
    public void saveContact(Contact contact) throws CantSaveContactException {
        chatManager.saveContact(contact);
    }

    @Override
    public void deleteContact(Contact contact) throws CantDeleteContactException {
        chatManager.deleteContact(contact);
    }

    @Override
    public List<Contact> discoverActorsRegistered() throws CantGetContactException {
        return chatManager.discoverActorsRegistered();
    }
}
