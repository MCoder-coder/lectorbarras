

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager private constructor(context: Context){

    private val editor:SharedPreferences.Editor
    var prefs: SharedPreferences
    init{
        this.prefs = context.getSharedPreferences("stockNuevo", Context.MODE_PRIVATE)
        editor = prefs.edit()

        editor.commit()


    }

    var stock:Int
        get() {
            return prefs.getInt("stock", 0)
        }
        set(stock) {
            editor.putInt("stock", stock)
            editor.apply()
        }






    companion object {

        fun getInstance(context:Context): SharedPrefManager {

            return SharedPrefManager(context)

        }
    }
}