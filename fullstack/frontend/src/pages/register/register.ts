import { Component, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { IonicPage, NavController, NavParams, AlertController, LoadingController, Loading } from 'ionic-angular';
import { AuthenticationService } from '../../core/services/authentication.service';

@IonicPage()
@Component({
  selector: 'page-register',
  templateUrl: 'register.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegisterPage {

  loading: Loading;
  model = { name: "", password: "", email: "" }
  createSuccess = false;

  constructor(
    public nav: NavController,
    public navParams: NavParams,
    public loadingCtrl: LoadingController,
    private auth: AuthenticationService,
    private alertCtrl: AlertController,
    private cdr: ChangeDetectorRef) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad RegisterPage');
  }

  register() {
    if (!this.model || !this.model.name || !this.model.password) { return; }
    this.showLoading();
    this.auth.register(this.model)
      .finally(() => {
        this.cdr.markForCheck();
        this.loading.dismiss()
      })
      .subscribe(res => {
        this.createSuccess = true;
        this.showPopup("Success", "Account created.");
      },
        error => {
          this.showPopup("Error", error.message);
        });
  }

  login() {
    this.nav.push('LoginPage');
  }

  showLoading(): void {
    this.loading = this.loadingCtrl.create({
      content: 'Please wait...',
      dismissOnPageChange: true
    });
    this.loading.present();
  }

  showPopup(title, text) {
    let alert = this.alertCtrl.create({
      title: title,
      subTitle: text,
      buttons: [
        {
          text: 'OK',
          handler: data => {
            if (this.createSuccess) {
              this.nav.popToRoot();
            }
          }
        }
      ]
    });
    alert.present();
  }
}
