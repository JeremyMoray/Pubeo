import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { PubeoService } from '../shared/services/pubeo.service';
import { AuthenticationService } from '../shared/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    constructor(private fb: FormBuilder, private authenticationService: AuthenticationService,private pubeoService: PubeoService, private router: Router) { }

    userForm: FormGroup;

    ngOnInit() {
        this.userForm = this.fb.group({
            email: [''],
            password: ['']
        })
    }

    get email() {
        return this.userForm.get('email');
    }

    get password() {
        return this.userForm.get('password');
    }

    login() {
        this.pubeoService.login(this.userForm.value).subscribe(data => this.proceedLogin(data), error => this.alertError(error));
    }

    proceedLogin(data){
        this.authenticationService.setToken(data.result);
        this.router.navigate(["accueil"]);
    }

    alertError(error: any){
        console.log(error);
        if(error.status == 404){
            alert("Cet email n'existe pas");
          }
        if(error.status == 400){
          alert("Mot de passe incorrect");
        }
    }
}