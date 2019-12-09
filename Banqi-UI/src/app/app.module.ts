import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatGridListModule} from "@angular/material/grid-list";
import { RegisterUserComponent } from './register-user/register-user.component';
import { SignInComponentComponent } from './sign-in-component/sign-in-component.component';
import { HomeComponent } from './home/home.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {HttpClientModule} from "@angular/common/http";
import { GamePlayComponent } from './game-play/game-play.component';

import { InviteComponent } from './invite/invite.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {UserDetailsService} from "./Service/user-details.service";
import {HistoryComponent} from  "./history/history.component";
import {MatTabsModule} from '@angular/material/tabs';
import {ProfileComponent} from "./profile/profile.component";
import {MatCardModule} from '@angular/material/card';
import { GameRuleComponent } from './game-rule/game-rule.component';
import { FinalComponentComponent } from './final-component/final-component.component';
import {MatDialogModule} from "@angular/material/dialog";

/*
import {HistoryComponent} from  "./history/history.component"*/


@NgModule({
  declarations: [
    AppComponent,
    RegisterUserComponent,
    SignInComponentComponent,
    HomeComponent,
    GamePlayComponent,
    InviteComponent,
    HistoryComponent,
    ProfileComponent,
    GameRuleComponent,
    FinalComponentComponent,/*
    HistoryComponent,*/

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatGridListModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSnackBarModule,
    HttpClientModule,
    MatTabsModule,
    MatCardModule,
    MatDialogModule

  ],
  entryComponents : [
    FinalComponentComponent,
    GameRuleComponent
  ],
  providers: [ UserDetailsService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
