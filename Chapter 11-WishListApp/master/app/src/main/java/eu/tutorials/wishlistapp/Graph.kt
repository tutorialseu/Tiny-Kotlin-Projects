package eu.tutorials.wishlistapp

import android.content.Context
import androidx.room.Room
import eu.tutorials.wishlistapp.data.WishDatabase
import eu.tutorials.wishlistapp.data.WishRepository

object Graph {
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db")
            .fallbackToDestructiveMigration() // don't use this in production app
            .build()
    }
}