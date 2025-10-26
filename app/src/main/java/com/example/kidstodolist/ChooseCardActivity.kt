package com.example.kidstodolist

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChooseCardActivity : BaseActivity() {

    private lateinit var backIcon: ImageView
    private lateinit var cardsRecyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private lateinit var cardList: List<CardItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_card)

        // Initialize views
        backIcon = findViewById(R.id.backIcon)
        cardsRecyclerView = findViewById(R.id.cardsRecyclerView)
        cardsRecyclerView.layoutManager = GridLayoutManager(this, 3)

        // Default: show History cards
        showCardsForCategory("History")
        updateSelectedIconBackground(R.id.iconHistory)

        // Category click listeners
        setCategoryClick(R.id.iconHistory, "History")
        setCategoryClick(R.id.iconAction, "Action")
        setCategoryClick(R.id.iconFood, "Food")
        setCategoryClick(R.id.iconTool, "Tool")
        setCategoryClick(R.id.iconClothes, "Clothes")
        setCategoryClick(R.id.iconPlace, "Place")
        setCategoryClick(R.id.iconVehicle, "Vehicle")

        // ✅ Gallery fragment
        findViewById<ImageView>(R.id.iconGallery).setOnClickListener {
            updateSelectedIconBackground(R.id.iconGallery)
            showGalleryFragment()
        }

        // Back button closes activity
        backIcon.setOnClickListener { finish() }
    }

    // Simple reusable function for setting category click listeners
    private fun setCategoryClick(iconId: Int, category: String) {
        findViewById<ImageView>(iconId).setOnClickListener {
            hideGalleryFragmentIfVisible()
            showCardsForCategory(category)
            updateSelectedIconBackground(iconId)
        }
    }

    private fun showCardsForCategory(category: String) {
        cardList = when (category) {
            "History" -> listOf(
                CardItem("Cooking", R.drawable.cooking),
                CardItem("Reading", R.drawable.read),
                CardItem("Sleeping", R.drawable.sleeping),
                CardItem("Exercise", R.drawable.exercise)
            )
            "Action" -> listOf(
                CardItem("Drink", R.drawable.drink),
                CardItem("Eat", R.drawable.dish),
                CardItem("Look", R.drawable.arrow),
                CardItem("Wear", R.drawable.male_clothes),
                CardItem("Go", R.drawable.walk),
                CardItem("Take off", R.drawable.take_off),
                CardItem("Wash hands", R.drawable.washing_hand),
                CardItem("Brush teeth", R.drawable.brush_teeth),
                CardItem("Open", R.drawable.open),
                CardItem("Close", R.drawable.close),
                CardItem("Wake up", R.drawable.morning),
                CardItem("Sit", R.drawable.sit),
                CardItem("Stand", R.drawable.stand),
                CardItem("Run", R.drawable.run),
                CardItem("Drawing", R.drawable.drawing),
                CardItem("Face wash", R.drawable.face),
                CardItem("Pack up", R.drawable.backpack),
                CardItem("Comb Hair", R.drawable.comb_hair),
                CardItem("Study", R.drawable.study),
                CardItem("Sleep", R.drawable.sleeping),
                CardItem("Cooking", R.drawable.cooking),
                CardItem("Swim", R.drawable.swim),
                CardItem("Speak", R.drawable.speak)
            )
            "Food" -> listOf(
                CardItem("BreakFast", R.drawable.breakfast),
                CardItem("Lunch", R.drawable.lunch),
                CardItem("Snacks", R.drawable.snack),
                CardItem("Dinner", R.drawable.pizza),
                CardItem("Burger", R.drawable.burger),
                CardItem("Water", R.drawable.water),
                CardItem("Tea", R.drawable.tea),
                CardItem("Juice", R.drawable.juice)
            )
            "Tool" -> listOf(
                CardItem("Lunch Box", R.drawable.lunch),
                CardItem("Water bottle", R.drawable.water),
                CardItem("Toys", R.drawable.toys),
                CardItem("Clay", R.drawable.clay),
                CardItem("Ball", R.drawable.basket),
                CardItem("Video Game", R.drawable.video_game),
                CardItem("DVD", R.drawable.dvd),
                CardItem("Tablet", R.drawable.tablet),
                CardItem("Tv", R.drawable.tv)
            )
            "Clothes" -> listOf(
                CardItem("Shirt", R.drawable.male_clothes),
                CardItem("Pants", R.drawable.pant),
                CardItem("Hat", R.drawable.hat),
                CardItem("Shoes", R.drawable.shoes)
            )
            "Place" -> listOf(
                CardItem("Home", R.drawable.house),
                CardItem("Park", R.drawable.park),
                CardItem("School", R.drawable.school),
                CardItem("Beach", R.drawable.beach)
            )
            "Vehicle" -> listOf(
                CardItem("Car", R.drawable.sport_car),
                CardItem("Bus", R.drawable.bus),
                CardItem("Bike", R.drawable.bike),
                CardItem("Train", R.drawable.train)
            )
            else -> emptyList()
        }

        cardAdapter = CardAdapter(cardList)
        cardsRecyclerView.adapter = cardAdapter
    }

    private fun showGalleryFragment() {
        val fragmentContainer = findViewById<View>(R.id.fragmentContainer)
        fragmentContainer.visibility = View.VISIBLE

        val fragment = GalleryFragment()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun hideGalleryFragmentIfVisible() {
        val fragmentContainer = findViewById<View>(R.id.fragmentContainer)
        if (fragmentContainer.visibility == View.VISIBLE) {
            fragmentContainer.visibility = View.GONE
            supportFragmentManager.popBackStack()
        }
    }

    private fun updateSelectedIconBackground(selectedIconId: Int) {
        val iconIds = listOf(
            R.id.iconHistory,
            R.id.iconGallery,
            R.id.iconAction,
            R.id.iconFood,
            R.id.iconTool,
            R.id.iconClothes,
            R.id.iconPlace,
            R.id.iconVehicle
        )

        val selectedBg = ContextCompat.getDrawable(this, R.drawable.icon_yellow_circle)
        val selectedText = ContextCompat.getColor(this, R.color.nav_selected_text)
        val unselectedText = ContextCompat.getColor(this, R.color.nav_unselected_text)

        for (id in iconIds) {
            val icon = findViewById<ImageView>(id)
            val parent = icon.parent as? LinearLayout
            parent?.let {
                icon.background = if (id == selectedIconId) selectedBg else null
                val labelView = it.getChildAt(1)
                if (labelView is TextView) {
                    labelView.setTextColor(if (id == selectedIconId) selectedText else unselectedText)
                }
            }
        }
    }
}
