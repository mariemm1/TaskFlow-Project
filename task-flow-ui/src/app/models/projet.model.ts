import { Equipe } from './equipe.model';
import { Tache } from './tache.model';
import { Statut } from './Enum/statut.enum';

export interface Projet {
  id?: number;
  nom: string;
  description: string;
  dateDebut: Date;
  dateFin: Date;
  statut?: Statut;
  equipe?: Equipe;
  taches?: Tache[];
}
