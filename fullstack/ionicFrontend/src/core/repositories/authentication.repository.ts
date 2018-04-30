import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import { UserLoginModel } from '../models/UserLoginModel';
import { OAuth2AccessToken } from '../models/SecurityModels';
import { UserRegisterModel } from '../models/UserRegisterModel';

@Injectable()
export class AuthenticationRepository {

  private token: String;
  private refreshToken: String;

  public readonly baseUrl = "http://localhost:8080/";
  public readonly authUrl = this.baseUrl + "auth/token";
  public readonly clientId = "testClientId";
  public readonly clientSecret = "testClientSecret";
  public basicHeader = btoa(this.clientId + ':' + this.clientSecret)

  constructor(public httpClient: HttpClient) {
  }

  login(login: UserLoginModel) {

    const headers = new HttpHeaders()
      .set("Authorization", "Basic " + this.basicHeader)
      .set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")

    const body = `grant_type=password&username=${login.username}&password=${login.password}`

    return this.httpClient.post<OAuth2AccessToken>(this.authUrl, body, { headers: headers });
  }

  register(model: UserRegisterModel) {
    return this.httpClient.post<void>(this.baseUrl + "auth/register", model);
  }
}
