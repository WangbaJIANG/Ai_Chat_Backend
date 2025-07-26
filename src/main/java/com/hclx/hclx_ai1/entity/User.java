package com.hclx.hclx_ai1.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author springBoot-Learning
 * @since 2024-12-10
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String password;

    private String nickname;

    private String gender;

    private String username;

    private int role;


    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", password=" + password +
            ", nickname=" + nickname +
            ", gender=" + gender +
            ", username=" + username +
        "}";
    }
}
