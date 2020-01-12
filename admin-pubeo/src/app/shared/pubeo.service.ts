import { Injectable } from '@angular/core';
import { Professionals } from './professionals.model';
import { Sticker } from './sticker.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PubeoService {
  professionelForm: Professionals;
  apiUrl = 'https://localhost:5001/';
  
  constructor(private http: HttpClient) {}

  getAllProfessionals() {
    return this.http.get<Professionals[]>(this.apiUrl + "Professionnels");
  }

  getProfessionalByName(nomEntreprise){
    return this.http.get<Professionals>(this.apiUrl + "Professionnels/" + nomEntreprise);
  }

  getAllStickers() {
    return this.http.get<Sticker[]>(this.apiUrl + "Stickers");
  }

  addProfessional(professionelForm: Professionals){
    return this.http.post(this.apiUrl + "Professionnels", professionelForm);
  }
}