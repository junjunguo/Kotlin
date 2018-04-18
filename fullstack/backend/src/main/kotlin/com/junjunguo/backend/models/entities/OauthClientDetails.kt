package com.junjunguo.backend.models.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "oauth_client_details")
data class OauthClientDetails(

    @Id
    @Column(name = "client_id", length = 256)
    var clientId: String?,

    @Column(name = "resource_ids", nullable = true, length = 256)
    var resourceIds: String?,

    @Column(name = "client_secret", nullable = true, length = 256)
    var clientSecret: String?,

    @Column(name = "scope", nullable = true, length = 256)
    var scope: String?,

    @Column(name = "authorized_grant_types", nullable = true, length = 256)
    var authorizedGrantTypes: String?,

    @Column(name = "web_server_redirect_uri", nullable = true, length = 256)
    var webServerRedirectUri: String?,

    @Column(name = "authorities", nullable = true, length = 256)
    var authorities: String?,

    @Column(name = "access_token_validity", nullable = true)
    var accessTokenValidity: Int?,

    @Column(name = "refresh_token_validity", nullable = true)
    var refreshTokenValidity: Int?,


    @Column(name = "additional_information", nullable = true, length = 4096)
    var additionalInformation: String?,

    @Column(name = "autoapprove", nullable = true, length = 256)
    var autoapprove: String?
) {
    constructor() : this(null, null, null, null, null, null, null, null, null, null, null)
}