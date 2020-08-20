import { Injectable } from '@angular/core';
import { Token } from '../models/token.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {

  private _accessToken: string;
  constructor(private router: Router) { }

  public setToken(token: string) {
    this._accessToken = token;
  }

  public getToken(): string {
    return this._accessToken;
  }

  public isAuthenticated(): boolean {
    return this._accessToken !== null && this._accessToken!==undefined;
  }
  public logout(): void{
    this._accessToken = null;
    this.router.navigate(["login"]);
  }
}
