import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {GameRuleComponent} from "../game-rule/game-rule.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser;
  subject;

  constructor(private route: ActivatedRoute,
              public dialog: MatDialog ) {

  }

  ngOnInit() {

    this.subject = this.route
      .data
      .subscribe((username) => {
        console.log(username);
      });

    this.currentUser = localStorage.getItem("user1");
  }
  onGameRules() {
    const dialogRef = this.dialog.open(GameRuleComponent, {
      width: '900px',
      height: '600px'
    });
  }

  // unreg(){
  //
  // }
}
