package com.junjunguo.user.repositories

import com.junjunguo.user.models.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
interface UserRepository : JpaRepository<UserEntity, Long>