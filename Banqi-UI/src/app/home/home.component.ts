import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {FinalComponentComponent} from "../final-component/final-component.component";
import {GameRuleComponent} from "../game-rule/game-rule.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor( private route: ActivatedRoute,
               public dialog: MatDialog ) { }

  ngOnInit() {
  }

  onGameRules() {
    const dialogRef = this.dialog.open(GameRuleComponent, {
      width: '500px',
      height: '500px'
    });
  }
}
