import { UserService } from "./../../core/services/user.service";
import {
  Component,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from "@angular/core";
import {
  Loading,
  LoadingController,
  AlertController,
  IonicPage,
  Alert,
  NavController
} from "ionic-angular";
import { UserModel } from "../../core/models/user.model";
import { Subscription } from "rxjs";
import { FriendService } from "../../core/services/friend.service";
import { FriendModel } from "../../core/models/friend.model";
import { FriendStatusEnum } from "../../core/models/enums/friend-status.enum";
@IonicPage()
@Component({
  selector: "page-friend",
  templateUrl: "./friend.html",
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FriendPage {
  friends: FriendModel[] = [];
  requests: FriendModel[] = [];
  pendings: FriendModel[] = [];

  loading: Loading;
  private subscription: Subscription;

  constructor(
    private cdr: ChangeDetectorRef,
    private friendService: FriendService,
    private nav: NavController,
    public alertCtrl: AlertController,
    public loadingCtrl: LoadingController
  ) {}

  ionViewWillEnter() {
    this.friendService.loadFriends();
    this.subscription = this.friendService.friends.subscribe(res => {
      this.friends = [];
      this.requests = [];
      this.pendings = [];
      for (const f of res) {
        if (f.status === FriendStatusEnum.PENDING) {
          this.pendings.push(f);
        } else if (f.status === FriendStatusEnum.FRIEND_REQUEST_SENT) {
          this.requests.push(f);
        } else {
          this.friends.push(f);
        }
      }
      this.cdr.markForCheck();
    });
  }

  ionViewWillLeave() {
    this.subscription.unsubscribe();
  }

  showLoading(): void {
    this.loading = this.loadingCtrl.create({
      content: "Please wait...",
      dismissOnPageChange: true
    });
    this.loading.present();
  }

  showError(text): void {
    const alert = this.alertCtrl.create({
      title: "Fail",
      subTitle: text,
      buttons: ["OK"]
    });
  }

  onAddFriend(): void {
    this.nav.setRoot("FindFriendPage");
  }

  onRemoveFriend(friend: FriendModel): void {
    this.friendService.removeFriend(friend.id).subscribe(
      () => {},
      err => {
        console.log(err);
      }
    );
  }

  onConfirmFriend(friend: FriendModel): void {
    this.friendService.confirmFriend(friend.id).subscribe(
      () => {},
      err => {
        console.log(err);
      }
    );
  }

  friendsTrackByFn(index: number, item: FriendModel) {
    return item.id;
  }
}
