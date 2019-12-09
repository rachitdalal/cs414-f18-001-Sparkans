import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserDetailsService} from "../Service/user-details.service";
import {GameRuleComponent} from "../game-rule/game-rule.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  currentUser: string;
  isUserSignedIn: boolean = false;

  constructor(private route: ActivatedRoute,
              private userDetails: UserDetailsService,
              public dialog: MatDialog) {

  }

  ngOnInit() {

    if (localStorage.getItem("user1")) {
      this.isUserSignedIn = true;
      this.currentUser = localStorage.getItem("user1");
    }
  }

  onGameRules() {
    const dialogRef = this.dialog.open(GameRuleComponent, {
      width: '900px',
      height: '600px'
    });
  }
}


