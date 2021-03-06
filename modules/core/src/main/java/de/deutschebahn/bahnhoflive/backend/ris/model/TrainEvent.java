package de.deutschebahn.bahnhoflive.backend.ris.model;

import androidx.annotation.NonNull;

import java.util.Comparator;

import de.deutschebahn.bahnhoflive.R;

public enum TrainEvent {

    DEPARTURE(TrainInfo::getDeparture, "departure", true, R.string.sr_timetable_type_departure),

    ARRIVAL(TrainInfo::getArrival, "arrival", false, R.string.sr_timetable_type_arrival);


    public final boolean isDeparture;
    public final int contentDescriptionPhrase;

    TrainEvent(MovementRetriever movementRetriever, String trackKey, boolean isDeparture, int contentDescriptionPhrase) {
        this.movementRetriever = movementRetriever;
        this.contentDescriptionPhrase = contentDescriptionPhrase;
        this.comparator = new TrainInfo.Comparator(this);
        this.trackKey = trackKey;
        this.isDeparture = isDeparture;
    }

    public interface MovementRetriever {
        TrainMovementInfo getTrainMovementInfo(TrainInfo trainInfo);
    }

    public final MovementRetriever movementRetriever;

    public final Comparator<TrainInfo> comparator;

    public final String trackKey;

    public interface Provider {
        @NonNull
        TrainEvent getTrainEvent();
    }

    public final static Provider DEPARTURE_PROVIDER = new Provider() {
        @NonNull
        @Override
        public TrainEvent getTrainEvent() {
            return DEPARTURE;
        }
    };
}
