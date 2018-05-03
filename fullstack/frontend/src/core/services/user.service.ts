import { LocalStorageRepository } from './../repositories/local-storage.repository';
import { Observable } from 'rxjs/Observable';
import { UserRegisterModel } from './../models/user-register.model';
import { UserRepository } from './../repositories/user.repository';
import { Injectable } from "@angular/core";
import { UserModel } from '../models/user.model';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class UserService {

    public currentUser = new BehaviorSubject<UserModel>(null);

    constructor(private userRepo: UserRepository, private localStoreRepo: LocalStorageRepository) {
        this.localStoreRepo.getUser()
            .then(res => this.currentUser.next(res))
            .catch(err => this.localStoreRepo.removeUser)
    }

    getUserModel() {
        return this.userRepo.getUserModel().do(res => {
            this.currentUser.next(res);
            this.localStoreRepo.saveUser(res);
        });
    }

    updateUser(user: UserModel) {
        return this.userRepo.updateUser(user).do(res => {
            this.currentUser.next(res);
            this.localStoreRepo.saveUser(res);
        });
    }
}