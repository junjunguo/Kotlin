package com.junjunguo.backend.repositories

import com.junjunguo.backend.models.entities.UserFriendsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Transactional(readOnly = true)
@Repository
interface FriendRepository : JpaRepository<UserFriendsEntity, Long> {

    @Query("SELECT f FROM UserFriendsEntity f WHERE f.firstUser.id = :userId OR f.secondUser.id = :userId")
    fun findFriends(@Param("userId") userId: Long): Optional<List<UserFriendsEntity>>

    /**
     * smallId must < biggerId
     */
    @Query("SELECT f from UserFriendsEntity f WHERE f.firstUser.id= :smallId AND f.secondUser.id = :biggerId")
    fun findFriend(@Param("smallId") smallId: Long, @Param("biggerId") biggerId: Long): Optional<UserFriendsEntity>
}
