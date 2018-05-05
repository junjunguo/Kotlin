import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs/Rx';

import { UserLoginModel } from '../models/user-login.model';
import { AccessTokenModel } from '../models/access-token.model';
import { UserRegisterModel } from '../models/user-register.model';
import { LocalStorageRepository } from './../repositories/local-storage.repository';
import { AuthenticationRepository } from './../repositories/authentication.repository';

@Injectable()
export class AuthenticationService {

  isLoggedIn = new BehaviorSubject<boolean>(false);

  private authModel: AccessTokenModel;

  constructor(
    private authRepo: AuthenticationRepository,
    private localStoreRepo: LocalStorageRepository
  ) {
    this.localStoreRepo.getAccessToken()
      .then(res => {
        if (res) {
          this.authModel = res;
          this.isLoggedIn.next(true);
        } else
          this.logout();
      })
      .catch(err => {
        this.logout();
      });
  }

  getAuthModel(): AccessTokenModel {
    return this.authModel;
  }

  login(login: UserLoginModel): Observable<AccessTokenModel> {
    return this.authRepo.login(login)
      .do(res => {
        this.authModel = res;
        this.isLoggedIn.next(true);
        this.localStoreRepo.saveAccessToken(res);
      }, err => this.logout());
  }

  logout(): void {
    this.authModel = undefined;
    this.isLoggedIn.next(false);
    this.localStoreRepo.clearStorage();
  }

  register(model: UserRegisterModel): Observable<void> {
    return this.authRepo.register(model);
  }
}
