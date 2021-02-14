package io.letDrink.localbar.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.reflect.TypeToken
import io.letDrink.localbar.db.LocalBarDB
import io.letDrink.localbar.db.converter.type

@Entity
@TypeConverters(value = [StringListConverter::class])
data class Cocktail(
    val description: String,
    val directions: List<String>,
    val github: String,
    val image: String,
    val keywords: List<String>,
    @PrimaryKey
    val name: String,
    val source: String?,
    val isFavorite: Boolean = false,
    val remoteId: Long = 0
)

class StringListConverter {

    @TypeConverter
    fun from(directions: List<String>): String {
        return directions.reduce { acc, s -> "$acc;$s" }
    }

    @TypeConverter
    fun to(data: String): List<String> {
        return data.split(";")
    }
}