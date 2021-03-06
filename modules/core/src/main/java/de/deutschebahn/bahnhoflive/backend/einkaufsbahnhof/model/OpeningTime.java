package de.deutschebahn.bahnhoflive.backend.einkaufsbahnhof.model;

import com.google.gson.annotations.SerializedName;

public class OpeningTime {

    @SerializedName("day-range")
    public final String dayRange;

    @SerializedName("time-from")
    public final String timeFrom;

    @SerializedName("time-to")
    public final String timeTo;

    public OpeningTime(String dayRange, String timeFrom, String timeTo) {
        this.dayRange = dayRange;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }
}
