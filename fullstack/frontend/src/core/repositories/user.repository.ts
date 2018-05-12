import { Enviroment } from "./../system/enviroments";
import { Observable } from "rxjs/Rx";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UserModel } from "../models/user.model";

@Injectable()
export class UserRepository {
  readonly baseUrl = new Enviroment().baseUrl;

  constructor(private http: HttpClient) {}

  getUserModel(): Observable<UserModel> {
    return this.http.get<UserModel>(`${this.baseUrl}info`);
  }

  updateUser(user: UserModel): Observable<UserModel> {
    return this.http.put<UserModel>(`${this.baseUrl}update`, user);
  }

  getAllUsers(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(`${this.baseUrl}all`);
  }
}
