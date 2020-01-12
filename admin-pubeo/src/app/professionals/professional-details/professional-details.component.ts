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

  nomEntreprise;
  professional: Professionals;
  constructor(private route:ActivatedRoute, private pubeoService: PubeoService) { }

  ngOnInit() {
    this.nomEntreprise = this.route.snapshot.paramMap.get('nomEntreprise');
    return this.pubeoService.getProfessionalByName(this.nomEntreprise).subscribe(data => this.professional = data);
  }

}
