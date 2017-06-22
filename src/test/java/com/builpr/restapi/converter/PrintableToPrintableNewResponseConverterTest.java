package com.builpr.restapi.converter;

import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryImpl;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;
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
 * Test for PrintableToPrintableNewResponseConverter
 */
public class PrintableToPrintableNewResponseConverterTest {
    private List<Category> CATEGORY_LIST = new ArrayList<>();
    private DatabaseUserManager databaseUserManager;
    private DatabasePrintableManager databasePrintableManager;

    private static final String TEST_USER = "convertertester";
    private static final int TEST_ID = 14789632;
    private static final String TEST_DESCRIPTION = "testdescription";
    private static final String TEST_TITLE = "testtitle";

    public PrintableToPrintableNewResponseConverterTest() {
        databaseUserManager = new DatabaseUserManager();
        databasePrintableManager = new DatabasePrintableManager();

        Category category = new CategoryImpl()
                .setCategoryId(1)
                .setCategoryName("newCat");
        Category category1 = new CategoryImpl()
                .setCategoryId(2)
                .setCategoryName("newCat2");
        CATEGORY_LIST.add(category);
        CATEGORY_LIST.add(category1);
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
    }

    @After
    public void clearDatabase() {
        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
    }

    @Test
    public void testFrom() {
        Printable printable = databasePrintableManager.getPrintableById(TEST_ID);
        PrintableNewResponse response = PrintableToPrintableNewResponseConverter.from(printable, CATEGORY_LIST);

        Assert.assertNotNull(response);
        Assert.assertTrue(Objects.equals(response.getDescription(), TEST_DESCRIPTION));
        Assert.assertTrue(Objects.equals(response.getTitle(), TEST_TITLE));
        Assert.assertTrue(response.getCategories().size() == CATEGORY_LIST.size());
        Assert.assertTrue(response.getOwnerID() == printable.getUploaderId());
        Assert.assertTrue(response.getPrintableID() == TEST_ID);
        Assert.assertTrue(response.getUploadDate() == printable.getUploadTime());
    }
}
