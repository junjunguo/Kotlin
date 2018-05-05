import { Observable } from 'rxjs/Rx';
import { FriendModel } from './../models/friend.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserModel } from '../models/user.model';

@Injectable()
export class FriendRepository {

    readonly baseUrl = 'http://localhost:8080/friend/';

    constructor(private http: HttpClient) {
    }

    getFriends(): Observable<FriendModel[]> {
        return this.http.get<FriendModel[]>(`${this.http}all`);
    }

    addFriend(friendId: number): Observable<FriendModel> {
        return this.http.post<FriendModel>(`${this.baseUrl}add`, { friendId });
    }

    removeFriend(friendId: number): Observable<void> {
        return this.http.put<void>(`${this.baseUrl}remove`, { friendId });
    }

    confirmFriend(friendId: number): Observable<FriendModel> {
        return this.http.put<FriendModel>(`${this.baseUrl}confirm`, { friendId });
    }
}
