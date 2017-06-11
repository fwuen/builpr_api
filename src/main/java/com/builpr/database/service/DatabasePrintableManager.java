package com.builpr.database.service;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableManager;
import com.builpr.restapi.utils.TokenGenerator;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * printable service
 */
public class DatabasePrintableManager extends DatabaseManager<PrintableManager> {

    public DatabasePrintableManager() {
        super(PrintableManager.class);
    }

    public List<Printable> getPrintablesForUser(int userID) {
        return getDao().stream().filter(Printable.UPLOADER_ID.equal(userID)).collect(Collectors.toList());
    }

    public Printable getPrintableById(int printableID) {
        List<Printable> list = getDao().stream().filter(Printable.PRINTABLE_ID.equal(printableID)).collect(Collectors.toList());
        return list.get(0);
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
     * @param multipartFile MultiPartFile
     * @return String
     * @throws IOException Exception
     */
    public String uploadFile(MultipartFile multipartFile) throws IOException {
//         Filename = token + timestamp
        TokenGenerator tokenGenerator = new TokenGenerator(false);
        String token = tokenGenerator.generate();
        long timestamp = System.currentTimeMillis();
        String filename = token + timestamp + ".stl";
        String path = "C:\\Users\\Markus\\Desktop\\Modells\\" + filename;
        File file = new File(path);
        Path path1 = Paths.get(path);
        try {
            Files.createFile(path1);
        } catch (FileAlreadyExistsException e) {
            System.err.println("already exists: " + e.getMessage());
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
//        Printable printable = getPrintableById(printableID);
//        String path = printable.getFilePath();
        String path = "C:\\Users\\Markus\\Desktop\\Modells\\Z-Shim_1mm.stl";
        File file = new File(path);
        Path p = FileSystems.getDefault().getPath(path);
        byte[] fileData = Files.readAllBytes(p);
        return null; //new CustomMultipartFile(fileData, path);
    }

    /**
     * @param printableID int
     * @return void
     */
    public void deletePrintable(int printableID) {
        this.getDao().stream().filter(Printable.PRINTABLE_ID.equal(printableID)).forEach(this.getDao().remover());
    }
}
