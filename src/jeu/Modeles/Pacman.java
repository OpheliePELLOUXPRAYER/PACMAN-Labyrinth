package jeu.Modeles;

import jeu.Coeur.Controleur;
import jeu.Enum.E_Contenu;
import jeu.Enum.E_Orientation;

import org.jdom2.Element;

public class Pacman extends Personnage
{
	private int score;
	private int calculScore;
	private int nbPacgum;
	private int nbVie;
	
	public Pacman(Controleur cont, Case cas, E_Orientation o, Element courant2)
	{
		super(cont,cas,o,courant2);
		score = 0;
		calculScore = 0;
		nbPacgum = 0;
		nbVie = 3;
	}
	public E_Contenu getType()
	{
		return E_Contenu.PACMAN;
	}
	public void decrementerNbVies() 
	{
		nbVie--;
		calculScore=calculScore-20;
		if(nbVie==0)
		{
			calculScore=0;
			nbVie = 3;
		}
	}
 	/** Getteur du score total du perso
	 * @return : le score total du perso
	 */
	public int getScore() 
	{
		return score;
	}	
//----------------------------------------------------------------------------------------
	/**
	 * Setteur du score total du perso
	 * @param e : le nouvel score total du perso
	 */
	public void setScore(int s) 
	{
		score = s;
	}
	
	public void augmenterScore(int a) 
	{
		if(a<0)
		{
			a=0;
		}
		score +=a;
	}
	public void diminuerScore(int d) 
	{
		score -= d;
	}
	public int getVitesse()
	{
		return(1);
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur du score du perso pour le niveau
	 * @return : le score du perso pour le niveau
	 */
	public int getCalculScore() 
	{
		return calculScore;
	}	
//----------------------------------------------------------------------------------------
	/**
	 * Setteur du score du perso pour le niveau 
	 * @param e : le nouvel score du perso pour le niveau
	 */
	public void setCalculScore(int s) 
	{
		calculScore = s;
	}
	public void augmenterCalculScore(int a) 
	{
		calculScore +=a;
	}
	public void diminuerCalculScore(int d) 
	{
		calculScore -= d;
	}
	
	
//----------------------------------------------------------------------------------------
	/**
	 * Getteur du compte de Pacgum du perso
	 * @return : le compte de Pacgum du perso
	 */
	public int getNbPacgum() 
	{
		return nbPacgum;
	}	
//----------------------------------------------------------------------------------------
	/**
	 * Setteur du compte de Pacgum du perso
	 * @param e : le compte de Pacgum du perso
	 */
	public void setNbPacgum(int c) 
	{
		nbPacgum = c;
	}
	public void IncrementerNbPacgum() 
	{
		nbPacgum++;
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur du compte de vies du perso
	 * @return : le compte de vies du perso
	 */
	public int getVies() 
	{
		return nbVie;
	}	
//----------------------------------------------------------------------------------------
	/**
	 * Setteur du compte de vies du perso
	 * @param e : le compte de vies du perso
	 */
	public void setNbVie(int v) 
	{
		nbVie = v;
	}
	public void blesser() 
	{
		decrementerNbVies();
		getCtrl().pacmanMort(this);
		mourir();
	}
	
	public void sortDeLaMap() 
	{
		super.sortDeLaMap();
		getCtrl().pacmanMort(this);
	}
	
	/**
	 * Action appele lorsqu'un perso entre dans la case de ce pacman
	 * @param p : perso qui rentre dans la case de ce pacman
	 * @param ancienne_case : case d'ou vient le perso entrant
	 */
	public void collision(Personnage p, Case c1)
	{
		//System.out.println("rentre dans collision pacman");
		//System.out.println(p.getType());
		if(p.getType().equals(E_Contenu.PACMAN))
		{
			getCase().setContenuDuDessus(p);
		}
		else
		{
			if(p.getApeure())
			{
				augmenterCalculScore(50);
				p.mourir();
			}
			else
			{
				getCase().setContenuDuDessus(p);
				blesser();
			}
		}
		c1.supprimerContenu(p);
	}
}
