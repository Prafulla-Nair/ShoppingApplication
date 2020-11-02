package com.example.shoppingapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class SplashscreenActivityTest {

    @Test
    fun splashscreenActivityTest() {
        ActivityScenario.launch(SplashscreenActivity::class.java)

        Espresso.onView(ViewMatchers.withText("Shopping Application"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
