package jeu.Enum;

import jeu.Coeur.*;
import jeu.Modeles.Personnage;


/**
 * Enum Orientation : represent l'orientation de nos personnages
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 */
public enum E_Orientation 
{
	HAUT,
	BAS,
	GAUCHE,
	DROITE;

	public static E_Orientation IntToEnum(int i)
	{
		E_Orientation e;
		i=Calculs.mod(i,4);
		if(i==0)
		{
			e=HAUT;
		}
		else if(i==1)
		{
			e=BAS;
		}
		else if(i==2)
		{
			e=GAUCHE;
		}
		else //if(i==3)
		{
			e=DROITE;
		}
		
		
		return e;
	}
	
	public static int EnumToInt(E_Orientation e)
	{
		int i = 0;
		switch(e)
		{
			case HAUT : i = 0;break;
			case BAS : i = 1;break;
			case GAUCHE : i = 2;break;
			case DROITE : i = 3;break;	
		}
		return i;
	}
	
	public static E_Orientation getOrientationAleatoire()
	{
		int e = Calculs.random(0, 3);
		return (IntToEnum(e));
	}
	
	/**
	 * Donne une nouvelle orientation en ce basant sur l'orientation d'origine et en lui faisant subir une rotation de n vers la droite
	 * @param ancien : l'ancienne orientation
	 * @param direction : le n rotation
	 * @return la nouvelle orinetation E_Orientation
	 */
	public static E_Orientation getNvlleOrientation(E_Orientation ancien, int direction)
	{
		if (direction>3 || direction<0)
		{
			direction=0;
		}
		E_Orientation nvllO=ancien;
		for(int i=0; i<direction;i++)
		{
			switch(nvllO)
			{
			case HAUT : nvllO=DROITE;break;
			case BAS : nvllO=GAUCHE;break;
			case GAUCHE : nvllO=HAUT;break;
			case DROITE : nvllO=BAS;break;
			
			}
		}
		return nvllO;
	}
}
