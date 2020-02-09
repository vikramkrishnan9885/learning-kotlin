package com.vk.kotlinlearning

import com.netflix.client.config.IClientConfig
import com.netflix.loadbalancer.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean


// Now we need to create one more configuration class for ribbon to mention the load balancing algorithm
// and health check. We will now use the default once provided by Ribbon,
// but in this class we can very well override those and add ours custom logic.
class RibbonConfiguration {

    @Autowired
    lateinit var config: IClientConfig

    @Bean
    fun ribbonPing(config: IClientConfig): IPing {
        return PingUrl()
    }

    @Bean
    fun ribbonRule(config: IClientConfig): IRule {
        //return AvailabilityFilteringRule()
        // https://stackoverflow.com/questions/44177345/loadbalancing-with-ribbon
        // https://github.com/Netflix/ribbon/wiki/Working-with-load-balancers#weightedresponsetimerule
        return WeightedResponseTimeRule()
    }
}