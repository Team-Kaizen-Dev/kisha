package com.teamkaizen.kisha.datalog;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class DataLog implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    @Version
    private int version;
    @CreatedDate
    private long dateCreated;

    private long userId;
    private String fullName;
    private String contactNumber;
    private String address;

    private double lat;
    private double lng;
    private int typeOfDisaster;
    private String message;

    private long timeLogged;

}
