import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup} from '@angular/forms'

@Component({
  selector: 'app-professional',
  templateUrl: './professional.component.html',
  styleUrls: ['./professional.component.css']
})
export class ProfessionalComponent implements OnInit {

  professionalForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.professionalForm = this.fb.group({
      nomEntreprise: '',
      mail: '',
      adresse: '',
      numeroTel: '',
      motDePasse: '',
      numeroTVA: '',
      localiteCode: ''
    })
  }

  ngOnInit() {
  }

}
