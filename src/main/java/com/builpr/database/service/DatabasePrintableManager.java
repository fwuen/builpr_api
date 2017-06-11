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
    } /**
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
