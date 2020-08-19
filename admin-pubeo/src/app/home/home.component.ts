import { Component, OnInit } from '@angular/core';
import { PubeoService } from '../shared/services/pubeo.service';

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
  constructor(private pubeoService: PubeoService) { }

  ngOnInit() {
    this.pubeoService.getCountParticular()
                .subscribe(data => this.particularCount = data,
                          () => alert("Erreur de connexion au serveur"));

    this.pubeoService.getCountProfessional()
                .subscribe(data => this.professionalCount = data,
                          () => alert("Erreur de connexion au serveur"));

    this.pubeoService.getCountStickers()
    .subscribe(data => this.stickersCount = data,
    () => alert("Erreur de connexion au serveur"));
    
    this.requestNumber = localStorage.getItem('requestNumber');
    this.requestErrorNumber = localStorage.getItem('requestErrorNumber');

    if(this.requestNumber == null)
        this.requestNumber = "0";
        localStorage.setItem('requestNumber', '0');

    if(this.requestErrorNumber == null)
        this.requestErrorNumber = "0";
        localStorage.setItem('requestErrorNumber', '0');
  }

}