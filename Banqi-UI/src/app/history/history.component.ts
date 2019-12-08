import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserDetailsService} from "../Service/user-details.service";


@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  currentUser: string;
  isUserSignedIn: boolean = false;

  constructor(private route: ActivatedRoute,
              private userDetails: UserDetailsService,) {

  }

  ngOnInit() {

    if (localStorage.getItem("user1")) {
      this.isUserSignedIn = true;
      this.currentUser = localStorage.getItem("user1");
    }
  }
}


