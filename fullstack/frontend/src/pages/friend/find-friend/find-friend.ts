import {
  Component,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from "@angular/core";
import { UserModel } from "../../../core/models/user.model";
import { UserService } from "../../../core/services/user.service";
import { IonicPage } from "ionic-angular";

@IonicPage()
@Component({
  selector: "page-find-friend",
  templateUrl: "./find-friend.html",
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FindFriendPage {
  users: UserModel[] = [];

  constructor(
    private userService: UserService,
    private cdr: ChangeDetectorRef
  ) {}

  ionViewWillEnter() {
    this.userService
      .getAllUsers()
      .finally(() => {
        this.cdr.markForCheck();
      })
      .subscribe(
        res => {
          this.users = res;
        },
        err => {
          console.log(err);
        }
      );
  }
}
