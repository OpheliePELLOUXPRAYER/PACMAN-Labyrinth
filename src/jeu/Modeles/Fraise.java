package jeu.Modeles;

import jeu.Coeur.*;
import jeu.Enum.E_Contenu;

public class Fraise extends Nourriture
{	
	public Fraise(Controleur ctrl)
	{
		super(ctrl, 15, "res/"+"fraise.png");
		setType(E_Contenu.FRAISE);
	}
}
