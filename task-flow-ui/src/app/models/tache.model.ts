import { Statut } from './Enum/statut.enum';
import { Membre } from './membre.model';
import { Projet } from './projet.model';

export interface Tache {
  id?: number;
  nom: string;
  description: string;
  dateDebut: Date;
  dateFin: Date;
  statut?: Statut;
  skills: string;
  projet?: Projet;
  membre?: Membre;
}
