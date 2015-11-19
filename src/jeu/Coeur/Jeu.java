package jeu.Coeur;

/**
 * Main du jeu
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 */
public class Jeu 
{
	public static int versionDemo =3;
	
	public static void main(String[] args) 
	{
		if(versionDemo==1)//Demo 1 : Demonstration des deplacements + manger pacGum + score
		{
			Controleur c =new Controleur("Pacman.xml", 2 , 3);
		}
		else if(versionDemo==2)	//Demo 2 : Demonstration des primitive du fantôme rouge
		{
			Controleur c =new Controleur("AutomateRouge.xml", 2 , 2);
		}
		else //Demo 3 : Présentation de la grande map aléatoire avec de tous les fantômes et les deux pacmans sur la carte
		{
			Controleur c =new Controleur("Automate.xml", 6 , 12);

		}
		
	}

}
