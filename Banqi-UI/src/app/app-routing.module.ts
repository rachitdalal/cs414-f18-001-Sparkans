import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RegisterUserComponent} from "./register-user/register-user.component";
import {SignInComponentComponent} from "./sign-in-component/sign-in-component.component";
import {AppComponent} from "./app.component";
import {HomeComponent} from "./home/home.component";
import {GamePlayComponent} from "./game-play/game-play.component";


const routes: Routes = [
  { path: 'register', component: RegisterUserComponent },
  { path: 'signin', component: SignInComponentComponent },
  { path: 'gamePlayAndInvite', component: GamePlayComponent },
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
