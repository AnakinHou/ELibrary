package org.tools.fx.library.db;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.tools.fx.library.entity.User;

public class DBTest {

	public static void main(String[] args) {
		// 创建EntityManagerFactory
		String name = "elib";
//		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(name);
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("hibernate.connection.url","jdbc:sqlite:db/Elib2.db");
		properties.put("hibernate.dialect","org.tools.fx.library.db.SQLiteDialect5");
		properties.put("hibernate.connection.driver_class","org.sqlite.JDBC");
//		properties.put("hibernate.ejb.persistenceUnitName","elib2");
//		      
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("elib", properties);
		
		Map<String, Object> props =  entityManagerFactory.getProperties();
		for (Map.Entry<String, Object>  entry: props.entrySet()) {
			System.out.println( entry.getKey() + "      "+entry.getValue());
		}

		// 创建EntityManager
		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		https://sohu.com-v-sohu.com/20181031/13957_57d141eb/1000k/hls/331ac1d65e0023.ts
		// 开启事务
//		EntityTransaction transaction = entityManager.getTransaction();
//		transaction.begin();
//
//		// 持久化数据
//		User u = new User();
//		u.setUsername("demo2");
//		u.setPassword("123456");
//		entityManager.persist(u);
//
//		// 提交事务
//		transaction.commit();

		 User u = entityManager.find(User.class, 1L);
		 System.out.println("u.getUsername():"+u.getUsername());
		 User u2 = entityManager.find(User.class, 2L);
		 System.out.println("u2.getUsername():"+u2.getUsername());
		 
		// 关闭EntityManager
		entityManager.close();
		
		// 关闭EntityManagerFactory
		entityManagerFactory.close();

		System.out.println(" over");
	}

}
//gopherProxySet            false
//hibernate.format_sql      true
//awt.toolkit               sun.lwawt.macosx.LWCToolkit
//file.encoding.pkg         sun.io
//java.specification.version      1.8
//sun.cpu.isalist      
//sun.jnu.encoding            UTF-8
//hibernate.dialect           org.tools.fx.library.db.SQLiteDialect5
//java.class.path            /Users/hydra/Develop/workspace-sts-4-4.7.0.RELEASE/ELibrary/target/classes:/Users/hydra/.m2/repository/org/xerial/sqlite-jdbc/3.32.3/sqlite-jdbc-3.32.3.jar:/Users/hydra/.m2/repository/org/hibernate/hibernate-entitymanager/5.2.10.Final/hibernate-entitymanager-5.2.10.Final.jar:/Users/hydra/.m2/repository/org/jboss/logging/jboss-logging/3.3.0.Final/jboss-logging-3.3.0.Final.jar:/Users/hydra/.m2/repository/org/hibernate/hibernate-core/5.2.10.Final/hibernate-core-5.2.10.Final.jar:/Users/hydra/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/hydra/.m2/repository/org/jboss/jandex/2.0.3.Final/jandex-2.0.3.Final.jar:/Users/hydra/.m2/repository/com/fasterxml/classmate/1.3.0/classmate-1.3.0.jar:/Users/hydra/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:/Users/hydra/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.0.1.Final/hibernate-commons-annotations-5.0.1.Final.jar:/Users/hydra/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.1-api/1.0.0.Final/hibernate-jpa-2.1-api-1.0.0.Final.jar:/Users/hydra/.m2/repository/org/javassist/javassist/3.20.0-GA/javassist-3.20.0-GA.jar:/Users/hydra/.m2/repository/net/bytebuddy/byte-buddy/1.6.6/byte-buddy-1.6.6.jar:/Users/hydra/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.0.1.Final/jboss-transaction-api_1.2_spec-1.0.1.Final.jar:/Users/hydra/.m2/repository/com/miglayout/miglayout-javafx/5.0/miglayout-javafx-5.0.jar:/Users/hydra/.m2/repository/com/miglayout/miglayout-core/5.0/miglayout-core-5.0.jar:/Users/hydra/.m2/repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar:/Users/hydra/.m2/repository/org/slf4j/slf4j-api/1.7.12/slf4j-api-1.7.12.jar:/Users/hydra/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.12/jcl-over-slf4j-1.7.12.jar:/Users/hydra/.m2/repository/org/slf4j/slf4j-log4j12/1.7.12/slf4j-log4j12-1.7.12.jar:/Users/hydra/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar
//java.vm.vendor              Oracle Corporation 
//sun.arch.data.model         64
//java.vendor.url             http://java.oracle.com/
//user.timezone      
//os.name                       Mac OS X
//java.vm.specification.version      1.8
//user.country                  CN      
//sun.java.launcher             SUN_STANDARD
//sun.boot.library.path         /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib
//sun.java.command              org.tools.fx.library.db.DBTest
//http.nonProxyHosts            local|*.local|169.254/16|*.169.254/16
//sun.cpu.endian               little
//user.home                    /Users/hydra
//user.language                 zh
//java.specification.vendor      Oracle Corporation
//java.home                     /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre
//file.separator      /
//line.separator      
//
//java.vm.specification.vendor      Oracle Corporation
//java.specification.name           Java Platform API Specification
//hibernate.transaction.coordinator_class      class org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorBuilderImpl
//java.awt.graphicsenv              sun.awt.CGraphicsEnvironment
//sun.boot.class.path               /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/sunrsasign.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/classes
//hibernate.hbm2ddl.auto             update
//sun.management.compiler            HotSpot 64-Bit Tiered Compilers
//ftp.nonProxyHosts                  local|*.local|169.254/16|*.169.254/16
//java.runtime.version               1.8.0_181-b13
//user.name      hydra
//path.separator      :
//os.version      10.14.6
//java.endorsed.dirs      /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/endorsed
//java.runtime.name      Java(TM) SE Runtime Environment
//hibernate.connection.url      jdbc:sqlite:db/Elib.db
//hibernate.ejb.persistenceUnitName      elib
//file.encoding      UTF-8
//java.vm.name      Java HotSpot(TM) 64-Bit Server VM
//hibernate.show_sql      true
//hibernate.connection.driver_class      org.sqlite.JDBC
//java.vendor.url.bug      http://bugreport.sun.com/bugreport/
//java.io.tmpdir      /var/folders/v7/8p0nv__j1313rtb172mfr0wh0000gn/T/
//java.version      1.8.0_181
//user.dir      /Users/hydra/Develop/workspace-sts-4-4.7.0.RELEASE/ELibrary
//os.arch      x86_64
//java.vm.specification.name      Java Virtual Machine Specification
//java.awt.printerjob      sun.lwawt.macosx.CPrinterJob
//sun.os.patch.level      unknown
//hibernate.boot.CfgXmlAccessService.key      org.hibernate.boot.cfgxml.spi.LoadedConfig@2654635
//java.library.path      /Users/hydra/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.
//java.vm.info      mixed mode
//java.vendor      Oracle Corporation
//java.vm.version      25.181-b13
//hibernate.bytecode.use_reflection_optimizer      false
//java.ext.dirs      /Users/hydra/Library/Java/Extensions:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java
//sun.io.unicode.encoding      UnicodeBig
//java.class.version      52.0
//socksNonProxyHosts      local|*.local|169.254/16|*.169.254/16