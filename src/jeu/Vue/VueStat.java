package jeu.Vue;

import jeu.Coeur.*;
import jeu.Enum.E_Contenu;
import jeu.Modeles.Pacman;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class VueStat extends BasicGameState {

	private final int id;
	private int idSuiv;
	private VueCentrale vue;
	private Controleur ctrl;
	private int nbPerso;
	private boolean changerEcran;	// true si on passe a une nouvelle vue, false sinon

	public VueStat(int i, VueCentrale v, Controleur c) {
		// TODO Auto-generated constructor stub
		id = i;
		vue = v;
		ctrl = c;
		changerEcran = false;
	}

	/**
	 * Fonction initialisant les éléments de la vue
	 * Elle est appelée une fois au moment de la création de la vue
	 */
	@Override
	public void init(GameContainer c, StateBasedGame gm)
			throws SlickException {
		// TODO Auto-generated method stub
		nbPerso = ctrl.getNbPerso();
		c.getInput().addKeyListener(new Clavier());
	}

	/**
	 * Fonction gérant l'affichage graphique
	 * Elle est appelée régulièrement
	 */
	@Override
	public void render(GameContainer c, StateBasedGame gm, Graphics gr)
			throws SlickException {
		// TODO Auto-generated method stub
		gr.drawString("NIVEAU TERMINE", c.getWidth()/2-80, 50);
		afficherScoreJoueur(gr);
		gr.drawString("Entrée pour continuer à jouer", 70, 400);
	}
	
	/**
	 * Affiche le score des joueurs en fonction du nombre de Pacman détecté
	 */
	public void afficherScoreJoueur(Graphics gr)
	{
		int nbJoueur = 0;
		for(int i = 0; i < nbPerso; i++)
		{
			if(ctrl.getPerso(i).getType().equals(E_Contenu.PACMAN))
			{
				Pacman pac = (Pacman) ctrl.getPerso(i);
				gr.drawString("Score du joueur "+ (nbJoueur+1) +" : "+ pac.getCalculScore(), 90, 100+(nbJoueur*125));
				gr.drawString("Nombre de PacGums mangés : "+ pac.getNbPacgum(), 90, 125+(nbJoueur*125));
				gr.drawString("Vies restantes : "+ pac.getVies(), 90, 150+(nbJoueur*125));
				gr.drawString("Score total : "+ pac.getScore(), 90, 175+(nbJoueur*125));
				nbJoueur++;
			}
		}
	}

	/**
	 * Met à jour les éléments de la vue 
	 */
	@Override
	public void update(GameContainer c, StateBasedGame gm, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if(changerEcran)
		{
			gm.enterState(idSuiv, new FadeOutTransition(Color.black, 150), new FadeInTransition(Color.black, 150));
			ctrl.changerDeNiveau();
			vue.lancerOrdo();
			changerEcran = false;
		}
	}
	
	/**
	 * Indique que l'on doit passer à l'écran dont on passe l'id
	 * @param i : id de l'écran que l'on veut afficher ou -1 si on quitte le jeu
	 */
	public void changerEcran(int i)
	{
		idSuiv = i;
		changerEcran = true;
	}

	/**
	 * Renvoie l'identifiant de la map
	 */
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
	
	
	class Clavier implements KeyListener
	{

		@Override
		public void inputEnded() {}

		@Override
		public void inputStarted() {}

		@Override
		public boolean isAcceptingInput() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void setInput(Input arg0) {}

		@Override
		public void keyPressed(int i, char ch) {}

		@Override
		public void keyReleased(int i, char ch) {
			// TODO Auto-generated method stub
			if(vue.getCurrentStateID() == id)
			{
				if(i == Input.KEY_ENTER)
				{
					changerEcran(3);
				}
			}
		}
		
	}

}
