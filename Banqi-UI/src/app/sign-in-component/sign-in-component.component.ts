import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserDetailsService} from "../Service/user-details.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {GameRuleComponent} from "../game-rule/game-rule.component";

@Component({
  selector: 'app-sign-in-component',
  templateUrl: './sign-in-component.component.html',
  styleUrls: ['./sign-in-component.component.css']
})
export class SignInComponentComponent implements OnInit {
  SIGN_IN_URL = "http://129.82.44.128:31406/signin";
  signInForm = new FormGroup({
    nickName : new FormControl('', [Validators.required]),
    password : new FormControl('', [Validators.required])
  });

  constructor( private http: HttpClient,
               private router: Router,
               private userDetails: UserDetailsService,
               private _snackBar: MatSnackBar,
               public dialog: MatDialog) { }

  ngOnInit() {
  }

  getErrorMessage(type: String) {
    if( type.length === 0 ){
      return this.signInForm.controls['nickName'].hasError('required') ? 'You must enter a value' :
        this.signInForm.controls['nickName'].hasError('email') ? 'Not a valid nickName' :
          '';
    } else {
      return this.signInForm.controls['password'].hasError('required') ? 'You must enter a password' : 'Password should have minimum 8 characters';
    }
  }

  /*
 * This method will be called when user click on Register user button
 * */
  signInUser ( value: FormGroup): any /*Subscription*/ {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    /*const userDetails = { nickName: value['nickName'].toLowerCase(), password: value['password'] };;*/
    const userDetails = value;
    localStorage.clear();

    return this.http.post<any>( this.SIGN_IN_URL, userDetails, httpOptions)
      .subscribe(( results ) => {
        if( results.signedin && results.signedin !== "false" ){
          this._snackBar.open("successfully Signed In !", "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
          localStorage.setItem("user1", value['nickName'] );
          this.userDetails.userName = value['nickName'];
          this.router.navigate(['invite']);
        } else {
          this._snackBar.open(results.detailMessage, "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]
          });
        }
      }, (error) => {
        this._snackBar.open("Something Went Wrong!!", "", {
          duration: 5000,
          horizontalPosition: "right",
          verticalPosition: "top",
          panelClass: ["customSnackBar"]

        });
      });

  }
  onGameRules() {
    const dialogRef = this.dialog.open(GameRuleComponent, {
      width: '900px',
      height: '600px'
    });
  }

}
