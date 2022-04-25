package com.example.ceibatest.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ceibatest.App
import com.example.ceibatest.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

object Commons {

    fun View.getParentActivity(): AppCompatActivity? {
        var context = this.context
        while (context is ContextWrapper) {
            if (context is AppCompatActivity)
                return context

            context = context.baseContext
        }
        return null
    }

    fun getString(string: Int, value: String): String =
        App.appContext.getString(string, value)


    @ExperimentalCoroutinesApi
    fun EditText.textChanges(): Flow<CharSequence?> {
        return callbackFlow<CharSequence?> {
            val listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    trySend(s)
                }
            }
            addTextChangedListener(listener)
            awaitClose { removeTextChangedListener(listener) }
        }.onStart { emit(text) }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getColor(color: Int): Int = App.appContext.getColor(color)

    @SuppressLint("NewApi")
    fun showSnackBar(title: String, view : View){
        val snack = Snackbar.make(view,title, Snackbar.LENGTH_LONG)
        snack.view.setBackgroundColor(getColor(R.color.red))
        snack.show()
    }

    fun getString(string: Int): String = App.appContext.getString(string)

    fun hideKeyboard(activity: Activity) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawable(drawable: Int): Drawable? = App.appContext.getDrawable(drawable)

    @SuppressLint("NewApi")
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun CharSequence?.getTextToSave(): String =
        this?.toString()?.replace(Regex("( )+"), " ")?.trim() ?: ""

}