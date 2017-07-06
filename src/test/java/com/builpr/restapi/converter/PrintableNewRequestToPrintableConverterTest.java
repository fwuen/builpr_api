package com.builpr.restapi.converter;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

/**
 * @author Markus Goller
 *
 * Tests for PrintableNewRequestToPrintableConverter
 */
public class PrintableNewRequestToPrintableConverterTest {
    private PrintableNewRequest testPrintableRequest;

    @Before
    public void setTestUp() {
        testPrintableRequest = new PrintableNewRequest();
        testPrintableRequest.setTitle("testTitle");
        testPrintableRequest.setDescription("testDescription");

    }

    @Test
    public void testFrom() {
        Printable printable = PrintableNewRequestToPrintableConverter.from(testPrintableRequest, 1, "testpath");
        Assert.assertTrue(printable.getUploaderId() == 1);
        Assert.assertTrue(Objects.equals(printable.getFilePath(), "testpath"));
        Assert.assertTrue(Objects.equals(printable.getTitle(), "testTitle"));
        Assert.assertTrue(printable.getDescription().isPresent());
        Assert.assertTrue(Objects.equals(printable.getDescription().get(), "testDescription"));
    }
}
