package com.example.entrevoisins_mvvm;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.entrevoisins_mvvm.utils.CustomViewAction;
import com.example.entrevoisins_mvvm.view.list.ListNeighboursActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)

public class NeighboursListTest {

    @Rule
    public ActivityScenarioRule<ListNeighboursActivity> mActivityScenarioRule =
        new ActivityScenarioRule<>(ListNeighboursActivity.class);

    @Test
    public void myNeighboursList_shouldHaveOneNeighbour() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
            .check(matches(hasChildCount(1)));
    }

    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
            .check(matches(hasChildCount(1)));

        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, CustomViewAction.clickChildViewWithId(R.id.item_list_delete_button)));

        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
            .check(matches(hasChildCount(0)));
    }

    @Test
    public void onClickOnListItem_shouldReturnDetailActivity() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
            .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.activity_neighbour_details))
            .check(matches(isDisplayed()));
    }

    @Test
    public void displayedNeighbourInfo_displayedNeighbourName_shouldMatch() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
            .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.neighbourProfilePicName))
            .check(matches(withText("Caroline")));
    }

    @Test
    public void onFavTabClick_getFavoriteNeighbours_favListIsDisplayed() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
            .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.addFavoriteFab))
            .perform(click());

        pressBack();

        onView(withContentDescription("Favorites"))
            .perform(click());

        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
            .check(matches(hasChildCount(1)));

        onView(allOf(withId(R.id.item_list_name), isDisplayed()))
            .check(matches(withText("Caroline")));
    }

    @Test
    public void onAddingNewNeighbour_shouldAddNewNeighbour() {
        onView(withId(R.id.add_neighbour_fab)).perform(click());

        onView(withId(R.id.name)).perform(typeText("Freddy"));
        onView(withId(R.id.phoneNumber)).perform(typeText("0123456789"));
        onView(withId(R.id.address)).perform(typeText("123rd, Avenue Street"));
        onView(withId(R.id.aboutMe)).perform(typeText("I like oranges"));
        closeSoftKeyboard();

        onView(withId(R.id.create)).perform(click());

        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
            .check(matches(hasChildCount(2)));
    }
}