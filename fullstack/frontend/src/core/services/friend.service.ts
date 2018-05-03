import { BehaviorSubject } from "rxjs";
import { Injectable } from "@angular/core";

import { FriendModel } from "../models/friend.model";
import { FriendRepository } from './../repositories/friend.repository';
import { LocalStorageRepository } from './../repositories/local-storage.repository';

@Injectable()
export class FriendService {

    private friendModels: FriendModel[];

    friends = new BehaviorSubject<FriendModel[]>(null);

    constructor(
        private friendRepo: FriendRepository,
        private localStoreRepo: LocalStorageRepository
    ) {
        this.localStoreRepo.getFriends()
            .then(res => {
                if (res) {
                    this.friendModels = JSON.parse(res);
                    this.friends.next(this.friendModels);
                }
            });
    }

    getFriends() {
        return this.friendRepo.getFriends()
            .do(res => {
                this.friendModels = res;
                this.localStoreRepo.saveFriends(this.friendModels);
            });
    }

    addFriend(friendId: number) {
        return this.friendRepo.addFriend(friendId);
    }

    removeFriend(friendId: number) {
        return this.friendRepo.removeFriend(friendId);
    }

    confirmFriend(friendId: number) {
        return this.friendRepo.confirmFriend(friendId)
            .do(res => {
                this.friendModels[this.friendModels.findIndex(x => x.id !== null && x.id === res.id)] = res;
                this.friends.next(this.friendModels);
                this.localStoreRepo.saveFriends(this.friendModels);
            });
    }
}