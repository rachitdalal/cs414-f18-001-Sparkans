  import {Component, OnDestroy, OnInit} from '@angular/core';
  import {Router} from "@angular/router";
  import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
  import {concatMap, flatMap, map, switchMap, take, takeWhile} from "rxjs/operators";
  import {interval, pipe, timer} from "rxjs";
  import {UserDetailsService} from "../Service/user-details.service";
  import {MatSnackBar} from "@angular/material/snack-bar";

  @Component({
    selector: 'app-invite',
    templateUrl: './invite.component.html',
    styleUrls: ['./invite.component.css']
  })
  export class InviteComponent implements OnInit, OnDestroy {
    SEND_INVITE = "http://localhost:31406/sendInvite";
    WAITING_INVITE = "http://localhost:31406/waitingInvite";
    ACCEPT_INVITATION = "http://localhost:31406/acceptInvite";
    obs;
    subscriber;
    isInvitationSentByThisUser: boolean = false;
    haveYouGotInvitation: boolean = false;
    invitationFromUserName: string ;

    constructor( private router: Router,
                 private http: HttpClient,
                 private userDetails: UserDetailsService,
                 private _snackBar: MatSnackBar ) {

      this.userDetails.invitationSubject.subscribe( ( value ) => {
        this.invitationFromUserName = value.invitaionSentFrom ;
        console.log("Updated after the invitation");
      } );

      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      };

      this.obs =  timer(0, 2000 )
        .pipe(switchMap(() => this.http.get(this.WAITING_INVITE,
                              {headers: httpOptions.headers,
                                params: this.getWaitingUser()} )));

      this.subscriber = this.obs.subscribe((data) => {

        if( data[0].inviteStatus.toLowerCase() == "not accepted" && data.length > 1
                  && !this.haveYouGotInvitation)  {
          this.haveYouGotInvitation = true;
          this._snackBar.open("You have got Invitation!", "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]
          });

        }
        /* Idea state For both the user to wait for the invitation and acceptance*/
        else if( data[0].inviteStatus.toLowerCase() == "not accepted" ) {
          console.log("waiting for the invite!!!");
        }
        /* user who sent invitation Navigates to gamePlay route */
        else if( data[0].inviteStatus.toLowerCase() == "accepted" ) {
          this.userDetails.userName2 = data[2].inviteTo;
          this.gamePlay();
        }

      }, ( error ) => {
        this._snackBar.open("Something Went Wrong!!! ", "", {
          duration: 5000,
          horizontalPosition: "right",
          verticalPosition: "top",
          panelClass: ["customSnackBar"]
        });
        console.log(error);
      })

    }
    ngOnInit() {
      this.userDetails.invitationSubject.subscribe( ( value ) => {
        this.invitationFromUserName = value.invitaionSentFrom ;
        console.log("Updated after the invitation");
      } )

    }

    getWaitingUser() {
      const params = new HttpParams().set('user', this.userDetails.userName);

      return params;
    }

    gamePlay() {
      this.router.navigate(['/gamePlay']);
    }

    onInvite( inviteUser: HTMLInputElement ) {
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      };
      const userNickName = this.userDetails.userName;

      let params = new HttpParams().set('from', userNickName).set('to', inviteUser.value);
      if (userNickName.toLowerCase() !== inviteUser.value.toLowerCase()) {
        return this.http.get<any>(this.SEND_INVITE, {headers: httpOptions.headers, params: params})
          .subscribe((results) => {
            if (results[0].inviteFor !== "" && results[1].from !== "") {
              console.log("Working httpGet Invite");
              this.userDetails.invitationSubject.next({invitaionSentFrom: this.userDetails.userName});
              this.userDetails.invitedUserName = this.userDetails.userName;
              this.isInvitationSentByThisUser = true;
              this._snackBar.open("Invitation has been sent", "", {
                duration: 5000,
                horizontalPosition: "right",
                verticalPosition: "top",
                panelClass: ["customSnackBar"]

              });

            }
            // this.result = results;
          }, (error) => {
            console.log(" Error Working httpGet Invite user ", error);
          });
      } else {
        this._snackBar.open("You cannot send request to yourself", "", {
          duration: 5000,
          horizontalPosition: "right",
          verticalPosition: "top",
          panelClass: ["customSnackBar"]

        });

      }
    }

    onAccept() {
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      };
      const userNickName = this.userDetails.userName;
      let params = new HttpParams().set('user', userNickName);

      return this.http.get<any>( this.ACCEPT_INVITATION, {headers: httpOptions.headers, params: params})
        .subscribe(( results ) => {
          if( results[0].inviteStatus  &&  results[1].inviteFrom ) {
            this.userDetails.userName2 = results[1].inviteFrom;
            this.gamePlay();
          }
        }, (error) => {
          console.log(" Error Working httpGet Invite user ", error);
        });

    }

    ngOnDestroy(): void {

      if( this.subscriber ) {
        this.subscriber.unsubscribe();
      }
    }
  }
