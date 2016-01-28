package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BlockchainConnectionStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.RegTestNetwork.FermatTestNetwork;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.RegTestNetwork.FermatTestNetworkNode;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantCancellBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.ErrorBroadcastingTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.Status;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.BlockchainException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.StoredBlock;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBroadcast;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.WalletTransaction;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by rodrigo on 10/4/15.
 */
public class BitcoinCryptoNetworkMonitor implements Agent {
    /**
     * class variables
     */
    Wallet wallet;
    PeerGroup peerGroup;
    File walletFileName;
    BlockChain blockChain;
    BitcoinNetworkEvents events;
    final NetworkParameters NETWORK_PARAMETERS;
    final BlockchainNetworkType BLOCKCHAIN_NETWORKTYPE;
    BitcoinCryptoNetworkDatabaseDao bitcoinCryptoNetworkDatabaseDao;




    /**
     * Platform variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;

    /**
     * Constructor
     * @param pluginDatabaseSystem
     */
    public BitcoinCryptoNetworkMonitor(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, Wallet wallet, File walletFilename) {
        /**
         * I initialize the local variables
         */
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.wallet = wallet;
        this.pluginId = pluginId;
        this.walletFileName = walletFilename;

        /**
         * I get the network parameter from the passed wallet.
         */
        NETWORK_PARAMETERS = wallet.getNetworkParameters();
        BLOCKCHAIN_NETWORKTYPE = BitcoinNetworkSelector.getBlockchainNetworkType(NETWORK_PARAMETERS);
    }


    @Override
    public void start() throws CantStartAgentException {
        //todo move this to the correct new thread format.
        try {
            doTheMainTask();
        } catch (BlockchainException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        /**
         * will wait until the peer agent stops.
         */
        peerGroup.stop();
        while(getPeerGroup().isRunning()){

        }
    }

    /**
     * Wallet setter
     * @param wallet
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }


    /**
     * Agent main method
     */
    private void doTheMainTask() throws BlockchainException {
        System.out.println("Crypto Network starting and connecting...");

        try{
            /**
             * creates the blockchain object for the specified network.
             */
            BitcoinCryptoNetworkBlockChain CryptoNetworkBlockChain = new BitcoinCryptoNetworkBlockChain(NETWORK_PARAMETERS, wallet);
            BlockChain blockChain = CryptoNetworkBlockChain.getBlockChain();

            /**
             * creates the peerGroup object
             */
            peerGroup = new PeerGroup(NETWORK_PARAMETERS, blockChain);
            peerGroup.addWallet(this.wallet);

            /**
             * add the events
             */
            events = new BitcoinNetworkEvents(pluginDatabaseSystem, pluginId, this.walletFileName);
            peerGroup.addEventListener(events);
            this.wallet.addEventListener(events);
            blockChain.addListener(events);


            /**
             * I will connect to the regTest server or search for peers if we are in a different network.
             */
            if (NETWORK_PARAMETERS == RegTestParams.get()){
                FermatTestNetwork fermatTestNetwork = new FermatTestNetwork();
                for (FermatTestNetworkNode node : fermatTestNetwork.getNetworkNodes()){
                    peerGroup.addAddress(node.getPeerAddress());
                }
            } else
                peerGroup.addPeerDiscovery(new DnsDiscovery(NETWORK_PARAMETERS));

            /**
             * Define internal agent information.
             */
            peerGroup.setUserAgent(BitcoinNetworkConfiguration.USER_AGENT_NAME, BitcoinNetworkConfiguration.USER_AGENT_VERSION);

            /**
             * starts the monitoring
             */
            peerGroup.setDownloadTxDependencies(true);
            peerGroup.start();
            peerGroup.startBlockChainDownload(null);

            /**
             * I will broadcast any transaction that might be in broadcasting status.
             * I might have this if the process was interrupted while broadcasting.
             */
            resumeBroadcastOfPendingTransactions(BLOCKCHAIN_NETWORKTYPE);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Will get all transactions hashes in Broadcasting status to resume them.
     */
    private void resumeBroadcastOfPendingTransactions(BlockchainNetworkType blockchainNetworkType) {
        /**
         * will get all the trasactions from the wallet in pending status and broadcast them
         */
        for (Transaction transaction : wallet.getPendingTransactions()){
            try {
                this.broadcastTransaction(transaction.getHashAsString());
            } catch (CantBroadcastTransactionException e) {
                /**
                 * if there was an error, I will mark the transaction as WITH_ERROR
                 */
                try {
                    getDao().setBroadcastStatus(Status.WITH_ERROR, peerGroup.getConnectedPeers().size(), e, transaction.getHashAsString());
                } catch (CantExecuteDatabaseOperationException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Broadcast a well formed, commited and signed transaction into the specified network
     *
     * @param txHash the transaction hash to be broadcasted
     * @return the broadcasted transaction.
     * @throws CantBroadcastTransactionException
     */
    public synchronized void broadcastTransaction(final String txHash) throws CantBroadcastTransactionException{
        /**
         * The transaction is stored in the Wallet and the database, so I will make sure this is correct.
         */
        Sha256Hash sha256Hash = Sha256Hash.wrap(txHash);
         validateTransactionExistsInWallet(sha256Hash);
         validateTransactionExistsinDatabase(txHash);

        final int connectedPeers = peerGroup.getConnectedPeers().size();

         /**
          * will update this transaction status to broadcasting.
          */
         try {
             getDao().setBroadcastStatus(Status.BROADCASTING, connectedPeers, null, txHash);
         } catch (CantExecuteDatabaseOperationException e) {
             e.printStackTrace();
         }

         final Transaction transaction = wallet.getTransaction(sha256Hash);
         TransactionBroadcast transactionBroadcast = peerGroup.broadcastTransaction(transaction);
         transactionBroadcast.setMinConnections(BitcoinNetworkConfiguration.MIN_BROADCAST_CONNECTIONS);

         ListenableFuture<Transaction> future = transactionBroadcast.future();
        /**
         * I add the future that will get the broadcast result into a call back to respond to it.
         */
        Futures.addCallback(future, new FutureCallback<Transaction>() {
            @Override
            public void onSuccess(Transaction result) {

                try {
                    getDao().setBroadcastStatus(Status.BROADCASTED, connectedPeers, null, txHash);
                    /**
                     * Store this outgoing transaction in the table
                     */
                    UUID transactionId = getDao().getBroadcastedTransactionId(BLOCKCHAIN_NETWORKTYPE, txHash);
                    storeOutgoingTransaction(wallet, transaction, transactionId);


                    /**
                     * saves the wallet again.
                     */
                    wallet.saveToFile(walletFileName);

                    System.out.println("Transaction succesfully broadcasted: " + transaction.getHashAsString());
                } catch (CantExecuteDatabaseOperationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    getDao().setBroadcastStatus(Status.WITH_ERROR, connectedPeers, (Exception) t, txHash);
                } catch (CantExecuteDatabaseOperationException e) {
                    e.printStackTrace();
                }
            }
        });


//        /**
//         * Will set the time out for this broadcast attempt.
//         */
//        try {
//            future.get(BitcoinNetworkConfiguration.TRANSACTION_BROADCAST_TIMEOUT, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            try {
//                getDao().setBroadcastStatus(Status.WITH_ERROR, connectedPeers, e, txHash);
//            } catch (CantExecuteDatabaseOperationException e1) {
//                e1.printStackTrace();
//            }
//        } catch (ExecutionException e) { a
//            try {
//                getDao().setBroadcastStatus(Status.WITH_ERROR, connectedPeers, e, txHash);
//            } catch (CantExecuteDatabaseOperationException e1) {
//                e1.printStackTrace();
//            }
//        } catch (TimeoutException e) {
//            try {
//                getDao().setBroadcastStatus(Status.WITH_ERROR, connectedPeers, e, txHash);
//            } catch (CantExecuteDatabaseOperationException e1) {
//                e1.printStackTrace();
//            }
//        } catch (Exception e){
//            try {
//                getDao().setBroadcastStatus(Status.WITH_ERROR, connectedPeers, e, txHash);
//            } catch (CantExecuteDatabaseOperationException e1) {
//                e1.printStackTrace();
//            }
//        }
    }


    /**
     * Will search this transaction in the database and make sure it exists.
     * @param txHash
     * @throws CantBroadcastTransactionException
     */
    private void validateTransactionExistsinDatabase(String txHash) throws CantBroadcastTransactionException{
        try {
            if (!getDao().transactionExistsInBroadcast(txHash)){
                throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, null, "the specified transaction " + txHash + " is not stored in the database.", "CryptoNetwork");
            }
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * if I couldn't validate it, because the database thrown an error, I will continue assuming that exists.
             */
            e.printStackTrace();
        }
    }

    /**
     * validates the passed transaction exists in the Wallet
     * @param sha256Hash
     */
    private void validateTransactionExistsInWallet(Sha256Hash sha256Hash) throws CantBroadcastTransactionException {
        Transaction transaction = wallet.getTransaction(sha256Hash);

        if (transaction == null){
            StringBuilder output = new StringBuilder("The transaction " + sha256Hash.toString());
            output.append(" is not stored in the CryptoNetwork.");
            output.append(System.lineSeparator());
            output.append("Stored transactions are:");
            output.append(System.lineSeparator());
            for (Transaction storedTransaction : wallet.getTransactions(true)){
                output.append(storedTransaction.getHashAsString());
                output.append(System.lineSeparator());
            }

            throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, null, output.toString(), null);
        }
    }

    /**
     * Stores and outgoing transaction into the database
     * @param wallet
     * @param tx
     * @param transactionId
     */
    private void storeOutgoingTransaction(Wallet wallet, Transaction tx, UUID transactionId) {
        events.saveOutgoingTransaction(wallet, tx, transactionId);
    }

    /**
     * gets the peer group
     * @return
     */
    public PeerGroup getPeerGroup() {
        return peerGroup;
    }

    /**
     * Will get the CryptoTransaction directly from the blockchain by requesting it to a peer.
     * If the transaction is not part of any of our vaults, we will ask it to a connected peer to retrieve it.
     * @param txHash the Hash of the transaction we are going to look for.
     * @param blockHash the Hash of block where this transaction was stored..
     * @return a CryptoTransaction with the information of the transaction.
     * @throws CantGetCryptoTransactionException
     */

    public CryptoTransaction getCryptoTransactionFromBlockChain(String txHash, String blockHash) throws CantGetCryptoTransactionException {
        Transaction transaction = null;
        try {
            transaction = this.getTransactionFromBlockChain(txHash, blockHash);
        } catch (Exception e) {
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "Error getting the transaction from the blockchain.", null);
        }
        return this.getCryptoTransactionFromBitcoinTransaction(transaction);
    }

    /**
     * Transform a Bitcoin Transaction into a Crypto Transaction
     * @param transaction
     * @return
     */
    private CryptoTransaction getCryptoTransactionFromBitcoinTransaction(Transaction transaction) {
        return CryptoTransaction.getCryptoTransaction(transaction);
    }

    /**
     * Gets from the DownloadPeer the block that matches the passed hash
     * @param blockHash
     * @return
     */
    private Block getBlockFromPeer(Sha256Hash blockHash) throws CantGetTransactionException {
        try {
            return peerGroup.getDownloadPeer().getBlock(blockHash).get(1, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, e, "There was a problem trying to get the block from the Peer.", null);
        }
    }

    /**
     * Stores a Bitcoin Transaction in the CryptoNetwork to be broadcasted later
     * @param tx
     * @param transactionId
     * @throws CantStoreBitcoinTransactionException
     */
    public synchronized void storeBitcoinTransaction (Transaction tx, UUID transactionId) throws CantStoreBitcoinTransactionException{
        try {
            /**
             * I store it in the database
             */
            getDao().storeBitcoinTransaction(BLOCKCHAIN_NETWORKTYPE, tx.getHashAsString(), transactionId, peerGroup.getConnectedPeers().size(), peerGroup.getDownloadPeer().getAddress().toString());

            /**
             * I store it in the wallet.
             */
            wallet.maybeCommitTx(tx);
            wallet.saveToFile(walletFileName);

            System.out.println("Transaction succesfully stored for broadcasting: " + tx.getHashAsString());
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantStoreBitcoinTransactionException(CantStoreBitcoinTransactionException.DEFAULT_MESSAGE, e, "There was an error storing the transaction in the database", null);
        } catch (Exception e) {
            /**
             * If there was an error, then I will make sure that the transaction is not left stored at the database.
             */
            try {
                getDao().deleteStoredBitcoinTransaction(tx.getHashAsString());
            } catch (CantExecuteDatabaseOperationException e1) {
                /**
                 * I will ignore this error
                 */
                e1.printStackTrace();
            }
        }
    }

    /**
     * returns and instance of the database dao class
     * @return
     */
    private BitcoinCryptoNetworkDatabaseDao getDao() {
        if (bitcoinCryptoNetworkDatabaseDao == null)
            bitcoinCryptoNetworkDatabaseDao = new BitcoinCryptoNetworkDatabaseDao(this.pluginId, this.pluginDatabaseSystem);
        return bitcoinCryptoNetworkDatabaseDao;
    }

    /**
     * invalidates the passed transaction by clearing inputs and outputs.
     * @param txHash
     */
    public void cancelBroadcast(String txHash) throws CantCancellBroadcastTransactionException{
        try {
            /**
             * I will get the transaction
             */
            Sha256Hash sha256Hash = Sha256Hash.wrap(txHash);
            Transaction transaction = wallet.getTransaction(sha256Hash);

            if (transaction == null)
                throw new CantCancellBroadcastTransactionException(CantCancellBroadcastTransactionException.DEFAULT_MESSAGE, null, "the specified transaction does not exists.", null);

            if (transaction.getConfidence().getConfidenceType() != TransactionConfidence.ConfidenceType.PENDING)
                throw new CantCancellBroadcastTransactionException(CantCancellBroadcastTransactionException.DEFAULT_MESSAGE, null, "The transaction was already broadcasted and Can't be cancelled.", null);

            /**
             * I will disconnect all inputs from the outpoint
             */
            for (TransactionInput input : transaction.getInputs()){
                input.disconnect();
            }

            /**
             * I will change all outputs values to zero
             */
            for (TransactionOutput output : transaction.getOutputs()){
                output.setValue(Coin.ZERO);
            }

            /**
             * do wallet maintenance
             */
            wallet.maybeCommitTx(transaction);
            wallet.cleanup();

            wallet.saveToFile(walletFileName);
        } catch (Exception e) {
            throw new CantCancellBroadcastTransactionException(CantCancellBroadcastTransactionException.DEFAULT_MESSAGE, e, "Change in transaction could not be saved in wallet.", null);
        }
    }

    /**
     * Gets the current blockchainConnectionStatus
     * @return
     */
    public BlockchainConnectionStatus getBlockchainConnectionStatus() throws CantGetBlockchainConnectionStatusException {
        try{
            int connectedPeers = peerGroup.getConnectedPeers().size();
            String downloadNodeIp = peerGroup.getDownloadPeer().getAddress().toString();
            long downloadPing = peerGroup.getDownloadPeer().getLastPingTime();

            BlockchainConnectionStatus blockchainConnectionStatus = new BlockchainConnectionStatus(connectedPeers, downloadNodeIp, downloadPing, BLOCKCHAIN_NETWORKTYPE);

            return blockchainConnectionStatus;
        } catch (Exception e){
            throw new CantGetBlockchainConnectionStatusException(CantGetBlockchainConnectionStatusException.DEFAULT_MESSAGE, e, "Error getting connection status from peers.", null);
        }

    }

    /**
     * Gets the specified Transaction from the Blockchain by looking it locally, or on the blockchain using the block hash
     * @param transactionHash
     * @param transactionBlockHash
     * @return
     * @throws CantGetTransactionException
     */
    public Transaction getTransactionFromBlockChain(String transactionHash, String transactionBlockHash) throws CantGetTransactionException {
        /**
         * I will check I don't get nulls in the parameters
         */
        if (transactionHash == null )
            throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, null, "TxHash parameters can't be null", null);

        /**
         * I will get the transaction from our own database if we have it.
         */
        Sha256Hash transactionSha256Hash = Sha256Hash.wrap(transactionHash);
        Transaction storedTransaction = wallet.getTransaction(transactionSha256Hash);

        if (storedTransaction != null)
            return storedTransaction;

        /**
         * I don't have it locally, so I will try to get it from the stored blockstore
         */
        if (transactionBlockHash == null )
            throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, null, "BlockHash parameters can't be null", null);

        Sha256Hash blockHash = Sha256Hash.wrap(transactionBlockHash);

        try {
            /**
             * If I don't have this block, then I will get the block from the peer
             */

            Block genesisBlock = getBlockFromPeer(blockHash);

            /**
             * Will search all transactions from the block until I find my own.
             */
            for (Transaction transaction : genesisBlock.getTransactions()) {
                if (transaction.getHashAsString().contentEquals(transactionHash)) {
                    /**
                     * I form the CryptoTransaction and return it.
                     */
                    return transaction;
                }
            }
        } catch (Exception e) {
            throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, e, "error getting the Transaction from the blockchain" , null);
        }

        return null;
    }
}
