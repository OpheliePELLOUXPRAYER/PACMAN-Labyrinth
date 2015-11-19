package jeu.Vue.OutilsVue;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

/**
 * Classe repr�sentant les boutons utilis�s dans les diff�rentes vues
 * Ces boutons servent � passer d'une vue � l'autre
 * @author Marie
 *
 */
public class Bouton{
	
	//private Image logo,jouer,regles,quitter;
	private Image[] images;
	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	private boolean boutonSurvole = false;
	private boolean boutonClique = false;
	// --- Parametres qui definissent la zone de clic ---
	//private Shape zone;
	//private Graphics g;
	// TEST 
	//private int Lscreen,Hscreen;
	
	public Bouton(int a, int o, int l, int h, String i1, String i2, String i3) throws SlickException {
		x = a;
		y = o;
		largeur = l;
		hauteur = h;
		images = new Image[3];
		init(i1, i2, i3);
		boutonSurvole = false;
		boutonClique = false;
		
		/*
		Hscreen=container.getHeight();
		Lscreen=container.getWidth();
		
		addListener(listener);
		Input input = container.getInput();
		zone = shape;
		boutonSurvole = zone.contains(container.getInput().getMouseX(), container.getInput().getMouseY());
		boutonClique = input.isMouseButtonDown(0); // si le clic gauche de la souris est active
		*/
	}
	
	public void init(String i1, String i2, String i3) throws SlickException
	{
		/*
		logo = new Image("/res/logo.png");
		jouer =new Image("/res/jouer.png");
		regles =new Image("/res/regles.png");
		quitter =new Image("/res/quitter.png");
		*/
		images[0] = new Image(i1);
		images[1] = new Image(i2);
		images[2] = new Image(i3);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

	// public Image getImage (String s)
	public Image getImage ()
	{
		/*
		switch(s)
		{
			case "logo":
				return logo;
			case "jouer":
				return jouer;
			case "regles":
				return regles;
			default :
				return quitter;
		}
		*/
		return images[0];
	}
	
	public int getLargeur() {
		// TODO Auto-generated method stub
		return largeur;
	}

	public int getHauteur() {
		// TODO Auto-generated method stub
		return hauteur;
	}
	
	public int getEtat()
	{
		int res;
		if(boutonClique)
		{
			res = 2;
		}
		else
		{
			if(boutonSurvole)
			{
				res = 1;
			}
			else
			{
				res = 0;
			}
		}
		return res;
	}
	
	public void setEtat(int e)
	{
		switch(e)
		{
		case 0 :
			boutonSurvole = false;
			boutonClique = false;
			break;
		case 1 :
			boutonSurvole = true;
			boutonClique = false;
			break;
		case 2 :
			boutonSurvole = false;
			boutonClique = true;
			break;
		}
	}
	
	public void render(Graphics g, int i)
	{
		g.drawImage(images[i],
				x, y,
				x+largeur, y+hauteur,
				0, 0,
				images[i].getWidth(), images[i].getHeight());
	}
	
	private void updateImage(Graphics g) throws SlickException
	{
		render(g, getEtat());
	}
}
