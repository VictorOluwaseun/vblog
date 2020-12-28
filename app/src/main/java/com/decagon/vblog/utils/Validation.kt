package com.decagon.vblog.utils

import android.text.TextUtils

class Validation {
     fun inputCheck(vararg fieldValues: String): Boolean{
         for (data in fieldValues){
             if (TextUtils.isEmpty(data)){
                 return false
             }
         }
        return true
    }
}