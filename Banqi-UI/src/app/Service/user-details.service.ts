import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {

  private _userName: string;
  private _invitationSubject = new Subject<any>();
  public pieceMoved = new Subject<any>();
  private _invitedUserName: String;
  private _userName2: string;
  private _chessBoard: any[][];
  private _isInvitationAccepted = new Subject<any>();

  get userName(): string {
    return this._userName;
  }

  set userName(value: string) {
    this._userName = value;
  }


  get invitedUserName(): String {
    return this._invitedUserName;
  }

  set invitedUserName(value: String) {
    this._invitedUserName = value;
  }

  get userName2(): string {
    return this._userName2;
  }

  set userName2(value: string) {
    this._userName2 = value;
  }

  get chessBoard(): any[][] {
    return this._chessBoard;
  }

  set chessBoard(value: any[][]) {
    this._chessBoard = value;
  }


  get invitationSubject(): Subject<any> {
    return this._invitationSubject;
  }

  set invitationSubject(value: Subject<any>) {
    this._invitationSubject = value;
  }


  get isInvitationAccepted(): Subject<any> {
    return this._isInvitationAccepted;
  }

  set isInvitationAccepted(value: Subject<any>) {
    this._isInvitationAccepted = value;
  }

  constructor() { }
}
