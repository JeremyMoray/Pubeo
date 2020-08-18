import { Injectable } from '@angular/core';
import { Professionals } from '../models/professionals.model';
import { Particulars } from '../models/particulars.model';
import { Sticker } from '../models/sticker.model';

@Injectable({
  providedIn: 'root'
})
export class PubeoService {

  professionelForm: Professionals;
  particularForm: Particulars;

  stickersList: Sticker[] = [
    {
      Id: 1,
      QRcode: "qrcodeur", 
      Title: "Burger quality", 
      Description: "good food", 
      Remuneration: 5,
      Height: 24, 
      Width: 24, 
      RemainingUses: 5
    },
    {
      Id: 2,
      QRcode: "qrcodeur", 
      Title: "Burger large sticker", 
      Description: "A lot of good food", 
      Remuneration: 25, 
      Height: 32, 
      Width: 32, 
      RemainingUses: 4
    },
    {
      Id: 3,
      QRcode: "qrcodeur", 
      Title: "Mac donalds XXL", 
      Description: "good food", 
      Remuneration: 5, 
      Height: 24, 
      Width: 24, 
      RemainingUses: 5
    },
    {
      Id: 4,
      QRcode: "qrcodeur", 
      Title: "Mac donalds", 
      Description: "bad food", 
      Remuneration: 25, 
      Height: 32, 
      Width: 32, 
      RemainingUses: 4
    },
    {
      Id: 5,
      QRcode: "qrcodeur", 
      Title: "Quick king", 
      Description: "good food", 
      Remuneration: 5, 
      Height: 24, 
      Width: 24, 
      RemainingUses: 5
    },
    {
      Id: 6,
      QRcode: "qrcodeur", 
      Title: "Mac quick", 
      Description: "good food", 
      Remuneration: 25, 
      Height: 32, 
      Width: 32, 
      RemainingUses: 4
    }
  ];

  professionelList: Professionals[] = [
    {
      Id: 1,
      Email: "burger.king@gmail.com",
      Name: "Burger king",
      Password: "burger123",
      PhoneNumber: "01456264589",
      Address: "Burger street 45",
      VATNumber: "154856484",
      City: "Paris",
      Zipcode: 75000,
      StickerList: 
        [
          this.stickersList[0],
          this.stickersList[1]
        ]
    },
    {
      Id: 2,
      Email: "mac.donalds@gmail.com",
      Name: "Mac donalds",
      Password: "burger123",
      PhoneNumber: "01456264589",
      Address: "Clown street 45",
      VATNumber: "541654156",
      City: "Paris",
      Zipcode: 75000,
      StickerList: 
        [
          this.stickersList[2],
          this.stickersList[3]
        ]
    },
    {
      Id: 3,
      Email: "Quick.burger@gmail.com",
      Name: "Quick",
      Password: "burger123",
      PhoneNumber: "01456264589",
      Address: "Quickos street 45",
      VATNumber: "541654156",
      City: "Bruxelles",
      Zipcode: 75000,
      StickerList: 
        [
          this.stickersList[4],
          this.stickersList[5]
        ]
    },
  ];

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
  ];

  constructor() { }

  getAllProfessionals() : Array<Professionals>{
    return this.professionelList;
  }

  getProfessionalById(Id) : Professionals{
    return this.professionelList[Id-1];
  }

  getAllStickers() : Array<Sticker>{
    return this.stickersList;
  }

  getAllParticulars() : Array<Particulars>{
    return this.particularList;
  }

  getParticularById(Id) : Particulars{
    return this.particularList[Id-1];
  }
}