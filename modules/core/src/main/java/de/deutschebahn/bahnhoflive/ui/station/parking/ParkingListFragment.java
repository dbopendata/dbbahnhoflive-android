package de.deutschebahn.bahnhoflive.ui.station.parking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import de.deutschebahn.bahnhoflive.R;
import de.deutschebahn.bahnhoflive.analytics.TrackingManager;
import de.deutschebahn.bahnhoflive.backend.bahnpark.model.BahnparkSite;
import de.deutschebahn.bahnhoflive.tutorial.TutorialManager;
import de.deutschebahn.bahnhoflive.tutorial.TutorialView;
import de.deutschebahn.bahnhoflive.ui.RecyclerFragment;
import de.deutschebahn.bahnhoflive.ui.map.Content;
import de.deutschebahn.bahnhoflive.ui.map.InitialPoiManager;
import de.deutschebahn.bahnhoflive.ui.map.MapPresetProvider;
import de.deutschebahn.bahnhoflive.ui.map.content.rimap.RimapFilter;
import de.deutschebahn.bahnhoflive.ui.station.HistoryFragment;
import de.deutschebahn.bahnhoflive.ui.station.StationViewModel;

public class ParkingListFragment extends RecyclerFragment<BahnparkSiteAdapter>
        implements MapPresetProvider {

    public static final String TAG = ParkingListFragment.class.getSimpleName();

    private TutorialView mTutorialView;
    private StationViewModel stationViewModel;

    public ParkingListFragment() {
        super(R.layout.fragment_recycler_linear);
        setTitle(R.string.stationinfo_parkings);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setAdapter(new BahnparkSiteAdapter(getChildFragmentManager()));

        stationViewModel = ViewModelProviders.of(getActivity()).get(StationViewModel.class);
        stationViewModel.getParkingsResource().getData().observe(this, new Observer<List<BahnparkSite>>() {
            @Override
            public void onChanged(@Nullable List<BahnparkSite> bahnparkSites) {
                setData(bahnparkSites);
            }
        });
        stationViewModel.getSelectedServiceContentType().observe(this, s -> {
            if (s != null) {
                HistoryFragment.parentOf(this).pop();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        mTutorialView = getActivity().findViewById(R.id.tab_tutorial_view);
        TutorialManager.getInstance(getActivity()).showTutorialIfNecessary(mTutorialView, "d1_parking");

        TrackingManager.fromActivity(getActivity()).track(TrackingManager.TYPE_STATE, TrackingManager.Screen.D1, TrackingManager.Category.PARKPLAETZE);

        stationViewModel.setTopInfoFragmentTag(TAG);
    }

    @Override
    public void onStop() {
        if (TAG.equals(stationViewModel.getTopInfoFragmentTag())) {
            stationViewModel.setTopInfoFragmentTag(null);
        }

        super.onStop();
    }

    public void setData(List<BahnparkSite> sites) {
        getAdapter().setData(sites);
    }

    @Override
    public void onDetach() {
        TutorialManager.getInstance(getActivity()).markTutorialAsIgnored(mTutorialView);
        super.onDetach();
    }


    public static ParkingListFragment create() {
        return new ParkingListFragment();
    }

    @Override
    public boolean prepareMapIntent(Intent intent) {
        final BahnparkSiteAdapter adapter = getAdapter();
        final BahnparkSite bahnparkSite = adapter.getSelectedItem();

        InitialPoiManager.putInitialPoi(intent, Content.Source.BAHNPARK, bahnparkSite);
        RimapFilter.putPreset(intent, RimapFilter.PRESET_PARKING);

        return true;
    }

}
