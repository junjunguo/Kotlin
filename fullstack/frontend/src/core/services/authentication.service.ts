import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, Subject } from "rxjs/Rx";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";

import { UserLoginModel } from "../models/user-login.model";
import { AccessTokenModel } from "../models/access-token.model";
import { UserRegisterModel } from "../models/user-register.model";
import { LocalStorageRepository } from "./../repositories/local-storage.repository";
import { AuthenticationRepository } from "./../repositories/authentication.repository";

@Injectable()
export class AuthenticationService {
  isLoggedIn = new BehaviorSubject<boolean>(false);

  private authModel: AccessTokenModel;

  constructor(
    private authRepo: AuthenticationRepository,
    private localStoreRepo: LocalStorageRepository
  ) {
    this.localStoreRepo
      .getAccessToken()
      .then(res => {
        if (res) {
          this.authModel = JSON.parse(res);
          this.isLoggedIn.next(true);
        } else this.logout();
      })
      .catch(err => {
        this.logout();
      });
  }

  accessTokenExpired() {
    this.authModel.access_token = undefined;
  }

  getAuthModel(): AccessTokenModel {
    return this.authModel;
  }

  login(login: UserLoginModel): Observable<AccessTokenModel> {
    return this.authRepo.login(login).do(
      res => {
        this.authModel = res;
        this.isLoggedIn.next(true);
        this.localStoreRepo.saveAccessToken(res);
      },
      err => this.logout()
    );
  }

  renewAccessToken(): Observable<AccessTokenModel> {
    if (!this.authModel || !this.authModel.refresh_token)
      return Observable.of(undefined);
    else
      return this.authRepo
        .renewAccessToken(this.authModel.refresh_token)
        .do(res => {
          this.authModel = res;
          this.isLoggedIn.next(true);
          this.localStoreRepo.saveAccessToken(this.authModel);
        });
  }

  logout(): void {
    this.isLoggedIn.next(false);
    this.authModel = undefined;
    this.localStoreRepo.clearStorage();
  }

  register(model: UserRegisterModel): Observable<void> {
    return this.authRepo.register(model);
  }
}
