import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserModel } from '../models/user.model';

@Injectable()
export class UserRepository {

    readonly baseUrl = 'http://localhost:8080/';

    constructor(private http: HttpClient) {
    }

    getUserModel(){
        return this.http.get<UserModel>(`${this.baseUrl}info`)
    }
}