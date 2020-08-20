import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { Particulars } from 'src/app/shared/models/particulars.model';
import { Sticker } from 'src/app/shared/models/sticker.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-particular-details',
  templateUrl: './particular-details.component.html',
  styleUrls: ['./particular-details.component.css']
})
export class ParticularDetailsComponent implements OnInit {

  Id;
  particular: Particulars;
  stickers: Sticker[];
  particularForm: FormGroup;
  particularAdaptiveForm: any;
  errorStatus: number;
  loading: boolean;
  
  constructor(private fb: FormBuilder, private route:ActivatedRoute, private pubeoService: PubeoService, private router : Router) { }

  ngOnInit() {
    this.Id= this.route.snapshot.paramMap.get('Id');
    this.loading = true;

    this.pubeoService.getParticularById(this.Id)
        .subscribe(data => this.setFormDefault(data),
                  error => this.connectionError(error.status));
    
    this.pubeoService.getAllStickersByParticularId(this.Id)
        .subscribe(data => this.stickers = data,
                  error => this.connectionError(error.status));
  }

  setFormDefault(particular){
    this.particular = particular;
    this.particularForm = this.fb.group({
      nom: [this.particular.nom, 
        [
          Validators.required,
          Validators.maxLength(100)
        ]
      ],
      prenom: [this.particular.prenom, 
        [
          Validators.required,
          Validators.maxLength(100)
        ]
      ],
      adresse: [this.particular.adresse, 
        [
          Validators.required,
          Validators.maxLength(300)
        ]
      ],
      pseudo: [this.particular.pseudo, 
        [
          Validators.required,
          Validators.maxLength(100)
        ]
      ],
      motDePasse: ['', 
        [
          Validators.minLength(6),
          Validators.maxLength(100),
          Validators.pattern(".*[0-9].*")
        ]
      ],
      dateNaissance: [this.particular.dateNaissance, 
        [
          Validators.required
        ]
      ],
      numeroTel: [this.particular.numeroTel, 
        [
          Validators.required,
          Validators.pattern("^[0-9]{10}$")
        ]
      ],
      mail: [this.particular.mail, 
        [
          Validators.required,
          Validators.email,
          Validators.maxLength(100)
        ]
      ],
      localiteCode: [this.particular.localite.codePostal, 
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
    this.particularAdaptiveForm = this.particularForm.value;
    if(!this.particularForm.value.motDePasse){
      delete this.particularAdaptiveForm['motDePasse'];
    }
    
    this.pubeoService.updateParticular(this.Id, this.particularAdaptiveForm).subscribe(null, error => this.alertError(error), () => alert("Modifications enregistrées"));
  }

  alertError(error: any){
    if(error.status == 404)
      alert("Le code postal n'existe pas");
    else{
      if(error.status == 409){
        if(error.error == 'Mail')
          alert("L'adresse mail existe déjà");
        
          if(error.error == 'Pseudo')
          alert("Le pseudo existe déjà");
      }
      else{
        this.router.navigate(['connectionError']);
      }
    }
  }

  connectionError(errorStatus: number){
    this.errorStatus = this.errorStatus;
    if(this.errorStatus != 404)
      this.router.navigate(['connectionError']);
  }
}
