package jeu.Coeur;

import jeu.Enum.E_Orientation;
import jeu.Modeles.Case;

/**
 * Classe Elemen Thermique utilise pour la fonction traque
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 */
public class ElementThermique {

	private ElementThermique parent;
	private E_Orientation orientation;
	private Case case_t;
	private int taille;
	
	public ElementThermique(E_Orientation orientation, Case case_t, int taille) {
		this.orientation = orientation;
		this.case_t = case_t;
		this.taille = taille;
		parent = null;
	}
	
	public ElementThermique(ElementThermique parent, E_Orientation orientation, Case case_t, int taille) {
		this.orientation = orientation;
		this.case_t = case_t;
		this.taille = taille;
		this.parent = parent;
	}
	
	
	public ElementThermique getParent(){
		return parent;
	}
	
	public void setParent(ElementThermique parent){
		this.parent = parent; 
	}
	
	public E_Orientation getOrientation(){
		return orientation;
	} 
	
	public Case getCase_t(){
		return case_t;
	}
	
	public int getTaille(){
		return taille;
	}		
}
