import { Sticker } from './sticker.model';
import { Localite } from './localite.model';

export class Particulars {
    id: string;
    nom: string;
    prenom: string;
    adresse: string;
    pseudo: string;
    dateNaissance: Date;
    numeroTel: string;
    mail: string;
    localite: Localite;
}