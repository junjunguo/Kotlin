package com.junjunguo.backend.repositories

import com.junjunguo.backend.models.entities.UserBaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Transactional(readOnly = true)
@Repository
interface UserRepository : JpaRepository<UserBaseEntity, Long> {

    @Query("SELECT u FROM UserBaseEntity u where u.email = ?1")
    fun findByEmail(email: String): Optional<UserBaseEntity>

    @Query("SELECT u FROM UserBaseEntity u where u.name = ?1")
    fun findByName(name: String): Optional<UserBaseEntity>
}
