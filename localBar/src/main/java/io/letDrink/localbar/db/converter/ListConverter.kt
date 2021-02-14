package io.letDrink.localbar.db.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun<T> type(): Type = object : TypeToken<T>() {}.type