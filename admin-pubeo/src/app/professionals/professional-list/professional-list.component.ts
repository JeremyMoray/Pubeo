import { Component, OnInit } from '@angular/core';
import { Professionals } from 'src/app/shared/professionals.model';
import { PubeoService } from 'src/app/shared/pubeo.service';

@Component({
  selector: 'app-professional-list',
  templateUrl: './professional-list.component.html',
  styleUrls: ['./professional-list.component.css']
})
export class ProfessionalListComponent implements OnInit {

  list: Professionals[];
  constructor(private particularsService: PubeoService) { }

  ngOnInit() {
    this.list = this.particularsService.getAllProfessionals();
    
  }

}
