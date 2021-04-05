package com.example.myapplicationnotes

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.*
import com.example.myapplicationnotes.data.NoteDatabase
import com.example.myapplicationnotes.models.NoteViewModel
import com.example.myapplicationnotes.repository.NoteRepository
import com.example.myapplicationnotes.widget.NewAppWidget
import java.text.SimpleDateFormat
import java.util.*
import java.util.Observer
import kotlin.collections.ArrayList


class MyFactory internal constructor(ctx: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {
    var data: ArrayList<String>? = null
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    var context: Context
    var sdf: SimpleDateFormat
    var widgetID: Int
    override fun onCreate() {
        data = ArrayList()
    }

    override fun getCount(): Int {
        return data!!.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rView = RemoteViews(
            context.packageName,
            R.layout.item
        )
        rView.setTextViewText(R.id.tvItemText, data!![position])
        return rView
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {
        data!!.clear()
        data!!.add(sdf.format(Date(System.currentTimeMillis())))
        data!!.add(hashCode().toString())
        data!!.add(widgetID.toString())
        notesViewModel = NoteViewModel(Application(), NoteRepository(NoteDatabase.invoke(context)))
        val s = notesViewModel.getNt()
        for(n in s){
                data!!.add(n.noteTitle)
        }
    }

    override fun onDestroy() {}

    init {
        context = ctx
        sdf = SimpleDateFormat("HH:mm:ss")
        widgetID = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )
    }
}
enum class ForeverStartLifecycleOwner : LifecycleOwner {
    INSTANCE;

    private val mLifecycleRegistry: LifecycleRegistry
    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    init {
        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }
}