import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {UserDetailsService} from "../Service/user-details.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-game-play',
  templateUrl: './game-play.component.html',
  styleUrls: ['./game-play.component.css']
})
export class GamePlayComponent implements OnInit {
  private readonly chessboard: any[][];
  dummyTest: boolean = false;
  isLoaded: boolean = false;
  boardPosition;
  subject;
  GET_GAME = "http://localhost:31406/getGame";
  GAME_NOT_LOADED = "There is no game to load! Please invite your friend and start the game again!";
  FLIP_PIECE = "http://localhost:31406/flip";

  constructor( private http: HttpClient,
               private userDetails: UserDetailsService,
               private _snackBar: MatSnackBar,
               private route: ActivatedRoute) {

      this.chessboard = [];
  }

  ngOnInit() {

    this.subject = this.route
      .data
      .subscribe(( username ) => {
        console.log(username);
      });
  }

  loadGame() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const userNickName = this.userDetails.userName;
    const user2NickName = this.userDetails.userName2;
    let params = new HttpParams().set('user1', userNickName).set('user2',  user2NickName);

    return this.http.get<any>( this.GET_GAME, {headers: httpOptions.headers, params: params})
      .subscribe(( result ) => {
        if( result && result.board ) {
          this.boardPosition = result.board;
          for( let  raw: number = 0; raw < this.boardPosition.length; raw += 1 ) {
            this.chessboard[raw] = this.boardPosition[raw];
            for( let column: number = 0; column< this.boardPosition[0].length; column += 1 ) {
              this.chessboard[raw][column] == result.board[raw][column];
              /*this.chessboard[raw][column] = Object.assign({}, this.boardPosition[raw][column]);*/
            }

          }
          this.isLoaded = true;
        } else {
          this._snackBar.open(this.GAME_NOT_LOADED, "", {
            duration: 500000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
        }
      }, ( error ) => {

      });
  }


  getRaw(i: number) {
    switch (i) {
      case 0:
        return "a";
        break;
      case 1:
        return "b";
        break;
      case 2:
        return "c";
        break;
      case 3:
        return "d";
        break;
      default:
          break;
    }

  }

  testButton(raw: number, column: number ) {
    console.log("test", raw, column )
  }

  dragStart(event, row, column) {
    event.dataTransfer.setData("text", event.target.id);
   // this.dummyTest = true;
  }

  allowDrop( event ) {
    event.preventDefault();
  }

  drop( event ) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const userNickName = this.userDetails.userName;

    event.preventDefault();
    let data = event.dataTransfer.getData("text");
    const from = data,
      to = event.currentTarget.id && event.currentTarget.id.split("_")[1];

    if( from !== to ) {
      let params = new HttpParams().set('user', userNickName).set('from', from ).set("to", to);

      return this.http.get<any>( this.FLIP_PIECE, {headers: httpOptions.headers, params: params})
        .subscribe(( result ) => {

          if( result[0].validMove.toLowerCase() == 'true' ) {
            if( event.target.nodeName.toLowerCase() !== 'button'&&
              event.currentTarget.nodeName.toLowerCase() === 'td' ) {
              if( event.currentTarget.childElementCount == 1 ) {
                event.target.getElementsByClassName("border")[0].replaceWith(document.getElementById(data))
              } else {
                event.target.appendChild(document.getElementById(data));
              }
            } else {
              event.target.replaceWith(document.getElementById(data))
            }
          }
          else {
            this._snackBar.open("Invalid Move!", "", {
              duration: 5000,
              horizontalPosition: "right",
              verticalPosition: "top",
              panelClass: ["customSnackBar"]

            });
          }
        });

    }

    //if( !this.dummyTest ) {
      // event.target.replaceWith(document.getElementById(data))
    // } else {
    //  this.dummyTest = !this.dummyTest;
    // }

  }

  drop1( event ) {
    event.preventDefault();
    let data = event.dataTransfer.getData("text");
    event.target.appendChild(document.getElementById(data));
    //if( !this.dummyTest ) {
    // event.target.replaceWith(document.getElementById(data))
    // } else {
    //  this.dummyTest = !this.dummyTest;
    // }

  }

  toggleFaceDownPiece( event, raw, column ) {
    const position: string = this.getRaw(raw) +( column + 1 );
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const userNickName = this.userDetails.userName;
    let params = new HttpParams().set('user', userNickName).set('position', position );

    if( this.chessboard[raw][column].isFaceDown ) {
      return this.http.get<any>( this.FLIP_PIECE, {headers: httpOptions.headers, params: params})
        .subscribe(( result ) => {
          if( result[0].flipped ) {
            /*setTimeout( ( ) => {*/
            this.chessboard[raw][column].isFaceDown = false;
            /*}, 0);*/
          }
        }, ( error ) => {
          this._snackBar.open("Something went wrong!!! ", "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
        });
    }

  }
}
