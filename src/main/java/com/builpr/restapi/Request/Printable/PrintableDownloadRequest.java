package com.builpr.restapi.Request.Printable;

import lombok.Getter;
import lombok.Setter;

/**
 * DownloadRequest for printables
 */
public class PrintableDownloadRequest {

    /**
     * id of the printable
     */
    @Getter
    @Setter
    int printableID;
}
