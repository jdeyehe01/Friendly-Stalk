package com.juanito.friendlystalk.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.juanito.friendlystalk.MyFriendsActivity;
import com.juanito.friendlystalk.R;

public class listWidget extends AppWidgetProvider {

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){ //String text
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.list_widget);
        //remoteViews.setTextViewText(R.id.textView, text);
        //remoteViews.setTextViewText(R.id.textView, text); // lister les amis et les setter dans une variable comme dans MyFriendsActivity ?

        Intent intent = new Intent(context, MyFriendsActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //remoteViews.setOnClickPendingIntent(R.id.textView, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.textViewPseudo, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int appWidgetId : appWidgetIds){
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
