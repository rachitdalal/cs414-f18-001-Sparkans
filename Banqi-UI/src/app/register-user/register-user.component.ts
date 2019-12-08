import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {
  REGISTER_USER_URL = "http://localhost:31406/register";
  registrationForm = new FormGroup({

    email : new FormControl('', [Validators.required, Validators.email]),
  password : new FormControl('', [Validators.required]),
  nickName : new FormControl('', [Validators.required])
  });
  userName;

  /*
  * Added HttpClient service to make rest calls
  * */
  constructor( private http: HttpClient,
               private router: Router,
               private _snackBar: MatSnackBar ) { }

  ngOnInit() {

  }

  //* Added this method to validate input
  //* @param { type: String }
  //*
  getErrorMessage(type: String) {
    if( type.length === 0 ){
      return this.registrationForm.controls['email'].hasError('required') ? 'You must enter a value' :
        this.registrationForm.controls['email'].hasError('email') ? 'Not a valid email' :
          '';
    } else if( type === 'nickName' ) {
      return this.registrationForm.controls['nickName'].hasError('required') ? 'You must enter a nickName' : '';
    } else {
      return this.registrationForm.controls['nickName'].hasError('required') ? 'You must enter a password' : '';
    }
  }

  /*
  * This method will be called when user click on Register user button
  * */
  registerUser ( value: FormGroup): Subscription {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const userDetails = value;

    return this.http.post<any>( this.REGISTER_USER_URL, userDetails, httpOptions)
      .subscribe(( results ) => {
        if( results[0].registered !== 'false' ){
          /* TODO: emit subject from here to app component to display name on the header*/
          this._snackBar.open("User has been registered !", "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
          // this.userName = userDetails['nickName'];
         this.router.navigate(['/signin']);
        } else {
          this._snackBar.open( results[1].detailMessage, "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
        }
      // this.result = results;
    }, (error) => {
        console.log("Register API Error", error.toString());
          this._snackBar.open("Registration has not been successful. Please try again later. ", "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
      });

  }

}
