import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { AuthenticationRepository } from './../repositories/authentication.repository';
import { AccessTokenModel } from '../models/access-token.model';
import { UserLoginModel } from '../models/user-login.model';
import { UserRegisterModel } from '../models/user-register.model';

@Injectable()
export class AuthenticationService {

  private authModel: AccessTokenModel;

  constructor(private authRepo: AuthenticationRepository) {
  }

  login(login: UserLoginModel): Observable<AccessTokenModel> {
    return this.authRepo.login(login)
      .do(res => this.authModel = res);
  }

  register(model: UserRegisterModel): Observable<void> {
    return this.authRepo.register(model);
  }
}
