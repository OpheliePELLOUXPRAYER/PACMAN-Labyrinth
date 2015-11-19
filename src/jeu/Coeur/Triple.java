package jeu.Coeur;

/**
 * Triple utilise pour la fonction voitXsurNcases
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 */
public class Triple<B, H, L >{
	private B bool;
	private H hauteur;
	private L largeur; 
	
	public Triple (B b, H h, L l)
	{
		this.bool=b;
		this.hauteur=h;
		this.largeur=l;
	}
	
	public B getBool ()
	{
		return this.bool;
	}
	
	public H getHauteur ()
	{
		return this.hauteur;
	}
	
	public L getLargeur ()
	{
		return this.largeur;
	}
}