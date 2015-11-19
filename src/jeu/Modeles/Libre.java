package jeu.Modeles;

import jeu.Enum.E_Contenu;

public class Libre implements Contenu
{

	private Case c;
	private String image;
	
	public Libre(Case cas)
	{
		c=cas;
		image = "";
	}
	
	public int getHauteur() 
	{
		return c.getHauteurCase();
	}

	public int getLargeur() 
	{
		return c.getLargeurCase();
	}

	public E_Contenu getType() 
	{
		return E_Contenu.LIBRE;
	}

	public String getImage() 
	{
		return image;
	}
	public Case getCase()
	{
		return(c);
	}
	public void setCase(Case cas)
	{
		c=cas;
	}

	public void collision(Personnage p, Case c1)
	{
		getCase().setContenuDuDessus(p);
		//p.setCase(getCase());
		c1.supprimerContenu(p);
		//System.out.println("rentre dans collision libre");
	}

	@Override
	public void sortDeLaMap() 
	{
		// TODO Auto-generated method stub
		//System.out.println("une case libre est morte ce soir");
		
	}
}
