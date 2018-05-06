import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/do';
import { NavController } from 'ionic-angular';
import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest
} from '@angular/common/http';
import { Injectable } from '@angular/core';

import { LoginPage } from './../../pages/login/login';
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
            authReq = this.appendToken(this.auth.getAuthModel().access_token, req);
        }

        return next.handle(authReq).catch(err => {
            if (this.isInvlidTokens(err)) {
                if (this.auth.getAuthModel) this.auth.logout();
                return Observable.throw(err)
            }
            if (err instanceof HttpErrorResponse && err.status === 401 && (this.isRefreshTokenExpired(err))) {
                this.auth.logout();
                return Observable.throw(err)
            }
            else if (err instanceof HttpErrorResponse && err.status === 401 && this.isAccessTokenExpired(err)) {
                return this.tryRenewAccessToken(req, next, err);
            } else {
                return Observable.throw(err);
            }
        });
    }

    private tryRenewAccessToken(req: HttpRequest<any>, next: HttpHandler, err: HttpErrorResponse) {
        this.auth.accessTokenExpired();
        return this.auth.renewAccessToken()
            .switchMap(res => {
                if (res) { return next.handle(this.appendToken(res.access_token, req)); }
                else { return Observable.throw(err) }
            })
    }

    private isAccessTokenExpired(err: HttpErrorResponse) {
        return err && err.error && err.error.error_description && err.error.error_description.includes("Access token expired");
    }

    private isRefreshTokenExpired(err: HttpErrorResponse) {
        return err && err.error && err.error.error_description && err.error.error_description.includes("Invalid refresh token (expired)");
    }

    private isInvlidTokens(err: HttpErrorResponse) {
        return err && err.error && err.error.error_description && err.error.error_description.includes("Full authentication is required to access this resource");
    }

    private appendToken(token: string, req: HttpRequest<any>): HttpRequest<any> {
        return req.clone({
            headers: req.headers.set('Authorization', `Bearer ${token}`)
        })
    }
}
