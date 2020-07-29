package org.tools.fx.library.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户表<br>
 * 虽然大多时候是自己用，但也得有个登录啊
 * 
 * INSERT INTO "USER" (USER_ID,PASSWORD,USERNAME,EXTRACODE) VALUES (
 * 1,'8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','d82494f05d6917ba02f7aaa29689ccb444bb73f20380876cb05d1f37537b7892');
 * 
 * 
 * @author Medusa
 *
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1865889145878743913L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EXTRACODE")
    private String extraCode;

    public Long getUserId() {
        return userId;
    }

    // public void setUserId(Long userId) {
    // this.userId = userId;
    // }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
