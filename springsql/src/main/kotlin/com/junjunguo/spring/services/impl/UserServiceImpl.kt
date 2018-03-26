package com.junjunguo.spring.services.impl

import com.junjunguo.spring.daos.UserRepository
import com.junjunguo.spring.models.api.User
import com.junjunguo.spring.models.entities.UserEntity
import com.junjunguo.spring.services.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private var dao: UserRepository) : UserService {
    override fun add(p: User): User {
        var u = UserEntity(null, p.email, p.name, "")
        dao.save(u)
//        return User(u.id, u.email, u.name, u.created, u.modified)
        return User(u.id, u.email, u.name)
    }

    override fun getAll(): List<User> {
//        return dao.findAll().map { it -> User(it.id, it.email, it.name, it.modified, it.created) }
        return dao.findAll().map { it -> User(it.id, it.email, it.name) }
    }
}