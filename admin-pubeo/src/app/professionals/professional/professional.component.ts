import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms'
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { Professionals } from 'src/app/shared/models/professionals.model';

@Component({
  selector: 'app-professional',
  templateUrl: './professional.component.html',
  styleUrls: ['./professional.component.css']
})
export class ProfessionalComponent implements OnInit {

  newPro: Professionals;
  professionalForm: FormGroup;
  @Output() addProOutput: EventEmitter<any> = new EventEmitter<any>();

  constructor(private fb: FormBuilder, private pubeoService:PubeoService) { }

  ngOnInit() {
    this.professionalForm = this.fb.group({
      nomEntreprise: ['', 
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100)
        ]
      ],
      mail: ['', 
        [
          Validators.required,
          Validators.email
        ]
      ],
      adresse: ['', 
        [
          Validators.required,
          Validators.maxLength(300)
        ]
      ],
      numeroTel: ['', 
        [
          Validators.required,
          Validators.pattern("^[0-9]{10}$")
        ]
      ],
      motDePasse: ['', 
        [
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(100),
          Validators.pattern(".*[0-9].*")
        ]
      ],
      numeroTVA: ['', 
        [
          Validators.required,
          Validators.pattern("^[0-9]{10}$")
        ]
      ],
      localiteCode: ['', 
        [
          Validators.required,
          Validators.pattern("^[0-9]{4}$"),
          Validators.minLength(4),
          Validators.maxLength(4)
        ]
      ]
    })
  }

  get nomEntreprise() {
    return this.professionalForm.get('nomEntreprise'); 
  }

  get email() {
    return this.professionalForm.get('mail');
  }

  get adresse() {
    return this.professionalForm.get('adresse');
  }

  get numeroTel() {
    return this.professionalForm.get('numeroTel');
  }

  get motDePasse() {
    return this.professionalForm.get('motDePasse');
  }

  get numeroTVA() {
    return this.professionalForm.get('numeroTVA');
  }

  get localiteCode() {
    return this.professionalForm.get('localiteCode');
  }

  sendForm(){
    this.pubeoService.addProfessional(this.professionalForm.value).subscribe(data => this.newPro = data, error => this.alertError(error), () => this.proceedSubmit());
  }

  proceedSubmit(){
      this.addProOutput.emit(this.newPro);
  }

  alertError(error: any){
    if(error.status == 404)
      alert("Le code postal n'existe pas");
    
    if(error.status == 409){
      
      if(error.error == 'Mail')
        alert("L'adresse mail existe déjà");
      
        if(error.error == 'NomEntreprise')
        alert("Le nom de l'entreprise existe déjà");
    }
  }

}
