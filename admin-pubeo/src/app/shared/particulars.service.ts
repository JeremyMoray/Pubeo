import { Injectable } from '@angular/core';
import { Particulars } from './particulars.model';

@Injectable({
  providedIn: 'root'
})
export class ParticularsService {

  particularForm : Particulars
  constructor() { }
}
