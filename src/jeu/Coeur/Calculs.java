package jeu.Coeur;

import java.util.Random;

/**
 * Classe rassemblant les fonctions de calcule
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 */
public class Calculs 
{
	/**
	 * Modulo positif
	 * @param a
	 * @param b
	 * @return a % b
	 */
	public static int mod (int a, int b) 
	{
		int res = a % b;
        if (res<0 && b>0) 
        {
            res += b;
        }
        return res;
    }
	
	/**
	 * Random 
	 * @param min
	 * @param max
	 * @return une valeur random compris entre min et max inclu
	 */
	public static int random(int min, int max)
	{
		return(min + (int)(Math.random() * ((max - min) + 1)));
	}
	
	/**
	 * Permet de choisir entre gauche ou droite (effectue un pile ou face)
	 * @return 
	 */
	public static int gaucheOuDroite()
	{
		Random random = new Random();
		return((random.nextInt(2)*2)+1);
	}
	
	/**
	 * 
	 * @return zero ou un 
	 */
	public static int zeroOuUn()
	{
		Random random = new Random();
		return(random.nextInt(2));
	}
}
