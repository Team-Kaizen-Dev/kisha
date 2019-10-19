package com.teamkaizen.kisha.datalog;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@Entity
@Data
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

}
