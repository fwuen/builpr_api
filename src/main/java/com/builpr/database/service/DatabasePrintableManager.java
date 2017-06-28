package com.builpr.database.service;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * printable service
 */
public class DatabasePrintableManager extends DatabaseManager<PrintableManager> {

    public DatabasePrintableManager() {
        super(PrintableManager.class);
    }

    /**
     * @param userID int
     * @return List<Printable>
     */
    public List<Printable> getPrintablesForUser(int userID) {
        return getDao().stream().filter(Printable.UPLOADER_ID.equal(userID)).collect(Collectors.toList());
    }

    /**
     * @param printableID int
     * @return Printable
     */
    public Printable getPrintableById(int printableID) {
        Optional<Printable> list = getDao().stream().filter(Printable.PRINTABLE_ID.equal(printableID)).findFirst();
        return list.orElse(null);
    }

    /**
     * @param printable int
     * @return void
     */
    public Printable persist(Printable printable) {
        return getDao().persist(printable);
    }

    /**
     * @param printable Printable
     * @return void
     */
    public void update(Printable printable) {
        this.getDao().update(printable);
    }

    /**
     * @param printableID int
     * @return void
     */
    public void deletePrintable(int printableID) {
        getDao().stream().filter(Printable.PRINTABLE_ID.equal(printableID)).forEach(getDao().remover());
    }

    /**
     * @param printableID Int
     * @return void
     */
    public void updateDownloads(int printableID) {
        this.getDao().stream().
                filter(Printable.PRINTABLE_ID.equal(printableID))
                .map(f -> f.setNumDownloads(f.getNumDownloads() + 1))
                .forEach(this.getDao().updater());
    }

    public boolean isPresent(int printableID) {
        return getDao().stream().anyMatch(Printable.PRINTABLE_ID.equal(printableID));
    }

    /**
     * @return List<Printable>
     */
    public List<Printable> getAllPrintables() {
        return getDao().stream().collect(Collectors.toList());
    }
}
