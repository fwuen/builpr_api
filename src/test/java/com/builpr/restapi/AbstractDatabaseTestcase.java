//package com.builpr.restapi;
//
//import com.builpr.restapi.utils.Connector;
//import com.builpr.restapi.utils.help_interfaces.DatabaseObject;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * parent class of every testclass that uses the database
// */
//public abstract class AbstractDatabaseTestcase {
//
//    private List<DatabaseObject> dataset;
//
//    public AbstractDatabaseTestcase() {
//        dataset = new ArrayList<DatabaseObject>();
//    }
//
//    /**
//     * sets data up before the first test is executed
//     */
//    @BeforeClass
//    public static void setUpBeforeClass() {
//        // @todo if possible, connect to the testscheme of the database
//    }
//
//    /**
//     * tears data down after the last test is executed
//     */
//    @AfterClass
//    public static void tearDownAfterClass() {
//        // @todo if possible, disconnect (if connected to it) from the testscheme of the database
//    }
//
//    /**
//     * sets data up before every test-method
//     */
//    @Before
//    public void setUp() {
//        createDataset();
//    }
//
//    /**
//     * tears data down after every test-method
//     */
//    @After
//    public void tearDown() {
//        destroyDataset();
//    }
//
//    public void addToDataset(DatabaseObject databaseObject) {
//        dataset.add(databaseObject);
//    }
//
//    /**
//     * persists dataset in the database
//     */
//    public void createDataset() {
//        for (DatabaseObject obj :
//                dataset) {
//            try {
//                Connector.getManagerByDatabaseObject(obj).persist(obj);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * removes dataset from the database
//     */
//    public void destroyDataset() {
//        for (DatabaseObject obj :
//                dataset) {
//            try {
//                Connector.getManagerByDatabaseObject(obj).remove(obj);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
