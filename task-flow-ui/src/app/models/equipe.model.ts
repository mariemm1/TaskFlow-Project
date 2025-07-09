import { ChefEquipe } from './chef.model';
import { Membre } from './membre.model';

export interface Equipe {
  id?: number;
  nom: string;
  description: string;
  chefDequipe?: ChefEquipe;
  membres?: Membre[];
}
