package jeu.Vue;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

public class VueTitre extends BasicGameState {
	
	private final int id;
	private int idSuiv;
	private VueCentrale vue;
	private boolean changerEcran;	// true si on passe a une nouvelle vue, false sinon
	
	/**
	 * On cree une vue pour le titre en lui donnant un identifiant unique
	 * @param i
	 */
	public VueTitre(int i, VueCentrale v) {
		super();
		// TODO Auto-generated constructor stub
		id = i;
		vue = v;
		changerEcran = false;
	}

	/**
	 * Fonction initialisant les elements de la vue
	 * Elle est appelee une fois au moment de la creation de la vue
	 */
	@Override
	public void init(GameContainer c, StateBasedGame gm)
			throws SlickException {
		// TODO Auto-generated method stub
		c.getInput().addKeyListener(new Clavier());
	}

	/**
	 * Fonction gerant l'affichage graphique
	 * Elle est appelee regulierement
	 */
	@Override
	public void render(GameContainer c, StateBasedGame gm, Graphics gr)
			throws SlickException {
		// TODO Auto-generated method stub
		// --- Dessin du logo ---
		Image logo = new Image("/res/logo.png");
		gr.drawImage(logo, (c.getWidth() - logo.getWidth())/2, c.getHeight()/2 - logo.getHeight());
		// --- Affichage du message pour démarrer le jeu ---
		gr.drawString("Appuyer sur Entrée pour commencer", c.getWidth()/2 - 160, c.getHeight()/2 + logo.getHeight()/2);
	}

	/**
	 * Met a jour les elements de la vue 
	 */
	@Override
	public void update(GameContainer c, StateBasedGame gm, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if(changerEcran)
		{
			if(idSuiv == -1)
			{
				c.exit();
			}
			else
			{
				gm.enterState(idSuiv, new FadeOutTransition(Color.black, 150), new FadeInTransition(Color.black, 150));
			}
			changerEcran = false;
		}
	}
	
	/**
	 * Indique que l'on doit afficher a l'ecran dont on passe l'id
	 * @param i : id de l'ecran que l'on veut afficher ou -1 si on quitte le jeu
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
				switch(i)
				{
				case Input.KEY_ENTER :
					changerEcran(1);
					break;
				case Input.KEY_ESCAPE :
					changerEcran(-1);
					break;
				}
			}
		}
		
	}

}
