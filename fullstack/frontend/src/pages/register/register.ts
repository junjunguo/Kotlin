import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { AlertController, IonicPage, Loading, LoadingController, NavController, NavParams } from 'ionic-angular';
import { AuthenticationService } from '../../core/services/authentication.service';

@IonicPage()
@Component({
  selector: 'page-register',
  templateUrl: 'register.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegisterPage {

  loading: Loading;
  model = { name: '', password: '', email: '' };
  createSuccess = false;

  constructor(
    private nav: NavController,
    private navParams: NavParams,
    private cdr: ChangeDetectorRef,
    private alertCtrl: AlertController,
    private auth: AuthenticationService,
    private loadingCtrl: LoadingController
  ) {
  }
  register(): void {
    if (!this.model || !this.model.name || !this.model.password) return;

    this.showLoading();
    this.auth.register(this.model)
      .finally(() => {
        this.cdr.markForCheck();
        this.loading.dismiss();
      })
      .subscribe(res => {
        this.createSuccess = true;
        this.showPopup('Success', 'Account created.');
      },
        error => {
          this.showPopup('Error', error.message);
        });
  }

  login(): void {
    this.nav.push('LoginPage');
  }

  showLoading(): void {
    this.loading = this.loadingCtrl.create({
      content: 'Please wait...',
      dismissOnPageChange: true
    });
    this.loading.present();
  }

  showPopup(title, text): void {
    const alert = this.alertCtrl.create({
      title,
      subTitle: text,
      buttons: [
        {
          text: 'OK',
          handler: data => {
            if (this.createSuccess)
              this.nav.popToRoot();

          }
        }
      ]
    });
    alert.present();
  }
}
