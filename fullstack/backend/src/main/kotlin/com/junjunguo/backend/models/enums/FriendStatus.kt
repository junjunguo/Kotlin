package com.junjunguo.backend.models.enums

/**
 * How does it work ?
 * 1. pending first second: first send request waiting for second to answer.
 * 2. pending second first: second send request waiting for first to answer.
 * 3. friend: if the respondent has confirmed.
 * 4. there no 4, the respondent will confirm the request or delete the record.
 * 5. to remove the relation: remove the record.
 */
enum class FriendStatus(val value: Int) {
    PENDING_FIRST_SECOND(0),
    PENDING_SECOND_FIRST(1), FRIEND(2)
}

