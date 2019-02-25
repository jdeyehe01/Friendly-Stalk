package com.juanito.friendlystalk;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.juanito.friendlystalk.Utils.Global.FRIENDS_LIST;

public class MyFriendsActivity extends AppCompatActivity {
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("User");
    private RecyclerView recyclerView;
    private MyUserAdapter userAdapter;
    static List<String> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friends);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        userList = new ArrayList<String>();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        displayFriends(currentUser.getEmail());



    }

    private void displayFriends(String idUser){
        Query query = db.orderByChild("email").equalTo(idUser);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        User u = d.getValue(User.class);

                        if(u.getFriendsPseudo() != null){
                            userList = u.getFriendsPseudo();
                            writeCache(userList);
                            //FRIENDS_LIST = userList.toString();
                            FRIENDS_LIST = readCache();
                        }


                    }

                userAdapter = new MyUserAdapter(userList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public String readCache() {
        File cacheDir = getCacheDir();
        try {
            FileInputStream fis = new FileInputStream(cacheDir.getPath() + "/" + "myCache.txt");
            StringBuffer sb = readFromFIS(fis);
            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void writeCache(List<String> friends) {
       File cacheDir = getCacheDir();
        try {
            FileOutputStream fos = new FileOutputStream(cacheDir.getPath() + "/" + "myCache.txt");
            Log.i("FOS",cacheDir.getAbsolutePath() + "/" + "myCache.txt");
            writeToFos(fos,friends);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void writeToFos(FileOutputStream fos , List<String> frinds) throws IOException {
        for(String pseudo : frinds){
            fos.write((pseudo+ "\n").getBytes());
        }
        fos.flush();
        fos.close();
    }

    private StringBuffer readFromFIS(FileInputStream fis) throws IOException {
        StringBuffer sb = new StringBuffer();
        int cr = 0;
        while ((cr = fis.read()) != -1) {
            sb.append((char) cr);
        }
        return sb;
    }
}