package com.future.phase2.tugas.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "user")
public class User implements Serializable {

    @Id
    protected String username;
    protected String password;
    protected String name;
    protected String role;

}
