import { Injectable } from '@angular/core';
import { Token } from '../models/token.model';

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {

  private _accessToken: string;
  constructor() { }

  public setToken(token: string) {
    this._accessToken = token;
  }

  public getToken(): string {
    return this._accessToken;
  }

  public isAuthenticated(): boolean {
    return this._accessToken !== null && this._accessToken!==undefined;
  }
}
