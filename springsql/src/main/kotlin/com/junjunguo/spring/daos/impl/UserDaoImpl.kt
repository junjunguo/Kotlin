//package com.junjunguo.spring.daos.impl
//
//import com.junjunguo.spring.daos.UserDao
//import com.junjunguo.spring.daos.UserRepository
//import com.junjunguo.spring.models.api.User
//import com.junjunguo.spring.models.entities.UserEntity
//import org.springframework.stereotype.Repository
//
//
//@Repository
////@NoRepositoryBean
//
////@Transactional
//class UserDaoImpl(
//    private var repo: UserRepository
//) : UserDao {
//
//    override fun add(p: User): UserEntity {
//        var u = UserEntity(0, p.email, p.name, "", null)
//        repo.save(u)
//        return u
//    }
//
//
//    override fun getAll(): MutableList<UserEntity> {
//        return repo.findAll()
//    }
//
//}