
package com.jr.lectorbarras.data.model

import android.content.Context
import android.content.SharedPreferences
import com.jr.lectorbarras.R


/**
 * Session manager to save and fetch data from SharedPreferences
 */

class SharedPrefManager private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        }

    val user: LoginResponse
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
          /*  return LoginResponse()(
                //sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email" , null).toString(),
                sharedPreferences.getString("password", null).toString()
            )*/
        }


    fun saveUser(user: LoginResponse) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

/*
        editor.putString("email", user.)
        editor.putString("password", user.password)
*/


        editor.apply()

    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}
