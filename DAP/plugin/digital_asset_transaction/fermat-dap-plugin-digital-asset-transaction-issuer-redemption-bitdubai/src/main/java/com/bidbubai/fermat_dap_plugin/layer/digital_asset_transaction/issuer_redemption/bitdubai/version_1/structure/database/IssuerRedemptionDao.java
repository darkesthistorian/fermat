package com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.structure.database;

import com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.exceptions.CantLoadIssuerRedemptionEventListException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 6/01/16.
 */
public class IssuerRedemptionDao {

    //VARIABLE DECLARATION
    private UUID pluginId;
    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    //CONSTRUCTORS

    public IssuerRedemptionDao() {
    }

    public IssuerRedemptionDao(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) throws CantExecuteDatabaseOperationException {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        database = openDatabase();
    }

    //PUBLIC METHODS

    public void notifyEvent(String eventId) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateStringFieldByEventId(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                EventStatus.NOTIFIED.getCode(),
                eventId);
    }

    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = this.database.getTable(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            eventRecord.setUUIDValue(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Asset Reception database");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }

    //PRIVATE METHODS
    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Issuer Redemption Transaction Database", "Error in database plugin.");
        }
    }


    private String getStringFieldByEventId(String columnName, String id) throws RecordsNotFoundException, CantLoadIssuerRedemptionEventListException {
        String context = "Column Name: " + columnName + " - Id: " + id;
        DatabaseTable databaseTable;
        databaseTable = database.getTable(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
        databaseTable.addStringFilter(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
        databaseTable.addFilterOrder(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantLoadIssuerRedemptionEventListException(e, context, null);
        }
        if (databaseTable.getRecords().isEmpty()) {
            throw new RecordsNotFoundException(null, context, "");
        }
        return databaseTable.getRecords().get(0).getStringValue(columnName);
    }

    private List<String> getPendingDAPEventsByType(EventType eventType) throws CantLoadIssuerRedemptionEventListException {
        String context = "Event Type: " + eventType.getCode();

        DatabaseTable eventsRecordedTable;
        eventsRecordedTable = database.getTable(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
        eventsRecordedTable.addStringFilter(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType.getCode(), DatabaseFilterType.EQUAL);
        eventsRecordedTable.addStringFilter(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
        eventsRecordedTable.addFilterOrder(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

        try {
            eventsRecordedTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantLoadIssuerRedemptionEventListException(e, context, null);
        }
        List<String> eventIdList = new ArrayList<>();
        for (DatabaseTableRecord record : eventsRecordedTable.getRecords()) {
            eventIdList.add(record.getStringValue(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME));
        }
        return eventIdList;
    }


    private List<String> getPendingEventsBySource(EventSource eventSource) throws CantLoadIssuerRedemptionEventListException {
        try {
            DatabaseTable eventsRecordedTable;
            eventsRecordedTable = database.getTable(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
            eventsRecordedTable.addStringFilter(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource.getCode(), DatabaseFilterType.EQUAL);
            eventsRecordedTable.addStringFilter(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            eventsRecordedTable.addFilterOrder(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            eventsRecordedTable.loadToMemory();
            List<String> eventIdList = new ArrayList<>();
            for (DatabaseTableRecord record : eventsRecordedTable.getRecords()) {
                eventIdList.add(record.getStringValue(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME));
            }
            return eventIdList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadIssuerRedemptionEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new CantLoadIssuerRedemptionEventListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }


    private void updateStringFieldByEventId(String columnName, String value, String id) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        String context = "Column Name: " + columnName + " - Id: " + id;
        DatabaseTable eventRecordedTable;
        eventRecordedTable = database.getTable(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
        eventRecordedTable.addStringFilter(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
        eventRecordedTable.loadToMemory();
        if (eventRecordedTable.getRecords().isEmpty()) {
            throw new RecordsNotFoundException(null, context, "");
        }
        for (DatabaseTableRecord record : eventRecordedTable.getRecords()) {
            record.setStringValue(columnName, value);
            eventRecordedTable.updateRecord(record);
        }
    }

    //GETTER AND SETTERS

    public List<String> getPendingCryptoRouterEvents() throws CantLoadIssuerRedemptionEventListException {
        return getPendingEventsBySource(EventSource.CRYPTO_ROUTER);
    }


    public com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType getEventTypeById(String id) throws CantLoadIssuerRedemptionEventListException, InvalidParameterException, RecordsNotFoundException {
        return com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.getByCode(getStringFieldByEventId(IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME, id));
    }

    public List<String> getPendingNewReceiveMessageActorEvents() throws CantLoadIssuerRedemptionEventListException {
        return getPendingDAPEventsByType(EventType.NEW_RECEIVE_MESSAGE_ACTOR);
    }
    //INNER CLASSES
}
