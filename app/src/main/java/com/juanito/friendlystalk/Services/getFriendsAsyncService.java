package com.juanito.friendlystalk.Services;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.juanito.friendlystalk.R;
import com.juanito.friendlystalk.User;

import java.util.List;

public class getFriendsAsyncService extends AsyncTask<String, Void, String> {

    private String concat = "";
    public List<String> userList;
    private RemoteViews views;


    public getFriendsAsyncService(RemoteViews views){
        this.views = views;
    }

    @Override
    protected String doInBackground(String... params) {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("User");
        Query query = db.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot d : dataSnapshot.getChildren()){
                    User u = d.getValue(User.class);
                    if(u.getFriendsPseudo() != null){
                        userList = u.getFriendsPseudo();
                    }
                }
                if(userList == null){
                    concat = "liste d'amis vide";
                    Log.i("LOG_TEST", "(Async) la liste est null");
                } else {
                    Log.i("LOG_TEST", "(Async) la liste est pas null");
                    Log.i("LOG_TEST", "(Async) concat vide : "+concat);
                    Log.i("LOG_TEST", "(Async) list size : "+userList.size());
                    for(int i=0; i < userList.size(); i++){
                        Log.i("LOG_TEST", "(Async) list get i  : "+userList.get(i));
                        concat+= userList.get(i)+". ";
                    }
                    //listFriends = concat;
                    Log.i("LOG_TEST", "(Async) mes amis : "+concat);
                    views.setTextViewText(R.id.textViewConcat, concat);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        return concat;
    }


    @Override
    protected void onPostExecute(String concat) { //une fois qu'on retourne le concat, le postExecute s'execute avec le concat en param
        Log.i("LOG_TEST", "(Async) POST EXECUTE : le concat est : "+concat);
        views.setTextViewText(R.id.textViewConcat, concat);
    }
    // le pb est que le concat de doInBackGround est retourné avant le resultat du newValueListener(), donc il est tout le temps à null, donc le postExecute recoit une chaine vide
    // et actualise le widget avec une chaine null
}
