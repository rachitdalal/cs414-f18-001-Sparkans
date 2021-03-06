import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {UserDetailsService} from "../Service/user-details.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivatedRoute} from "@angular/router";
import {interval, pipe, timer} from "rxjs";
import {FinalComponentComponent} from "../final-component/final-component.component";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {GameRuleComponent} from "../game-rule/game-rule.component";

@Component({
  selector: 'app-game-play',
  templateUrl: './game-play.component.html',
  styleUrls: ['./game-play.component.css']
})
export class GamePlayComponent implements OnInit {
  SAVE_GAME = "http://129.82.44.128:31406/save";
  QUIT_GAME = "http://129.82.44.128:31406/quit";
  private chessboard: any[][];
  subscriber;
  isLoaded: boolean = false;
  boardPosition;
  subject;
  GET_GAME = "http://129.82.44.128:31406/getGame";
  GAME_NOT_LOADED = "There is no game to load! Please invite your friend and start the game again!";
  FLIP_PIECE = "http://129.82.44.128:31406/flip";
  CHECK_LEGAL_MOVE = "http://129.82.44.128:31406/checkValidMove";
  currentUser: string;
  playerTurn: string;
  blackPlayer: object = {};
  redPlayer: object = {};
  winner: string;
  showTurn: boolean = false;
  shouldIPollToServer: boolean = true;

  constructor( private http: HttpClient,
               private userDetails: UserDetailsService,
               private _snackBar: MatSnackBar,
               private route: ActivatedRoute,
               public dialog: MatDialog) {

      this.chessboard = [];
  }

  ngOnInit() {

    /*this.loadGame();*/
    if( this.shouldIPollToServer ) {
      let timer1 = timer(0, 1000);
      this.subscriber = timer1.subscribe((data) => {
        console.log("Test");
        /*this.chessboard = this.userDetails.chessBoard;*/
        if(this.shouldIPollToServer) {
          this.latestMove();
        } else {
          this.subscriber.unsubscribe();
        }

      });
    } else {
      this.subscriber.unsubscribe();
    }


    /*this.userDetails.pieceMoved.subscribe( ( data ) => {
      console.log("Test");
      this.chessboard = data;
      this.chessboard = JSON.parse(localStorage.getItem("board"));
    })*/

    this.currentUser = localStorage.getItem("user1");
  }

  loadGame() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const userNickName = localStorage.getItem("user1");
    const user2NickName = localStorage.getItem("user2");
    let params = new HttpParams().set('user1', this.currentUser).set('user2',  user2NickName);

    return this.http.get<any>( this.GET_GAME, {headers: httpOptions.headers, params: params})
      .subscribe(( result ) => {
        if( result && result.board ) {
          this.boardPosition = result.board;
          /*this.userDetails.chessBoard = this.boardPosition;*/
          for( let  raw: number = 0; raw < this.boardPosition.length; raw += 1 ) {
            this.chessboard[raw] = this.boardPosition[raw];
            for( let column: number = 0; column< this.boardPosition[0].length; column += 1 ) {
              this.chessboard[raw][column] == result.board[raw][column];
              /*this.chessboard[raw][column] = Object.assign({}, this.boardPosition[raw][column]);*/
            }

          }

          this.isLoaded = true;
          this.playerTurn = result.playerTurn;
        } else {
          this._snackBar.open(this.GAME_NOT_LOADED, "", {
            duration: 5000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
        }
      }, ( error ) => {

      });
  }

  latestMove() {

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })

    };
    const userNickName = localStorage.getItem("user1");
    const user2NickName = localStorage.getItem("user2");
    let params = new HttpParams().set('user1', this.currentUser).set('user2',  user2NickName);

    return this.http.get<any>( this.GET_GAME, {headers: httpOptions.headers, params: params})
      .subscribe(( result ) => {
        if( result && result.board ) {
          this.boardPosition = result.board;
          /*this.userDetails.chessBoard = this.boardPosition;*/
          for( let  raw: number = 0; raw < this.boardPosition.length; raw += 1 ) {
            this.chessboard[raw] = this.boardPosition[raw];
            for( let column: number = 0; column< this.boardPosition[0].length; column += 1 ) {
              this.chessboard[raw][column] == result.board[raw][column];
              /*this.chessboard[raw][column] = Object.assign({}, this.boardPosition[raw][column]);*/
            }

          }
          this.isLoaded = true;
          this.playerTurn = result.playerTurn.toLowerCase();

          if(!this.blackPlayer.hasOwnProperty('blackPlayer')) {
            if( result.whitePlayer === this.currentUser ) {
              this.blackPlayer['blackPlayer'] =  'BLACK';
            }
            else if ( result.redPlayer === this.currentUser ) {
              this.redPlayer['redPlayer'] =  'RED';
            }
          }
          if( this.playerTurn.toLowerCase() === this.currentUser ) {
            this.showTurn = true;
          } else {
            this.showTurn = false;
          }
          this.winner = result.winner;
          /*if( this.winner.toLowerCase() !== 'none') {
            const win = this.winner === this.currentUser;
            this._snackBar.open( win ? 'You Won !' : ( this.winner === localStorage.getItem('user2') ? 'You Lost !' : ''), "", {
              duration: 5000,
              horizontalPosition: "right",
              verticalPosition: "top",
              panelClass: ["customSnackBar"]

            });
          }*/
          if( this.winner.toLowerCase() !== 'none' ) {
            // const win = this.winner.toLowerCase() === this.currentUser.toLowerCase();
            this.shouldIPollToServer = false;
            if( this.winner.toLowerCase() === this.currentUser.toLowerCase() ) {
              const dialogRef = this.dialog.open(FinalComponentComponent, {
                width: '250px',
                data: {name: this.currentUser, status: 'Won', isConfirmationPopup : false }
              });
            } else {
              if(this.winner !== localStorage.getItem('user1')  ) {
                const dialogRef = this.dialog.open(FinalComponentComponent, {
                  width: '250px',
                  data: {name: localStorage.getItem('user1'), status: 'Lost', isConfirmationPopup : false }
                });
              }
              else if( this.winner === localStorage.getItem('user1') ) {
                const dialogRef = this.dialog.open(FinalComponentComponent, {
                  width: '250px',
                  data: {name: localStorage.getItem('user1'), status: 'Won', isConfirmationPopup : false }
                });
              } else {
                const dialogRef = this.dialog.open(FinalComponentComponent, {
                  width: '250px',
                  data: {name: localStorage.getItem('user1'), status: 'Lost', isConfirmationPopup : false }
                });
              }

            }
          }
        } else {
          this._snackBar.open(this.GAME_NOT_LOADED, "", {
            duration: 5000,
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
  /* Old Code commented to for drag to Start*/
  /*dragStart(event, row, column) {
    /!*event.dataTransfer.setData("text", event.target.id);*!/
    if(this.playerTurn && this.playerTurn.toLowerCase() === this.currentUser ) {
      event.dataTransfer.setData("text", event.target.parentNode.getAttribute("id").split("_")[1]);
    } else {
      this._snackBar.open("It's not your turn", "", {
        duration: 2000,
        horizontalPosition: "right",
        verticalPosition: "top",
        panelClass: ["customSnackBar"]

      });
      event.preventDefault();
      return;
    }

  }*/

    dragStart(event, row, column) {
      /*event.dataTransfer.setData("text", event.target.id);*/
      let id;
      if(this.playerTurn && this.playerTurn.toLowerCase() === this.currentUser ) {
        // console.log("test", event.target.parentNode.parentNode);

        if( event.target && event.target.parentNode.nodeName.toLowerCase() === 'td'  ) {
          id = event.target.parentNode.getAttribute("id").split("_")[1];
        } else if ( event.target.parentNode  && event.target.parentNode.parentNode.nodeName.toLowerCase() === 'td' ) {
          id = event.target.parentNode.parentNode.getAttribute("id").split("_")[1];
        } else if( event.target.parentNode.parentNode  && event.target.parentNode.parentNode.parentNode.nodeName.toLowerCase() === 'td' ) {
          id = event.target.parentNode.parentNode.parentNode.getAttribute("id").split("_")[1];
        } else {
          return;
        }
        event.dataTransfer.setData("text", id);
      } else {
        this._snackBar.open("It's not your turn", "", {
          duration: 2000,
          horizontalPosition: "right",
          verticalPosition: "top",
          panelClass: ["customSnackBar"]

        });
        event.preventDefault();
        return;
      }

    }

    allowDrop( event ) {
      event.preventDefault();
    }

 /* if( event.target.nodeName.toLowerCase() !== 'button'&&
  event.currentTarget.nodeName.toLowerCase() === 'td' ) {
  if( event.currentTarget.childElementCount == 1 ) {
  /!* Replacing/ Capturing the piece when move is legal *!/
  event.target.getElementsByClassName("border").length > 0 ? event.target.getElementsByClassName("border")[0].replaceWith(document.getElementById(data)) :
  event.currentTarget.getElementsByClassName('border').length > 0 ? event.currentTarget.getElementsByClassName('border')[0].replaceWith(document.getElementById(data)) : '';
} else {
  event.target.appendChild(document.getElementById(data));
}
} else {
  event.target.replaceWith(document.getElementById(data))
}*/

  drop( event ) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const userNickName = this.userDetails.userName;

    event.preventDefault();
    let data = event.dataTransfer.getData("text");
    const from = data;
      let to = event.currentTarget.id && event.currentTarget.id.split("_")[1];

    //to = to.charAt(0).toString() + ( Number(to.charAt(1)) - 1 ).toString()

    if( from !== to ) {
      let params = new HttpParams().set('user', this.currentUser).set('from', from ).set("to", to);

      return this.http.get<any>( this.CHECK_LEGAL_MOVE, {headers: httpOptions.headers, params: params})
        .subscribe(( result ) => {

          if( result[0].validMove.toLowerCase() == 'true' ) {
            if( event.target.nodeName.toLowerCase() !== 'button'&&
              event.currentTarget && event.currentTarget.nodeName.toLowerCase() === 'td' ) {
              if( event.currentTarget.childElementCount == 1 ) {
                /* Replacing/ Capturing the piece when move is legal */
                event.target.getElementsByClassName("border").length > 0 ? event.target.getElementsByClassName("border")[0].replaceWith(document.getElementById(data)) :
                  event.currentTarget.getElementsByClassName('border').length > 0 ? event.currentTarget.getElementsByClassName('border')[0].replaceWith(document.getElementById(data)) : '';
              } else {
                event.target.appendChild(document.getElementById(data));
              }
            }
            /*THis will capture the piece when move is legal*/
            else if ( event.target.parentNode.nodeName.toLocaleLowerCase() === 'button' && event.target.nodeName.toLowerCase() === 'div') {
              /*if()*/
              event.target.parentNode.replaceWith(document.getElementById(data));
              /*event.target.replaceWith(document.getElementById(data))*/
            }
            /* THis else if condition will be called when piece will move to empty space*/
            else  {
              if( event.target.nodeName.toLowerCase() === 'td') {
                event.target.appendChild(document.getElementById("TD_"+data).firstElementChild );
              }
            }
            this.latestMove();
          }
          else {
            this._snackBar.open("Invalid Move!", "", {
              duration: 3000,
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

  /*drop1( event ) {
    event.preventDefault();
    let data = event.dataTransfer.getData("text");
    event.target.appendChild(document.getElementById(data));
    //if( !this.dummyTest ) {
    // event.target.replaceWith(document.getElementById(data))
    // } else {
    //  this.dummyTest = !this.dummyTest;
    // }

  }*/

  toggleFaceDownPiece( event, raw, column ) {
    const position: string = this.getRaw(raw) +( column + 1 );
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const userNickName = this.currentUser;
    let params = new HttpParams().set('user', userNickName).set('position', position );
    if(this.playerTurn && this.playerTurn.toLowerCase() === this.currentUser ) {
      if( this.boardPosition[raw][column].isFaceDown ) {
        return this.http.get<any>( this.FLIP_PIECE, {headers: httpOptions.headers, params: params})
          .subscribe(( result ) => {
            if( result[0].flipped ) {
              /*setTimeout( ( ) => {*/
              this.chessboard[raw][column].isFaceDown = false;
              this.boardPosition[raw][column].isFaceDown = false;
              // this.userDetails.pieceMoved.next( this.chessboard );
              /*this.userDetails.chessBoard = this.chessboard;
              if( localStorage.getItem("board")) {
                localStorage.removeItem("board");
              }
              localStorage.setItem("board", JSON.stringify(this.chessboard));*/
              /*}, 0);*/
              this.latestMove();
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
    } else {
      this._snackBar.open("It's not your turn", "", {
        duration: 2000,
        horizontalPosition: "right",
        verticalPosition: "top",
        panelClass: ["customSnackBar"]

      });
      event.preventDefault();
      return;
    }

  }
  onGameRules() {
    const dialogRef = this.dialog.open(GameRuleComponent, {
      width: '900px',
      height: '600px'
    });
  }

  onSaveGame() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const user1 = this.currentUser,
          user2 = localStorage.getItem("user2");
    let params = new HttpParams().set('user1', user1).set('user2',  user2);

    return this.http.get<any>( this.SAVE_GAME, {headers: httpOptions.headers, params: params})
      .subscribe(( result ) => {
        if( result[0] && result[0].saved && result[0].saved.toLowerCase() === 'true' ) {
          this._snackBar.open("Game has been saved!", "", {
            duration: 3000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
        } else {
          this._snackBar.open("Game has not been saved!", "", {
            duration: 3000,
            horizontalPosition: "right",
            verticalPosition: "top",
            panelClass: ["customSnackBar"]

          });
        }
      })
  }

  onQuit() {

   const dialogRef = this.dialog.open(FinalComponentComponent, {
      width: '400px',
     height: '200px',
     data : { isConfirmationPopup : true }
    });

    dialogRef.afterClosed().subscribe(result => {
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      };

      const user1 = this.currentUser,
        user2 = localStorage.getItem("user2");
      let params = new HttpParams().set('user1', user1).set('user2',  user2);
      return this.http.get<any>( this.QUIT_GAME, {headers: httpOptions.headers, params: params})
        .subscribe(( result ) => {
          if(result[0] && result[0].saved && result[0].saved == 'true' ) {
            this.winner = result[0].winner;
          }
        });
    });
  }
}
