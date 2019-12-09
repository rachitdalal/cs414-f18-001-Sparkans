import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-game-rule',
  templateUrl: './game-rule.component.html',
  styleUrls: ['./game-rule.component.css']
})
export class GameRuleComponent implements OnInit {

  constructor( public dialogRef: MatDialogRef<any>,
               @Inject(MAT_DIALOG_DATA) public data: any ) { }

  ngOnInit() {
  }

}
