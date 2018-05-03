import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserModel } from '../models/user.model';

@Injectable()
export class UserRepository {

    readonly baseUrl = 'http://localhost:8080/user/';

    constructor(private http: HttpClient) {
    }

    getUserModel() {
        return this.http.get<UserModel>(`${this.baseUrl}info`)
    }

    updateUser(user: UserModel) {
        return this.http.put<UserModel>(`${this.baseUrl}update`, user);
    }
}