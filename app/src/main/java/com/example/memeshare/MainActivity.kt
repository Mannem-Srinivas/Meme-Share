package com.example.memeshare

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var memeImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        memeImageView = findViewById(R.id.memeImageView)
        loadMeme()
    }

    private fun loadMeme() {
        val queue = Volley.newRequestQueue(this)
        val url = " https://meme-api.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
            { response ->
                val url = response.getString("url")

                Glide.with(this).load(url).into(memeImageView)

            },
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG)
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Meme", null)
        return Uri.parse(path)
    }

    fun shareMeme(view: View) {
        val bitmap = (memeImageView.drawable as BitmapDrawable).bitmap
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            val uri = getImageUriFromBitmap(bitmap)
            putExtra(Intent.EXTRA_STREAM, uri)
        }
        startActivity(Intent.createChooser(shareIntent, "Share this meme via"))
    }

    fun nextMeme(view: View) {
    loadMeme()
    }
}