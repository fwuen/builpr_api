package com.builpr.database.service;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.printable.PrintableManager;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import org.junit.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DatabasePrintableManagerTest
 */
public class DatabasePrintableManagerTest {
    private DatabasePrintableManager databasePrintableManager;
    private DatabaseUserManager databaseUserManager;
    private PrintableManager printableManager;

    private static final String TEST_USER = "printable_test_user";
    private static final int TEST_ID = 666666666;
    private static final int INVALID_ID = 1234567899;

    private static final String TEST_TITLE = "test title";
    private static final String TEST_DESCRIPTION = "test description";

    private Printable testPrintable;

    public DatabasePrintableManagerTest() {
        databaseUserManager = new DatabaseUserManager();
        databasePrintableManager = new DatabasePrintableManager();
        printableManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(PrintableManager.class);

    }

    @Before
    public void setTestUp() {
        User testUser = new UserImpl()
                .setUsername(TEST_USER)
                .setEmail("markus.goller97@googlemail.com")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()))
                .setFirstname("Donald")
                .setLastname("Trump")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowEmail(false);

        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
        databaseUserManager.persist(testUser);

        Optional<Printable> list = printableManager.stream().filter(Printable.PRINTABLE_ID.equal(TEST_ID)).findAny();
        list.ifPresent(printableManager::remove);

        testPrintable = new PrintableImpl()
                .setPrintableId(TEST_ID)
                .setDescription(TEST_DESCRIPTION)
                .setTitle(TEST_TITLE)
                .setUploaderId(databaseUserManager.getByUsername(TEST_USER).getUserId())
                .setNumDownloads(1)
                .setFilePath("C:\\test\\not\\existing.stl");

        printableManager.persist(testPrintable);
    }

    @After
    public void clearDatabase() {
        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
    }

    @Test
    public void testGetPrintablesForUser() {
        List<Printable> printablesForUser = databasePrintableManager.getPrintablesForUser(databaseUserManager.getByUsername(TEST_USER).getUserId());

        Assert.assertTrue(!printablesForUser.isEmpty());
    }

    @Test
    public void testGetPrintablesForUserNonExistent() {
        List<Printable> printablesForUser = databasePrintableManager.getPrintablesForUser(INVALID_ID);

        Assert.assertTrue(printablesForUser.isEmpty());
    }

    @Test
    public void testGetPrintableById() {
        Printable printable = databasePrintableManager.getPrintableById(TEST_ID);

        Assert.assertNotNull(printable);
    }

    @Test
    public void testGetPrintableByIdNonExistent() {
        Printable printable = databasePrintableManager.getPrintableById(INVALID_ID);

        Assert.assertNull(printable);
    }

    @Test
    public void testPersist() {
        Optional<Printable> list = printableManager.stream().filter(Printable.PRINTABLE_ID.equal(TEST_ID)).findAny();
        list.ifPresent(printableManager::remove);
        databasePrintableManager.persist(testPrintable);

        list = printableManager.stream().filter(Printable.PRINTABLE_ID.equal(TEST_ID)).findAny();
        Assert.assertTrue(list.isPresent());
    }

    @Test
    public void testUpdate() {
        testPrintable.setTitle("printable title changed");
        testPrintable.setDescription("printable description changed");
        databasePrintableManager.update(testPrintable);

        Assert.assertTrue(!Objects.equals(testPrintable.getTitle(), TEST_TITLE));
        Assert.assertTrue(testPrintable.getDescription().isPresent());
        Assert.assertTrue(!Objects.equals(testPrintable.getDescription().get(), TEST_DESCRIPTION));
    }

    @Test
    public void testDeletePrintable() {
        databasePrintableManager.deletePrintable(TEST_ID);

        Assert.assertNull(databasePrintableManager.getPrintableById(TEST_ID));
    }

    @Test
    public void testUpdateDownloads() {
        databasePrintableManager.updateDownloads(TEST_ID);

        Printable printable = databasePrintableManager.getPrintableById(TEST_ID);

        Assert.assertTrue(printable.getNumDownloads() == (testPrintable.getNumDownloads() + 1));
    }

    @Test
    public void testIsPresent() {
        Assert.assertTrue(databasePrintableManager.isPresent(TEST_ID));

        Assert.assertFalse(databasePrintableManager.isPresent(0));
    }

    @Test
    public void testGetAllPrintables() {
        List<Printable> printables = printableManager.stream().collect(Collectors.toList());
        int size = printables.size();

        databasePrintableManager.deletePrintable(TEST_ID);

        List<Printable> printableList = databasePrintableManager.getAllPrintables();
        int size2 = printableList.size();

        Assert.assertTrue(size == size2 + 1);
    }
}
