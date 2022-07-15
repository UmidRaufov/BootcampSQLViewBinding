package uz.silence.bootcampsqlviewbinding.MB

import uz.silence.bootcampsqlviewbinding.CLASS.Bootcamp

interface DatabaseService {

    fun addBasic(bootcamp: Bootcamp)

    fun deleteBasic(bootcamp: Bootcamp)

    fun updateBasic(bootcamp: Bootcamp):Int

    fun getBasicById(id: Int): Bootcamp

    fun getAllBasic(): ArrayList<Bootcamp>

}