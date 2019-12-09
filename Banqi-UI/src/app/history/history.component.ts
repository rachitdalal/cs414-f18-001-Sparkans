import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserDetailsService} from "../Service/user-details.service";
import {GameRuleComponent} from "../game-rule/game-rule.component";
import {MatDialog} from "@angular/material/dialog";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit, AfterViewInit {
  HISTORY = 'http://localhost:31406/history';
  currentUser: string;
  isUserSignedIn: boolean = false;
  historyData:any;
  displayedColumns: string[] = ['Player1', 'Opponent', 'Status', 'Game Start'];
  @ViewChild('resume', {static: false}) input: ElementRef;
  constructor(private route: ActivatedRoute,
              private userDetails: UserDetailsService,
              public dialog: MatDialog,
              private http: HttpClient,
              private router: Router,
              private sanitizer: DomSanitizer,
              private elRef:ElementRef) {

  }

  ngOnInit() {

    if (localStorage.getItem("user1")) {
      this.isUserSignedIn = true;
      this.currentUser = localStorage.getItem("user1");
    }

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    const user1 = this.currentUser;
    let params = new HttpParams().set( 'user1', user1 );

    return this.http.get<any>( this.HISTORY, {headers: httpOptions.headers, params: params})
      .subscribe(( result ) => {
        console.log("history", result);
        if( result[0].length > 0 ) {
          let data = [];
          for( let index = 0; index < result[0].length; index += 1  ) {
            if ( index == 3 ) {
              let status = result[0][index];
              if( status === 'new' || status === 'paused') {
                status = 'In Progress';
                result[0][index] = status;
              }
            }
            if ( index == 4 ) {
              let status = result[0][index];
              if( result[0][3] === 'In Progress') {
                status = 'In Progress';

              }
            }
            if( index !== 2 ) {
              data.push( result[0][index]);
            }
          }
          if( data[2] === 'In Progress') {
            data[3] = this.sanitizer.bypassSecurityTrustHtml('<button #resume (click)="onResume(result[0][1], result[0][1])">Resume</button>') ;
          }

          this.historyData = data;
        }
      })
  }

  onResume( user1, user2 ) {
    if(!localStorage.getItem('user1')){
      localStorage.setItem('user1', user1);
    }
    if( !localStorage.getItem('user2') ) {
      localStorage.setItem('user2', user2);
    }
    this.router.navigate(['/gamePlay']);
  }

  onGameRules() {
    const dialogRef = this.dialog.open(GameRuleComponent, {
      width: '900px',
      height: '600px'
    });
  }


  ngAfterViewInit() {
    // assume dynamic HTML was added before
    //this.input.nativeElement.querySelector('button').addEventListener('click', this.onResume.bind(this));
  }
}


