import { Component, OnInit } from '@angular/core';
import { Professionals } from 'src/app/shared/models/professionals.model';
import { PubeoService } from 'src/app/shared/services/pubeo.service';

@Component({
  selector: 'app-professional-list',
  templateUrl: './professional-list.component.html',
  styleUrls: ['./professional-list.component.css']
})
export class ProfessionalListComponent implements OnInit {

  professionnals: Professionals[];
  constructor(private particularsService: PubeoService) { }

  ngOnInit() {
    this.particularsService.getAllProfessionals()
        .subscribe(data => this.professionnals = data,
                  errorStatus => alert("Problème serveur - code : " + errorStatus));
  }

}
