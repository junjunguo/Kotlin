import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

import { LocalStorageRepository } from './../repositories/local-storage.repository';
import { UserRepository } from './../repositories/user.repository';
import { UserModel } from '../models/user.model';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class UserService {

    currentUser = new BehaviorSubject<UserModel>(undefined);

    constructor(
        private auth: AuthenticationService,
        private userRepo: UserRepository,
        private localStoreRepo: LocalStorageRepository
    ) {
        this.auth.isLoggedIn.subscribe(
            loggedIn => {
                if (loggedIn)
                    this.localStoreRepo.getUser()
                        .then(res => {
                            if (res)
                                this.currentUser.next(res);
                            else this.getUserModel()
                                .subscribe();
                        })
                        .catch(err => {
                            this.localStoreRepo.removeUser()
                                .then(() =>
                                    this.getUserModel()
                                        .subscribe()
                                );
                        });
                else this.currentUser.next(undefined);
            });
    }

    getUserModel(): Observable<UserModel> {
        return this.userRepo.getUserModel()
            .do(res => {
                this.currentUser.next(res);
                this.localStoreRepo.saveUser(res);
            });
    }

    updateUser(user: UserModel): Observable<UserModel> {
        return this.userRepo.updateUser(user)
            .do(res => {
                this.currentUser.next(res);
                this.localStoreRepo.saveUser(res);
            });
    }
}
