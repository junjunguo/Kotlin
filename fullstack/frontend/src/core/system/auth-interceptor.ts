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

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    private readonly LOCAL_STORAGE_NAME = 'play_pulse_user_local_storage';

    constructor(
        private nav: NavController,
    ) { }

    public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const currentUser = JSON.parse(localStorage.getItem(this.LOCAL_STORAGE_NAME));
        let authReq = req;
        if (currentUser) {
            authReq = req.clone({ headers: req.headers.set('Authorization', currentUser.token) });
        }

        return next.handle(authReq).do(() => {
        }, err => {
            if (err instanceof HttpErrorResponse) {
                if (err.status === 401) {
                    this.nav.setRoot(LoginPage);
                }
            }
        });
    }
}
