export interface Membre {
  id?: number;
  nom: string;
  prenom: string;
  email: string;
  motDePasse: string;
  competences: string[];
  postMembre?: string;
  role?: string;
}
