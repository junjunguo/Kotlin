import { AuthenticationService } from './../../core/services/authentication.service';
import { Component, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { AlertController, IonicPage, Loading, LoadingController, NavController, NavParams, MenuController } from 'ionic-angular';
import { UserLoginModel } from '../../core/models/user-login.model';
import { HomePage } from '../home/home';

@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginPage {

  loading: Loading;
  model: UserLoginModel = { username: '', password: '' };

  constructor(
    public nav: NavController,
    public navParams: NavParams,
    public auth: AuthenticationService,
    public loadingCtrl: LoadingController,
    public alertCtrl: AlertController,
    public menCtr: MenuController,
    private cdr: ChangeDetectorRef) {
    this.auth.isLoggedin()
      .subscribe(isLoggedIn => {
        if (isLoggedIn) {
          this.nav.setRoot(HomePage);
          this.menCtr.enable(true);
        }
      })
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
      .finally(() => {
        this.cdr.markForCheck();
        this.loading.dismiss
      })
      .subscribe(value => {
        this.nav.setRoot(HomePage);
        this.menCtr.enable(true);
      },
        error => {
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
