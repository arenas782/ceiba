package com.example.ceibatest.utils

import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ceibatest.utils.Commons.getParentActivity


@BindingAdapter("adapter")
fun setAdapter(rv: RecyclerView, mAdapter: RecyclerView.Adapter<*>) {
    rv.apply {
        setHasFixedSize(true)
        adapter = mAdapter
    }
}

@BindingAdapter("mutableTextWatcher")
fun setMutableTextWatcher(edittext: EditText, textWatcher: TextWatcher) {
    val parentActivity: AppCompatActivity? = edittext.getParentActivity()
    if (parentActivity != null)
        edittext.addTextChangedListener(textWatcher)
}





