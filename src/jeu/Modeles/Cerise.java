package jeu.Modeles;

import jeu.Coeur.*;
import jeu.Enum.E_Contenu;

public class Cerise extends Nourriture
{
	
	public Cerise(Controleur ctrl)
	{
		super(ctrl,10,"res/"+"cerise.png");
		setType(E_Contenu.CERISE);
	}
}
