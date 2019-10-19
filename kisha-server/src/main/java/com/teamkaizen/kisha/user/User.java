package com.teamkaizen.kisha.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Version
    private int version;
    private String username;
    private String password;
    private String fullName;
    private String contactNumber;
    private String address;
    private double lat;
    private double lng;
}
