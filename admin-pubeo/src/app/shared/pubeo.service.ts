import { Injectable } from '@angular/core';
import { Professionals } from './professionals.model';

@Injectable({
  providedIn: 'root'
})
export class PubeoService {

  professionelForm: Professionals

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
          }
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
          {
            Id: 1,
            QRcode: "qrcodeur", 
            Title: "Mac donalds XXL", 
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
          {
            Id: 1,
            QRcode: "qrcodeur", 
            Title: "Quick king", 
            Description: "good food", 
            Remuneration: 5, 
            Height: 24, 
            Width: 24, 
            RemainingUses: 5
          },
          {
            Id: 2,
            QRcode: "qrcodeur", 
            Title: "Mac quick", 
            Description: "good food", 
            Remuneration: 25, 
            Height: 32, 
            Width: 32, 
            RemainingUses: 4
          }
        ]
    },
  ]
  constructor() { }

  getAllProfessionals() : Array<Professionals>{
    return this.professionelList;
  }

  getProfessionalById(Id) : Professionals{
    return this.professionelList[Id-1];
  }
}