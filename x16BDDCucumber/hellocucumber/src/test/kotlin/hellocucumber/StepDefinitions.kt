package hellocucumber

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When


class StepDefinitions {

    @Given("today is Sunday")
    fun today_is_Sunday() { // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @When("I ask whether it's Friday yet")
    fun i_ask_whether_it_s_Friday_yet() { // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Then("I should be told {string}")
    fun i_should_be_told(string: String?) { // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }


}