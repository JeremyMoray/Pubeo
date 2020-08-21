import { Injectable } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  constructor(public auth: AuthenticationService, public router: Router) {}
  canActivate(): boolean {
    if (!this.auth.isAuthenticated()) {
      this.router.navigate(['login']);
      return false;
    }
    const helper = new JwtHelperService();

    const decodedToken = helper.decodeToken(this.auth.getToken());

    // Other functions

    /*if(decodedToken['http://schemas.microsoft.com/ws/2008/06/identity/claims/role'] != "admin"){
      alert("Vous n'êtes pas autorisé à accéder à cette ressource");
      return false;
    }*/

    return true;
  }
}
