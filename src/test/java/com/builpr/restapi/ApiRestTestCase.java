package com.builpr.restapi;


import com.builpr.database.db.builpr.collection.Collection;
import com.builpr.database.db.builpr.conversation.Conversation;
import com.builpr.database.db.builpr.message.Message;
import com.builpr.database.db.builpr.model.Model;
import com.builpr.database.db.builpr.rating.Rating;
import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.usercategories.UserCategories;
import com.builpr.restapi.utils.Connector;
import com.builpr.restapi.utils.help_interfaces.DatabaseObject;
import org.junit.*;

import java.util.List;

/**
 * Überklasse für alle Tests, die etwas mit der API zu tun haben
 */
public abstract class ApiRestTestCase {

    private List<DatabaseObject> dataset;

    /**
     * Baut Daten auf, noch bevor der erste Test durchgeführt wurde
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        // @todo Wenn es mal möglich ist, kommt hier der Code rein, dass sich der Test mit der TestDB verbindet
    }

    /**
     * Löscht Daten, nachdem alle Tests ausgeführt wurden
     */
    @AfterClass
    public static void tearDownAfterClass() {
       /*@todo Wenn es mal möglich ist, kommt hier der Code rein, dass der Test den Schemanamen, mit dem er sich zurücksetzt*/
    }

    /**
     * Baut vor jeder einzelnen Testmethode Daten auf
     */
    @Before
    public void setUp() {
        if(dataset != null) {
            createDataSet();
        }
    }

    /**
     * Löscht Daten nach jedem einzelnen Test
     */
    @After
    public void tearDown() {
        deleteDataSet();
    }

    /**
     * Legt Testdatensätze in der Datebank an
     */
    public void createDataSet() {
        for (DatabaseObject databaseObject :
                dataset) {
            if (databaseObject instanceof Collection) {
                Connector.getCollectionManager().persist((Collection) databaseObject);
            } else if (databaseObject instanceof Conversation) {
                Connector.getConversationManager().persist((Conversation) databaseObject);
            } else if (databaseObject instanceof Message) {
                Connector.getMessageManager().persist((Message) databaseObject);
            } else if (databaseObject instanceof Model) {
                Connector.getModelManager().persist((Model) databaseObject);
            } else if (databaseObject instanceof Rating) {
                Connector.getRatingManager().persist((Rating) databaseObject);
            } else if (databaseObject instanceof User) {
                Connector.getUserManager().persist((User) databaseObject);
            } else if (databaseObject instanceof UserCategories) {
                Connector.getUserCateogriesManager().persist((UserCategories) databaseObject);
            } else {
                return;
            }
        }
    }

    /**
     * Löscht alle Datensätze, die in {@see createDataSet} angelegt wurden
     */
    public void deleteDataSet() {
        for (DatabaseObject databaseObject :
                dataset) {
            if (databaseObject instanceof Collection) {
                Connector.getCollectionManager().remove((Collection) databaseObject);
            } else if (databaseObject instanceof Conversation) {
                Connector.getConversationManager().remove((Conversation) databaseObject);
            } else if (databaseObject instanceof Message) {
                Connector.getMessageManager().remove((Message) databaseObject);
            } else if (databaseObject instanceof Model) {
                Connector.getModelManager().remove((Model) databaseObject);
            } else if (databaseObject instanceof Rating) {
                Connector.getRatingManager().remove((Rating) databaseObject);
            } else if (databaseObject instanceof User) {
                Connector.getUserManager().remove((User) databaseObject);
            } else if (databaseObject instanceof UserCategories) {
                Connector.getUserCateogriesManager().remove((UserCategories) databaseObject);
            } else {
                return;
            }
        }
    }

    /**
     * adds an entry to the dataset
     *
     * @param databaseObject data which should be persisted in the database
     */
    public void addToDataset(DatabaseObject databaseObject) throws Exception {
        dataset.add(databaseObject);
    }

}
