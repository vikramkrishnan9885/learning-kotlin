package hellocucumber

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import junit.framework.Assert.assertEquals

fun isItFriday(today: String)= if (today == "Friday") "TGIF" else "Nope"

class StepDefinitions {

    private lateinit var today: String
    private lateinit var actualAnswer: String

    @Given("today is {string}")
    fun today_is(today: String) {
        this.today = today
    }

    @When("I ask whether it's Friday yet")
    fun i_ask_whether_it_s_Friday_yet() {
        actualAnswer = isItFriday(today)
    }

    @Then("I should be told {string}")
    fun i_should_be_told(expectedAnswer: String) {
        assertEquals(expectedAnswer, actualAnswer)
    }

}