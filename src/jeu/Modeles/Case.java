package jeu.Modeles;


import java.util.ArrayList;

import jeu.Coeur.*;
import jeu.Enum.E_Contenu;

/**
 * Classe Case
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 */
public class Case 
{
	private boolean segments[]; // segment[0]==true veut dire qu'il y a un mur en haut de cette case
	// (0=Haut, 1=bas , 2=gauche , 3=droite)
	private int hauteur;
	private int largeur;
	private int modeleCase;
	private ArrayList<Contenu> contenus_case;
	
//----------------------------------------------------------------------------------------
	/**
	 * Constructeur de case : met le contenu a null (=libre) et choisi le modele de la case aleatoirement
	 */
	public Case(int h, int l)
	{
		segments = new boolean[4];
		hauteur=h;
		largeur=l;
		contenus_case = new ArrayList<Contenu>();
		setContenuDuDessus(new Libre(this));
		remplirSegmentFromModele(Calculs.random(0, 14));
	}
//----------------------------------------------------------------------------------------
	/**
	 * Constructeur de case : met le contenu a null (=libre) et le modele choisi
	 * @param modele : modele de la case demande
	 */
	public Case(int h, int l,int modele)
	{
		segments = new boolean[4];
		hauteur=h;
		largeur=l;
		contenus_case = new ArrayList<Contenu>();
		setContenuDuDessus(new Libre(this));
		remplirSegmentFromModele(modele);
		
	}
//----------------------------------------------------------------------------------------
	/**
	 * Permet de remplir les segments de la case selon le modele choisi
	 * @param modele : modele de la case choisi
	 */
	public void remplirSegmentFromModele(int modele)
	{
		modele=Calculs.mod(modele,15);
		modeleCase = modele;
		boolean l = false;
		boolean m = true;
		switch (modele) 
		{
			//					h,b,g,d
	        case 0: setSegments(l,l,l,m); break;
	        case 1: setSegments(l,l,m,l); break;
	        case 2: setSegments(l,l,m,m); break;
	        case 3: setSegments(l,m,l,l); break;
	        case 4: setSegments(l,m,l,m); break;
	        case 5: setSegments(l,m,m,l); break;
	        case 6: setSegments(l,m,m,m); break;
	        case 7: setSegments(m,l,l,l); break;
	        case 8: setSegments(m,l,l,m); break;
	        case 9: setSegments(m,l,m,l); break;
	        case 10: setSegments(m,l,m,m); break;
	        case 11: setSegments(m,m,l,l); break;
	        case 12: setSegments(m,m,l,m); break;
	        case 13: setSegments(m,m,m,l); break;
	        default: setSegments(l,l,l,l); break;
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * Setteur des segments de la case
	 * @param h : true = Mur en haut ; false = libre en haut
	 * @param b : true = Mur en bas ; false = libre en bas
	 * @param g : true = Mur a gauche ; false = libre a gauche
	 * @param d : true = Mur a droite ; false = libre a droite
	 */
	public void setSegments(boolean h,boolean b, boolean g, boolean d)
	{
		segments[0]=h;
		segments[1]=b;
		segments[2]=g;
		segments[3]=d;
	}
//----------------------------------------------------------------------------------------
	/**
	 * Setteur du contenu de la case
	 * @param c : nouveau contenu de la case
	 */
	public void setContenuDuDessus(Contenu c)
	{
		contenus_case.add(0,c);
		c.setCase(this);
		
	}
//----------------------------------------------------------------------------------------
	public void supprimerContenu(Contenu c)
	{
		contenus_case.remove(c);
	}
//----------------------------------------------------------------------------------------
	public boolean estOccupeParUnPerso()
	{
		boolean occupe=false;
		/*int i=0;
		while(!occupe && i<contenus_case.size())
		{
			occupe=(occupe || contenus_case.get(i).getType().equals(E_Contenu.FANTOME) || contenus_case.get(i).getType().equals(E_Contenu.PACMAN) );
		}
		*/
		occupe = (getContenu().getType().equals(E_Contenu.FANTOME) || getContenu().getType().equals(E_Contenu.PACMAN));
		return occupe;
	}
//----------------------------------------------------------------------------------------
	/**
	 * getteur du contenu de la case
	 * @return : le contenu de la case
	 */
	public Contenu getContenu()
	{
		Contenu c;
		if(contenus_case.isEmpty())
		{
			c=null;
			System.out.println("ceci n'est pas sense se produire");
		}
		else
		{
			c=contenus_case.get(0);
		}
		return c;
		}
//----------------------------------------------------------------------------------------
	/**
	 * getteur du contenu de la case
	 * @return : le contenu de la case
	 */
	public ArrayList<Contenu> getContenus()
	{
		return contenus_case;
	}
//----------------------------------------------------------------------------------------
	/**
	 * getteur du contenu de la case
	 * @return : le contenu de la case
	 */
	public ArrayList<String> getImagesDesContenusAAfficher()
	{
		ArrayList<String> l =new ArrayList<String>();
		Contenu cont;
		for(int i =contenus_case.size()-1;i>=0;i--)
		{
			cont =contenus_case.get(i);
			if(!cont.getType().equals(E_Contenu.FANTOME) && !cont.getType().equals(E_Contenu.PACMAN) && !cont.getType().equals(E_Contenu.LIBRE))
			{
				l.add(cont.getImage());
			}
		}
		return l;
	}
	
//----------------------------------------------------------------------------------------
	/**
	 * Getteur du segment haut 
	 * @return : true = Mur en haut ; false = libre en haut
	 */
	public boolean getHaut()
	{
		return(segments[0]);
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur du segment bas 
	 * @return : true = Mur en bas ; false = libre en bas
	 */
	public boolean getBas()
	{
		return(segments[1]);
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur du segment gauche
	 * @return : true = Mur a gauche ; false = libre a gauche
	 */
	public boolean getGauche()
	{
		return(segments[2]);
	}
//----------------------------------------------------------------------------------------
	/**
	 * getteur de la hauteur
	 * @return : hauteur de la case
	 */
	public int getHauteurCase()
	{
		return(hauteur);
	}
//----------------------------------------------------------------------------------------
	/**
	 * getteur de la largeur
	 * @return : largeur de la case
	 */
	public int getLargeurCase()
	{
		return(largeur);
	}
//----------------------------------------------------------------------------------------
	/**
	 * getteur du segment droit
	 * @return : true = Mur a droite ; false = libre a droite
	 */
	public boolean getDroite()
	{
		return(segments[3]);
	}
//----------------------------------------------------------------------------------------
	public boolean getSegment(int i)
	{
		return(segments[i]);
	}	
//----------------------------------------------------------------------------------------
	public void tournerCaseADroite()
	{
		boolean haut=segments[0];
		segments[0]=segments[2];
		segments[2]=segments[1];
		segments[1]=segments[3];
		segments[3]=haut;
		for(int i=0; i<contenus_case.size()-1;i++)
		{
			if(contenus_case.get(i).getType().equals(E_Contenu.PACMAN) || contenus_case.get(i).getType().equals(E_Contenu.FANTOME))
			{
				Personnage p =(Personnage) contenus_case.get(i);
				p.changerOrientationPerso(1);
			}
		}
	}
//----------------------------------------------------------------------------------------
	public void tournerCaseAGauche()
	{
		boolean haut=segments[0];
		segments[0]=segments[3];
		segments[3]=segments[1];
		segments[1]=segments[2];
		segments[2]=haut;
		for(int i=0; i<contenus_case.size()-1;i++)
		{
			if(contenus_case.get(i).getType().equals(E_Contenu.PACMAN) || contenus_case.get(i).getType().equals(E_Contenu.FANTOME))
			{
				Personnage p =(Personnage) contenus_case.get(i);
				p.changerOrientationPerso(3);
			}
		}
	}
//----------------------------------------------------------------------------------------
	public void prendLaValeurDeLaCase(Case val_case_a_prendre)
	{
		for(int i=0; i<4;i++)
		{
			segments[i]=val_case_a_prendre.getSegment(i);
		}
		
		contenus_case=val_case_a_prendre.getContenus();
		modeleCase = val_case_a_prendre.getModeleCase();
		//contenus_case.clear();
		//contenus_case.addAll(c);
		System.out.println("la nouvelle case a "+contenus_case.size()+" contenus");
		for(Contenu c : contenus_case)
		{
			if(c.getType().equals(E_Contenu.FANTOME) || c.getType().equals(E_Contenu.PACMAN))
			{
				Personnage p= (Personnage)c;
				p.setTeleportation(true);
			}
			c.setCase(this);
		}
	}
//----------------------------------------------------------------------------------------
	public void sortDeLaMap()
	{
		for(int i=0; i<contenus_case.size()-1;i++)
		{
			contenus_case.get(i).sortDeLaMap();
		}
		contenus_case.clear();
	}
	
//----------------------------------------------------------------------------------------
	/**
	 * Fonction appele lorsqu'un perso rentre dans cette case, elle changera la place du perso ou appelera les fonction qui le feron
	 * @param p :
	 * @param ancienne_case
	 */
	/*public void entreDansLaCase(Personnage p)
	{
		getContenu().collision(p, this);
	}
	*/
//----------------------------------------------------------------------------------------
	public void afficherSegmentCase()
	{
		System.out.print("haut ="+segments[0]);
		System.out.print(", bas ="+segments[1]);
		System.out.print(", gauche ="+segments[2]);
		System.out.println(", droite ="+segments[3]);
	}
//----------------------------------------------------------------------------------------

	public void affichageCase(){
		System.out.print("( ");
		for(Contenu c : contenus_case){
			System.out.print(E_Contenu.EnumToChar(c.getType()));
			System.out.print(", ");
		}
		System.out.print(")");
	}



//----------------------------------------------------------------------------------------
	public int getModeleCase()
	{
		return modeleCase;
	}


}
