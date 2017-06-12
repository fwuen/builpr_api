package com.builpr.database.service;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.printable.PrintableManager;
import com.builpr.restapi.model.CustomMultipartFile;
import com.builpr.restapi.model.Request.Printable.PrintableEditRequest;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;
import com.builpr.restapi.utils.TokenGenerator;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
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
        for (String category : categories) {
            category = category.toLowerCase();
            if (category.contains(" ")) {
                categories.remove(category);
            }
        }
        return categories;
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
     * @param request PrintableEditRequest
     * @return void
     */
    public void update(PrintableEditRequest request) {
        this.getDao().stream().
                filter(Printable.PRINTABLE_ID.equal(request.getPrintableID()))
                .map(Printable.TITLE.setTo(request.getTitle()))
                .map(Printable.DESCRIPTION.setTo(request.getDescription()))
                .forEach(this.getDao().updater());
    }

    /**
     * @param multipartFile MultiPartFile
     * @return String
     * @throws IOException Exception
     */
    public String uploadFile(MultipartFile multipartFile) throws IOException {
//         Filename = token + timestamp
        TokenGenerator tokenGenerator = new TokenGenerator(true);
        String token = tokenGenerator.generate();
        long timestamp = System.currentTimeMillis();
        String filename = token + timestamp + ".stl";
        String path = "C:\\Users\\Markus\\Desktop\\Modells\\" + filename;
        File file = new File(path);
        Path path1 = Paths.get(path);
        try {
            Files.createFile(path1);
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
    public MultipartFile downloadFile(int printableID) throws IOException {
        Printable printable = getPrintableById(printableID);
        String path = printable.getFilePath();
        Path p = FileSystems.getDefault().getPath(path);
        byte[] fileData = Files.readAllBytes(p);
        return new CustomMultipartFile(fileData, path);
    }

    /**
     * @param printableID int
     * @return void
     */
    public void deletePrintable(int printableID) {
        getDao().remove(
                this.getPrintableById(printableID)
        );
    }
}
