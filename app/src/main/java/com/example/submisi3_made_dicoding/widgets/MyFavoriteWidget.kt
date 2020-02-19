package com.example.submisi3_made_dicoding.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.example.submisi3_made_dicoding.R

/**
 * Implementation of App Widget functionality.
 */
class MyFavoriteWidget : AppWidgetProvider() {

    companion object{
        private const val TOAST_ACTION = "com.example.submisi3_made_dicoding.TOAST_ACTION"
        private const val REFRESH_WIDGET = "com.example.submisi3_made_dicoding.REFRESH_WIDGET"
        const val EXTRA_ITEM = "com.example.submisi3_made_dicoding.EXTRA_ITEM"
        const val EXTRA_NAME = "com.example.submisi3_made_dicoding.EXTRA_NAME"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val intent = Intent(context,FavoriteWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.item_widget)
            views.setRemoteAdapter(R.id.stacko,intent)
            views.setEmptyView(R.id.stacko, R.id.mees_exc)

            val toastIntent = Intent(context,FavoriteWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val pendingIntent = PendingIntent.getBroadcast(context,0,toastIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stacko,pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId,views)

        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if(intent.action != null){
            if (intent.action == TOAST_ACTION){
                val index = intent.getIntExtra(EXTRA_ITEM,0)
                val title = intent.getStringExtra(EXTRA_NAME)
                Toast.makeText(context, "Hey$index$title", Toast.LENGTH_LONG).show()

            }else if(intent.action  == REFRESH_WIDGET){
                val updateIntent = Intent(context,FavoriteWidgetService::class.java)
                updateIntent.data = updateIntent.toUri(Intent.URI_INTENT_SCHEME).toUri()
                val view = RemoteViews(context.packageName,R.layout.favorite_widget)
                view.setRemoteAdapter(R.id.stacko,updateIntent)
                view.setEmptyView(R.id.stacko,R.id.mees_exc)
                Toast.makeText(context,context.getString(R.string.refersh), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

//leave it as empty
    override fun onEnabled(context: Context) {}

    override fun onDisabled(context: Context) {}

}

