package com.fgt.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class SecondActivity : AppCompatActivity() {

    var items: Item? = null
    private lateinit var teamimage: ImageView
    private lateinit var teamname: TextView
    private lateinit var teamDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mainUI()
        var intent = intent

        items = intent.getParcelableExtra("name")
        teamname.text = items?.name
        teamDescription.text = items?.desc
        Glide.with(this).load(items?.image).into(teamimage)
    }

    private fun mainUI() {

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout{
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            teamimage =  imageView {}.lparams(height = dip(75))

                            teamname = textView(intent.getStringExtra("name")){
                                this.gravity = Gravity.CENTER
                                textSize = 20f

                            }.lparams{
                                topMargin = dip(5)
                            }

                            teamDescription = textView(intent.getStringExtra("desc"))
                                .lparams{
                                topMargin = dip(20)
                            }
                        }
                    }

            }
        }

    }


}
