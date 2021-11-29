package edu.umich.mahira.fridgefriend

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.InputStream

class RecipeAdapter(private val context: Context,
                    private val dataSource: Array<Recipe?>
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Recipe? {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_recipe, parent, false)

        // Get title element
        val titleTextView = rowView.findViewById(R.id.recipeTitle) as TextView
        var recipeImgView = rowView.findViewById(R.id.recipeImg) as ImageView
        val recipe = getItem(position) as Recipe
        val ogImg = recipe.img
        var alteredImg = ogImg?.dropLast(1)
        alteredImg = alteredImg?.drop(2)
        val imageBytes = Base64.decode(alteredImg, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        recipeImgView.setImageBitmap(image)
        recipeImgView.maxWidth = 150
        recipeImgView.maxHeight = 150
        titleTextView.text = recipe.title
        return rowView
    }
}