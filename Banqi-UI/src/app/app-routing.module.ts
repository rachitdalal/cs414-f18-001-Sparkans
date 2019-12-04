import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RegisterUserComponent} from "./register-user/register-user.component";
import {SignInComponentComponent} from "./sign-in-component/sign-in-component.component";
import {AppComponent} from "./app.component";
import {HomeComponent} from "./home/home.component";
import {GamePlayComponent} from "./game-play/game-play.component";
import {InviteComponent} from "./invite/invite.component";
/*import {HistoryComponent} from "./history/history.component"*/


const routes: Routes = [
  { path: 'register', component: RegisterUserComponent },
  { path: 'signin', component: SignInComponentComponent },
  { path: 'gamePlay', component: GamePlayComponent },
  { path: 'invite', component: InviteComponent },/*
  { path: 'history', component: HistoryComponent},*/
  { path: '',
    redirectTo: '/Home',
    pathMatch: 'full'
  },
  { path: '**', component: HomeComponent }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
