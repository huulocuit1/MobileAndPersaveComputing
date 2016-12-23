package com.example.hl_th.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import adapter.CustomExpandListView;
import adapter.ItemListViewAdapter;
import adapter.ItemSpinnerAdapter;
import entities.AdressDetail;
import entities.Atm;
import entities.BaseItem;
import entities.DistanceAB;
import entities.ItemNavigation;
import server.AsynAtmByType;
import server.AsyncListener;
import server.DataManager;
import utils.GetDirection;
import utils.LocationUtils;
import utils.MyLocation;
import utils.common;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private ListView menulist;
    private ItemListViewAdapter itemAdapter;
    private ArrayList<ItemNavigation> itemNavigationArrayList;
    private LocationUtils locationUtils;
    private GoogleMap mMap;
    private ProgressDialog progressDialog;
    private MyEventListener myEventListener;

    private ExpandableListView expandableListView;
    private CustomExpandListView customExpandListView;
    private ArrayList<String> header_expand = new ArrayList<>();

    private HashMap<String, ArrayList<BaseItem>> item_expand_list = new HashMap<>();

    private DrawerLayout drawer;
    private MyLocation myLocation;
    private Dialog type_vehicles;
    private Dialog type_maps;
    private Dialog type_search_atm;
    private Dialog type_list_atm;
    private Dialog type_setting;
    Toolbar toolbar;
    Toolbar toolbar2;
    NavigationView navigationView;
    ArrayList<BaseItem> arrayDistrict = new ArrayList<>();
    ItemSpinnerAdapter spinnerItem;
    Spinner spinner_city;
    Spinner spinner_bank;
    Spinner spinner_km;
    ArrayList<String> listNameSpinner;

    ArrayList<BaseItem> arrBank = new ArrayList<>();
    ItemSpinnerAdapter spinnerItem1;
    ArrayList<String> listNameSpinner1 = new ArrayList<>();

    private AdressDetail adressDetail;

    private ArrayList<Atm> atmArrayList = new ArrayList<>();
    private ArrayList<Atm> atmNearestYou = new ArrayList<>();
    private ArrayList<Atm> atmByDistrictType = new ArrayList<>();
    ArrayList<Atm> atmArray = new ArrayList<>();
    public static int changeListExpand = 1;
    private int distance = 1;

    private TextView id_destination;
    private TextView id_time;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        initView();
        if (DataManager.getInstance().getAtmDetail() != null) {
            atmArrayList = DataManager.getInstance().getAtmDetail();
        }
        customExpandListView = new CustomExpandListView(this, header_expand, item_expand_list);
        expandableListView.setAdapter(customExpandListView);
        common.initProgress(this);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.color.colorToolbar);
        toolbar2.setBackgroundResource(R.color.colorToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        itemAdapter = new ItemListViewAdapter(this, R.layout.layout_listview_item, itemNavigationArrayList);
        menulist.setAdapter(itemAdapter);
        navigationView.setNavigationItemSelectedListener(this);
        handlerListView();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_fragment_map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Start", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Resume", Toast.LENGTH_LONG).show();
    }

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        menulist = (ListView) findViewById(R.id.menuList);
        locationUtils = new LocationUtils(this);
        locationUtils.checkInternetConnection();
        spinner_km = (Spinner) findViewById(R.id.id_km);
        id_destination = (TextView) findViewById(R.id.id_destination);
        id_time = (TextView) findViewById(R.id.id_time);

        expandableListView = (ExpandableListView) findViewById(R.id.lvExp);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        initArrayItemNavigation();
        adressDetail = new AdressDetail("Hcm");
        String x = adressDetail.getCurrentCity();
        initSpinnerItem();

        String[] spinner_km_item = {"1 km", "2 km", "3 km", "4 km", "5 km", "6 km", "7 km", "8 km"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner_km_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_km.setAdapter(arrayAdapter);
        spinner_km.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) spinner_km.getSelectedView();
                String convert = textView.getText().charAt(0) + "";
                distance = Integer.parseInt(convert);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    boolean expand = false;

    private void initSpinnerItem() {
        int icon_atm = R.drawable.icon_atm_locator;
        int icon_city = R.drawable.city_ico;
        listNameSpinner = new ArrayList<>();
        listNameSpinner1 = new ArrayList<>();

        arrayDistrict = new ArrayList<>();
        arrBank = new ArrayList<>();

        int numberDistrict = DataManager.getInstance().getDistrictDetails().size();
        for (int i = 0; i < numberDistrict; i++) {
            arrayDistrict.add(new BaseItem(DataManager.getInstance().getDistrictDetails().get(i).name, icon_city));
        }
        int numberBanks = DataManager.getInstance().getBankDetail().size();
        for (int i = 0; i < numberBanks; i++) {
            arrBank.add(new BaseItem(DataManager.getInstance().getBankDetail().get(i).name, icon_atm));
        }

    }

    public void expandAllAtm(View v) {
        prepareDataForExpand();
        customExpandListView.notifyDataSetInvalidated();
        int x = changeListExpand;
        if (expand == false) {
            expandableListView.setVisibility(View.VISIBLE);
            expandableListView.expandGroup(0);
            expandableListView.smoothScrollToPositionFromTop(1, 5, 10000);
            expand = true;
            if (item_expand_list != null) {
                if (changeListExpand == 1) {
                    atmArray = atmArrayList;
                } else if (changeListExpand == 2) {
                    atmArray = atmNearestYou;
                } else if (changeListExpand == 3) {
                    atmArray = atmByDistrictType;
                }
                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                        expandableListView.setVisibility(View.GONE);
                        LatLng latLng = new LatLng(atmArray.get(i1).getLat(), atmArray.get(i1).getLng());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng)             // Sets the center of the map to location user
                                .zoom(15)                   // Sets the zoom
                                .bearing(90)                // Sets the orientation of the camera to east
                                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        final MarkerOptions option = new MarkerOptions();
                        option.title(atmArray.get(i1).getBanktype());
                        option.snippet(atmArray.get(i1).getAddress());
                        option.position(new LatLng(atmArray.get(i1).getLat(), atmArray.get(i1).getLng()));
                        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        final Marker currentMarker = mMap.addMarker(option);
                        currentMarker.showInfoWindow();
                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                Toast.makeText(getBaseContext(), option.getTitle(), Toast.LENGTH_LONG).show();
                            }
                        });
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                LatLng latLng1 = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                                LatLng latLng2 = new LatLng(myLocation.getMyLocation().getLatitude(), myLocation.getMyLocation().getLongitude());
                                new GetDirection(mMap, MainActivity.this, latLng1, latLng2).execute();
                                String dis = "%.2f";
                                String ti = "%.1f";

                                float distance = (float) (DistanceAB.distance(latLng1, latLng2) / 1000);
                                float time = (distance / 40);
                                if(time<0.1) {
                                    time = time * 60;
                                    ti = String.format(ti, time);
                                    id_time.setText("" + ti + " m");
                                }else{
                                    ti = String.format(ti, time);
                                    id_time.setText("" + ti + " h");
                                }

                                dis = String.format(dis, distance);

                                id_destination.setText("" + dis + " km");
                                return false;
                            }
                        });
                        return false;
                    }
                });
            }
        } else {
            expandableListView.collapseGroup(0);
            expandableListView.setVisibility(View.GONE);
            expand = false;
        }
    }


    public void showATMnearestYou(View v) throws IOException {
        boolean checkLocationOfATM;
        if (expand == true) {
            expand = false;
        }
        if (atmNearestYou.size() > 0) {
            atmNearestYou.clear();
        }
        if (atmArrayList != null || atmArrayList.size() != 0) {
            for (int i = 0; i < atmArrayList.size(); i++) {
                checkLocationOfATM = atmNearYou(new LatLng(atmArrayList.get(i).getLat(), atmArrayList.get(i).getLng()), distance * 1000);
                if (checkLocationOfATM == true) {
                    atmNearestYou.add(atmArrayList.get(i));
                }
            }
        }
        if (atmNearestYou.size() == 0) {

            Toast.makeText(this, "Have no Atm which near your location", Toast.LENGTH_LONG).show();
        }
        changeListExpand = 2;
        if (atmNearestYou.size() > 0) {
            mMap.clear();
            myLocation.showMyLocation();
            showListMarker(atmNearestYou);

        }

    }

    private void showListMarker(final ArrayList<Atm> atms) throws IOException {
        mMap.clear();
        myLocation.showMyLocation();
        for (Atm atm : atms) {
            final LatLng latLng = new LatLng(atm.getLat(), atm.getLng());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)             // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            final MarkerOptions option = new MarkerOptions();
            option.title(atm.getBanktype());
            option.snippet(atm.getAddress());
            option.position(new LatLng(atm.getLat(), atm.getLng()));
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            final Marker currentMarker = mMap.addMarker(option);
            currentMarker.showInfoWindow();
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(getBaseContext(), option.getTitle(), Toast.LENGTH_LONG).show();
                }
            });
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    LatLng latLng1 = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                    LatLng latLng2 = new LatLng(myLocation.getMyLocation().getLatitude(), myLocation.getMyLocation().getLongitude());
                    new GetDirection(mMap, MainActivity.this, latLng1, latLng2).execute();
                    String dis = "%.2f";
                    String ti = "%.1f";

                    float distance = (float) (DistanceAB.distance(latLng1, latLng2) / 1000);
                    float time = (distance / 40);
                    if(time<0.1) {
                        time = time * 60;
                        ti = String.format(ti, time);
                        id_time.setText("" + ti + " m");
                    }else{
                        ti = String.format(ti, time);
                        id_time.setText("" + ti + " h");
                    }

                    dis = String.format(dis, distance);

                    id_destination.setText("" + dis + " km");
                    return false;
                }
            });
        }
    }

    public boolean atmNearYou(LatLng latLng, int maxdistance) {

        LatLng youtLocation = new LatLng(myLocation.getMyLocation().getLatitude(), myLocation.getMyLocation().getLongitude());
        return DistanceAB.distance(latLng, youtLocation) > maxdistance ? false : true;

    }


    private void prepareDataForExpand() {
        item_expand_list.clear();
        header_expand.clear();
        Log.d("MainActivity", "" + item_expand_list.values().size());
//        item_expand_list.values().size();
        ArrayList<Atm> initListItem = new ArrayList<>();
        ArrayList<BaseItem> arrayList = new ArrayList<>();
        header_expand.add(new String(""));
        switch (changeListExpand) {
            case 1:
                initListItem = atmArrayList;
                break;
            case 2:
                initListItem = atmNearestYou;
                break;
            case 3:
                initListItem = atmByDistrictType;
                break;

        }
        for (int i = 0; i < initListItem.size(); i++) {
            arrayList.add(new BaseItem(initListItem.get(i).getAddress(), R.drawable.icon_atm_locator));
        }
        item_expand_list.put(header_expand.get(0), arrayList);


    }


    private void initArrayItemNavigation() {
        itemNavigationArrayList = new ArrayList<ItemNavigation>();
        int images[] = {R.drawable.ic_type_vehecle, R.drawable.ic_typemap, R.drawable.ic_list_atm, R.drawable.search_press, R.drawable.camera, R.drawable.setting_icon};
        String[] names = {"Type vehecles", "Type map", "List Atm", "Search", "Capture", "Setting"};
        for (int i = 0; i < images.length; i++) {
            itemNavigationArrayList.add(new ItemNavigation(images[i], names[i]));
        }


    }

    private Dialog showDialogWithType(Dialog dialog, int layout, String name) {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        if (name != null || !name.equals("")) {
            dialog.setTitle(name);
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        return dialog;

    }

    private void cancelEventButton(final Dialog dialog, View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });
    }


    private void handlerListView() {
        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        type_vehicles = showDialogWithType(type_vehicles, R.layout.dialog_type_search, "");
                        Button btn_driving = (Button) type_vehicles.findViewById(R.id.btn_driving);
                        btn_driving.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                type_vehicles.hide();
                            }
                        });
                        type_vehicles.show();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case 1:
                        type_maps = showDialogWithType(type_maps, R.layout.dialog_type_maps, "");
                        type_maps.show();
                        Button btn_normal = (Button) type_maps.findViewById(R.id.btn_normal);
                        Button btn_hybred = (Button) type_maps.findViewById(R.id.btn_hybred);
                        Button btn_satellite = (Button) type_maps.findViewById(R.id.btn_satellite);
                        Button btn_terrain = (Button) type_maps.findViewById(R.id.btn_terrain);
                        Button btn_cancel = (Button) type_maps.findViewById(R.id.id_btn_cancel_type_map);

                        cancelEventButton(type_maps, btn_cancel);

                        btn_normal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                drawer.closeDrawer(GravityCompat.START);
                                type_maps.dismiss();

                            }
                        });
                        btn_hybred.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                drawer.closeDrawer(GravityCompat.START);
                                type_maps.dismiss();

                            }
                        });
                        btn_satellite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                drawer.closeDrawer(GravityCompat.START);
                                type_maps.dismiss();

                            }
                        });
                        btn_terrain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                drawer.closeDrawer(GravityCompat.START);
                                type_maps.dismiss();
                            }
                        });

                        break;
                    case 2: {
                        type_list_atm = showDialogWithType(type_list_atm, R.layout.dialog_search_atm, "");
                        spinner_city = (Spinner) type_list_atm.findViewById(R.id.id_city);
                        spinner_bank = (Spinner) type_list_atm.findViewById(R.id.id_bank);
                        spinnerItem = new ItemSpinnerAdapter(getBaseContext(), R.layout.layout_spinner_item, arrayDistrict);
                        spinner_city.setAdapter(spinnerItem);


                        spinnerItem1 = new ItemSpinnerAdapter(getBaseContext(), R.layout.layout_spinner_item, arrBank);
                        spinner_bank.setAdapter(spinnerItem1);
                        type_list_atm.show();
                        Button btn_search = (Button) type_list_atm.findViewById(R.id.id_list_atm__search);
                        btn_search.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int item1 = spinner_city.getSelectedItemPosition();
                                int item2 = spinner_bank.getSelectedItemPosition();
                                String district = "'" + arrayDistrict.get(item1).getName() + "'";
                                String bank = "'" + arrBank.get(item2).getName() + "'";
                                AsynAtmByType asyncAtm = new AsynAtmByType(new AsyncListener() {
                                    @Override
                                    public void onAsyncComplete() throws IOException {

                                        Toast.makeText(MainActivity.this, "" + DataManager.getInstance().getAtmByType().size(), Toast.LENGTH_LONG).show();
                                        atmByDistrictType = (ArrayList<Atm>) DataManager.getInstance().getAtmByType();
                                        changeListExpand = 3;
                                        expand = true;
                                        showListMarker(atmByDistrictType);
                                    }
                                });
                                String url = String.format(getString(R.string.url_get_atms_by_district_banktype), district, "&", bank);
                                asyncAtm.execute(String.format(getString(R.string.url_get_atms_by_district_banktype), district, "&", bank));

                                type_list_atm.dismiss();
                                drawer.closeDrawer(GravityCompat.START);

                            }
                        });
                        Button btn_cancel_list = (Button) type_list_atm.findViewById(R.id.id_btn_cancel_searching_by_info);
                        cancelEventButton(type_list_atm, btn_cancel_list);
                        break;
                    }
                    case 3: {
                        type_search_atm = showDialogWithType(type_search_atm, R.layout.dialog_show_atm, "");
                        type_search_atm.show();
                        break;
                    }
                    case 5: {
                        type_setting = showDialogWithType(type_setting, R.layout.dialog_setting, "");
                        type_setting.show();
                    }

                }
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mMap = googleMap;
        myLocation = new MyLocation(this, mMap, locationUtils);
        try {
            myLocation.showMyLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myEventListener = new MyEventListener(this, mMap, myLocation);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                common.dismissProgress();
            }
        });


    }
}
