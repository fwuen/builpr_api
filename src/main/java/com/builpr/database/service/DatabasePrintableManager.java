package com.builpr.database.service;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.printable.PrintableManager;
import com.builpr.restapi.converter.PrintableToSolrPrintableConverter;
import com.builpr.restapi.model.CustomMultipartFile;
import com.builpr.restapi.model.Request.Printable.PrintableEditRequest;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;
import com.builpr.restapi.utils.TokenGenerator;
import com.builpr.search.SearchManagerException;
import com.builpr.search.model.Indexable;
import com.builpr.search.model.PrintableReference;
import com.builpr.search.solr.SolrSearchManager;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static com.builpr.Constants.TEST_PATH;

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
     * @param printableReferences PrintableReference
     * @return List<Printable>
     */
    public List<Printable> getPrintableList(List<PrintableReference> printableReferences) {
        List<Printable> list = new ArrayList<>();
        for (PrintableReference reference : printableReferences) {
            if (getPrintableById(Integer.parseInt(reference.getId())) != null) {
                list.add(getPrintableById(Integer.parseInt(reference.getId())));
            }
        }
        return list;
    }

    /**
     * @param printable int
     * @return void
     */
    public void persist(Printable printable) {
        getDao().persist(printable);
    }

    /**
     * @param printableID int
     * @return boolean
     */
    public boolean checkPrintableId(int printableID) {
        if (printableID < 1) {
            return false;
        }
        Printable printable = getPrintableById(printableID);
        return printable != null;
    }

    /**
     * @param description String
     * @return boolean
     */
    public boolean checkDescription(String description) {
        return description.length() <= 1000;
    }

    /**
     * @param title String
     * @return boolean
     */
    public boolean checkTitle(String title) {
        return !(title.length() < 5 || title.length() > 100);
    }

    /**
     * @param categories List<String>
     * @return List<String>
     */
    public List<String> checkCategories(List<String> categories) {
        List<String> newCategories = new ArrayList<>();

        for (String category : categories) {
            if (category.matches("^[\\pL\\pN\\p{Pc}]*$")) {
                category = category.toLowerCase();
                newCategories.add(category);
            }
        }
        // add elements to setOfCategories in order to delete duplicates
        Set<String> setOfCategories = new HashSet<>();
        setOfCategories.addAll(newCategories);
        newCategories.clear();
        newCategories.addAll(setOfCategories);
        return newCategories;
    }

    /**
     * @param request PrintableNewRequest
     * @param userID  int
     * @param path    String
     * @return Printable
     */
    public Printable createPrintable(PrintableNewRequest request, int userID, String path) {
        PrintableImpl printable = new PrintableImpl();
        printable.setTitle(request.getTitle());
        printable.setDescription(request.getDescription());
        printable.setUploaderId(userID);
        Date currentDate = new Date(System.currentTimeMillis());
        printable.setUploadDate(currentDate);
        printable.setFilePath(path);

        persist(printable);
        return printable;
    }

    /**
     * @param printable Printable
     * @return void
     */
    public void update(Printable printable) {
        this.getDao().update(printable);
    }

    /**
     * @param data byte[]
     * @return String
     * @throws IOException Exception
     */
    public String uploadFile(byte[] data) throws IOException {
//         Filename = token + timestamp
        TokenGenerator tokenGenerator = new TokenGenerator(126, true);
        String token = tokenGenerator.generate();
        long timestamp = System.currentTimeMillis();
        String filename = token + timestamp + ".stl";
        String path = TEST_PATH + filename;

        MultipartFile multipartFile = new CustomMultipartFile(data, path);
        File file = new File(path);
        Path filePath = Paths.get(path);

        try {
            Files.createFile(filePath);
        } catch (FileAlreadyExistsException e) {
            throw new FileAlreadyExistsException("File alreads existent");
        }

        multipartFile.transferTo(file);


        return path;
    }

    /**
     * @param printableID int
     * @return MultipartFile
     * @throws IOException Exception
     */
    public byte[] downloadFile(int printableID) throws IOException {
        Printable printable = getPrintableById(printableID);
        String path = printable.getFilePath();
        Path p = FileSystems.getDefault().getPath(path);
        return Files.readAllBytes(p);
    }

    /**
     * @param printableID int
     * @return void
     */
    public void deletePrintable(int printableID) {
        getDao().stream().filter(Printable.PRINTABLE_ID.equal(printableID)).forEach(getDao().remover());
    }

    /**
     * @return void
     * @throws SearchManagerException exception
     */
    public void indexPrintables() throws SearchManagerException {
        List<Printable> printables = getDao().stream().collect(Collectors.toList());
        List<Indexable> solrPrintableList = new ArrayList<>();
        for (Printable printable : printables) {
            com.builpr.search.model.Printable solrPrintable = PrintableToSolrPrintableConverter.getSolrPrintable(printable);
            solrPrintableList.add(solrPrintable);
        }
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL("http://192.168.1.50:8983/solr");
        if (!solrPrintableList.isEmpty()) {
            solrSearchManager.addToIndex(solrPrintableList);
        }
    }

    /**
     * @return void
     */
    public void deletePrintableFromIndex() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL("http://192.168.1.50:8983/solr");
        // TODO clear methode aufrufe

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
}
