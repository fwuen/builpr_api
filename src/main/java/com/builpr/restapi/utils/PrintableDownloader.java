package com.builpr.restapi.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Uploads the files to the server
 */
public class PrintableDownloader {

    /**
     * Download a file from the server
     *
     * @param path String
     * @return MultipartFile
     * @throws IOException Exception
     */
    public byte[] downloadFile(String path) throws IOException {
        Path p = FileSystems.getDefault().getPath(path);
        return Files.readAllBytes(p);
    }
}
