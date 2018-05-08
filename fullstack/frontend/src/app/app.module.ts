import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { BrowserModule } from "@angular/platform-browser";
import { ErrorHandler, NgModule } from "@angular/core";

import { IonicApp, IonicErrorHandler, IonicModule } from "ionic-angular";
import { IonicStorageModule } from "@ionic/storage";
import { StatusBar } from "@ionic-native/status-bar";
import { SplashScreen } from "@ionic-native/splash-screen";

import { AppComponent } from "./app.component";
import { HomePage } from "../pages/home/home";
import { ListPage } from "../pages/list/list";
import { UserService } from "../core/services/user.service";
import { FriendService } from "../core/services/friend.service";
import { AuthenticationRepository } from "./../core/repositories/authentication.repository";
import { AuthenticationService } from "./../core/services/authentication.service";
import { LocalStorageRepository } from "./../core/repositories/local-storage.repository";
import { UserRepository } from "../core/repositories/user.repository";
import { FriendRepository } from "../core/repositories/friend.repository";
import { AuthInterceptor } from "../core/system/auth-interceptor";

@NgModule({
  declarations: [AppComponent, HomePage, ListPage],
  imports: [
    BrowserModule,
    HttpClientModule,
    IonicModule.forRoot(AppComponent),
    IonicStorageModule.forRoot({
      name: "frontenddb",
      driverOrder: ["indexeddb", "sqlite", "websql"]
    })
  ],
  bootstrap: [IonicApp],
  entryComponents: [AppComponent, HomePage, ListPage],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    LocalStorageRepository,
    AuthenticationRepository,
    UserRepository,
    FriendRepository,
    AuthenticationService,
    UserService,
    FriendService
  ]
})
export class AppModule {}
