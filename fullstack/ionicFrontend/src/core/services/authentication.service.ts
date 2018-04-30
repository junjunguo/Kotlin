import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import { UserLoginModel } from '../models/UserLoginModel';
import { OAuth2AccessToken } from '../models/SecurityModels';
import { UserRegisterModel } from '../models/UserRegisterModel';
import { AuthenticationRepository } from './../repositories/authentication.repository';

@Injectable()
export class AuthenticationService {

  private authModel: OAuth2AccessToken;

  constructor(private authRepo: AuthenticationRepository) {
  }

  login(login: UserLoginModel) {
    return this.authRepo.login(login).do(res => this.authModel = res);
  }

  register(model: UserRegisterModel) {
    return this.authRepo.register(model);
  }
}
