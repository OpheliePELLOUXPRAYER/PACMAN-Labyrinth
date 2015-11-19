package jeu.Modeles;
import jeu.Coeur.*;
import jeu.Enum.E_Contenu;

public class Pastille extends Nourriture 
{
	public Pastille(Controleur ctrl)
	{
		super(ctrl, 0, "res/"+"pastille.png");
		setType(E_Contenu.PASTILLE);
		
	}
	public void estMange(Pacman p)
	{
		System.out.println("estMange de PASTILLE");
		getControleur().unePastilleAEteMange();
		getCase().supprimerContenu(this);
	}


}
