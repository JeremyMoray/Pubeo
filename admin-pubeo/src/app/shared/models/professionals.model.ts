import { Sticker } from './sticker.model';
import { Localite } from './localite.model';

export class Professionals {
    id: string;
    nomEntreprise: string;
    adresse: string;
    numeroTel: string;
    mail: string;
    numeroTVA: string;
    localite: Localite;
    stickers: Sticker[];
}
