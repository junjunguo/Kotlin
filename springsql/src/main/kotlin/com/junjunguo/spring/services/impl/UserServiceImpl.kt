package com.junjunguo.spring.services.impl

import com.junjunguo.spring.daos.UserDao
import com.junjunguo.spring.models.api.User
import com.junjunguo.spring.services.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private var dao: UserDao) : UserService {
    override fun getById(id: Long): User {
        val u = dao.getById(id)
        return User(u.id, u.email, u.name, u.modified, u.created)
    }

    override fun delete(id: Long) = dao.delete(id)

    override fun edit(user: User): User {
        val u = dao.edit(user)
        return User(u.id, u.email, u.name, u.modified, u.created)
    }

    override fun add(p: User): User {
        val u = dao.add(p)
        return User(u.id, u.email, u.name, u.modified, u.created)
    }

    override fun getAll() = dao.getAll().map { it -> User(it.id, it.email, it.name, it.modified, it.created) }

}