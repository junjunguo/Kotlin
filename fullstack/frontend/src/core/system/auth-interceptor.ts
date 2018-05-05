import { LoginPage } from './../../pages/login/login';
import { NavController } from 'ionic-angular';
import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest
} from '@angular/common/http';
import { Injectable } from '@angular/core';

import 'rxjs/add/operator/do';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(
        // private nav: NavController,
        private auth: AuthenticationService
    ) { }

    public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let authReq = req;

        if (this.auth && this.auth.getAuthModel() && this.auth.getAuthModel() && this.auth.getAuthModel().access_token) {
            authReq = req.clone({
                headers: req.headers.set('Authorization',
                    `Bearer ${this.auth.getAuthModel().access_token}`)
            });
        }

        return next.handle(authReq)
            .do(() => {
            }, err => {
                if (err instanceof HttpErrorResponse) {
                    if (err.status === 401) {
                        // this.nav.setRoot('LoginPage');
                    }
                }
            });
    }
}
