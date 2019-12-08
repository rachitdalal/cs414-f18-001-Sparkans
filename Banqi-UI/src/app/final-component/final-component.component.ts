import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-final-component',
  templateUrl: './final-component.component.html',
  styleUrls: ['./final-component.component.css']
})
export class FinalComponentComponent implements OnInit {

  constructor( public dialogRef: MatDialogRef<any>,
               @Inject(MAT_DIALOG_DATA) public data: any ) {
    dialogRef.disableClose = true;
  }

  ngOnInit() {
  }

}
