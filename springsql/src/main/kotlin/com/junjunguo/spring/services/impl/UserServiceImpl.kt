package com.junjunguo.spring.services.impl

import com.junjunguo.spring.daos.UserRepository
import com.junjunguo.spring.models.api.User
import com.junjunguo.spring.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var repo: UserRepository

    override fun getAll(): List<User> {
        return repo.findAll().map { it -> User(it.id, it.email, it.name) }
    }
}