import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import { Subject, BehaviorSubject } from 'rxjs/Rx';

import { UserLoginModel } from '../models/user-login.model';
import { AccessTokenModel } from '../models/access-token.model';
import { UserRegisterModel } from '../models/user-register.model';
import { LocalStorageRepository } from './../repositories/local-storage.repository';
import { AuthenticationRepository } from './../repositories/authentication.repository';

@Injectable()
export class AuthenticationService {

  private authModel: AccessTokenModel;

  isLoggedIn = new BehaviorSubject<boolean>(false);

  constructor(
    private authRepo: AuthenticationRepository,
    private localStoreRepo: LocalStorageRepository
  ) {
    this.localStoreRepo.getAccessToken()
      .then(res => {
        if (res) {
          this.authModel = JSON.parse(res);
          this.isLoggedIn.next(true);
        } else {
          this.logout();
        }
      }).catch(err => {
        this.logout();
      });
  }

  getAuthModel() {
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

  logout() {
    this.authModel = null;
    this.isLoggedIn.next(false);
    this.localStoreRepo.clearStorage();
  }

  register(model: UserRegisterModel): Observable<void> {
    return this.authRepo.register(model);
  }
}
