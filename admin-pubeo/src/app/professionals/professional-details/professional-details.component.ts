import { Component, OnInit } from '@angular/core';
import { Professionals } from 'src/app/shared/professionals.model';
import { ActivatedRoute } from '@angular/router';
import { PubeoService } from 'src/app/shared/pubeo.service';

@Component({
  selector: 'app-professional-details',
  templateUrl: './professional-details.component.html',
  styleUrls: ['./professional-details.component.css']
})
export class ProfessionalDetailsComponent implements OnInit {

  Id;
  professional: Professionals;
  constructor(private route:ActivatedRoute, private pubeoService: PubeoService) { }

  ngOnInit() {
    this.Id= this.route.snapshot.paramMap.get('Id');
    this.professional = this.pubeoService.getProfessionalById(this.Id);
  }

}
