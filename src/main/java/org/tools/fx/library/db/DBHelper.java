package org.tools.fx.library.db;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBHelper {

    private static EntityManagerFactory entityManagerFactory;

    private static String getRunPath() {
        /**
         * 方法一：获取当前可执行jar包所在目录
         */
        String filePath = System.getProperty("java.class.path");
        String pathSplit = System.getProperty("path.separator");// 得到当前操作系统的分隔符，windows下是";",linux下是":"
        /**
         * 若没有其他依赖，则filePath的结果应当是该可运行jar包的绝对路径， 此时我们只需要经过字符串解析，便可得到jar所在目录
         */
        if (filePath.contains(pathSplit)) {
            filePath = filePath.substring(0, filePath.indexOf(pathSplit));
        } else if (filePath.endsWith(".jar")) {// 截取路径中的jar包名,可执行jar包运行的结果里包含".jar"
            filePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
        }
        return filePath;
    }

    private static String getJarPath() {
        /**
         * 方法二：获取当前可执行jar包所在目录
         */
        String filePath = "";
        String pathSplit = System.getProperty("file.separator");
        URL url = DBHelper.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            filePath = URLDecoder.decode(url.getPath(), "utf-8");// 转化为utf-8编码，支持中文
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar")) {// 可执行jar包运行的结果里包含".jar"
            // 获取jar包所在目录
            filePath = filePath.substring(0, filePath.lastIndexOf(pathSplit) + 1);
        }
//        System.out.println("=========filePath:" + filePath);
//        File file = new File(filePath);
//        filePath = file.getAbsolutePath();// 得到windows下的正确路径
        return filePath;
    }

    private static String getExePath() {
        return new File("").getAbsolutePath();
    }


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
            // System.out.println("==============初始化 db factory");
//            System.out.println("======== getRunPath():" + getRunPath());
//            System.out.println("======== getJarPath():" + getJarPath());
//            System.out.println("======== getExePath():" + getExePath());
//            System.out.println("jdbc:sqlite:" + getJarPath() + "/db/Elib.db");
            Map<String, String> properties = new HashMap<String, String>();
            // properties.put("hibernate.connection.url","jdbc:sqlite:db/Elib.db");
            // properties.put("hibernate.connection.url","jdbc:sqlite::resource:db/Elib.db");
             properties.put("hibernate.connection.url","jdbc:sqlite:"+getJarPath()+"/db/Elib.db");
            properties.put("hibernate.dialect", "org.tools.fx.library.db.SQLiteDialect5");
            properties.put("hibernate.connection.driver_class", "org.sqlite.JDBC");
            // properties.put("hibernate.ejb.persistenceUnitName","elib2");
            //
            entityManagerFactory = Persistence.createEntityManagerFactory("elib", properties);
            // entityManagerFactory = Persistence.createEntityManagerFactory("elib");
        }
    }

}
