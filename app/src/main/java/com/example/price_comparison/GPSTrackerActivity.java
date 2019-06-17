package com.example.price_comparison;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Klasa zarządza lokalizacją GPS.
 */
public class GPSTrackerActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient; /**< \brief Obiekt GoogleApi */
    Location mLastLocation; /**< \brief Ostatnia uzyskana lokalizacja */

    /**
     * \brief Metoda uruchamiana przy starcie obecnego activity.
     * Metoda uruchamiana przy starcie obecnego activity.
     * @param savedInstanceState zapisane stan instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    /**
     * \brief Metoda łączy się z klientem Google Api.
     * Metoda łączy się z klientem Google Api.
     */
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    /**
     * \brief Metoda rozłącza się z klientem Google Api.
     * Metoda rozłącza się z klientem Google Api.
     */
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * \brief Metoda wywoływana, jeśli zrealizowane zostało połączenie.
     * Metoda wywoływana, jeśli zrealizowane zostało połączenie.
     * @param bundle pakiet
     */
    @Override
    public void onConnected(Bundle bundle) {
        try {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                Intent intent = new Intent();
                intent.putExtra("Longitude", mLastLocation.getLongitude());
                intent.putExtra("Latitude", mLastLocation.getLatitude());
                setResult(1,intent);
                finish();

            }
        } catch (SecurityException e) {

        }

    }

    /**
     * \brief Metoda wywoływana, jeśli połączenie zostanie zawieszone.
     * Metoda wywoływana, jeśli połączenie zostanie zawieszone.
     * @param i liczba
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * \brief Metoda wywoływana, jeśli nie uda się połączyć.
     * Metoda wywoływana, jeśli nie uda się połączyć.
     * @param connectionResult wynik połączenia
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}