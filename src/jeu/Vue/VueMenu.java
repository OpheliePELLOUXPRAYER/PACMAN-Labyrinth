package jeu.Vue;



import jeu.Vue.OutilsVue.Bouton;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class VueMenu extends BasicGameState {
	
	private final int id;
	private int idSuiv;
	private VueCentrale vue;
	private boolean changerEcran;	// true si on passe a une nouvelle vue, false sinon
	// --- Gestion des boutons ---
	private Bouton[] boutons;
	private String[][] nomsBtn;
	private int nbBtn;
	private int btnActif;
	
	
	public VueMenu(int i, VueCentrale v) throws SlickException {
		// TODO Auto-generated constructor stub
		id = i;
		vue = v;
		changerEcran = false;
	}

	/**
	 * Fonction initialisant les elements de la vue
	 * Elle est appelee une fois au moment de la creation de la vue
	 */
	
	public void init(GameContainer c, StateBasedGame gm)
			throws SlickException {
		// TODO Auto-generated method stub
		// --- Creation des boutons ---
		nbBtn = 3;
		nomsBtn = new String[nbBtn][3];
		// --- Bouton Jouer ---
		nomsBtn[0][0] = "res/jouer.png";
		nomsBtn[0][1] = "res/jouerV.png";
		nomsBtn[0][2] = "res/jouerVF.png";
		// --- Bouton Regles ---
		nomsBtn[1][0] = "res/regles.png";
		nomsBtn[1][1] = "res/reglesV.png";
		nomsBtn[1][2] = "res/reglesVF.png";
		// --- Bouton Quitter ---
		nomsBtn[2][0] = "res/quitter.png";
		nomsBtn[2][1] = "res/quitterV.png";
		nomsBtn[2][2] = "res/quitterVF.png";
		// --- Instanciation des boutons ---
		boutons = new Bouton[nbBtn];
		int lBtn = c.getWidth()/(nbBtn+1);
		int hBtn = lBtn/2;
		int delta = lBtn/(nbBtn+1);		// decalage entre les boutons
		for(int i = 0; i < nbBtn; i++)
		{
			boutons[i] = new Bouton(delta + (delta + lBtn)*i, c.getHeight()/2, lBtn, hBtn, nomsBtn[i][0], nomsBtn[i][1], nomsBtn[i][2]);
		}
		btnActif = 0;
		boutons[btnActif].setEtat(1);
		// -------------------------------
		c.getInput().addKeyListener(new Clavier());
	}
 

	
	/**
	 * Fonction gerant l'affichage graphique
	 * Elle est appelee regulierement
	 */
	@Override
	public void render(GameContainer c, StateBasedGame gm, Graphics gr)throws SlickException 
	{
		int screen_largeur = vue.getContainer().getWidth();
		
		// TODO Auto-generated method stub
		// --- Dessin du logo ---
		Image logo = new Image("/res/logo.png");
		gr.drawImage(logo, screen_largeur/2 - logo.getWidth()/2, logo.getHeight()/4);
		// --- Dessin des boutons ---
		for(int i = 0; i < nbBtn; i++)
		{
			boutons[i].render(gr, boutons[i].getEtat());
		}
		// --------------------------
		
	}

	/**
	 * Met a jour les elements de la vue 
	 */
	@Override
	public void update(GameContainer c, StateBasedGame gm, int delta)
			throws SlickException {
		if(changerEcran)
		{
			gm.enterState(idSuiv, new FadeOutTransition(Color.black, 150), new FadeInTransition(Color.black, 150));
			if(idSuiv == 3)
			{
				vue.lancerOrdo();
			}
			changerEcran = false;
		}
		for(int i = 0; i < nbBtn; i++)
		{
			if(i != btnActif)
			{
				boutons[i].setEtat(0);
			}
		}
	}
	
	/**
	 * Permet de changer d'ecran en fonction du bouton sur lequel on a appuye
	 * @param b : indice du bouton
	 */
	public void boutonAppuye(int b)
	{
		switch(b)
		{
		case 0 :
			// on passe a l'ecran de jeu
			idSuiv = 3;
			break;
		case 1 :
			// on passe a l'ecran des regles
			vue.regleFromMenu();
			idSuiv = 2;
			break;
		case 2 :
			// on quitte le jeu
			vue.getContainer().exit();
			break;
		}
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
		public void keyPressed(int i, char ch)
		{
			if(vue.getCurrentStateID() == id)
			{
				switch(i)
				{
				case Input.KEY_LEFT :
					if(btnActif != 0)
					{
						btnActif --;
						boutons[btnActif].setEtat(1);
					}
					break;
				case Input.KEY_RIGHT :
					if(btnActif != (nbBtn-1))
					{
						btnActif ++;
						boutons[btnActif].setEtat(1);
					}
					break;
				case Input.KEY_ENTER :
					boutons[btnActif].setEtat(2);
					boutonAppuye(btnActif);
				}
			}
		}

		@Override
		public void keyReleased(int i, char ch)
		{
			// TODO Auto-generated method stub
			if(i == Input.KEY_ENTER)
			{
				boutons[btnActif].setEtat(1);
			}
		}
		
	}	
}
