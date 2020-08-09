import { Component, OnInit } from '@angular/core';
import { Professionals } from 'src/app/shared/professionals.model';
import { ActivatedRoute } from '@angular/router';
import { PubeoService } from 'src/app/shared/pubeo.service';
import { NgForm } from '@angular/forms';

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
    this.resetForm();
    this.nomEntreprise = this.route.snapshot.paramMap.get('nomEntreprise');
    return this.pubeoService.getProfessionalByName(this.nomEntreprise).subscribe(data => this.professional = data);
  }

  resetForm(form?: NgForm) {
    if (form != null)
      form.resetForm();
    this.pubeoService.professionelForm = {
      id: null,
      nomEntreprise: null,
      MotDePasse: null,
      adresse: null,
      numeroTel: null,
      mail: null,
      numeroTVA: null,
      stickers: null
    }
  }

  onSubmit(form: NgForm){
    this.pubeoService.updateProfessional(form.value)
      .subscribe();
  }

}
