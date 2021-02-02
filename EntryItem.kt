package com.example.feelingsdiary

import android.content.Intent
import java.util.*

class EntryItem {
    var entry = String()
    var date = Date()

    internal constructor(entry: String, date: Date){
        this.entry = entry
        this.date = date
    }

    internal constructor(intent: Intent){
        entry = intent.getStringExtra("entry").toString()
        date = Calendar.getInstance().time

    }
}