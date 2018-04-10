package com.junjunguo.user.system.securities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.AuthorizationRequest
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler

class UserApprovalHandler: ApprovalStoreUserApprovalHandler() {

    private var useApprovalStore = true

    @Autowired
    private lateinit var clientDetailsService: ClientDetailsService

    /**
     * Service to load client details (optional) for auto approval checks.
     *
     * @param clientDetailsService a client details service
     */
    override fun setClientDetailsService(clientDetailsService: ClientDetailsService) {
        this.clientDetailsService = clientDetailsService
        super.setClientDetailsService(clientDetailsService)
    }

    /**
     * @param useApprovalStore the useTokenServices to set
     */
    fun setUseApprovalStore(useApprovalStore: Boolean) {
        this.useApprovalStore = useApprovalStore
    }

    /**
     * Allows automatic approval for a white list of clients in the implicit grant case.
     *
     * @param authorizationRequest The authorization request.
     * @param userAuthentication the current user authentication
     *
     * @return An updated request if it has already been approved by the current user.
     */
    override fun checkForPreApproval(
        authorizationRequest: AuthorizationRequest,
        userAuthentication: Authentication
    ): AuthorizationRequest {
        var authorizationRequest = authorizationRequest

        var approved = false
        // If we are allowed to check existing approvals this will short circuit the decision
        if (useApprovalStore) {
            authorizationRequest = super.checkForPreApproval(authorizationRequest, userAuthentication)
            approved = authorizationRequest.isApproved
        } else {
            if (clientDetailsService != null) {
                val requestedScopes = authorizationRequest.scope
                try {
                    val client = clientDetailsService!!
                        .loadClientByClientId(authorizationRequest.clientId)
                    for (scope in requestedScopes) {
                        if (client.isAutoApprove(scope)) {
                            approved = true
                            break
                        }
                    }
                } catch (e: ClientRegistrationException) {
                }

            }
        }
        authorizationRequest.isApproved = approved

        return authorizationRequest

    }
}