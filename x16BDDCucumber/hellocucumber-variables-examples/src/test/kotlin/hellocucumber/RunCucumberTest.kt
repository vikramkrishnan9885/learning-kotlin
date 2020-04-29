package hellocucumber

import io.cucumber.junit.CucumberOptions
import io.cucumber.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
// https://forum.katalon.com/t/how-to-run-multiple-feature-files-in-a-specific-sequence-using-cucumber-runner-class/24327
@CucumberOptions(
        // https://www.jvt.me/posts/2019/04/07/prettier-cucumber-jvm-html-reports/
        // https://www.toolsqa.com/selenium-cucumber-framework/cucumber-reports/
        plugin = ["pretty","html:CucumberReports", "json:CucumberReports/Cucumber.json","junit:CucumberReports/Cucumber.xml"],
        // Removed "usage" io.cucumber.core.exception.CucumberException: Only one plugin can use STDOUT, now both pretty and usage use it. If you use more than one plugin you must specify output path with PLUGIN:PATH_OR_URL
        features = ["src/test/resources/hellocucumber/is_it_friday_yet.feature"]

)
class RunCucumberTest

