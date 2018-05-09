import { BehaviorSubject } from "rxjs";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/Observable";

import { UserModel } from "../models/user.model";
import { AuthenticationService } from "./authentication.service";
import { UserRepository } from "./../repositories/user.repository";
import { LocalStorageRepository } from "./../repositories/local-storage.repository";

@Injectable()
export class UserService {
  currentUser = new BehaviorSubject<UserModel>(undefined);

  constructor(
    private auth: AuthenticationService,
    private userRepo: UserRepository,
    private localStoreRepo: LocalStorageRepository
  ) {
    this.auth.isLoggedIn.subscribe(loggedIn => {
      if (loggedIn) this.loadUser();
      else this.currentUser.next(undefined);
    });
  }

  private loadUser(): void {
    this.localStoreRepo
      .getUser()
      .then(res => {
        if (res) this.currentUser.next(JSON.parse(res));
        else this.getUserModel().subscribe();
      })
      .catch(err => {
        this.localStoreRepo
          .removeUser()
          .then(() => this.getUserModel().subscribe());
      });
  }

  getUserModel(): Observable<UserModel> {
    return this.userRepo.getUserModel().do(res => {
      this.currentUser.next(res);
      this.localStoreRepo.saveUser(res);
    });
  }

  updateUser(user: UserModel): Observable<UserModel> {
    return this.userRepo.updateUser(user).do(res => {
      this.currentUser.next(res);
      this.localStoreRepo.saveUser(res);
    });
  }

  getAllUsers(): Observable<UserModel[]> {
    return this.userRepo.getAllUsers();
  }
}
