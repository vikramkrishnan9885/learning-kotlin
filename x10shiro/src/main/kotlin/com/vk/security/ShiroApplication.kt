package com.vk.security

import org.apache.shiro.cache.CacheManager
import org.apache.shiro.cache.MemoryConstrainedCacheManager
import org.apache.shiro.realm.Realm
import org.apache.shiro.realm.text.PropertiesRealm
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class ShiroApplication {

	val log: Logger = LoggerFactory.getLogger(ShiroApplication::class.java)

	@Bean
	public fun realm():Realm {
		// uses 'classpath:shiro-users.properties' by default
		val propertiesRealm: PropertiesRealm = PropertiesRealm()

		// Caching isn't needed in this example, but we can still turn it on
		propertiesRealm.setCachingEnabled(true);
		return propertiesRealm
	}
	// ANOTHER TYPE OF REALM
	// @Bean
	// public Realm realm() {
	//     TextConfigurationRealm realm = new TextConfigurationRealm();
	//     realm.setUserDefinitions("joe.coder=password,user\n" +
	//             "jill.coder=password,admin");
	// 
	//     realm.setRoleDefinitions("admin=read,write\n" +
	//             "user=read");
	//     realm.setCachingEnabled(true);
	//     return realm;
	// }

	@Bean
	fun shiroFilterChainDefinition(): ShiroFilterChainDefinition? {
		val chainDefinition = DefaultShiroFilterChainDefinition()
		// use permissive to NOT require authentication, our controller Annotations will decide that
		chainDefinition.addPathDefinition("/**", "authcBasic[permissive]")
		return chainDefinition
	}

	@Bean
	fun cacheManager(): CacheManager? { 
		// Caching isn't needed in this example, but we will use the MemoryConstrainedCacheManager for this example.
		return MemoryConstrainedCacheManager()
	}
}

fun main(args: Array<String>) {
	runApplication<ShiroApplication>(*args)
}
