package com.leemo.leemo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPassUser {
    private String login;
    private String email;
    private String password;
    private String repeatPassword;
    private Integer token;
}
