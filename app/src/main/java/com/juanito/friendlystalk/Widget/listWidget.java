package com.juanito.friendlystalk.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.juanito.friendlystalk.MyFriendsActivity;
import com.juanito.friendlystalk.MyUserAdapter;
import com.juanito.friendlystalk.R;
import com.juanito.friendlystalk.Services.getFriendsAsyncService;
import com.juanito.friendlystalk.User;

import java.util.List;

import static com.juanito.friendlystalk.Utils.Global.FRIENDS_LIST;

public class listWidget extends AppWidgetProvider {

    public String concat = "";
    //public String listFriends;
    //public List<String> userList;

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){ //String text

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.list_widget);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Log.i("LOG_TEST", "-----> getCurrentUser : OK");
            //new getFriendsAsyncService(remoteViews).execute(""); //param jamais utilisé
            // pour renseigner le text (la chaine qui contient la liste d'ami concatené), ne pas faire ça après le execute sinon on ecrase l'ancienne valeur du texte par celle qu'on rentre
            remoteViews.setTextViewText(R.id.textViewConcat, FRIENDS_LIST);
        } else {
            Log.i("LOG_TEST", "-----> getCurrentUser : KO");
            remoteViews.setTextViewText(R.id.textViewConcat, "Connectez vous !!");
        }

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int appWidgetId : appWidgetIds){
            //Log.i("LOG_TEST", "--------------------------------------------------- LANCEMENT UPDATEWIDGET --------------------------------------- "+concat);
            updateWidget(context, appWidgetManager, appWidgetId); //"UPDATED"
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
