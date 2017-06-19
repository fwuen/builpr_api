package com.builpr.restapi.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.builpr.Constants.TEST_PATH;

/**
 *
 */
public class PrintableUploader {

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
            multipartFile.transferTo(file);
        } catch (FileAlreadyExistsException e) {
            throw new FileAlreadyExistsException("File alreads existent");
        }



        return path;
    }

}
