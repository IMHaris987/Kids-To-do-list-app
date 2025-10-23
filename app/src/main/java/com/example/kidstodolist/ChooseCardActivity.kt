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

        // Setup RecyclerView with GridLayoutManager (3 columns)
        cardsRecyclerView.layoutManager = GridLayoutManager(this, 3)

        // Default category
        showCardsForCategory("History")
        updateSelectedIconBackground(R.id.iconHistory)

        // Click listeners for each category icon
        findViewById<ImageView>(R.id.iconHistory).setOnClickListener {
            showCardsForCategory("History")
            updateSelectedIconBackground(R.id.iconHistory)
        }
        findViewById<ImageView>(R.id.iconGallery).setOnClickListener {
            // Hide RecyclerView
            cardsRecyclerView.visibility = View.GONE

            // Open GalleryFragment
            val fragment = GalleryFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment) // fragmentContainer is a FrameLayout in your layout
                .addToBackStack(null)
                .commit()

            updateSelectedIconBackground(R.id.iconGallery)
        }

        findViewById<ImageView>(R.id.iconAction).setOnClickListener {
            showCardsForCategory("Action")
            updateSelectedIconBackground(R.id.iconAction)
        }
        findViewById<ImageView>(R.id.iconFood).setOnClickListener {
            showCardsForCategory("Food")
            updateSelectedIconBackground(R.id.iconFood)
        }
        findViewById<ImageView>(R.id.iconTool).setOnClickListener {
            showCardsForCategory("Tool")
            updateSelectedIconBackground(R.id.iconTool)
        }
        findViewById<ImageView>(R.id.iconClothes).setOnClickListener {
            showCardsForCategory("Clothes")
            updateSelectedIconBackground(R.id.iconClothes)
        }
        findViewById<ImageView>(R.id.iconPlace).setOnClickListener {
            showCardsForCategory("Place")
            updateSelectedIconBackground(R.id.iconPlace)
        }
        findViewById<ImageView>(R.id.iconVehicle).setOnClickListener {
            showCardsForCategory("Vehicle")
            updateSelectedIconBackground(R.id.iconVehicle)
        }

        // Back arrow click
        backIcon.setOnClickListener { finish() }
    }

    /**
     * Loads cards for a given category and updates the RecyclerView.
     */
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

    /**
     * Updates icon background (light yellow only behind the icon, not the full area)
     */
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

        val selectedBg = ContextCompat.getDrawable(this, R.drawable.icon_yellow_circle) // circular light yellow bg
        val unselectedBg = null
        val selectedText = ContextCompat.getColor(this, R.color.nav_selected_text)
        val unselectedText = ContextCompat.getColor(this, R.color.nav_unselected_text)

        for (id in iconIds) {
            val icon = findViewById<ImageView>(id)
            val parent = icon.parent
            if (parent is LinearLayout) {
                // Only change icon background, not full container
                if (id == selectedIconId) {
                    icon.background = selectedBg
                } else {
                    icon.background = unselectedBg
                }

                // Change text color
                if (parent.childCount >= 2) {
                    val labelView = parent.getChildAt(1)
                    if (labelView is TextView) {
                        labelView.setTextColor(if (id == selectedIconId) selectedText else unselectedText)
                    }
                }
            }
        }
    }
}
