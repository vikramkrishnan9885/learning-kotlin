package com.vk.security.dao

import com.vk.security.models.Stormtrooper

interface StormtrooperDao {
    fun listStormtroopers(): Collection<Stormtrooper>
    fun getStormtrooper(id:String):Stormtrooper
    fun addStormtrooper(stormtrooper: Stormtrooper): Stormtrooper
    fun updateStormtrooper(id: String, stormtrooper: Stormtrooper): Stormtrooper
    fun deleteStormtrooper(id: String): Boolean
}