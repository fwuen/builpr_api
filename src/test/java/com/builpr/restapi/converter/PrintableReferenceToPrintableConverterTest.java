package com.builpr.restapi.converter;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.search.model.PrintableReference;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Tests for the PrintableReferenceToPrintableConverter
 */
public class PrintableReferenceToPrintableConverterTest {
    private DatabaseUserManager databaseUserManager;
    private DatabasePrintableManager databasePrintableManager;
    private PrintableReferenceToPrintableConverter converter;

    private static final int TEST_ID = 654654;
    private static final int TEST_ID2 = 6574654;

    private static final String TEST_USER = "printablereftest";
    private static final String TEST_USER2 = "printablereftest2";

    private static final String TEST_DESCRIPTION = "testDescription";
    private static final String TEST_TITLE = "test title";

    public PrintableReferenceToPrintableConverterTest() {
        databaseUserManager = new DatabaseUserManager();
        databasePrintableManager = new DatabasePrintableManager();
        converter = new PrintableReferenceToPrintableConverter();
    }

    @Before
    public void setTestUp() {
        User testUser = new UserImpl()
                .setUsername(TEST_USER)
                .setEmail("printableref@googlemail.com")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()))
                .setFirstname("rating")
                .setLastname("testuser")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowEmail(false);

        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
        databaseUserManager.persist(testUser);

        User testUser2 = new UserImpl()
                .setUsername(TEST_USER2)
                .setEmail("printableref2@googlemail.com")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()))
                .setFirstname("rating2")
                .setLastname("testuser2")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowEmail(false);

        if (databaseUserManager.getByUsername(TEST_USER2) != null) {
            databaseUserManager.deleteByUsername(TEST_USER2);
        }
        databaseUserManager.persist(testUser2);

        if (databasePrintableManager.isPresent(TEST_ID)) {
            databasePrintableManager.deletePrintable(TEST_ID);
        }

        Printable testPrintable = new PrintableImpl()
                .setPrintableId(TEST_ID)
                .setDescription(TEST_DESCRIPTION)
                .setTitle(TEST_TITLE)
                .setUploaderId(databaseUserManager.getByUsername(TEST_USER).getUserId())
                .setNumDownloads(1)
                .setFilePath("C:\\test\\not\\existing.stl");

        databasePrintableManager.persist(testPrintable);

        if (databasePrintableManager.isPresent(TEST_ID2)) {
            databasePrintableManager.deletePrintable(TEST_ID2);
        }

        Printable testPrintable2 = new PrintableImpl()
                .setPrintableId(TEST_ID2)
                .setDescription(TEST_DESCRIPTION)
                .setTitle(TEST_TITLE)
                .setUploaderId(databaseUserManager.getByUsername(TEST_USER2).getUserId())
                .setNumDownloads(1)
                .setFilePath("C:\\test2\\not\\existing.stl");

        databasePrintableManager.persist(testPrintable2);

    }

    @After
    public void clearDatabase() {
        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }

        if (databaseUserManager.getByUsername(TEST_USER2) != null) {
            databaseUserManager.deleteByUsername(TEST_USER2);
        }
    }

    @Test
    public void testGetPrintableList() {
        PrintableReference reference = new PrintableReference(Integer.toString(TEST_ID));
        PrintableReference reference2 = new PrintableReference(Integer.toString(TEST_ID2));

        List<PrintableReference> referenceList = new ArrayList<>();
        referenceList.add(reference);
        referenceList.add(reference2);

        List<Printable> printableList = converter.getPrintableList(referenceList);

        Assert.assertTrue(printableList.size() == referenceList.size());
        Assert.assertTrue(Objects.equals(printableList.get(0).getDescription().get(), TEST_DESCRIPTION));
        Assert.assertTrue(Objects.equals(printableList.get(1).getDescription().get(), TEST_DESCRIPTION));
        Assert.assertTrue(Objects.equals(printableList.get(0).getTitle(), TEST_TITLE));
        Assert.assertTrue(Objects.equals(printableList.get(1).getTitle(), TEST_TITLE));
        Assert.assertTrue(Objects.equals(printableList.get(0).getFilePath(), "C:\\test\\not\\existing.stl"));
        Assert.assertTrue(Objects.equals(printableList.get(1).getFilePath(), "C:\\test2\\not\\existing.stl"));
        Assert.assertTrue(printableList.get(0).getUploaderId() == databaseUserManager.getByUsername(TEST_USER).getUserId());
        Assert.assertTrue(printableList.get(1).getUploaderId() == databaseUserManager.getByUsername(TEST_USER2).getUserId());
        Assert.assertTrue(printableList.get(0).getPrintableId() == TEST_ID);
        Assert.assertTrue(printableList.get(1).getPrintableId() == TEST_ID2);
        Assert.assertTrue(printableList.get(0).getNumDownloads() == 1);
        Assert.assertTrue(printableList.get(1).getNumDownloads() == 1);
    }
}
