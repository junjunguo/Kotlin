import { Subscription } from "rxjs";
import {
  Component,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from "@angular/core";
import { UserModel } from "../../../core/models/user.model";
import { UserService } from "../../../core/services/user.service";
import { IonicPage } from "ionic-angular";
import { FriendService } from "../../../core/services/friend.service";
import { FriendModel } from "../../../core/models/friend.model";

@IonicPage()
@Component({
  selector: "page-find-friend",
  templateUrl: "./find-friend.html",
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FindFriendPage {
  users: UserModel[] = [];
  friends: FriendModel[] = [];
  subscription: Subscription;
  constructor(
    private userService: UserService,
    private friendService: FriendService,
    private cdr: ChangeDetectorRef
  ) {}

  ionViewWillEnter() {
    this.subscription = this.friendService.friends.subscribe(res => {
      this.friends = res;
      this.updateUsers();
    });
    this.userService
      .getAllUsers()
      .finally(() => {
        this.cdr.markForCheck();
      })
      .subscribe(
        res => {
          this.users = res;
          this.updateUsers();
        },
        err => {
          console.log(err);
        }
      );
  }

  ionViewWillLeave() {
    this.subscription.unsubscribe();
  }

  addFriend(user: UserModel) {
    this.friendService
      .addFriend(user.id)
      .finally(() => this.cdr.markForCheck())
      .subscribe(res => {
        console.log("added friend ", res);
      });
  }

  usersTrackByFn(index: number, item: UserModel) {
    return item.id;
  }

  private updateUsers() {
    this.users = this.users.filter(u => !this.friends.some(f => f.id === u.id));
    this.cdr.markForCheck();
  }
}
