package edu.umich.mahira.fridgefriend

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView

class RecipeDetailActivity : AppCompatActivity() {
    var image: String = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        var Image: ImageView = findViewById(R.id.recipeDetailmg)
        val Title: TextView = findViewById(R.id.recipeDetailTitle)
        val Ingredients: TextView = findViewById(R.id.recipeDetailIngredients)
        val Instructios: TextView = findViewById(R.id.recipeDetailInstructions)
        Title.text = intent.getStringExtra("title")
        Ingredients.text = intent.getStringExtra("ingredients")
        Instructios.text = intent.getStringExtra("instructions")
        // image is too large to pass through intent so we use a singleton object to store it
        val ogImg = RecipeDetailStore.image
        var alteredImg = ogImg?.dropLast(1)
        alteredImg = alteredImg?.drop(2)
        val imageBytes = Base64.decode(alteredImg, 0)
        var image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        Image.setImageBitmap(image)
        Image.maxHeight = 100
        Image.maxWidth = 100


    }
}