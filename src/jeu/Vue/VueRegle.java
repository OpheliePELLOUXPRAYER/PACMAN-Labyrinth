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

public class VueRegle extends BasicGameState {
	
	private final int id;
	private int idSuiv;
	private int idPrec;
	private VueCentrale vue;
	private boolean changerEcran;	// true si on passe a une nouvelle vue, false sinon

	public VueRegle(int i, VueCentrale v) {
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
	 * Fonction gerant l'affichage graphique
	 * Elle est appelée régulièrement
	 */
	@Override
	public void render(GameContainer c, StateBasedGame gm, Graphics gr)
			throws SlickException {
		// TODO Auto-generated method stub
		gr.drawString("REGLES", c.getWidth()/2 - 20, 50);
		// --- Les règles de base ---
		gr.drawString("Mangez toutes les PacGums pour terminer un niveau", 15, 100);
		gr.drawString("Vous gagnez un point par PacGum", 15, 125);
		gr.drawString("Les points sont comptabilisés à chaque fin de niveau", 15, 150);
		gr.drawString("Le premier à atteindre le score fixé gagne la partie", 15, 175);
		gr.drawString("Les fruits rapportent plus de points", 15, 200);
		// --- Les règles de notre jeu ---
		gr.drawString("Les colonnes et les lignes du labyrinthe bougent", 15, 225);
		gr.drawString("Vous pouvez tourner la pièce où vous êtes", 15, 250);
		// --- Les commandes
		gr.drawString("COMMANDES", 15, 300);
		gr.drawString("Actions", 30, 325);
		gr.drawString("Se déplacer", 30, 350);
		gr.drawString("Tourner gauche", 30, 375);
		gr.drawString("Tourner droite", 30, 400);
		gr.drawString("Joueur 1", 200, 325);
		gr.drawString("Flèches", 200, 350);
		gr.drawString(":", 200, 375);
		gr.drawString("!", 200, 400);
		gr.drawString("Joueur 2", 370, 325);
		gr.drawString("ZSQD", 370, 350);
		gr.drawString("A", 370, 375);
		gr.drawString("E", 370, 400);
		gr.drawString("Echap pour retourner à l'écran précédent", 60, 500);
	}

	/**
	 * Met à jour les éléments de la vue 
	 */
	@Override
	public void update(GameContainer c, StateBasedGame gm, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if(vue.getRegleFromMenu())
		{
			idPrec = 1;
		}
		else
		{
			idPrec = 4;
		}
		if(changerEcran)
		{
			gm.enterState(idSuiv, new FadeOutTransition(Color.black, 150), new FadeInTransition(Color.black, 150));
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
				if(i == Input.KEY_ESCAPE)
				{
					changerEcran(idPrec);
				}
			}
		}
		
	}

}
