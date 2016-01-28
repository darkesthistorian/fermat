/*
 * @#EncodeMsjContent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.EncodeMsjContent</code> is
 * responsible of encode the content of the message by type of content
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class EncodeMsjContent {

    /**
     *  Construct the content of the message fot the type <code>DigitalAssetMetadataTransactionType.CHAT_METADATA_TRASMIT</code>
     *
     * @param chatMetadata
     * @return String message content
     */
    public static String encodeMSjContentChatMetadataTransmit(ChatMetadata chatMetadata, PlatformComponentType senderType, PlatformComponentType receiverType){

        String contemnt = "";

        /*
         * Create the json object
         */
        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.ID_CHAT,chatMetadata.getChatId().toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.CHAT_METADATA_TRASMIT.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.CHAT_METADATA, chatMetadata.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS, chatMetadata.getDistributionStatus().toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.CHAT_STATUS, chatMetadata.getChatMessageStatus().toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MESSAGE_STATUS, chatMetadata.getMessageStatus().toString());

        return gson.toJson(jsonObjectContent);
    }

    /**
     * Construct the content of the message fot the type <code>DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE</code>
     *
     * @param chatId
     * @param newDistributionStatus
     * @return String message content
     */

    public static String encodeMSjContentTransactionNewStatusNotification(UUID chatId,UUID messageID, DistributionStatus newDistributionStatus, PlatformComponentType senderType, PlatformComponentType receiverType) {


        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.ID_CHAT, chatId.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MESSAGE_ID, messageID.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS, gson.toJson(newDistributionStatus));
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());

        return gson.toJson(jsonObjectContent);
    }

    public static String encodeMSjContentTransactionNewStatusNotification(UUID chatId,UUID messageID, MessageStatus messageStatus, PlatformComponentType senderType, PlatformComponentType receiverType) {


        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.ID_CHAT, chatId.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MESSAGE_ID, messageID.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MESSAGE_STATUS, gson.toJson(messageStatus));
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());

        return gson.toJson(jsonObjectContent);
    }
}
