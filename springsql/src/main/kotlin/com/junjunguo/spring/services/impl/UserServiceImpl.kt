package com.junjunguo.spring.services.impl

import com.junjunguo.spring.daos.UserDao
import com.junjunguo.spring.models.api.User
import com.junjunguo.spring.services.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(private var dao: UserDao) : UserService {

    // use repository
//    override fun add(p: User): User {
//        var u = UserEntity(null, p.email, p.name, "")
//        repo.save(u)
//        return User(u.id, u.email, u.name)
//    }
//
//    override fun getAll(): List<User> {
//        return repo.findAll().map { it -> User(it.id, it.email, it.name) }
//    }

    override fun add(p: User): User {
        val u = dao.add(p)
        return User(u.id, u.email, u.name)

    }

    override fun getAll() = dao.getAll().map { it -> User(it.id, it.email, it.name) }

}