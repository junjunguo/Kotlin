import { UserService } from './../../core/services/user.service';
import { Component, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { Loading, LoadingController, AlertController, IonicPage, Alert } from 'ionic-angular';
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
    // alert: Alert;
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
    ionViewDidLoad() {

        this.showLoading();
    }

    ionViewWillLeave() {
        this.subscription.unsubscribe();
    }

    reset(): void {
        this.isEditing = false;
        this.editUser = { ...this.user }
    }

    save() {
        if (this.editUser.email === this.user.email && this.editUser.name === this.user.name) return;
        this.showLoading();
        this.userService
            .updateUser(this.editUser)
            .finally(() => {
                this.loading.dismiss();
                console.log("finally --- ")
                this.cdr.markForCheck();
            })
            .subscribe(res => {
                this.isEditing = false;
            }, err => {
                // this.showError(err.message);
                console.log(" -- - - - err -0-- ", err);
            });
    }

    showLoading(): void {
        this.loading = this.loadingCtrl.create({
            content: 'Please wait...',
            dismissOnPageChange: true
        });
        this.loading.present();
    }

    // showError(text): void {
    //     const alert = this.alertCtrl.create({
    //         title: 'Fail',
    //         subTitle: text,
    //         buttons: ['OK']
    //     });
    // }
}