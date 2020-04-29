package hellocucumber

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import junit.framework.Assert.assertEquals

// fun isItFriday(today: String) = "Nope"
fun isItFriday(today: String)= if (today == "Friday") "TGIF" else "Nope"

class StepDefinitions {

    private lateinit var today: String
    private lateinit var actualAnswer: String


    @Given("today is Sunday")
    fun today_is_Sunday() {

        today = "Sunday"
        // Write code here that turns the phrase above into concrete actions
        //throw PendingException()
    }

    @When("I ask whether it's Friday yet")
    fun i_ask_whether_it_s_Friday_yet() {

        actualAnswer = isItFriday(today)

        // Write code here that turns the phrase above into concrete actions
        // throw PendingException()
    }

    @Then("I should be told {string}")
    fun i_should_be_told(expectedAnswer: String?) {

        assertEquals(expectedAnswer, actualAnswer)

        // Write code here that turns the phrase above into concrete actions
        // throw PendingException()
    }

    @Given("today is Friday")
    fun today_is_Friday() {
        today = "Friday"
    }


}