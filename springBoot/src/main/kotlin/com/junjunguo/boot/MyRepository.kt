package com.junjunguo.boot

import org.springframework.stereotype.Service

@Service
class MyRepository {

    private val persons: MutableList<Person> = ArrayList()

    fun add(person: Person) = persons.add(person)
    fun getAll() = persons
    fun getById(id: String) = persons.find { it.id == id }
    fun get(name: String) = persons.find { it.name == name }
    fun get(age: Int) = persons.find { it.age == age }
    fun update(person: Person): Person? {
        if (!person.id.isNullOrEmpty()) {
            val i = persons.indexOf(getById(person.id))
            if (i > -1) {
                persons[i] = person
                return person
            }
        }
        return null
    }

    fun delete(id: String): Person? {
        val p = getById(id) ?: return null
        persons.remove(p)
        return p
    }
}