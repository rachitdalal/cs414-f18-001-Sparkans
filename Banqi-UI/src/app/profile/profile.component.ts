import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser;
  subject;

  constructor(private route: ActivatedRoute) {

  }

  ngOnInit() {

    this.subject = this.route
      .data
      .subscribe((username) => {
        console.log(username);
      });

    this.currentUser = localStorage.getItem("user1");
  }
}
