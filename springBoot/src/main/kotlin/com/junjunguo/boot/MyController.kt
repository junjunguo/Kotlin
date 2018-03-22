package com.junjunguo.boot

import org.springframework.web.bind.annotation.*

@RestController
class MyController(private val repository: MyRepository) {

    @GetMapping("/all")
    fun findAll() = repository.getAll()

    @GetMapping("/find/{value}")
    fun find(@PathVariable value: String): Person? {
        val p = repository.get(value)
        if (p != null) return p
        else if (value.toIntOrNull() != null) return repository.get(value.toInt())
        return null
    }

    @PostMapping("/add")
    fun add(@RequestBody person: Person): Person {
        this.repository.add(person)
        return person
    }

    @PostMapping("/add/{name}")
    fun addByName(@PathVariable name: String): Person {
        val p = Person(name, 1)
        repository.add(p)
        return p
    }

    @PutMapping("/update")
    fun update(@RequestBody person: Person): Person? {
        return this.repository.update(person)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: String) = repository.delete(id)

}