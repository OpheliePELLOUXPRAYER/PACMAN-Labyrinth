package jeu.Enum;

/**
 * Enum Contenu : represent le contenu des cases de notre map 
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 */
public enum E_Contenu 
{
	PACMAN,
	FANTOME,
	PACGUM,
	PASTILLE,
	LIBRE,
	CERISE,
	ORANGE,
	FRAISE;

	public static char EnumToChar(E_Contenu co)
	{
		char l;
		switch(co)
		{
		case LIBRE :
			l='L';
			break;
		case PACMAN :
			l='P';
			break;
		case FANTOME :
			l='F';
			break;
		case PACGUM :
			l='G';
			break;
		case CERISE :
			l='C';
			break;
		case ORANGE :
			l = 'O';
		case FRAISE : 
			l = 'R';
		case PASTILLE :
			l='A';
			break;
		default :
			l='N';
		}
		return l;
	}
}


