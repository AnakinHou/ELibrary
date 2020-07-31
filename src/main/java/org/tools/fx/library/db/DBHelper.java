package org.tools.fx.library.db;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBHelper {

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManagerFactory getEMFactory() {
        // if (entityManagerFactory == null) {
        // entityManagerFactory = Persistence.createEntityManagerFactory("elib");
        // }
        initDB();
        return entityManagerFactory;
    }

    public static EntityManager getEM() {
        return getEMFactory().createEntityManager();
    }

    public static void initDB() {
        if (entityManagerFactory == null) {
//            System.out.println("==============初始化 db  factory");
            Map<String, String> properties = new HashMap<String, String>();
//            properties.put("hibernate.connection.url","jdbc:sqlite:db/Elib.db");
            properties.put("hibernate.connection.url","jdbc:sqlite::resource:db/Elib.db");
            properties.put("hibernate.dialect","org.tools.fx.library.db.SQLiteDialect5");
            properties.put("hibernate.connection.driver_class","org.sqlite.JDBC");
//          properties.put("hibernate.ejb.persistenceUnitName","elib2");
//                
              entityManagerFactory = Persistence.createEntityManagerFactory("elib", properties);
//            entityManagerFactory = Persistence.createEntityManagerFactory("elib");
        }
    }

}
