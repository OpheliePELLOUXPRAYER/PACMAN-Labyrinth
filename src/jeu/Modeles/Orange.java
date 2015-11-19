package jeu.Modeles;
import jeu.Coeur.*;
import jeu.Enum.E_Contenu;

public class Orange extends Nourriture
{
	public Orange(Controleur ctrl)
	{
		super(ctrl, 5,"res/"+"orange.png");
		setType(E_Contenu.ORANGE);
	}
}
