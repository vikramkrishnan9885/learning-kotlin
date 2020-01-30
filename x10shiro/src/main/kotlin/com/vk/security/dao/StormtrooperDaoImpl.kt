package com.vk.security.dao

import com.vk.security.models.Stormtrooper
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Repository
import java.security.SecureRandom
import java.util.*

@Repository
class StormtrooperDaoImpl: StormtrooperDao {

    private val trooperTypes = arrayOf("Basic", "Space", "Aquatic", "Marine", "Jump", "Sand")
    private val planetsList = arrayOf("Coruscant", "Tatooine", "Felucia", "Hoth", "Naboo", "Serenno")
    private val speciesList = arrayOf("Human", "Kel Dor", "Nikto", "Twi'lek", "Unidentified")
    private val RANDOM: Random = SecureRandom()

    private val trooperMap: MutableMap<String, Stormtrooper> = Collections.synchronizedSortedMap(TreeMap())

    fun DefaultStormtrooperDao() {
        for (i in 0..49) {
            addStormtrooper(randomTrooper())
        }
    }

    override fun listStormtroopers(): Collection<Stormtrooper> {
        return Collections.unmodifiableCollection(trooperMap.values)
    }

    override fun getStormtrooper(id: String): Stormtrooper {
        return trooperMap[id]!!
    }

    override fun addStormtrooper(stormtrooper: Stormtrooper): Stormtrooper {
        if (stormtrooper.id == null || stormtrooper.id.trim { it <= ' ' }.isEmpty()) {
            stormtrooper.id = generateRandomId()
        }
        trooperMap[stormtrooper.id] = stormtrooper
        return stormtrooper
    }

    override fun updateStormtrooper(id: String, stormtrooper: Stormtrooper): Stormtrooper { // we are just backing with a map, so just call add.
        return addStormtrooper(stormtrooper)
    }

    override fun deleteStormtrooper(id: String): Boolean {
        return trooperMap.remove(id) != null
    }


    ///////////////////////////////////
    //  Dummy data generating below  //
    ///////////////////////////////////


    private fun randomTrooper(id: String): Stormtrooper {
        val planet = planetsList[RANDOM.nextInt(planetsList.size)]
        val species = speciesList[RANDOM.nextInt(speciesList.size)]
        val type = trooperTypes[RANDOM.nextInt(trooperTypes.size)]
        return Stormtrooper(id, planet, species, type)
    }

    private fun generateRandomId(): String { // HIGH chance of collisions, but, this is all for fun...
        return "FN-" + java.lang.String.format("%04d", RANDOM.nextInt(9999))
    }

    private fun randomTrooper(): Stormtrooper {
        return randomTrooper(generateRandomId())
    }

}