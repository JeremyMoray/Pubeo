import { Component, OnInit } from '@angular/core';
import { PubeoService } from '../shared/services/pubeo.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  requestNumber: string;
  requestErrorNumber: string;
  particularCount: number;
  professionalCount: number;
  stickersCount: number;
  constructor(private pubeoService: PubeoService, private router: Router) { }

  ngOnInit() {
    this.pubeoService.getCountParticular()
                .subscribe(data => this.particularCount = data,
                          () => this.connectionError());

    this.pubeoService.getCountProfessional()
                .subscribe(data => this.professionalCount = data,
                          () => this.connectionError());

    this.pubeoService.getCountStickers()
    .subscribe(data => this.stickersCount = data,
    () => this.connectionError());
    
    this.requestNumber = localStorage.getItem('requestNumber');
    this.requestErrorNumber = sessionStorage.getItem('requestErrorNumber');

    if(this.requestNumber == null)
        this.requestNumber = "0";
        sessionStorage.setItem('requestNumber', '0');

    if(this.requestErrorNumber == null)
        this.requestErrorNumber = "0";
        sessionStorage.setItem('requestErrorNumber', '0');
  }

  connectionError(){
    this.router.navigate(['connectionError'])
  }

}