package com.hackathon.amex.amexaccept;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.HashSet;
import java.util.Set;


public class BusinessSearch {

    private static final Set<Integer> placeTypes = new HashSet<>();
    static {placeTypes.add(Place.TYPE_ESTABLISHMENT);}
    private static final AutocompleteFilter autocompleteFilter = AutocompleteFilter.create(placeTypes);

    public static PendingResult<AutocompletePredictionBuffer> search(GoogleApiClient client, String query, LatLngBounds bounds) {
        return Places.GeoDataApi.getAutocompletePredictions(client, query, bounds, autocompleteFilter);
    }

}
