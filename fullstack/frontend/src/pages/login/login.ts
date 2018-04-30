import { AuthenticationService } from './../../core/services/authentication.service';
import { Component } from '@angular/core';
import { AlertController, IonicPage, Loading, LoadingController, NavController, NavParams } from 'ionic-angular';
import { UserLoginModel } from '../../core/models/user-login.model';

@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html'
})
export class LoginPage {
  loading: Loading;
  model: UserLoginModel = { username: '', password: '' };

  constructor(
    public nav: NavController,
    public navParams: NavParams,
    public auth: AuthenticationService,
    public loadingCtrl: LoadingController,
    public alertCtrl: AlertController) {
  }

  ionViewDidLoad(): void {
    console.log('ionViewDidLoad LoginPage');
  }

  createAccount(): void {
    this.nav.push('RegisterPage');
  }

  login(): void {
    this.showLoading();
    this.auth.login(this.model)
      .subscribe(value => {
        console.log(' - - - sub ', value);
        // if (value) {
        //   this.nav.setRoot('HomePage');
        // } else {
        //   this.showError("Access Denied");
        // }
      },
        error => {
          console.log(error);
          this.showError(error.message);
        });
  }

  showLoading(): void {
    this.loading = this.loadingCtrl.create({
      content: 'Please wait...',
      dismissOnPageChange: true
    });
    this.loading.present();
  }

  showError(text): void {
    this.loading.dismiss();

    const alert = this.alertCtrl.create({
      title: 'Fail',
      subTitle: text,
      buttons: ['OK']
    });
    alert.present();
  }

}
