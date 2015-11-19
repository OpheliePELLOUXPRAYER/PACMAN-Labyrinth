package jeu.Modeles;
import jeu.Coeur.*;
import jeu.Enum.E_Contenu;

public class Pacgum extends Nourriture
{
	public Pacgum(Controleur ctrl)
	{
		super(ctrl, 1, "res/"+"pacgum.png");
		setType(E_Contenu.PACGUM);
	}
	public void estMange(Pacman pac)
	{
		pac.IncrementerNbPacgum();
		getControleur().decrementerNbPacgum();
		pac.augmenterCalculScore(getValeur());
		getCase().supprimerContenu(this);
	}

}
