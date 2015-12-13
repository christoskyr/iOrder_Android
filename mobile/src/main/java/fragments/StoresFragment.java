package fragments;

import activities.Order_Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import asynctasks.StoresAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import interfaces.OnStoresTaskCompletedInterface;
import java.util.ArrayList;
import java.util.HashMap;

import models.Store;
import mycompany.iorder.R;

public class StoresFragment extends Fragment implements LocationListener, OnStoresTaskCompletedInterface {

    // Keys for storing activity state in the Bundle.
    protected final static String LOCATION_KEY = "location-key";
    private GoogleMap googleMap;
    private FrameLayout mapContainer;
    private SupportMapFragment mapFragment;
    private ArrayList<Store> stores;
    private StoresAsyncTask storesAsyncTask;
    private HashMap<LatLng, String[]> markersMap;

    public StoresFragment()
    {
    }

    private void SetUpShops()
    {
        markersMap = new HashMap<LatLng, String[]>();
        for (final Store store: stores) {

            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(store.getLatitude()), Double.parseDouble(store.getLongitude()))).title(store.getName()).snippet("Click here to make an order");

            googleMap.addMarker(markerOptions);
            String[] storeValues = {store.getId(), store.getName()};
            markersMap.put(new LatLng(Double.parseDouble(store.getLatitude()), Double.parseDouble(store.getLongitude())), storeValues);
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getActivity(), Order_Activity.class);
                    for (HashMap.Entry<LatLng,String[]> entry : markersMap.entrySet()) {
                        Log.d("hash position:" , entry.getKey().toString());
                        Log.d("marker position:" , marker.getPosition().toString());
                        Log.d("hashvalues  n:" , entry.getValue()[0] + "," + entry.getValue()[1]) ;
                        if (entry.getKey().equals(marker.getPosition())){
                            intent.putExtra("storeId", entry.getValue()[0]);
                            intent.putExtra("storeName", entry.getValue()[1]);
                        }
                    }
                    startActivity(intent);

                }
            });
        }

    }

    private void setUpMap()
    {
        googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null){
            LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,  15));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    private void setUpMapIfNeeded()
    {
        if (googleMap == null)
        {
            googleMap = mapFragment.getMap();
            if (googleMap != null)
            {
                if (stores == null)
                {
                    storesAsyncTask = new StoresAsyncTask();
                    storesAsyncTask.storesDelegate = this;
                    storesAsyncTask.execute(new Context[0]);
                }
                setUpMap();
            }
        }
    }
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setRetainInstance(true);
        ((LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE)).requestLocationUpdates(LocationManager.GPS_PROVIDER,100,0,this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewgroup, Bundle bundle)
    {
        View v = inflater.inflate(R.layout.fragment_stores, viewgroup, false);
        mapContainer = (FrameLayout)v.findViewById(R.id.mapStores);
        return v;
    }

    public void onLocationChanged(Location location)
    {
        googleMap.clear();
        MarkerOptions markeroptions = new MarkerOptions();
        markeroptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
        markeroptions.title("my position");
        googleMap.addMarker(markeroptions);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16F));
    }

    public void onProviderDisabled(String s)
    {
    }

    public void onProviderEnabled(String s)
    {
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if (mapFragment != null)
        {
            setUpMapIfNeeded();
        }
    }
    @Override
    public void onStart()
    {
        super.onStart();
        mapFragment = null;
        mapFragment = new SupportMapFragment();
        FragmentTransaction fragmenttransaction = getChildFragmentManager().beginTransaction();
        fragmenttransaction.add(R.id.mapStores, mapFragment);
        fragmenttransaction.commit();
    }

    public void onStatusChanged(String s, int i, Bundle bundle)
    {
    }

    public void onStoresTaskCompletedInterface(ArrayList arraylist)
    {
        if (arraylist != null)
        {
            stores = arraylist;
            SetUpShops();
        }
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();

        try {
            Fragment fragment = (getFragmentManager()
                    .findFragmentById(R.id.map));
            FragmentTransaction ft = getActivity().getSupportFragmentManager()
                    .beginTransaction();
            ft.remove(fragment);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}