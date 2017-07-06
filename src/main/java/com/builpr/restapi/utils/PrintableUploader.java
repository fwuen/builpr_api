package com.builpr.restapi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.builpr.Constants.TEST_PATH;

/**
 * PrintableUploader
 */
public class PrintableUploader {

    /**
     * Upload a file to the server
     *
     * @param data byte[]
     * @return String
     * @throws IOException Exception
     */
    public String uploadFile(byte[] data) throws IOException {
        // Filename = token + timestamp
        TokenGenerator tokenGenerator = new TokenGenerator(126, true);
        String token = tokenGenerator.generate();
        long timestamp = System.currentTimeMillis();
        String filename = token + timestamp + ".stl";
        String path = TEST_PATH + filename;

        File file = new File(path);
        Path filePath = Paths.get(path);

        try {
            Files.createFile(filePath);
        } catch (FileAlreadyExistsException e) {
            throw new FileAlreadyExistsException("File alreads existent");
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File could not be found");
        }

        return path;
    }

}
