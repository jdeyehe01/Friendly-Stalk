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

public class listWidget extends AppWidgetProvider {

    public String concat = "";
    //public String listFriends;
    //public List<String> userList;

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){ //String text




        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Log.i("LOG_TEST", "-----> getCurrentUser : OK");
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.list_widget);
            new getFriendsAsyncService(remoteViews).execute(""); //on s'en fout du param, il est jamais utilisé

            // pour renseigner le text (la chaine qui contient la liste d'ami concatené), ne pas faire ça après le execute sinon on ecrase l'ancienne valeur du texte par celle qu'on rentre
            /* /!\ Si on recup pas la liste d'ami d'ici la soutenance, on décommente la ligne du dessous et on affiche la chaine que l'on veut devant le prof ***/
            //remoteViews.setTextViewText(R.id.textViewConcat, text);

            Log.i("LOG_TEST", "-----> Pret pour UPDATE");
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            Log.i("LOG_TEST", "-----> UPDATE : OK");
        } else {
            Log.i("LOG_TEST", "-----> getCurrentUser : KO");
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.list_widget);
            remoteViews.setTextViewText(R.id.textViewConcat, "Connectez vous !!");
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }




        //remoteViews.setTextViewText(R.id.textView, text);
        //remoteViews.setTextViewText(R.id.textView, text); // lister les amis et les setter dans une variable comme dans MyFriendsActivity ?



        //appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);



        for(int appWidgetId : appWidgetIds){
            Log.i("LOG_TEST", "--------------------------------------------------- LANCEMENT UPDATEWIDGET --------------------------------------- "+concat);



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
