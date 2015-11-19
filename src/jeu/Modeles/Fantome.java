package jeu.Modeles;

import jeu.Coeur.Controleur;
import jeu.Enum.E_Contenu;
import jeu.Enum.E_Orientation;

import org.jdom2.Element;

public class Fantome extends Personnage
{
	private boolean apeure;
	
	public Fantome(Controleur cont, Case cas, E_Orientation o, Element courant2)
	{
		super(cont,cas,o,courant2);
		apeure = false;
	}
	public E_Contenu getType()
	{
		return E_Contenu.FANTOME;
	}
	
	public boolean getApeure()
	{
		return apeure;
	}
	
	public void setApeure(boolean b)
	{
		apeure = b;
	}
	
	public void collision(Personnage p, Case c1) 
	{
		System.out.println("Rentre dans collision fantome");
		if(p.getType().equals(E_Contenu.FANTOME))
		{
			System.out.println("Tu es un fantome");
			getCase().setContenuDuDessus(p);
			c1.supprimerContenu(p);
		}
		else
		{
			System.out.println("Tu es un pacman et je te le confirme");
			Pacman pac = (Pacman) p;
			if(getApeure())
			{
				pac.augmenterCalculScore(50);
				mourir();
			}
			else
			{
				pac.blesser();
			}
		}
		c1.supprimerContenu(p);		
	}
}
