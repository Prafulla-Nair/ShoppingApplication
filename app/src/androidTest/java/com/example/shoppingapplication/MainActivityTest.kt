package com.example.shoppingapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Test
    fun mainActivityTest() {

        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(withId(R.id.productsRecyclerview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val showCartButton =

            Espresso.onView(withId(R.id.showCart))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        showCartButton.perform(ViewActions.click())

        val backButton =
            Espresso.onView(withId(R.id.toolbar))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        backButton.perform(ViewActions.click())
    }
}
