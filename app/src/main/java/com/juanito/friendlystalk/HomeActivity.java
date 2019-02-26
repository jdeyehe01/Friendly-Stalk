package com.juanito.friendlystalk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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
    private Switch invisibleSwitch;
    protected Location currentLocation;
    private User userCurrent = new User();
    private Map<String,String> res = new HashMap<>();
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference("User");

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
        invisibleSwitch = findViewById(R.id.ghostSwitch);
        invisibleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchInvisible(true);
                } else {
                    switchInvisible(false);
                }
            }
        });

        btnLogout.setOnClickListener(logoutListener);
        btnListFriends.setOnClickListener(listFriends);
        btnInvitations.setOnClickListener(listDemands);
        btnUpdateInformation.setOnClickListener(updateInformationListener);
        initUserFromDb();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userCurrent.getInvisible() != null) {
            updatePosition();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userCurrent.getInvisible() != null) {
            updatePosition();
        }
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

    View.OnClickListener listDemands = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(HomeActivity.this, MyDemandsActivity.class));
        }
    };

    View.OnClickListener updateInformationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(HomeActivity.this,MyInformationActivity.class));
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
            if (!userCurrent.getInvisible()) {
                System.out.println("Updating position");
                fusedLocationProvider.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    String address = getCompleteAddressString(location.getLatitude(), location.getLongitude());
                                    currentLocation = location;
                                    localText.setText(address);
                                    localText.setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.colorAccent));
                                    insertLocalisationBdd(address);
                                }
                            }
                        });
            } else {
                System.out.println("User invisible, not updating");
                String emoji = new String(Character.toChars(0x1F47B));
                localText.setText("(invisible) "+emoji);
                localText.setTextColor(Color.parseColor("#FF0000"));
            }
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
        Query query = db.orderByChild("email").equalTo(currentUser.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    User u = d.getValue(User.class);
                    if(u!=null) {
                      Date currentDate = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Map<String, Object> userUpdates = new HashMap<>();
                        userUpdates.put("adresse", localisation);
                        userUpdates.put("dateAdresse", dateFormat.format(currentDate));
                        db.child(u.getId()).updateChildren(userUpdates);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void switchInvisible(final boolean invisible) {
        final Map<String, Object> userUpdates = new HashMap<>();
        if (invisible) {
            userUpdates.put("adresse","");
        }

        Query query = db.orderByChild("email").equalTo(currentUser.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    User u = d.getValue(User.class);
                    if(u!=null) {
                        userUpdates.put("invisible", invisible);
                        db.child(u.getId()).updateChildren(userUpdates);
                        updatePosition();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initUserFromDb(){
        Query query = db.orderByChild("email").equalTo(currentUser.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    User u = d.getValue(User.class);
                    if(u!=null) {
                        userCurrent = u;
                        updatePosition();
                        if (userCurrent.getInvisible()) invisibleSwitch.setChecked(true);
                       res = addressFriends(u.getFriendsPseudo());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private Map<String, String> addressFriends(List<String> listFriends){
         //Toast.makeText(HomeActivity.this, Toast.LENGTH_SHORT).show();

        for(String pseudo : listFriends){
           Query query = db.orderByChild("pseudo").equalTo(pseudo);
           query.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot d : dataSnapshot.getChildren()){
                       User u = d.getValue(User.class);
                       if(u!=null) {

                           res.put(u.getPseudo(),u.getAdresse());
                          // Toast.makeText(HomeActivity.this,res.toString(), Toast.LENGTH_SHORT).show();
                          // Toast.makeText(HomeActivity.this,userCurrent.getInvisible().toString(), Toast.LENGTH_SHORT).show();

                       }
                   }

                   for (Map.Entry<String,String> userToCompare : res.entrySet()) {
                       if (!userCurrent.getAdresse().isEmpty() && !userCurrent.getInvisible()) {
                           Location toCompare = getLocationFromAddress(userToCompare.getValue());
                           Location userAdress = getLocationFromAddress(userCurrent.getAdresse());

                           float distance = userAdress.distanceTo(toCompare);
                           if (distance > 200) {
                               System.out.println(userToCompare.getKey()+" est trop loin ("+distance+"m)");
                           } else {
                               System.out.println(userToCompare.getKey()+" est à moins de 200m !");
                           }
                       }
                   }

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }

       return res;
       }

}


