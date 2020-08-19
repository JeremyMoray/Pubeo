import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Particulars } from 'src/app/shared/models/particulars.model';

@Component({
  selector: 'app-particular',
  templateUrl: './particular.component.html',
  styleUrls: ['./particular.component.css']
})
export class ParticularComponent implements OnInit {

  newPart: Particulars;
  particularForm: FormGroup;
  @Output() addPartOutput: EventEmitter<any> = new EventEmitter<any>();

  constructor(private fb: FormBuilder, private pubeoService : PubeoService) { }

  ngOnInit() {
    this.particularForm = this.fb.group({
      nom: ['', 
        [
          Validators.required,
          Validators.maxLength(100)
        ]
      ],
      prenom: ['', 
        [
          Validators.required,
          Validators.maxLength(100)
        ]
      ],
      adresse: ['', 
        [
          Validators.required,
          Validators.maxLength(300)
        ]
      ],
      pseudo: ['', 
        [
          Validators.required,
          Validators.maxLength(100)
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
      dateNaissance: ['', 
        [
          Validators.required
        ]
      ],
      numeroTel: ['', 
        [
          Validators.required,
          Validators.pattern("^[0-9]{10}$")
        ]
      ],
      mail: ['', 
        [
          Validators.required,
          Validators.email,
          Validators.maxLength(100)
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

  get nom() {
    return this.particularForm.get('nom'); 
  }

  get prenom() {
    return this.particularForm.get('prenom'); 
  }

  get adresse() {
    return this.particularForm.get('adresse'); 
  }

  get pseudo() {
    return this.particularForm.get('pseudo'); 
  }

  get dateNaissance() {
    return this.particularForm.get('dateNaissance'); 
  }

  get numeroTel() {
    return this.particularForm.get('numeroTel'); 
  }

  get mail() {
    return this.particularForm.get('mail'); 
  }

  get motDePasse() {
    return this.particularForm.get('motDePasse'); 
  }

  get localiteCode() {
    return this.particularForm.get('localiteCode'); 
  }

  sendForm(){
    this.pubeoService.addParticular(this.particularForm.value).subscribe(data => this.newPart = data, error => this.alertError(error), () => this.proceedSubmit());
  }

  proceedSubmit(){
    this.addPartOutput.emit(this.newPart);
  }

  alertError(error: any){
    if(error.status == 404)
      alert("Le code postal n'existe pas");
    
    if(error.status == 409){
      
      if(error.error == 'Mail')
        alert("L'adresse mail existe déjà");
      
        if(error.error == 'Pseudo')
        alert("Le pseudo existe déjà");
    }
  }
}
