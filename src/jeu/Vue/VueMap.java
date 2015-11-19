package jeu.Vue;

import java.util.ArrayList;

import jeu.Modeles.*;
import jeu.Coeur.*;
import jeu.Enum.*;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class VueMap extends BasicGameState 
{
	
	private final int id;
	private int idSuiv;
	private VueCentrale vue;
	private Controleur ctrl;
	private boolean changerEcran;
	private Map map;
	private int nbPerso;
	private int[] xPersos;
	private int[] yPersos;
	private Animation[][] animations;
	private int tCase = 42;				// taille des cases du labyrinthe

	/**
	 * On cree une vue en lui donnant son identifiant, une map et un controleur
	 * @param i : l'identifiant de la vue, il est unique
	 * @param m : la map que l'on souhaite afficher
	 * @param c : le controleur de la map
	 */
	public VueMap(int i, VueCentrale v, Map m, Controleur c)
	{
		super();
		// TODO Auto-generated constructor stub
		id = i;
		vue = v;
		ctrl = c;
		changerEcran = false;
		map = m;
	}

	/**
	 * Fonction initialisant les elements de la vue
	 * Elle est appelee une fois au moment de la cr�ation de la vue
	 */
	@Override
	public void init(GameContainer c, StateBasedGame gm)
			throws SlickException
	{
		// TODO Auto-generated method stub
		ctrl.initDebPartie();
		nbPerso = ctrl.getNbPerso();
		xPersos = new int[nbPerso];
		yPersos = new int[nbPerso];
		animations = new Animation[nbPerso][5];		// 1 animation pour chaque direction, donc 4 par perso
		int tAnim = 150;							// temps d'affichage de chaque frame en ms
		for(int i = 0; i<nbPerso; i++)
		{
			SpriteSheet spritePC = new SpriteSheet(ctrl.getPerso(i).getImage(), 42, 42);
			for(int j = 0; j<animations[i].length; j++)
			{
				animations[i][j] = new Animation();
				animations[i][j].addFrame(spritePC.getSprite(0, j), tAnim);
				animations[i][j].addFrame(spritePC.getSprite(1, j), tAnim);
			}
			xPersos[i] = ctrl.getPerso(i).getLargeur()*tCase;
			yPersos[i] = ctrl.getPerso(i).getHauteur()*tCase;
		}
		c.getInput().addKeyListener(new Clavier());
	}

	/**
	 * Fonction gerant l'affichage graphique
	 * Elle est appelee regulierement
	 */
	@Override
	public void render(GameContainer c, StateBasedGame gm, Graphics gr)
			throws SlickException
	{		
		// TODO Auto-generated method stub
		int epaisseurMur = 6;
		gr.setColor(new Color(10,30,100)); // FOND BLEU FONCE
		gr.fillRect(0, 0, c.getWidth(), c.getHeight());
		
		for(int i=0; i<map.getHauteurMap(); i++)
		{
			for(int j=0; j<map.getLargeurMap(); j++)
			{
				if((i%2 == 0 && j%2 == 0) || (i%2 == 1 && j%2 == 1))
				{
					gr.drawImage(new Image((System.getProperty("user.dir"))+"/res/piece.png").getScaledCopy(126, 126), tCase*j*3,  tCase*i*3 + 50);
					//gr.setColor(Color.darkGray);
					//gr.fillRect(tCase*j*3, tCase*i*3 + 50, tCase*3, tCase*3);
				}
			}
		}

		gr.setColor(Color.white); // MUR
		
		for(int i=0; i<map.getHauteurMap()*3; i++)
		{
			for(int j=0; j<map.getLargeurMap()*3; j++)
			{
				Case c_courant=map.getCase(i, j);
				if(c_courant.getHaut())
					gr.fillRect(tCase*j, 50 + tCase*i - epaisseurMur/2, tCase, epaisseurMur);			// Segment haut
				if(c_courant.getBas())
					gr.fillRect(tCase*j, 50 + tCase*(i+1) - epaisseurMur/2, tCase, epaisseurMur);		// Segment bas
				if(c_courant.getGauche())
					gr.fillRect(tCase*j - epaisseurMur/2, 50 + tCase*i, epaisseurMur, tCase);		// Segment gauche
				if(c_courant.getDroite())
					gr.fillRect(tCase*(j+1) - epaisseurMur/2, 50 + tCase*i, epaisseurMur, tCase);	// Segment droite
				ArrayList<String> nom_images =c_courant.getImagesDesContenusAAfficher();
				for(int k=0; k<nom_images.size();k++)
				{
					Image im=new Image(nom_images.get(k));
					gr.drawImage(im, j*tCase, 50 + i*tCase);
				}
			}
		}
		for(int k=0; k<nbPerso; k++)
		{
			Personnage p = ctrl.getPerso(k);
			if(p.getApeure()){
				gr.drawAnimation(animations[k][4], xPersos[k], 50 + yPersos[k]);
			}
			else
			{
				gr.drawAnimation(animations[k][(E_Orientation.EnumToInt(p.getOrientation()))], xPersos[k], 50 + yPersos[k]);
			}
		}

		if(ctrl.getYaunmort())
		{
			gr.setColor(Color.black);
			gr.fillRect(c.getWidth()/2 - 120, c.getHeight()/2 - 5, 280, 30);
			gr.setColor(Color.white);
			gr.drawString("L'un d'entre vous a été tué", c.getWidth()/2 - 100, c.getHeight()/2);
		}
		
		/*if(ctrl.getDebPartie()>=0 && ctrl.getDebPartie()<9)
		{
			gr.setColor(Color.red);
			if(ctrl.getDebPartie() == 3)
			{
				gr.drawString("3", c.getWidth()/2, c.getHeight()/2);
			}
			else if(ctrl.getDebPartie() == 5)
			{
					gr.drawString("2", c.getWidth()/2, c.getHeight()/2);
			}
			else if(ctrl.getDebPartie() == 7)
			{
				gr.drawString("1", c.getWidth()/2, c.getHeight()/2);
			}
			
		}*/
		
		// BARRE SCORE
		gr.setColor(Color.black);
		gr.fillRect(0, 0, map.getLargeurMap()*3*tCase, 50);
		gr.setColor(Color.white);
		afficherScoreJoueur(gr);
		// Les touches pour passer d'un ecran a l'autre
		//gr.drawString("Echap : Pause - M : Stat - O : Score atteint", 0, map.getHauteurMap()*3*42+35);
		gr.drawString("Echap : Pause", 0, map.getHauteurMap()*3*42+35);
	}
	
	/**
	 * Dessine un mur si la case en comporte un sur ce cote
	 * @param x : abscisses du mur
	 * @param y : ordonnes du mur
	 * @param l : largeur du mur
	 * @param h : hauteur du mur
	 */
	public void dessinerMur(int x, int y, int l, int h, Graphics gr)
	{
		gr.setColor(Color.black);
		//gr.setColor(new Color(120,160,40));
		//gr.setColor(new Color(57, 164, 29)); // CHANGER COULEUR MUR
		gr.fillRect(x, y, l, h);
	}
	
	/**
	 * Affiche le score des joueurs en fonction du nombre de Pacman detectes
	 */
	public void afficherScoreJoueur(Graphics gr)
	{
		int nbJoueur = 0;
		for(int i = 0; i < nbPerso; i++)
		{
			if(ctrl.getPerso(i).getType() == E_Contenu.PACMAN)
			{
				Pacman pac =  (Pacman) ctrl.getPerso(i);
				gr.drawString("Joueur "+ (nbJoueur+1) + " : " + pac.getScore() + " pts", 10+nbJoueur*280, 5);
				gr.drawString("Score Niveau : "+ pac.getCalculScore() +" | Vies : "+ pac.getVies(), 10+nbJoueur*280, 25);
				nbJoueur++;
			}
		}
	}

	/**
	 * Met a jour les elements de la vue 
	 */
	@Override
	public void update(GameContainer c, StateBasedGame gm, int delta)
			throws SlickException
	{
		setMap(ctrl.getMap());
		// TODO Auto-generated method stub
		// --------- Changement d'ecran ---------
		if(ctrl.getFinDeNiveau())
		{
			gm.enterState(5, new FadeOutTransition(Color.black, 50), new FadeInTransition(Color.black, 50));
			vue.arreterOrdo();
			changerEcran = false;
		}
		else
		{
			if(changerEcran)
			{
				gm.enterState(idSuiv, new FadeOutTransition(Color.black, 50), new FadeInTransition(Color.black, 50));
				vue.arreterOrdo();
				changerEcran = false;
			}
		}
				
		// ----- Deplacement des personnages -----
		int vit;
		if(Jeu.versionDemo == 3){
			vit = 14;	
		}else{
			vit = 6;			// vitesse des personnages en nombre de pixels par cycle
		}
		for(int i = 0; i<nbPerso; i++){
			if(!ctrl.getPerso(i).getTeleportation())
			{
				// on lance le d�placement
				switch(ctrl.getPerso(i).getOrientation())
				{
				case HAUT :
					deplacerPersoHautOuBas(i, vit);
					break;
				case BAS :
					deplacerPersoHautOuBas(i, vit);
					break;
				case GAUCHE :
					deplacerPersoGaucheOuDroite(i, vit);
					break;
				case DROITE :
					deplacerPersoGaucheOuDroite(i, vit);
					break;
				}
			}
			else
			{
				deplacerPersoCase(i);
			}
			// apres tout ca, on teste si le perso est sorti de la map de facon propre pour faire l'anim du tore
			testSortieMap(i);
		}
	}
	
	/**
	 * Deplace un personnage sur la case indique, sans animation
	 * @param ip : indice du personnage
	 */
	public void deplacerPersoCase(int ip)
	{
		xPersos[ip] = tCase*ctrl.getPerso(ip).getLargeur();
		yPersos[ip] = tCase*ctrl.getPerso(ip).getHauteur();
		if(ctrl.getPerso(ip).getTeleportation())
		{
			ctrl.getPerso(ip).setTeleportation(false);
		}
	}
	
	/**
	 * Permet de deplacer le personnage sur l'axe des ordonnees en fonction de son orientation
	 * @param ip : indice du personnage
	 * @param v : vitesse du deplacement en pixel par cycle
	 */
	public void deplacerPersoHautOuBas(int ip, int v)
	{
		Personnage p = ctrl.getPerso(ip);
		int hp = p.getHauteur();
		int lp = p.getLargeur();
		if(yPersos[ip] != tCase*hp)
		{
			switch(p.getOrientation())
			{
			case HAUT :
				yPersos[ip] -= v;
				break;
			case BAS :
				yPersos[ip] += v;
				break;
			}
			xPersos[ip] = tCase*lp;
		}
	}
	
	/**
	 * Permet de deplacer le personnage sur l'axe des abscisses en fonction de son orientation
	 * @param ip : indice du personnage
	 * @param v : vitesse du deplacement en pixel par cycle
	 */
	public void deplacerPersoGaucheOuDroite(int ip, int v)
	{
		Personnage p = ctrl.getPerso(ip);
		int hp = p.getHauteur();
		int lp = p.getLargeur();
		if(xPersos[ip] != tCase*lp)
		{
			switch(p.getOrientation())
			{
			case GAUCHE :
				xPersos[ip] -= v;
				break;
			case DROITE :
				xPersos[ip] += v;
				break;
			}
			yPersos[ip] = tCase*hp;
		}
	}
	
	/**
	 * Permet de savoir si Pacman sort de la map, et donc de faire l'animation du tore
	 * @param ip : indice du personnage
	 */
	public void testSortieMap(int ip)
	{
		int lm = map.getLargeurMap()*3*tCase;
		int hm = map.getHauteurMap()*3*tCase;
		switch(ctrl.getPerso(ip).getOrientation())
		{
		case HAUT :
			if(yPersos[ip] < 0)
			{
				traiterSortieMapHautOuGauche(ip, -tCase, hm);
			}
		case BAS :
			if(yPersos[ip] > hm - tCase)
			{
				traiterSortieMapBasOuDroite(ip, hm, -tCase);
			}
		case GAUCHE :
			if(xPersos[ip] < 0)
			{
				traiterSortieMapHautOuGauche(ip, -tCase, lm);
			}
		case DROITE :
			if(xPersos[ip] > lm - tCase)
			{
				traiterSortieMapBasOuDroite(ip, lm, -tCase);
			}
		}
	}
	
	/**
	 * Traite le deplacement du Pacman quand il sort de la map en haut ou a gauche
	 * @param ip : indice du personnage
	 * @param ci : coordonnee initiale (ie coordonnee a laquelle le personnage quitte la map)
	 * @param cf : coordonnee finale (ie la ou on fait reapparaitre le personnage avant qu'il ne rentre sur la map)
	 */
	public void traiterSortieMapHautOuGauche(int ip, int ci, int cf)
	{
		switch(ctrl.getPerso(ip).getOrientation())
		{
		case HAUT :
			if(yPersos[ip] < ci)
			{
				yPersos[ip] = cf;
			}
			break;
		case GAUCHE :
			if(xPersos[ip] < ci)
			{
				xPersos[ip] = cf;
			}
			break;
		}
	}
	
	/**
	 * Traite le deplacement du Pacman quand il sort de la map en bas ou a droite
	 * @param ip : indice du personnage
	 * @param ci : coordonnee initiale (ie coordonnee a laquelle le personnage quitte la map)
	 * @param cf : coordonnee finale (ie la ou on fait reapparaitre le personnage avant qu'il ne rentre sur la map)
	 */
	public void traiterSortieMapBasOuDroite(int ip, int ci, int cf)
	{
		switch(ctrl.getPerso(ip).getOrientation())
		{
		case BAS :
			if(yPersos[ip] > ci)
			{
				yPersos[ip] = cf;
			}
			break;
		case DROITE :
			if(xPersos[ip] > ci)
			{
				xPersos[ip] = cf;
			}
			break;
		}
	}
	
	/**
	 * Indique que l'on doit afficher l'ecran dont on passe l'id
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
	public int getID()
	{
		// TODO Auto-generated method stub
		return id;
	}
	
	
	/**
	 * Permet de gerer l'appui sur les touches du clavier quand on est sur la vue Map
	 * @author Marie
	 *
	 */
	class Clavier implements KeyListener
	{

		@Override
		public void inputEnded() 
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void inputStarted() 
		{
			// TODO Auto-generated method stub
		}

		@Override
		public boolean isAcceptingInput() 
		{
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void setInput(Input i) 
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(int i, char c) 
		{
			// TODO Auto-generated method stub
			//ctrl.ajouterClicEnAttente(i);			// A reimplementer plus tard ?
			if(i != Input.KEY_ESCAPE /*&& i != Input.KEY_M && i != Input.KEY_O*/)
			{
				ctrl.uneToucheAEtePressee(i);
			}
			if(vue.getCurrentStateID() == id)
			{
				switch(i)
				{
				case Input.KEY_ESCAPE:
					changerEcran(4);
					break;
				/*case Input.KEY_M :		// Pour declencher l'ecran des stats
					changerEcran(5);
					break;
				case Input.KEY_O :		// Pour declencher l'ecran de score atteint		
					changerEcran(6);
					break;*/
				}
			}
		}

		@Override
		public void keyReleased(int i, char c) 
		{
			// TODO Auto-generated method stub
		}
		
	}
	
	public void setMap(Map m)
	{
		map = m;
	}
}
