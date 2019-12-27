import { Sticker } from './sticker.model';

export class Particulars {
    Id: number;
    Email: string;
    Username: string;
    Password: string;
    Firstname: string;
    Lastname: string;
    PhoneNumber: string;
    Birthdate: Date;
    Address: string;
    City: string;
    Zipcode: number;
    StickerList: Sticker[];
}