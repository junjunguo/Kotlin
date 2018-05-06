import { Subscription } from 'rxjs';
import { Component, ViewChild } from '@angular/core';
import { MenuController, Nav, Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from '../pages/home/home';
import { ListPage } from '../pages/list/list';
import { ProfilePage } from './../pages/profile/profile';
import { AuthenticationService } from './../core/services/authentication.service';

@Component({
  templateUrl: 'app.component.html'
})
export class AppComponent {

  @ViewChild(Nav) nav: Nav;

  rootPage: any = 'LoginPage';
  profilePage: any = 'ProfilePage';

  pages: Array<{ title: string, component: any }>;

  subscription: Subscription;

  constructor(
    public platform: Platform,
    public statusBar: StatusBar,
    public splashScreen: SplashScreen,
    public menuCtr: MenuController,
    private auth: AuthenticationService) {
    this.initializeApp();

    // used for an example of ngFor and navigation
    this.pages = [
      { title: 'Home', component: HomePage },
      { title: 'List', component: ListPage }
    ];

  }

  initializeApp(): void {
    this.platform.ready()
      .then(() => {
        // Okay, so the platform is ready and our plugins are available.
        // Here you can do any higher level native things you might need.
        this.statusBar.styleDefault();
        this.splashScreen.hide();

        console.log("subscription app")
        this.subscription = this.auth.isLoggedIn.subscribe(loggedIn => {
          console.log("app - ", loggedIn);
          if (!loggedIn) { this.logout() }
          else if (loggedIn && this.auth.getAuthModel()) {
            this.menuCtr.enable(false);
          }
        });
      });
  }

  onOpenPage(page): void {
    // Reset the content nav to have just this page
    // we wouldn't want the back button to show in this scenario
    this.nav.setRoot(page.component);
  }

  onOpenProfile(): void {
    this.nav.setRoot('ProfilePage');
  }

  onLogout(): void {
    this.auth.logout();
    this.logout();
  }

  private logout(): void {
    if (!this.isOnPublicPage) this.nav.setRoot(this.rootPage);
    this.menuCtr.enable(false);
  }

  private isOnPublicPage() {
    console.log(this.nav.getActive.name);
    return this.nav.getActive.name == 'RegisterPage';
  }
}
