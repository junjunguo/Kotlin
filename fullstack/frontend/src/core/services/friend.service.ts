import { AuthenticationService } from "./authentication.service";
import { Observable } from "rxjs/Observable";
import { BehaviorSubject } from "rxjs";
import { Injectable } from "@angular/core";

import { FriendModel } from "../models/friend.model";
import { FriendRepository } from "./../repositories/friend.repository";
import { LocalStorageRepository } from "./../repositories/local-storage.repository";

@Injectable()
export class FriendService {
  friends = new BehaviorSubject<FriendModel[]>(undefined);

  private friendModels: FriendModel[];

  constructor(
    private auth: AuthenticationService,
    private friendRepo: FriendRepository,
    private localStoreRepo: LocalStorageRepository
  ) {
    this.auth.isLoggedIn.subscribe(loggedIn => {
      if (loggedIn) this.loadFriends();
      else {
        this.friendModels = undefined;
        this.friends.next(this.friendModels);
      }
    });
  }

  private loadFriends(): void {
    this.localStoreRepo.getFriends().then(res => {
      if (res) {
        this.friendModels = JSON.parse(res);
        this.friends.next(this.friendModels);
      } else this.getFriends().subscribe();
    });
  }

  getFriends(): Observable<FriendModel[]> {
    return this.friendRepo.getFriends().do(res => {
      this.friendModels = res;
      this.localStoreRepo.saveFriends(this.friendModels);
    });
  }

  addFriend(friendId: number): Observable<FriendModel> {
    return this.friendRepo.addFriend(friendId);
  }

  removeFriend(friendId: number): Observable<void> {
    return this.friendRepo.removeFriend(friendId);
  }

  confirmFriend(friendId: number): Observable<FriendModel> {
    return this.friendRepo.confirmFriend(friendId).do(res => {
      this.friendModels[
        this.friendModels.findIndex(x => x.id !== null && x.id === res.id)
      ] = res;
      this.friends.next(this.friendModels);
      this.localStoreRepo.saveFriends(this.friendModels);
    });
  }
}
