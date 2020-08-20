import { Professionals } from './professionals.model';

export class Sticker {
    id: string;
    titre: string;
    description: string;
    hauteur: number;
    largeur: number;
    nbUtilisationsRestantes: number;
    professionnel: Professionals;
}
