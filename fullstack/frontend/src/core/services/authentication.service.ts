import { LocalStorageRepository } from './../repositories/local-storage.repository';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { AuthenticationRepository } from './../repositories/authentication.repository';
import { AccessTokenModel } from '../models/access-token.model';
import { UserLoginModel } from '../models/user-login.model';
import { UserRegisterModel } from '../models/user-register.model';
import { Subject } from 'rxjs/Rx';

@Injectable()
export class AuthenticationService {

  private authModel: AccessTokenModel;

  constructor(private authRepo: AuthenticationRepository, private localStoreRepo: LocalStorageRepository) {
  }

  isLoggedin() {
    const subject = new Subject<boolean>();

    if (this.authModel && this.authModel.refresh_token) subject.next(true);

    const obs = new Observable();
    this.localStoreRepo.getAccessToken()
      .then((res) => {
        if (!res) subject.next(false);
        else {
          this.authModel = JSON.parse(res);
          subject.next(true);
        }
      }).catch(err => {
        this.localStoreRepo.clearStorage();
        subject.next(false);
      });
    return subject;
  }

  login(login: UserLoginModel): Observable<AccessTokenModel> {
    return this.authRepo.login(login)
      .do(res => {
        this.authModel = res;
        this.localStoreRepo.saveAccessToken(res);
      });
  }

  logout() {
    this.authModel = null;
    this.localStoreRepo.clearStorage();
  }

  register(model: UserRegisterModel): Observable<void> {
    return this.authRepo.register(model);
  }
}
