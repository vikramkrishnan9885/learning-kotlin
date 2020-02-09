package com.vk.kotlinlearning

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Level
import java.util.logging.Logger

@RestController
@EnableHystrix
@SpringBootApplication
class HystrixServerApplication {

	companion object {

		@JvmStatic
		val LOG:Logger = Logger.getLogger(HystrixServerApplication::class.java.name)

		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<HystrixServerApplication>(*args)
		}

	}

	@Throws(InterruptedException::class)
	@RequestMapping("/")
	@HystrixCommand(
			fallbackMethod = "helloFallback"
			, commandProperties = arrayOf(
				HystrixProperty(
						name = "execution.isolation.thread.timeoutInMilliseconds"
						, value = "6000"
				),
				HystrixProperty(
						name = "circuitBreaker.errorThresholdPercentage"
						, value="60"
				)
			)
	)
	fun hello():String {
		val max = 10
		val min = 0
		val timeSleep = ((Math.random()*(max -min + 1) + min)*1000).toLong()
		LOG.log(Level.INFO,timeSleep.toString())
		// Log before thread sleep otherwise it won't print because fallbackMethod gets invoked
		Thread.sleep(timeSleep)

		return "Welcome Hystrix"
	}

	fun helloFallback(): String {
		return "Request fails. It takes long time to response"
	}
}