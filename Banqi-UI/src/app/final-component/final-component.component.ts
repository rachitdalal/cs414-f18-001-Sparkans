import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";

@Component({
  selector: 'app-final-component',
  templateUrl: './final-component.component.html',
  styleUrls: ['./final-component.component.css']
})
export class FinalComponentComponent implements OnInit {

  constructor( public dialogRef: MatDialogRef<any>,
               @Inject(MAT_DIALOG_DATA) public data: any,
               private router: Router) {
    dialogRef.disableClose = true;
  }

  ngOnInit() {
  }

  onInvite() {
    this.router.navigate(['/invite']);
  }

  onHome() {
    this.router.navigate(['/Home']);
  }
}
