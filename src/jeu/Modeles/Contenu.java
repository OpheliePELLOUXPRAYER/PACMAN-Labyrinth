package jeu.Modeles;

import jeu.Enum.*;

public interface Contenu 
{
	
	
	
	/**
	 * Getteur de la hauteur du contenu
	 * @return : la hauteur du contenu
	 */
	public int getHauteur();
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de la largeur du contenu
	 * @return : la largeur du contenu
	 */
	public int getLargeur();
//----------------------------------------------------------------------------------------
	/**
	 * Setteur de la hauteur du contenu
	 * @param h : la nouvelle hauteur du contenu
	 */
//	public void setHauteur(int h);
//----------------------------------------------------------------------------------------
	/**
	 * Setteur de la largeur du contenu
	 * @param l : la nouvelle largeur du contenu
	 */
//	public void setLargeur(int l);
//----------------------------------------------------------------------------------------
	/**
	 * Permet de savoir ce qu'est le contenu (PacWoman, Fantome, Fruit, Pastille, PacGum)
	 * @return : le type enumerer du contenu (PacWoman, Fantome, PacGum, Pastille, Fruit)
	 */
	public E_Contenu getType();
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de l'image du contenu
	 * @return : chemin (String) de l'image du contenu
	 */
	public String getImage();
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de la case
	 * @return : la case ou se situe le contenu
	 */
	public Case getCase();
//----------------------------------------------------------------------------------------
	/**
	 * Setteur de la case
	 * @param cas : nouvelle case associe
	 */
	public void setCase(Case cas);
	
//----------------------------------------------------------------------------------------
	/**
	 * Action appele lorsqu'un perso entre dans la case du contenu
	 * @param p : perso qui rentre dans la case du contenu
	 * @param ancienne_case : case d'ou vient le perso
	 */
	public void collision(Personnage p, Case c1);
//----------------------------------------------------------------------------------------
	public void sortDeLaMap();
//----------------------------------------------------------------------------------------
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	/**
	 * Permet de savoir si le contenu est un PacWoman
	 * @return true si le contenu est un PacWoman, false si c'est autre chose
	 */
//	public boolean estUnPacWoman();
//----------------------------------------------------------------------------------------
		/**
		 * Permet de savoir si le contenu est un Fantome
		 * @return true si le contenu est un Fantome, false si c'est autre chose
		 */
//		public boolean estUnFantome();
//----------------------------------------------------------------------------------------
	/**
	 * Permet de savoir si le contenu est une Pastille
	 * @return true si le contenu est une Pastille, false si c'est autre chose
	 */
//	public boolean estUnePastille();
//----------------------------------------------------------------------------------------
	/**
	 * Permet de savoir si le contenu est une PacGum
	 * @return true si le contenu est une PacGum, false si c'est autre chose
	 */
//	public boolean estUnePacGum();
//----------------------------------------------------------------------------------------
	/**
	 * Permet de savoir si le contenu est un Fruit
	 * @return true si le contenu est un Fruit, false si c'est autre chose
	 */
//	public boolean estUnFruit();
//----------------------------------------------------------------------------------------
	
}











