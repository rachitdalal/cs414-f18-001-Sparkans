import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {GameRuleComponent} from "../game-rule/game-rule.component";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser;
  subject;
  UNREGISTER = 'http://localhost:31406/unregister';

  constructor(private route: ActivatedRoute,
              public dialog: MatDialog,
              private http: HttpClient,
              private _snackBar: MatSnackBar,
              private router: Router) {

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

  onUnRegister(){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    const user1 = this.currentUser;
    let params = new HttpParams().set( 'user1', user1 );
    return this.http.get<any>( this.UNREGISTER, {headers: httpOptions.headers, params: params})
      .subscribe(( result ) => {
        if( result[0] && result[0].unregistered == 'true') {
          this._snackBar.open('You have been unregistered!', "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
          this.router.navigate(['/Home']);
        } else {
          this._snackBar.open('Something Went Wrong!', "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
        }

      });
   }
}
