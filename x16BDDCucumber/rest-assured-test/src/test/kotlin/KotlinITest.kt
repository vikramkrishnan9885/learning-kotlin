// based on: https://github.com/rest-assured/rest-assured/blob/master/examples/kotlin-example/src/test/kotlin/io.restassured.kotlin/KotlinITest.kt
// also see https://ryanharrison.co.uk/2019/02/10/testing-restful-services-kotlin-with-rest-assured.html
// https://github.com/rest-assured/rest-assured/wiki/Usage
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class KotlinITest {

    lateinit var webServer: MockWebServer

    @Before
    fun `mock web server is started`() {
        webServer = MockWebServer()
        webServer.start()

        RestAssured.port = webServer.port
    }

    @After
    fun `shutdown webserver after each test`() {
        webServer.shutdown()
        RestAssured.reset()
    }

    @Test
    fun `kotlin extension can validate single expecation for json bodies in 'Then' block when no 'Extract' block is defined`() {
        // Given
        givenServerWillReturnJson(""" { "greeting" : "Hello World" } """)

        // When
        When {
            get("/greeting")
        } Then {
            body("greeting", not(emptyOrNullString()))
        }
    }

    @Test
    fun `kotlin extension can validate multiple expectations for json bodies in 'Then' block when no 'Extract' block is defined`() {
        // Given
        givenServerWillReturnJson(""" { "greeting" : "Hello World", "other" : "thing", "something" : "else" } """)

        // When
        When {
            get("/greeting")
        } Then {
            body("greeting", equalTo("Hello World"))
            body("other", equalTo("thing"))
            body("something", equalTo("else"))
        }
    }

    @Test
    fun `kotlin extension can validate json bodies in 'Then' block when 'Extract' block is defined with different path than expectation`() {
        // Given
        givenServerWillReturnJson(""" { "greeting" : "Hello World", "other" : "thing" } """)

        // When
        val greeting : String = When {
            get("/greeting")
        } Then {
            body("greeting", not(emptyOrNullString()))
        } Extract {
            path("greeting")
        }

        // Then
        assertEquals("Hello World", greeting)
    }

    @Test
    fun `kotlin extension can validate json bodies in 'Then' block when 'Extract' block is defined with same path as expectation`() {
        // Given
        givenServerWillReturnJson(""" { "greeting" : "Hello World", "other" : "thing" } """)

        // When
        val other : String = When {
            get("/greeting")
        } Then {
            body("greeting", not(emptyOrNullString()))
        } Extract {
            path("other")
        }

        // Then
        assertEquals("thing", other)
    }

    private fun givenServerWillReturnJson(jsonString: String) {
        val response = MockResponse()
        response.setBody(jsonString)
        response.setHeader("content-type", "application/json")
        webServer.enqueue(response)
    }
}