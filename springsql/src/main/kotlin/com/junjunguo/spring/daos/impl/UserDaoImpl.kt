package com.junjunguo.spring.daos.impl

import com.junjunguo.spring.daos.UserDao
import com.junjunguo.spring.models.api.User
import com.junjunguo.spring.models.entities.UserEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Repository
@Transactional
class UserDaoImpl : UserDao {

    @PersistenceContext
    private lateinit var em: EntityManager

    override fun add(p: User): UserEntity {
        var u = UserEntity(null, p.email, p.name, "")
        em.persist(u)
        return u
    }

    override fun getAll(): MutableList<UserEntity> {
        val cq = em.criteriaBuilder.createQuery(UserEntity::class.java)
        cq.select(cq.from(UserEntity::class.java))
        return em.createQuery(cq).resultList
    }

}