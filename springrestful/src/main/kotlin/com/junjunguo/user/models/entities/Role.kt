package com.junjunguo.user.models.entities

import com.junjunguo.user.models.enums.RoleEnum
import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne()
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    @Column(columnDefinition = "smallint")
    var role: RoleEnum
)