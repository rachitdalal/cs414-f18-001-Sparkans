import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserDetailsService} from "../Service/user-details.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-sign-in-component',
  templateUrl: './sign-in-component.component.html',
  styleUrls: ['./sign-in-component.component.css']
})
export class SignInComponentComponent implements OnInit {
  SIGN_IN_URL = "http://localhost:31406/signin";
  signInForm = new FormGroup({
    nickName : new FormControl('', [Validators.required]),
    password : new FormControl('', [Validators.required])
  });

  constructor( private http: HttpClient,
               private router: Router,
               private userDetails: UserDetailsService,
               private _snackBar: MatSnackBar ) { }

  ngOnInit() {
  }

  getErrorMessage(type: String) {
    if( type.length === 0 ){
      return this.signInForm.controls['nickName'].hasError('required') ? 'You must enter a value' :
        this.signInForm.controls['nickName'].hasError('email') ? 'Not a valid nickName' :
          '';
    } else {
      return this.signInForm.controls['password'].hasError('required') ? 'You must enter a password' : '';
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
    const userDetails = value;

    return this.http.post<any>( this.SIGN_IN_URL, userDetails, httpOptions)
      .subscribe(( results ) => {
        if( results.signedin ){
          this._snackBar.open("successfully Signed In !", "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
          this.userDetails.userName = value['nickName'];
          this.router.navigate(['invite']);
        }
      }, (error) => {
      });

  }
}
