package com.kaizen.team.kishaapp.datalog.data;


import com.kaizen.team.kishaapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class HazardGenerator {

    private HazardGenerator(){}

    public static List<HazardCategory> getCategories() {
        List<HazardCategory> categories = new ArrayList<>();
        categories.add(new HazardCategory("FIRE", R.drawable.ic_fire, R.color.fire_red));
        categories.add(new HazardCategory("EARTHQUAKE", R.drawable.ic_mountain, R.color.earthquake_brown));
        categories.add(new HazardCategory("FLOOD", R.drawable.ic_water, R.color.flood_blue));
        categories.add(new HazardCategory("TYPHOON", R.drawable.ic_wind, R.color.thyphoon_blue));
        categories.add(new HazardCategory("OTHERS", R.drawable.ic_others, R.color.others_color));
        return  categories;
    }
}
