package jeu.Modeles;

import jeu.Enum.*;
import jeu.Coeur.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;


public abstract class Personnage implements Contenu
{

	private String image_perso;
	private int etat_perso;
	private String tableauEtat_perso[];
	private ArrayList<Transition<String,Integer>> tableauTransition_perso[];
	private E_Orientation orientation_perso;
	private boolean estEjecte;
	private Controleur ctrl;
	private Case c;
	private int touches_perso[];
	private int derniereTouchePressee_perso;
	private boolean teleportation;
	
	
//----------------------------------------------------------------------------------------
	/**
	 * Constructeur de la classe personnage
	 * @param h : hauteur du personnage (position initiale)
	 * @param l : largeur du personnage (position initiale)
	 * @param o : orientaion initiale du personnage
	 * @param courant2 : balise de l'automate
	 */
	public Personnage(Controleur cont, Case cas, E_Orientation o, Element courant2)
	{
		etat_perso = 0;
		ctrl=cont;
		cas.setContenuDuDessus(this);
		orientation_perso = o;
		estEjecte = false;
		int taille = Integer.parseInt(courant2.getAttributeValue("nb_etat"));
		tableauEtat_perso= new String[taille];
		tableauTransition_perso =new ArrayList[taille];
		derniereTouchePressee_perso = -1;
		teleportation=false;
		
		image_perso=(System.getProperty("user.dir")+"/res/"+courant2.getAttributeValue("image"));  //RECUPERATION  D'UNE IMAGE DANS LE PARSER
		
		// Creation d une liste d Element dont la racine=<automate classe="" nb_etat="" image="">
		List liste = courant2.getChildren();
		//Creation d'un Iterator sur notre liste=<etat id="" nb_transition="" >||<touches>
		Iterator i = liste.iterator();
		while(i.hasNext())
		{
		  Element courant = (Element)i.next();
		  if (courant.getName().equals("touches")) // BALISE <touches>
		  {
			  remplir_tableau_touches_perso(courant);
		  }
		  else // BALISE <etat id="" nb_transition="" >
		  {
			  remplir_tableau(Integer.parseInt(courant.getAttributeValue("id")),courant);
		  }
		}
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
	public boolean getTeleportation()
	{
		return teleportation;
	}
	
	public void setTeleportation(boolean t)
	{
		teleportation=t;
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
//----------------------------------------------------------------------------------------
	/**
	 * Setteur de la hauteur du perso
	 * @param h : la nouvelle hauteur du perso
	 */
/*	public void setHauteur(int h)
	{
		hauteur_perso=h;
	}*/
//----------------------------------------------------------------------------------------
	/**
	 * Setteur de la largeur du perso
	 * @param l : la nouvelle largeur du perso
	 */
	/*public void setLargeur(int l)
	{
		largeur_perso=l;
	}*/
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de l'image du perso 
	 * @return : chemin (String) de l'image du perso
	 */
	public String getImage() 
	{
		return image_perso;
	}
	

//----------------------------------------------------------------------------------------
	/**
	 * Setteur de l'image du perso 
	 * @return : chemin (String) de l'image du perso
	 */
	public void setImage(String image) 
	{
		image_perso = image;
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de la case du perso
	 * @return : la case sur laquelle est le perso
	 */
	public Case getCase() 
	{
		return c;
	}
	public int getVitesse()
	{
		return(1);
	}
//----------------------------------------------------------------------------------------
	/**
	 * Setteur de la case
	 * @param cas : nouvelle case associe
	 */
	public void setCase(Case cas) 
	{
		c=cas;
	}	
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de l'etat du perso
	 * @return : l'etat du perso
	 */
	public int getEtat() 
	{
		return etat_perso;
	}	
//----------------------------------------------------------------------------------------
	/**
	 * Setteur de l'etat du perso
	 * @param e : le nouvel etat du perso
	 */
	public void setEtat(int e) 
	{
		etat_perso= e;
	}

	
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de l'orientation du perso
	 * @return : l'orientation du perso
	 */
	public E_Orientation getOrientation() 
	{
		return orientation_perso;
	}
//----------------------------------------------------------------------------------------
	/**
	 * Setteur de l'orientation du perso
	 * @param o : nouvelle oreintation du perso
	 */
	public void setOrientation(E_Orientation o) 
	{
		orientation_perso=o;
	}
//----------------------------------------------------------------------------------------
	/**
	 * getteur du tableau de transition du perso (tableau de listes de transitions (couple condition/Etat)
	 * @return le tableau de transition du perso
	 */
	public  ArrayList<Transition<String, Integer>>[] getTableauTransition()
	{  
	   return tableauTransition_perso;
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur du tableau d'etat du perso
	 * @return le tableau d'etat du perso
	 */
	public String[] getTableauEtat()
	{
		return tableauEtat_perso;
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur du tableau d'etat du perso
	 * @return le tableau d'etat du perso
	 */
	public int[] getTouches_perso()
	{
		return touches_perso;
	}
//----------------------------------------------------------------------------------------	
	public void setDerniereTouchePressee_perso(int tp)
	{
		derniereTouchePressee_perso=tp;
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de la derniere touche pressee
	 * @return la derniere touche pressee
	 */
	public int getDerniereTouchePressee_perso()
	{
		return derniereTouchePressee_perso;
	}
//----------------------------------------------------------------------------------------
	public boolean getApeure()
	{
		return false;
	}
	
	public boolean estEjecte()
	{
		return estEjecte;
	}
	
	public void setEstEjecte(boolean b)
	{
		estEjecte = b;
	}
	
	public Controleur getCtrl()
	{
		return ctrl;
	}

//----------------------------------------------------------------------------------------	
	
//----------------------------------------------------------------------------------------		
	public void mourir() 
	{
		setTeleportation(true);
		ctrl.choisirCaseSansPersoAleatoire().getContenu().collision(this, getCase());
	}
//----------------------------------------------------------------------------------------	
	public void sortDeLaMap() 
	{
		estEjecte = true;
		System.out.println("un perso est sortie de la map");
		mourir();
		
	}
//----------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------
	private void remplir_tableau(int indice, Element racine)
	 {
		// Creation d une liste d Element
		List liste = racine.getChildren();
		ArrayList<Transition<String,Integer>> liste_transition = new ArrayList();
		String action = null;
		String action_etat = null;
		int etat_sortie = 0;
		
		//Creation d'un Iterator sur notre liste=[action;transition]
		Iterator i = liste.iterator();
		while(i.hasNext())
		{
			Element courant = (Element)i.next();
			if (courant.getName().equals("action"))
			{
				action_etat = courant.getValue();
			}
			else // Transition
			{
				//Creation d'un Iterator sur notre liste=[action;etat_sortie]
				Iterator j = courant.getChildren().iterator();
				while(j.hasNext())
				{
					Element intermediaire = (Element)j.next();
					if (intermediaire.getName().equals("action"))
					{
						action = intermediaire.getValue();
					}
					else
					{
						etat_sortie = Integer.parseInt(intermediaire.getValue());
					}					
				}
					Transition<String,Integer> t = new Transition<String,Integer>(action,etat_sortie);
					liste_transition.add(t);
			}
			
		}
		tableauEtat_perso[indice]= action_etat;
		tableauTransition_perso[indice]=liste_transition;

	 }
	
	
	private void remplir_tableau_touches_perso(Element racine)
	 {
		// Creation d une liste d Element dont la racine=<touches>
		List liste = racine.getChildren();
		touches_perso = new int[6];
		String touche = null;
		int compteur = 0;
		
		//Creation d'un Iterator sur notre liste=<haut>||<bas>||<gauche>||<droite>||<rotation_gauche>||<rotation_droite>
		Iterator i = liste.iterator();
		while((compteur<6) && (i.hasNext()) )
		{
			Element courant = (Element)i.next();
			touches_perso[compteur]=integerFromTouche(courant);
			compteur++;
		}
	 }
	private int integerFromTouche (Element e)
	{
		int resultat = -1; 
		switch(e.getValue())
		{
		// LIBRAIRIE SLICK
		case "haut": // KEY_UP 200
			resultat = 200;
			break;
		case "bas": // KEY_DOWN 208
			resultat = 208;
			break;
		case "gauche": // KEY_LEFT 203
			resultat = 203;
			break;
		case "droite": // KEY_RIGHT 205
			resultat = 205;
			break;
		case ":": // Rotation Gauche
			resultat = 53;
			break;
		case "!": // Rotation Droite
			resultat = 41;
			break;	
		case "z": 
			resultat = 44;
			break;
		case "s": 
			resultat = 31;
			break;
		case "q": 
			resultat = 16;
			break;
		case "d": 
			resultat = 32;
			break;
		case "a": // Rotation Gauche
			resultat = 30;
			break;
		case "e": // Rotation Droite
			resultat = 18;
			break;	
		case "null": // KEY_RIGHT -1
			resultat = -1;
			break;
		default:
			resultat = (int) (e.getValue()).charAt(0) ;			
		}
		return resultat;
	}
//----------------------------------------------------------------------------------------
	public void changerOrientationPerso( int direction)
	{	
		setOrientation(E_Orientation.getNvlleOrientation(orientation_perso,direction));
	}
//----------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------
	/**
	 * Affichage du tableau de transition du perso
	 */
	public void affichageTableauTransition()
	{	
		int taille = getTableauTransition().length;
		int indice=0;
		
		System.out.println("Transition:");
		while(indice<taille)
		{
			ArrayList<Transition<String, Integer>> courant = getTableauTransition()[indice];
			Iterator i = courant.iterator();
			while (i.hasNext())
			{
				Transition<String, Integer> t = (Transition<String, Integer>) i.next();
				System.out.println(indice+"->"+t.getAction()+"->"+t.getEtatSortie());
			}
			indice++;
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * Affichage du tableau d'etat du perso
	 */
	public void affichageTableauEtat()
	{
		int taille = getTableauEtat().length;
		int indice=0;
		
		System.out.println("Etat:");
		while(indice<taille)
		{
			System.out.println(indice+"->"+getTableauEtat()[indice]);
			indice++;
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * Affichage du tableau de transition du perso
	 */
	public void affichageTouchesPerso()
	{	
		int taille = getTouches_perso().length;
		int indice=0;
		
		System.out.println("Touches: \n <haut> <bas> <gauche> <droite> <rotation_gauche> <rotation_droite>");
		while(indice<taille)
		{
			System.out.print(getTouches_perso()[indice] + "      ");
			indice++;
		}
	}
//----------------------------------------------------------------------------------------	
	
}
