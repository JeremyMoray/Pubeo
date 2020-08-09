import { Component, OnInit } from '@angular/core';
import { PubeoService } from 'src/app/shared/pubeo.service';
import { NgForm } from '@angular/forms';
import { Guid } from 'src/app/shared/guid';

@Component({
  selector: 'app-professional',
  templateUrl: './professional.component.html',
  styleUrls: ['./professional.component.css']
})
export class ProfessionalComponent implements OnInit {

  constructor(private pubeoService: PubeoService) { }

  ngOnInit() {
    this.resetForm();
  }

  resetForm(form?: NgForm) {
    if (form != null)
      form.resetForm();
    this.pubeoService.professionelForm = {
      id: Guid.newGuid(),
      nomEntreprise: '',
      MotDePasse: '',
      adresse: '',
      numeroTel: '',
      mail: '',
      numeroTVA: '',
      stickers: null
    }
  }

  onSubmit(form: NgForm){
    this.pubeoService.addProfessional(form.value)
      .subscribe();
  }

}