import { FriendModel } from './../models/friend.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserModel } from '../models/user.model';

@Injectable()
export class FriendRepository {

    readonly baseUrl = 'http://localhost:8080/friend/';

    constructor(private http: HttpClient) {
    }

    getFriends() {
        return this.http.get<FriendModel[]>(`${this.http}all`)
    }

    addFriend(friendId: number) {
        return this.http.post<FriendModel>(`${this.baseUrl}add`, { friendId: friendId })
    }

    removeFriend(friendId: number) {
        return this.http.put<void>(`${this.baseUrl}remove`, { friendId: friendId })
    }

    confirmFriend(friendId: number) {
        return this.http.put<FriendModel>(`${this.baseUrl}confirm`, { friendId: friendId })
    }
}