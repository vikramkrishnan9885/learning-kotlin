package com.vk.security.controllers

import com.vk.security.dao.StormtrooperDao
import com.vk.security.models.Stormtrooper
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(
        path = arrayOf("/troopers"),
        produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
)
class StormController {

    @Autowired
    lateinit var stormtrooperDao: StormtrooperDao


    @GetMapping
    @RequiresPermissions("troopers:read")
    fun listTroopers(): Collection<Stormtrooper> {
        return stormtrooperDao.listStormtroopers()
    }

    @GetMapping(path = ["/{id}"])
    @RequiresPermissions("troopers:read")
    @Throws(NotFoundException::class)
    fun getTrooper(@PathVariable("id") id: String): Stormtrooper {
        return stormtrooperDao.getStormtrooper(id)
    }

    @PostMapping
    @RequiresPermissions("troopers:create")
    fun createTrooper(@RequestBody trooper: Stormtrooper): Stormtrooper? {
        return stormtrooperDao.addStormtrooper(trooper)
    }

    @PostMapping(path = ["/{id}"])
    @RequiresPermissions("troopers:update")
    @Throws(NotFoundException::class)
    fun updateTrooper(
            @PathVariable("id") id: String,
            @RequestBody updatedTrooper: Stormtrooper
    ): Stormtrooper {
        return stormtrooperDao.updateStormtrooper(id, updatedTrooper)
    }


    @DeleteMapping(path = ["/{id}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequiresPermissions("troopers:delete")
    fun deleteTrooper(
            @PathVariable("id")  id: String
    ) {
        stormtrooperDao.deleteStormtrooper(id)
    }
}