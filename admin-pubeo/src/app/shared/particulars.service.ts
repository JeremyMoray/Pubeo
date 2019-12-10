import { Injectable } from '@angular/core';
import { Particulars } from './particulars.model';

@Injectable({
  providedIn: 'root'
})
export class ParticularsService {

  particularForm: Particulars
  particularList: Particulars[] = [
    {
      Id: 1,
      Email: "jeje@gmail.com",
      Username: "zombilax",
      Password: "encode125",
      Firstname: "Jeremy",
      Lastname: "Moray",
      PhoneNumber: "054562659",
      Birthdate: new Date('1998-07-12'),
      Address: "Rue des oiseaux 45",
      City: "Bertrix",
      Zipcode: 6880
    },
    {
      Id: 2,
      Email: "fdsfsd@gmail.com",
      Username: "oald",
      Password: "poiu",
      Firstname: "Grégoire",
      Lastname: "Polia",
      PhoneNumber: "0454126554",
      Birthdate: new Date('1960-12-22'),
      Address: "Rue des étoiles 14",
      City: "Jupiter",
      Zipcode: 10000
    },
    {
      Id: 3,
      Email: "hguy@gmail.com",
      Username: "bolse",
      Password: "fdsf",
      Firstname: "Jol",
      Lastname: "Bola",
      PhoneNumber: "04545454555",
      Birthdate: new Date('1999-11-24'),
      Address: "Rue des boulangers 39",
      City: "Libramont",
      Zipcode: 17000
    },
  ]
  constructor() { }

  getAllParticulars() : Array<Particulars>{
    return this.particularList;
  }

  getParticularById(Id) : Particulars{
    return this.particularList[Id-1];
  }
}
