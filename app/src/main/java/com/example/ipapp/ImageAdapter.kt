package com.example.ipapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ImageAdapter(private val bitmaps: Array<Bitmap?>) :
    RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ImageAdapter.MyViewHolder {
        // create a new view
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_view, parent, false) as ImageView
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(imageView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val bitmap: Bitmap? = bitmaps[position]
        holder.imageView.setImageBitmap(bitmap)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = bitmaps.size
}


