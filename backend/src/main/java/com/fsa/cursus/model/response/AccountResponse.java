package com.fsa.cursus.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AccountResponse implements Serializable {

    private Long accountId;
    private String fullName;
    private String username;
    private String role;
    private boolean status;
    private String password;
}
