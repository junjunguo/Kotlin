package com.junjunguo.backend.repositories

import com.junjunguo.backend.models.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Transactional(readOnly = true)
@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u where u.email = ?1")
    fun findByEmail(email: String): Optional<UserEntity>

    @Query("SELECT u FROM UserEntity u where u.name = ?1")
    fun findByName(name: String): Optional<UserEntity>
}
