
export interface OAuth2AccessToken {
    /**
     * The access token issued by the authorization server. This value is REQUIRED.
     */
    access_token: String;

	/**
	 * The type of the token issued as described in <a
	 * href="http://tools.ietf.org/html/draft-ietf-oauth-v2-22#section-7.1">Section 7.1</a>. Value is case insensitive.
	 * This value is REQUIRED.
	 */
    token_type: String;
	/**
	 * The refresh token which can be used to obtain new access tokens using the same authorization grant as described
	 * in <a href="http://tools.ietf.org/html/draft-ietf-oauth-v2-22#section-6">Section 6</a>. This value is OPTIONAL.
	 */
    refresh_token: String;
    /**
     * The lifetime in seconds of the access token. 
     * For example, the value "3600" denotes that the access token will expire in one hour from the time the response was generated.
     */
    expires_in: number;
	/**
	 * The scope of the access token as described by <a
	 * href="http://tools.ietf.org/html/draft-ietf-oauth-v2-22#section-3.3">Section 3.3</a>
	 */
    scope: String;

    jti: String;
}