import { Component } from "@angular/core";
import { Subscription } from "rxjs";
import { NavController, IonicPage } from "ionic-angular";
import { UserService } from "../../core/services/user.service";
import { UserModel } from "../../core/models/user.model";

@Component({
  selector: "page-home",
  templateUrl: "./home.html"
})
export class HomePage {
  private subscription: Subscription;
  userModel: UserModel;

  constructor(
    public navCtrl: NavController,
    private userService: UserService
  ) {}
  ionViewWillEnter() {
    this.subscription = this.userService.currentUser.subscribe(u => {
      this.userModel = u;
    });
  }
  ionViewWillLeave() {
    this.subscription.unsubscribe();
  }
}
