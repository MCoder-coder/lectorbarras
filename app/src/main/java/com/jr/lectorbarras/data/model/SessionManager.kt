

import android.content.Context
import android.content.SharedPreferences

class SessionManager private constructor(context: Context){

    private var editor:SharedPreferences.Editor
    var prefs: SharedPreferences
    private var mct: Context? = null

    init{
        this.prefs = context.getSharedPreferences("api-config", Context.MODE_PRIVATE)
        editor = prefs.edit()
        editor.putBoolean("isLoggedIn", true);
        editor.commit()


    }




    var host:String
        get() {
            return prefs.getString("host", "")!!
        }
        set(host) {
            editor.putString("host", host)
            editor.apply()
        }

    var hash:String
        get() {
            return prefs.getString("hash", "")!!
        }
        set(hash) {
            editor.putString("hash", hash)
            editor.apply()
        }


    var email:String
        get() {
            return prefs.getString("email", "")!!
        }
        set(email) {
            editor.putString("email", email)
            editor.apply()
        }





    companion object {
        //        private val jInstance : SessionManager
//        @Synchronized fun getInstance(context:Context):SessionManager {
//            if (jInstance != null)
//            {
//                return jInstance
//            }
//            else
//            {
//                jInstance = SessionManager(context)
//                return jInstance
//            }
//        }
        fun getInstance(context:Context):SessionManager {
//            if (jInstance != null)
//            {
            return SessionManager(context)
//            }
//            else
//            {
//                jInstance = SessionManager(context)
//                return jInstance
//            }
        }
    }
}