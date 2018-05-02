import { AccessTokenModel } from './../models/access-token.model';
import { AccessTokenEnum } from './../models/enums/access-token.enum';
import { Injectable } from "@angular/core";

import { Storage } from '@ionic/storage';
import { UserModel } from '../models/user.model';

@Injectable()
export class LocalStorageRepository {

    private readonly app_user_json = "app_user_json";
    private readonly app_access_token_json = "app_access_token_json";

    constructor(private storage: Storage) { }

    saveAccessToken(token: AccessTokenModel) {
        return this.storage.set(this.app_access_token_json, JSON.stringify(token));
    }

    saveUser(user: UserModel) {
        return this.storage.set(this.app_user_json, JSON.stringify(user));
    }

    getUser() {
        return this.storage.get(this.app_user_json);
    }

    getAccessToken() {
        return this.storage.get(this.app_access_token_json);
    }

    getByKey(key: string) {
        return this.storage.get(key);
    }

    removeAccessToken() {
        return this.storage.remove(this.app_access_token_json);
    }

    removeUser() {
        return this.storage.remove(this.app_user_json);
    }

    clearStorage() {
        return this.storage.clear();
    }
}