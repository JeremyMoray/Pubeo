import { Injectable } from '@angular/core';
import { Particulars } from './particulars.model';
import { Sticker } from './sticker.model';

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
      Zipcode: 6880,
      StickerList: 
        [
          {
            Id: 1,
            QRcode: "qrcodeur", 
            Title: "Burger king", 
            Description: "good food", 
            Remuneration: 5, 
            Height: 24, 
            Width: 24, 
            RemainingUses: 5
          },
          {
            Id: 2,
            QRcode: "qrcodeur", 
            Title: "Mac donalds", 
            Description: "bad food", 
            Remuneration: 25, 
            Height: 32, 
            Width: 32, 
            RemainingUses: 4
          }
        ]
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
      Zipcode: 10000,
      StickerList: 
        [
          {
            Id: 1,
            QRcode: "qrcodeur", 
            Title: "Burger king", 
            Description: "good food", 
            Remuneration: 5, 
            Height: 24, 
            Width: 24, 
            RemainingUses: 5
          },
          {
            Id: 2,
            QRcode: "qrcodeur", 
            Title: "Mac donalds", 
            Description: "bad food", 
            Remuneration: 25, 
            Height: 32, 
            Width: 32, 
            RemainingUses: 4
          }
        ]
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
      Zipcode: 17000,
      StickerList: 
        [
          {
            Id: 1,
            QRcode: "qrcodeur", 
            Title: "Burger king", 
            Description: "good food", 
            Remuneration: 5, 
            Height: 24, 
            Width: 24, 
            RemainingUses: 5
          },
          {
            Id: 2,
            QRcode: "qrcodeur", 
            Title: "Mac donalds", 
            Description: "bad food", 
            Remuneration: 25, 
            Height: 32, 
            Width: 32, 
            RemainingUses: 4
          }
        ]
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
