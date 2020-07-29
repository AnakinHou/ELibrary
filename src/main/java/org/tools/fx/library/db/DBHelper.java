package org.tools.fx.library.db;

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
            entityManagerFactory = Persistence.createEntityManagerFactory("elib");
        }
    }

}
