import { Injectable } from '@angular/core';
import { Professionals } from '../models/professionals.model';
import { Particulars } from '../models/particulars.model';
import { Sticker } from '../models/sticker.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError as observableThrowError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PubeoService {

  professionelForm: Professionals;
  particularForm: Particulars;

  private baseUrl="https://localhost:5001";

  constructor(private http: HttpClient) { }

  getAllProfessionals() : Observable<Professionals[]>{
    return this.http.get<Professionals[]>(this.baseUrl + "/Professionnels");
  }

  addProfessional(professional: any): Observable<Professionals> {
    return this.http.post<Professionals>(this.baseUrl + "/Professionnels", professional, {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    });
  }

  deleteProfessional(Id): Observable<void>{
    return this.http.delete<void>(this.baseUrl + "/Professionnels/" + Id);
  }

  getProfessionalById(Id) : Observable<Professionals>{
    return this.http.get<Professionals>(this.baseUrl + "/Professionnels/" + Id);
  }

  getAllStickers() : Observable<Sticker[]>{
    return this.http.get<Sticker[]>(this.baseUrl + "/Stickers");
  }

  getAllStickersByProfessionalId(Id) : Observable<Sticker[]>{
    return this.http.get<Sticker[]>(this.baseUrl + "/Stickers/GetAllByProfessionnelId/" + Id);
  }

  getAllParticulars() : Observable<Particulars[]>{
    return this.http.get<Particulars[]>(this.baseUrl + "/Particuliers");
  }

  getParticularById(Id) : Observable<Particulars>{
    return this.http.get<Particulars>(this.baseUrl + "/Particuliers/" + Id);
  }
}