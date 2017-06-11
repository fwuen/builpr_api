package com.builpr.restapi.model.Request.Printable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Request to create a new printable
 */
public class PrintableNewRequest {

    /**
     * title of the printable
     */
    @Getter
    @Setter
    String title;

    /**
     * printable description
     */
    @Getter
    @Setter
    String description;


    /**
     * List of categories the user assigned to the printable
     */
    @Getter
    @Setter
    List<String> categories;
    /**
     * Printable-file
     */
    @Getter
    @Setter
    MultipartFile file;
}
