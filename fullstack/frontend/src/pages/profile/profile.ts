import { UserService } from './../../core/services/user.service';
import { Component, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { Loading, LoadingController, AlertController, IonicPage } from 'ionic-angular';
import { UserModel } from '../../core/models/user.model';
import { Subscription } from 'rxjs';
@IonicPage()
@Component({
    selector: "page-profile",
    templateUrl: "./profile.html",
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfilePage {

    user: UserModel;
    editUser: UserModel;
    isEditing = false;
    loading: Loading;

    private subscription: Subscription;

    constructor(
        private cdr: ChangeDetectorRef,
        private userService: UserService,
        public alertCtrl: AlertController,
        public loadingCtrl: LoadingController,
    ) {
    }

    ionViewWillEnter() {
        this.subscription = this.userService.currentUser
            .subscribe(u => {
                this.user = { ...u };
                this.editUser = { ...u }
                this.cdr.markForCheck();
            });
    }
    ionViewWillLeave() {
        this.subscription.unsubscribe();
    }

    reset(): void {
        this.isEditing = false;
        this.editUser = { ...this.user }
    }

    save() {
        this.showLoading();
        this.userService
            .updateUser(this.editUser)
            .finally(() => {
                this.loading.dismiss();
                this.cdr.markForCheck();
            })
            .subscribe(res => {
                this.isEditing = false;
            }, err => {
                this.showError(err.message);
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