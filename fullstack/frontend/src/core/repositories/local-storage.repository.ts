import { Storage } from '@ionic/storage';
import { Injectable } from '@angular/core';

import { AccessTokenModel } from './../models/access-token.model';
import { UserModel } from '../models/user.model';
import { FriendModel } from '../models/friend.model';

@Injectable()
export class LocalStorageRepository {

    private readonly appUserJson = 'app_user_json';
    private readonly appUserFriendsJson = 'app_user_friends_json';
    private readonly appAccessTokenJson = 'app_access_token_json';

    constructor(private storage: Storage) { }

    getFriends(): Promise<string> {
        return this.storage.get(this.appUserFriendsJson);
    }

    getUser(): Promise<string> {
        return this.storage.get(this.appUserJson);
    }

    getAccessToken(): Promise<string> {
        return this.storage.get(this.appAccessTokenJson);
    }

    // getByKey(key: string): Promise<any> {
    //     return this.storage.get(key);
    // }

    saveAccessToken(token: AccessTokenModel): Promise<void> {
        return this.storage.set(this.appAccessTokenJson, JSON.stringify(token));
    }

    saveUser(user: UserModel): Promise<void> {
        return this.storage.set(this.appUserJson, JSON.stringify(user));
    }
    saveFriends(friends: FriendModel[]): Promise<void> {
        return this.storage.set(this.appUserFriendsJson, JSON.stringify(friends));
    }

    removeAccessToken(): Promise<void> {
        return this.storage.remove(this.appAccessTokenJson);
    }

    removeUser(): Promise<void> {
        return this.storage.remove(this.appUserJson);
    }

    removeFriends(): Promise<void> {
        return this.storage.remove(this.appUserFriendsJson);
    }

    clearStorage(): Promise<void> {
        return this.storage.clear();
    }
}
