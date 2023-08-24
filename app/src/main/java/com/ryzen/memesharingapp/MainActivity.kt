package com.ryzen.memesharingapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.memeImageView
import kotlinx.android.synthetic.main.activity_main.progressBar

class MainActivity : AppCompatActivity() {
    var currUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* val memeImage = findViewById<>(R.id.memeImageView) */

        loadMeme()
    }

    private fun loadMeme() {

        progressBar.visibility = View.VISIBLE

        /* Instantiate the RequestQueue. */
        // val queue = Volley.newRequestQueue(this)

        currUrl = "https://meme-api.com/gimme"

        /* Request a string response from the provided URL. */
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, currUrl, null, { response ->
            /* Display the first 500 characters of the response string. */
            // textView.text = "Response is: ${response.substring(0, 500)}"

            // Log.d("Success google","Response is ${response.substring(0,500)}")

            currUrl = response.getString("url")

            Glide.with(this).load(currUrl).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

            }).into(memeImageView)

        }, {
            Toast.makeText(this, "Something went wrong!!!", Toast.LENGTH_LONG).show()
        })

        /* Add the request to the RequestQueue. */
        // queue.add(jsonObjectRequest)
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareButton(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "A funny meme check this out : ${currUrl}")
        val chooser = Intent.createChooser(intent, "SHARING THIS MEME WITH MY MEME APP")
        startActivity(chooser)
    }

    fun nextButton(view: View) {
        loadMeme()
    }
}