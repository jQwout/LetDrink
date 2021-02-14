package io.letdrink.common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun Context.intent(clazz: Class<out Activity>, block: Intent.() -> Unit): Intent {
    return Intent(this, clazz).apply {
        block()
    }
}