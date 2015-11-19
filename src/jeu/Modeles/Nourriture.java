package jeu.Modeles;

import jeu.Enum.E_Contenu;
import jeu.Coeur.*;

public abstract class Nourriture implements Contenu
{
	private int valeur;
	private String image;
	private Case c;
	private E_Contenu type;
	private Controleur ctrl;
	
	public Nourriture(Controleur c, int val, String im)
	{
		ctrl=c;
		valeur=val;
		image=im;
	}
	
	public void setValeur(int val)
	{
		valeur = val;
	}
	
	public int getValeur()
	{
		return valeur;
	}
	public Controleur getControleur()
	{
		return ctrl;
	}
	public void setImage(String im)
	{
		image = im;
	}
	public void setCase(Case cas) 
	{
		c=cas;
	}
	public Case getCase() 
	{
		
		return c;
	}
	
	public String getImage()
	{
		return image;
	}
	
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de la hauteur du perso
	 * @return : la hauteur du perso
	 */
	public int getHauteur()
	{
		return c.getHauteurCase();
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de la largeur du perso
	 * @return : la largeur du perso
	 */
	public int getLargeur()
	{
		return c.getLargeurCase();
	}

	public E_Contenu getType() 
	{
		return type;
	}
	public void setType(E_Contenu t)
	{
		type=t;
	}
	public void estMange(Pacman pac)
	{
		//System.out.println("estMange de Nourriture");
		pac.augmenterCalculScore(getValeur());
		getCase().supprimerContenu(this);
	}
	public void collision(Personnage p, Case c1) 
	{
		//System.out.println("Collision Nourriture");
		if(p.getType().equals(E_Contenu.PACMAN))
		{
			Pacman pac =(Pacman) p;
			estMange(pac);
		}
		getCase().setContenuDuDessus(p);
		c1.supprimerContenu(p);
		
	}
	
	public void sortDeLaMap() 
	{
		ctrl.ajouterNourritureARemettre(this);
	}

	public void affichageContenu(){
		System.out.print("N");
	}
}
