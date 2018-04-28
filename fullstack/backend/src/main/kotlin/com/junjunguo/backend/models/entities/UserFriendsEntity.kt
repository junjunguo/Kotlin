package com.junjunguo.backend.models.entities

import com.junjunguo.backend.models.enums.FriendStatus
import org.hibernate.annotations.Check
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(
    name = "user_friends",
    uniqueConstraints = [(UniqueConstraint(columnNames = ["user_first_id", "user_second_id"]))]
)
@Check(constraints = "user_first_id < user_second_id")
data class UserFriendsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne
    @JoinColumn(name = "user_first_id")
    var firstUser: UserBaseEntity,

    @ManyToOne
    @JoinColumn(name = "user_second_id")
    var secondUser: UserBaseEntity,

    @Column(name = "status", columnDefinition = "smallint")
    var status: FriendStatus,

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    var createdDate: Date = Date(System.currentTimeMillis()),

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "confirmed_date", nullable = true)
    var confirmedDate: Date?
) {
    constructor(firstUser: UserBaseEntity, secondUser: UserBaseEntity, status: FriendStatus) : this(
        null,
        firstUser,
        secondUser,
        status,
        Date(System.currentTimeMillis()),
        null
    )
}