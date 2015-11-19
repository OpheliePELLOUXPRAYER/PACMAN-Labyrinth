package jeu.Vue;

import jeu.Modeles.*;
import jeu.Coeur.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Marie
 *
 */

public class VueCentrale extends StateBasedGame 
{
	private Controleur ctrl;
	private final int idTitre = 0;
	private final int idMenu = 1;
	private final int idRegle = 2;
	private final int idMap = 3;
	private final int idPause = 4;
	private final int idStat = 5;
	private final int idScore = 6;
	private final int idFin = 7;
	private boolean regleFromMenu;

	public VueCentrale(String name, Map m, Controleur c) throws SlickException 
	{
		super(name);
		// TODO Auto-generated constructor stub
		ctrl = c;
		regleFromMenu = false;
		addState(new VueTitre(idTitre, this));
		addState(new VueMenu(idMenu, this));
		addState(new VueRegle(idRegle, this));
		addState(new VueMap(idMap, this, m, c));
		addState(new VuePause(idPause, this));
		addState(new VueStat(idStat, this, c));
		addState(new VueScoreAtteint(idScore, this));
		addState(new VueFinPartie(idFin, this));
		arreterOrdo();
	}

	@Override
	public void initStatesList(GameContainer c) throws SlickException 
	{
		// TODO Auto-generated method stub
		this.enterState(idTitre);
	}
	
	/**
	 * Red�marre l'ordonnanceur
	 * A utiliser quand on entre dans la vue Map
	 */
	public void lancerOrdo()
	{
		ctrl.reprendre();
	}
	
	/**
	 * Met en pause l'ordonnanceur
	 * A utiliser quand on veut afficher les diff�rents menus du jeu
	 */
	public void arreterOrdo()
	{
		ctrl.pause();
	}
	
	/**
	 * Indique qu'on a accede a l'ecran des regles depuis la vue Menu
	 */
	public void regleFromMenu()
	{
		regleFromMenu = true;
	}
	
	/**
	 * Indique qu'on a accede a l'ecran des regles depuis la vue Pause
	 */
	public void regleFromPause()
	{
		regleFromMenu = false;
	}
	
	/**
	 * Donne la valeur booleenne de regleFromMenu
	 */
	public boolean getRegleFromMenu()
	{
		return regleFromMenu;
	}

}
