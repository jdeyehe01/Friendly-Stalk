package com.juanito.friendlystalk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView localText;
    private Button btnLogout;
    private Button btnListFriends;
    private Button btnInvitations;
    private Button btnUpdateInformation;
    protected Location currentLocation;

    private FusedLocationProviderClient fusedLocationProvider;

    //private User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        btnLogout = findViewById(R.id.btnLogout);
        btnInvitations = findViewById(R.id.btnMyInvitations);
        btnListFriends = findViewById(R.id.btnMyFriends);
        btnUpdateInformation = findViewById(R.id.btnUpdateInformation);
        localText = findViewById(R.id.localText);

        btnLogout.setOnClickListener(logoutListener);
        btnListFriends.setOnClickListener(listFriends);
        updatePosition();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updatePosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePosition();
        Toast.makeText(HomeActivity.this, "Bienvenue " + currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
    }


    View.OnClickListener logoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            //ancienne methode
            //startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            //nouvelle methode (ajouts de FLAG pour eviter de revenir en arriere alors qu'on est bien logout
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(HomeActivity.this, "Vous êtes deconnecté", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener listFriends = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(HomeActivity.this, MyFriendsActivity.class));
        }
    };

    private void updatePosition() {
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            System.out.println("pasdelocalisation");
            return;
        } else {
            fusedLocationProvider.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                String address = getCompleteAddressString(location.getLatitude(), location.getLongitude());
                                currentLocation = location;
                                localText.setText(address);
                                insertLocalisationBdd(address);
                            }
                        }
                    });
        }
    }

    private Location getLocationFromAddress(String address) {
        Location locationToReturn = new Location("");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                locationToReturn.setLatitude(returnedAddress.getLatitude());
                locationToReturn.setLongitude(returnedAddress.getLongitude());

                return locationToReturn;
            }
        } catch (Exception e) {
            System.out.println("Couldn't create location from address : "+e);
        }

        return null;
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current location", strReturnedAddress.toString());
            } else {
                Log.w("My Current location", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current location", "Canont get Address!");
        }
        return strAdd;
    }

    private void insertLocalisationBdd(final String localisation) {
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("User");

        Query query = db.orderByChild("email").equalTo(currentUser.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    User u = d.getValue(User.class);
                    if(u!=null) {
                      u.setLastLocation(localisation);
                      Date currentDate = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Map<String, Object> userUpdates = new HashMap<>();
                        userUpdates.put("Adresse", localisation);
                        userUpdates.put("DateAdresse", dateFormat.format(currentDate));
                        db.child(u.getId()).updateChildren(userUpdates);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


