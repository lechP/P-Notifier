package com.picadilla.notifier.service;

/**
 * Service responsible for notifying given batch of players.
 */
public interface Notifier {

    /**
     * Notifies batch of players. Size of the batch is specified as external property and injected into application.
     */
    void notifyBatchOfPlayers();

}
