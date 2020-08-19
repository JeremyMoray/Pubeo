import { Component, OnInit } from '@angular/core';
import { Professionals } from 'src/app/shared/models/professionals.model';
import { ActivatedRoute } from '@angular/router';
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { Sticker } from 'src/app/shared/models/sticker.model';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-professional-details',
  templateUrl: './professional-details.component.html',
  styleUrls: ['./professional-details.component.css']
})
export class ProfessionalDetailsComponent implements OnInit {

  Id;
  professional: Professionals;
  stickers: Sticker[];
  professionalForm: FormGroup;
  professionalAdaptiveForm: any;
  errorStatus: number;
  loading: boolean;

  constructor(private fb: FormBuilder, private route: ActivatedRoute, private pubeoService: PubeoService) { }

  ngOnInit() {
    this.Id= this.route.snapshot.paramMap.get('Id');
    this.loading = true;

    this.pubeoService.getProfessionalById(this.Id)
        .subscribe(data => this.setFormDefault(data),
                  error => this.errorStatus = error.status);

    this.pubeoService.getAllStickersByProfessionalId(this.Id)
        .subscribe(data => this.stickers = data,
                  error => this.errorStatus = error.status);
  }

  setFormDefault(professional){
    this.professional = professional;
    this.professionalForm = this.fb.group({
      nomEntreprise: [this.professional.nomEntreprise, 
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100)
        ]
      ],
      mail: [this.professional.mail, 
        [
          Validators.required,
          Validators.email
        ]
      ],
      adresse: [this.professional.adresse, 
        [
          Validators.required,
          Validators.maxLength(300)
        ]
      ],
      numeroTel: [this.professional.numeroTel, 
        [
          Validators.required,
          Validators.pattern("^[0-9]{10}$")
        ]
      ],
      motDePasse: ['', 
        [
          Validators.minLength(6),
          Validators.maxLength(100),
          Validators.pattern(".*[0-9].*")
        ]
      ],
      numeroTVA: [this.professional.numeroTVA, 
        [
          Validators.required,
          Validators.pattern("^[0-9]{10}$")
        ]
      ],
      localiteCode: [this.professional.localite.codePostal, 
        [
          Validators.required,
          Validators.pattern("^[0-9]{4}$"),
          Validators.minLength(4),
          Validators.maxLength(4)
        ]
      ]
    })
    this.loading = false;
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
    this.professionalAdaptiveForm = this.professionalForm.value;
    if(!this.professionalForm.value.motDePasse){
      delete this.professionalAdaptiveForm['motDePasse'];
    }
    this.pubeoService.updateProfessional(this.Id, this.professionalAdaptiveForm).subscribe(null, error => this.alertError(error), () => alert("Modifications enregistrées"));
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
