package jeu.Vue;

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

public class VueScoreAtteint extends BasicGameState {

	private final int id;
	private int idSuiv;
	private VueCentrale vue;
	private boolean changerEcran;	// true si on passe a une nouvelle vue, false sinon

	public VueScoreAtteint(int i, VueCentrale v) {
		// TODO Auto-generated constructor stub
		id = i;
		vue = v;
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
		gr.drawString("Score Atteint", 50, 50);
		gr.drawString("Entrée pour continuer la partie", 30, 150);
		gr.drawString("Echap pour terminer la partie", 30, 175);
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
			if(idSuiv == 3)
			{
				vue.lancerOrdo();
			}
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
				switch(i){
				case Input.KEY_ENTER :
					changerEcran(3);
					break;
				case Input.KEY_ESCAPE :
					changerEcran(7);
					break;
				}
			}
		}
		
	}

}
