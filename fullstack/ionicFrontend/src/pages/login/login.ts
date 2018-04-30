import { UserRegisterModel } from './../../models/UserRegisterModel';
import { AuthServiceProvider } from './../../providers/auth-service/auth-service';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, LoadingController, AlertController, Loading } from 'ionic-angular';
import { UserLoginModel } from '../../models/UserLoginModel';

/**
 * Generated class for the LoginPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {
  loading: Loading;
  model: UserLoginModel = { username: "", password: "" }

  constructor(
    public nav: NavController,
    public navParams: NavParams,
    public auth: AuthServiceProvider,
    public loadingCtrl: LoadingController,
    public alertCtrl: AlertController) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
  }

  public createAccount() {
    this.nav.push('RegisterPage');
  }

  public login() {
    this.showLoading()
    this.auth.login(this.model).subscribe(value => {
      console.log(" - - - sub ", value)
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

  showLoading() {
    this.loading = this.loadingCtrl.create({
      content: 'Please wait...',
      dismissOnPageChange: true
    });
    this.loading.present();
  }

  showError(text) {
    this.loading.dismiss();

    let alert = this.alertCtrl.create({
      title: 'Fail',
      subTitle: text,
      buttons: ['OK']
    });
    alert.present();
  }

}
