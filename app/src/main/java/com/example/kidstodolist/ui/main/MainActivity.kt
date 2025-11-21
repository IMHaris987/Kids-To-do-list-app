package com.example.kidstodolist.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kidstodolist.ui.language.LanguageSelectionFragment
import com.example.kidstodolist.utils.LocaleHelper
import com.example.kidstodolist.ui.menu.MenuFragment
import com.example.kidstodolist.R
import com.example.kidstodolist.ui.addtask.AddTaskActivity
import com.example.kidstodolist.ui.base.BaseActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

// ✅ Main screen of the app that handles task list, drawer navigation, and selection mode
class MainActivity : BaseActivity() {

    // Drawer and toolbar views
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menuIcon: ImageView
    private lateinit var penIcon: ImageView
    private lateinit var taskListTitle: TextView
    private lateinit var fabAddTask: FloatingActionButton
    private lateinit var backIcon: ImageView

    // Bottom action bar (for multi-select mode)
    private lateinit var bottomActionBar: LinearLayout
    private lateinit var favoriteIcon: ImageView
    private lateinit var deleteIcon: ImageView
    private lateinit var selectAllText: TextView

    // Task items and their checkboxes
    private lateinit var task1Container: View
    private lateinit var task2Container: View
    private lateinit var task1CheckBox: CheckBox
    private lateinit var task2CheckBox: CheckBox

    // State variables
    private var isSelectionMode = false
    private var task1IsFavorite = false
    private var task2IsFavorite = false

    override fun attachBaseContext(newBase: Context) {
        // Apply correct locale for this activity
        super.attachBaseContext(LocaleHelper.loadLocale(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Show MenuFragment on first launch
        if (savedInstanceState == null) {
            openFragment(MenuFragment())
        }

        // === Initialize views ===
        drawerLayout = findViewById(R.id.drawer_layout)
        menuIcon = findViewById(R.id.menuIcon)
        penIcon = findViewById(R.id.penIcon)
        taskListTitle = findViewById(R.id.taskListTitle)
        fabAddTask = findViewById(R.id.fabAddTask)
        backIcon = findViewById(R.id.backIcon)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        // ✅ Load MenuFragment into navigation drawer
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navigation_view, MenuFragment())
        transaction.commit()

        // ✅ Handle drawer open
        menuIcon.setOnClickListener {
            drawerLayout.openDrawer(findViewById(R.id.navigation_view))
        }

        // ✅ Animations for UI entry
        fadeInView(toolbar, 1000)
        fadeInView(menuIcon, 1200)
        fadeInView(penIcon, 1400)
        fadeInView(taskListTitle, 1500)
        fadeInView(findViewById(R.id.contentScrollView), 1600)
        scaleToolbar(toolbar)

        // === Initialize bottom bar ===
        bottomActionBar = findViewById(R.id.bottomActionBar)
        favoriteIcon = findViewById(R.id.favoriteIcon)
        deleteIcon = findViewById(R.id.deleteIcon)
        selectAllText = findViewById(R.id.selectAllText)

        // === Initialize tasks and checkboxes ===
        task1Container = findViewById(R.id.task1Container)
        task2Container = findViewById(R.id.task2Container)
        task1CheckBox = findViewById(R.id.task1CheckBox)
        task2CheckBox = findViewById(R.id.task2CheckBox)

        // Initial UI state
        bottomActionBar.visibility = View.GONE
        task1CheckBox.visibility = View.GONE
        task2CheckBox.visibility = View.GONE
        task1CheckBox.isChecked = false
        task2CheckBox.isChecked = false
        selectAllText.text = "Select All"

        // ✅ Checkbox listener updates bottom bar and “Select All” text
        val checkChanged: (CompoundButton, Boolean) -> Unit = { _, _ ->
            updateSelectAllLabel()
            updateBottomFavoriteIconBasedOnSelection()
        }
        task1CheckBox.setOnCheckedChangeListener { btn, checked -> checkChanged(btn, checked) }
        task2CheckBox.setOnCheckedChangeListener { btn, checked -> checkChanged(btn, checked) }

        // ✅ Toolbar icon click actions
        penIcon.setOnClickListener { enterSelectionMode() }
        backIcon.setOnClickListener { exitSelectionMode() }

        // ✅ Handle "Select All" toggling
        selectAllText.setOnClickListener {
            val allSelected = task1CheckBox.isChecked && task2CheckBox.isChecked
            task1CheckBox.isChecked = !allSelected
            task2CheckBox.isChecked = !allSelected
            updateSelectAllLabel()
            updateBottomFavoriteIconBasedOnSelection()
        }

        // ✅ Favorite and delete button actions
        favoriteIcon.setOnClickListener {
            toggleFavoriteForSelected()
            updateBottomFavoriteIconBasedOnSelection()
        }

        deleteIcon.setOnClickListener {
            deleteSelectedTasks()
            updateBottomFavoriteIconBasedOnSelection()
        }

        // ✅ Add task button (opens AddTaskActivity)
        fabAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        updateBottomFavoriteIconBasedOnSelection()

        // ✅ Handle back press for fragment visibility
        onBackPressedDispatcher.addCallback(this) {
            val fragmentContainer = findViewById<View>(R.id.fragment_container)
            if (fragmentContainer.visibility == View.VISIBLE) {
                // Animate and close fragment
                fragmentContainer.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .withEndAction {
                        fragmentContainer.visibility = View.GONE
                        fragmentContainer.alpha = 1f
                        supportFragmentManager.popBackStack()
                    }
                    .start()
            } else {
                finish()
            }
        }
    }

    // Replace main fragment container content
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // ✅ Triggered from MenuFragment to open language selection
    fun closeDrawerAndOpenLanguageFragment() {
        drawerLayout.closeDrawer(findViewById(R.id.navigation_view))
        drawerLayout.postDelayed({
            val fragment = LanguageSelectionFragment().apply {
                setLanguageSelectedListener { selectedLanguage ->
                    // Update UI after language change
                    taskListTitle.text = selectedLanguage
                }
            }

            val fragmentContainer = findViewById<View>(R.id.fragment_container)
            fragmentContainer.visibility = View.VISIBLE
            fragmentContainer.alpha = 0f
            fragmentContainer.animate().alpha(1f).setDuration(200).start()

            supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }, 200)
    }

    // === Selection Mode ===

    private fun enterSelectionMode() {
        if (isSelectionMode) return
        isSelectionMode = true

        // Update icons and show checkboxes
        menuIcon.visibility = View.GONE
        penIcon.visibility = View.GONE
        backIcon.visibility = View.VISIBLE
        fabAddTask.visibility = View.GONE
        task1CheckBox.visibility = View.VISIBLE
        task2CheckBox.visibility = View.VISIBLE

        // Animate bottom bar appearance
        bottomActionBar.visibility = View.VISIBLE
        bottomActionBar.translationY = 200f
        bottomActionBar.alpha = 0f
        bottomActionBar.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(260)
            .start()

        updateBottomFavoriteIconBasedOnSelection()
    }

    private fun exitSelectionMode() {
        if (!isSelectionMode) return
        isSelectionMode = false

        // Restore normal UI
        menuIcon.visibility = View.VISIBLE
        penIcon.visibility = View.VISIBLE
        backIcon.visibility = View.GONE
        fabAddTask.visibility = View.VISIBLE

        task1CheckBox.isChecked = false
        task2CheckBox.isChecked = false
        task1CheckBox.visibility = View.GONE
        task2CheckBox.visibility = View.GONE

        bottomActionBar.animate()
            .translationY(200f)
            .alpha(0f)
            .setDuration(180)
            .withEndAction { bottomActionBar.visibility = View.GONE }
            .start()

        selectAllText.text = "Select All"
        updateBottomFavoriteIconBasedOnSelection()
    }

    // Update "Select All"/"Deselect All" label
    private fun updateSelectAllLabel() {
        val allSelected = task1CheckBox.isChecked && task2CheckBox.isChecked
        selectAllText.text = if (allSelected) "Deselect All" else "Select All"
    }

    // Toggle favorite state for selected tasks
    private fun toggleFavoriteForSelected() {
        val faveColor = Color.parseColor("#FFF9C4")
        val normalColor = Color.WHITE

        if (task1CheckBox.isChecked && task1Container.visibility == View.VISIBLE) {
            task1IsFavorite = !task1IsFavorite
            task1Container.setBackgroundColor(if (task1IsFavorite) faveColor else normalColor)
            task1CheckBox.isChecked = false
        }

        if (task2CheckBox.isChecked && task2Container.visibility == View.VISIBLE) {
            task2IsFavorite = !task2IsFavorite
            task2Container.setBackgroundColor(if (task2IsFavorite) faveColor else normalColor)
            task2CheckBox.isChecked = false
        }

        updateSelectAllLabel()
    }

    // Delete selected tasks
    private fun deleteSelectedTasks() {
        if (task1CheckBox.isChecked && task1Container.visibility == View.VISIBLE) {
            task1Container.visibility = View.GONE
            task1CheckBox.isChecked = false
            task1IsFavorite = false
        }
        if (task2CheckBox.isChecked && task2Container.visibility == View.VISIBLE) {
            task2Container.visibility = View.GONE
            task2CheckBox.isChecked = false
            task2IsFavorite = false
        }
        updateSelectAllLabel()
    }

    // Update bottom favorite icon based on current state
    private fun updateBottomFavoriteIconBasedOnSelection() {
        if (!isSelectionMode) {
            val anyFavorite = task1IsFavorite || task2IsFavorite
            favoriteIcon.setImageResource(
                if (anyFavorite) R.drawable.ic_unfavorite else R.drawable.ic_star
            )
            return
        }

        val selectedTasks = mutableListOf<Boolean>()
        if (task1CheckBox.isChecked) selectedTasks.add(task1IsFavorite)
        if (task2CheckBox.isChecked) selectedTasks.add(task2IsFavorite)

        if (selectedTasks.isEmpty()) {
            favoriteIcon.setImageResource(R.drawable.ic_star)
            return
        }

        val allSelectedAreFavorite = selectedTasks.all { it }
        favoriteIcon.setImageResource(
            if (allSelectedAreFavorite) R.drawable.ic_unfavorite else R.drawable.ic_star
        )
    }

    // Simple fade animation for any view
    private fun fadeInView(view: View, duration: Long) {
        view.alpha = 0f
        view.animate().alpha(1f).setDuration(duration).start()
    }

    // Subtle toolbar scaling animation
    private fun scaleToolbar(toolbar: Toolbar) {
        toolbar.animate()
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(700)
            .withEndAction {
                toolbar.animate().scaleX(1f).scaleY(1f).setDuration(300).start()
            }
            .start()
    }
}