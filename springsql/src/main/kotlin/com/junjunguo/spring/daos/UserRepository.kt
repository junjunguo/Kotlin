package com.junjunguo.spring.daos

import com.junjunguo.spring.models.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long>