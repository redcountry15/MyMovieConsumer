package com.example.submisi3_made_dicoding.widgets

import android.content.Intent
import android.widget.RemoteViewsService

class FavoriteWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        FavoriteViewFactory(this.applicationContext)

}