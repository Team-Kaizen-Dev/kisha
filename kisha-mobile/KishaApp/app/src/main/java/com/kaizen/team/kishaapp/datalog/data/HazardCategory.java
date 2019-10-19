package com.kaizen.team.kishaapp.datalog.data;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */

@Setter
@Getter
public class HazardCategory {
    private String name;
    private int imageId;
    private int colorId;

    public HazardCategory(String name, int imageId, int colorId) {
        this.name = name;
        this.imageId = imageId;
        this.colorId = colorId;
    }
}
