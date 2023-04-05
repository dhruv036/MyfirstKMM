package com.dhruv.myfirstkmm.Data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    actual  fun createDriver() : SqlDriver
}