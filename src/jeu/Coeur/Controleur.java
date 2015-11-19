package jeu.Coeur;

import jeu.Modeles.*;
import jeu.Enum.*;
import jeu.Vue.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import org.jdom2.Element;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Controleur du jeu 
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 */
public class Controleur 
{
	private Map map;
	private Personnage persos[];
	private ArrayList<Personnage> ordrePerso;
	private Timer t;
	private boolean pause;
	private boolean yaunmort;
	private boolean unePastilleAEteMange;
	private ArrayList<Nourriture> nourriture_a_remetre;
	private int nbPacGum;
	private int niveau;
	private int debPartie;
	
//----------------------------------------------------------------------------------------
	/**
	 * Constructeur du Controleur
	 * @param FichierXml : nom du fichier XML a parser pour recuperer les infos sur les automates
	 * @param h_map : hauteur de la map (taille en nombre de piece)
	 * @param l_map : largeur de la map (taille en nombre de piece)
	 */
	public Controleur(String FichierXml, int h_map, int l_map)
	{
		debPartie = 4;
		pause = false;
		niveau=1;
		yaunmort = false;
		unePastilleAEteMange = false;
		nourriture_a_remetre=new  ArrayList<Nourriture>();
		Parser p = new Parser(FichierXml);
		map=new Map(h_map, l_map);
		//PERSONNAGE
		int nbPerso = Integer.parseInt(p.getRacine().getAttributeValue("nb_automates"));
		persos = new Personnage[nbPerso];
		List liste = p.getRacine().getChildren();
		//Creation d'un Iterator sur notre liste Automate=[fantome;pacman;labyrinthe]
		Iterator i = liste.iterator();
		int indice=0;
		while(i.hasNext())
		{
			   Element courant = (Element)i.next();
			   E_Orientation o_perso = E_Orientation.getOrientationAleatoire();
			   Personnage pers;
			   if(courant.getAttributeValue("classe").equals("pacman"))
			   {
				   pers = new Pacman(this, choisirCaseLibreAleatoire(), o_perso, courant);
			   }
			   else
			   {
				   pers = new Fantome(this, choisirCaseLibreAleatoire(), o_perso, courant);
			   }
			   pers.affichageTouchesPerso();
			   pers.affichageTableauEtat();
			   pers.affichageTableauTransition();
			   persos[indice]=pers;
			   ////System.out.println("");
			   indice++;
		}
		ordrePerso= new ArrayList<Personnage>();//remplacer par une file a prioritï¿½		
		remplirMap();
		
		t=new Timer();
		
		TimerTask task = new TimerTask() 
		{
			@Override
			public void run() 
			{
				ordonnanceur();
			}
		};
		t.schedule(task, 4000);
		
		AppGameContainer container;
		try
		{
			container = new AppGameContainer(new VueCentrale("PacMan : La folie du labyrinthe", map, this));
			container.setDisplayMode(l_map*3*42, 50 + h_map*3*42, false); // 3 cases / piï¿½ce, 42 pixels une case
			container.setShowFPS(false);
			container.start();
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}
//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
	/*  GETTEURS */
//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
	
	/**
	 * getteur du nombre de personnages du jeu
	 * @return le nombre de personnages du jeu
	 */
	public int getNbPerso()
	{
		return persos.length;
	}
//----------------------------------------------------------------------------------------
	/**
	 * getteur du numero du niveau actuel
	 * @return le numero du niveau actuel
	 */
	public int getNumNiveau()
	{
		return niveau;
	}
//----------------------------------------------------------------------------------------
	/**
	 * getteur du boolean fin de niveau
	 * @return true si il faut afficher la vue de fin de niveau (score) ; false sinon
	 */
	public boolean getFinDeNiveau()
	{
		return nbPacGum==0;
	}
//----------------------------------------------------------------------------------------
	/**
	 * getteur d'un personnage
	 * @param i : indice du perso dans le tableau de persos
	 * @return le perso de l'indice i
	 */
	public Personnage getPerso(int i)
	{
		return persos[i];
	}
	
	public boolean getYaunmort()
	{
		return yaunmort;
	}
	
	public Map getMap()
	{
		return map;
	}
	
	public int getDebPartie()
	{
		return debPartie;
	}
	
//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
	/*  SETTEURS ET AUTRES FONCTIONS QUI MODIFIENT LES ATTRIBUTs DU CONTROLEUR*/
//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
	/**
	 * Initialise le debut de partie
	 */
	public void initDebPartie()
	{
		debPartie = 0;
	}
	
	/**
	 * decremente le nombre de pacGum de 1
	 */
	public void decrementerNbPacgum()
	{
		nbPacGum--;
	}
//----------------------------------------------------------------------------------------

	/**
	 * permet d'ajouter la nourriture qui sort de la map dans la liste "nourriture_a_remetre "
	 * la nourriture presente cette liste sera remis sur la piece a inserer sur la map au prochain "bougerMap"
	 * @param n nourriture a ajouter a la liste
	 */
	public void ajouterNourritureARemettre(Nourriture n)
	{
		nourriture_a_remetre.add(n);
	}
//----------------------------------------------------------------------------------------
	/**
	 * indique qu'un pacman est mort et bloque l'ordonanceur jusqu'a ce que pacman rePop
	 */
	public void pacmanMort(Pacman pac)
	{
		//System.out.println("Un Pacman est mort");
		pac.setEtat(0);
		pac.setDerniereTouchePressee_perso(-1);
		yaunmort = true;
	}
	//----------------------------------------------------------------------------------------
	/**
	 * indique que le pacman est reaparu sur la map et deploque l'ordonanceur
	 */
	public void pacmanRePop()
	{
		yaunmort = false;
	}
//----------------------------------------------------------------------------------------
	/**
	 * Permet de mettre en pause le jeu
	 */
	public void pause()
	{
		pause = true;
	}
//----------------------------------------------------------------------------------------
	/**
	 * Permet de reprendre le jeu
	 */
	public void reprendre()
	{
		pause = false;
		initDebPartie();
	}
//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
	/*  AUTRES */
//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
	/**
	 * permet de remplir la map de pastille/fruit et le reste des cases libre de pacGum
	 * a appeler apres la creation de la map et le placement des persos..
	 */
	public void remplirMap()
	{
		// remplissage de la map
		int nb_pastille;
		nbPacGum=0;
		int nb_cerise;
		int nb_fraise;
		int nb_orange;
		
		if(Jeu.versionDemo==1)
		{
			nb_pastille=0;
			nb_cerise=1;
			nb_fraise=2;
			nb_orange=1;
		}
		else if(Jeu.versionDemo==2)
		{
			nb_pastille=2;
			nb_cerise=0;
			nb_fraise=0;
			nb_orange=0;
		}
		else
		{
			nb_pastille=6;
			nb_cerise=5;
			nb_fraise=8;
			nb_orange=7;
		}
		Case c;
		
		for(int k=0;k<nb_pastille;k++)
		{
			c=choisirCaseLibreAleatoire();
			////System.out.println("une etape de passe");
			c.setContenuDuDessus(new Pastille(this));
		}
		for(int k=0;k<nb_cerise;k++)
		{
			c=choisirCaseLibreAleatoire();
			////System.out.println("deux etapes de passe");
			c.setContenuDuDessus(new Cerise(this));
		}
		for(int k=0;k<nb_fraise;k++)
		{
			c=choisirCaseLibreAleatoire();
			////System.out.println("trois etapes de passe");
			c.setContenuDuDessus(new Fraise(this));
		}
		for(int k=0;k<nb_orange;k++)
		{
			c=choisirCaseLibreAleatoire();
			//System.out.println("derniere etape passe");
			c.setContenuDuDessus(new Orange(this));
		}
		for(int h=0;h<(map.getHauteurMap()*3);h++)
		{
			for(int l=0; l<(map.getLargeurMap()*3);l++)
			{
				if(map.getCase(h, l).getContenu().getType().equals(E_Contenu.LIBRE))
				{
					map.getCase(h, l).setContenuDuDessus(new Pacgum(this));
					nbPacGum++;
				}
			}
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * permet de passer au niveau suivant, de reecreer et reremplir la map, de gerer les scores des persos et de les replacer sur la map
	 */
	public void changerDeNiveau()
	{
		niveau++;
		map=new Map(map.getHauteurMap(), map.getLargeurMap());
		remplirMap();
		Pacman pac_qui_a_manger_le_plus_de_pacgum=null;
		int nbPacGumMax=0;
		for(Personnage p : ordrePerso)
		{
			p.setEtat(0);
			if(p.getType().equals(E_Contenu.PACMAN))
			{
				Pacman pac =(Pacman) p;
				pac.augmenterScore(pac.getCalculScore());
				if(pac.getNbPacgum()>nbPacGumMax)
				{
					pac_qui_a_manger_le_plus_de_pacgum=pac;
					nbPacGumMax=pac.getNbPacgum();
				}
				pac.setCalculScore(0);
				pac.setDerniereTouchePressee_perso(-1);
				pac.setNbPacgum(0);
			}
			p.mourir();
		}
		pac_qui_a_manger_le_plus_de_pacgum.augmenterScore(20);
		initDebPartie();
		
	}
//----------------------------------------------------------------------------------------
	/**
	 * choisis aleatoirement une case libre
	 * @return une case libre aleatoire
	 */public Case choisirCaseLibreAleatoire()
	{
		//System.out.println("------------ CHOIXCASELIBREALEATOIRE ---------------- ");
		int h_perso=Calculs.random(0, (map.getHauteurMap()*3)-1);
		int l_perso=Calculs.random(0, (map.getLargeurMap()*3)-1);
		//System.out.println("h_perso = " + h_perso + " l_perso = " + l_perso);
		Case nvlleCase =map.getCase(h_perso, l_perso);
		
		while(!nvlleCase.getContenu().getType().equals(E_Contenu.LIBRE))
		{
			h_perso=Calculs.random(0, (map.getHauteurMap()*3)-1);
			l_perso=Calculs.random(0, (map.getLargeurMap()*3)-1);
			nvlleCase =map.getCase(h_perso, l_perso);
		}

		return(nvlleCase);
	}
//----------------------------------------------------------------------------------------
	/**
	 * choisis aleatoirement une case sans perso (elle peut contenir des fruits, pacGum ou pastille..)
	 * @return une case sans perso aleatoire
	 */
	 public Case choisirCaseSansPersoAleatoire()
	{
		int h_perso=Calculs.random(0, ((map.getHauteurMap()*3)-1));
		int l_perso=Calculs.random(0, ((map.getLargeurMap()*3)-1));
		Case nvlleCase =map.getCase(h_perso, l_perso);
			
		//while(nvlleCase.getContenu().getType().equals(E_Contenu.FANTOME) || nvlleCase.getContenu().getType().equals(E_Contenu.PACMAN))
		while(nvlleCase.estOccupeParUnPerso())
		{
			 h_perso=Calculs.random(0, (map.getHauteurMap()*3)-1);
			 l_perso=Calculs.random(0, (map.getLargeurMap()*3)-1);
			 nvlleCase =map.getCase(h_perso, l_perso);
		}
		return(nvlleCase);
	}
	 /**
	 * est appele quand un pacman mange une pastille, permet de mettre les fantomes a "apeure"
	 */
	public void unePastilleAEteMange()
	{
		//System.out.println("Une Pastille A Ete Mange");
		for (Personnage p : ordrePerso)
		{
			if(p.getType().equals(E_Contenu.FANTOME))
			{
				Fantome f =(Fantome) p;
				f.setApeure(true);
			}
		}
		unePastilleAEteMange = true;
	}
//----------------------------------------------------------------------------------------
	/**
	 * est appele quand le joueur presse une touche du clavier
	 * cherche a quel perso appartient cette touche de commande, ce qu'elle signifie et met a jour l'attribut "derniereTouchePresse" de ce perso
	 * @param tp la touche du clavier qui a ete presse
	 */
	public void uneToucheAEtePressee(int tp)
	{
		int indice_tp=-1;
		Personnage p=null;
		//System.out.println(tp);
		for(int i=0; i<persos.length;i++)
		{
			for(int j=0; j<6;j++)
			{
				if(persos[i].getTouches_perso()[j] == tp)
				{
					p=persos[i];
					indice_tp=j;
				}
			}
		}
		if(indice_tp!=-1 && p!=null)
		{
			p.setDerniereTouchePressee_perso(indice_tp);
		}
		else
		{
			//System.out.println("CETTE TOUCHE N APPARTIENT A PERSONNE");
		}
	}	
	
//----------------------------------------------------------------------------------------
	/**
	 * Fonction a appeler lorsqu'il n'y a pas de mur devant, regarde si on peut tourner et choisi aleatoirement si le perso le fait
	 * @param p : personnage a deplacer
	 * @return : le nombre de fois ou il faut tourner a droite (0=tout droit, 1=droite ou 3=gauche)
	 */
	public int carrefour(Personnage p)
	{
		int n=0;
		int pileOuFace = Calculs.zeroOuUn();
		//System.out.println("pile ou face vaut : "+pileOuFace);
		if(pileOuFace==1)
		{
			if(!mur(p,1))
			{
				n=1;
			}
			else if(!mur(p,3))
			{
				n=3;
			}
		}
		return n;
	}
	
	public void remetreNourriture(Piece p_a_rajouter)
	{
		while(!nourriture_a_remetre.isEmpty())
		{
			//System.out.println("Taille nourriture : " + nourriture_a_remetre.size());
			p_a_rajouter.rajouterNourritureAleatoirement(nourriture_a_remetre.get(0));
			nourriture_a_remetre.remove(0);
		}
	}
	
	
	
	
//----------------------------------------------------------------------------------------
	public void bougerMap()
	{
		E_Orientation direction=E_Orientation.getOrientationAleatoire();
		Piece p_a_rajouter=new Piece(map.getHauteurMap(),map.getLargeurMap());
		remetreNourriture(p_a_rajouter);
		
		switch(direction)
		{
			case HAUT :
					map.glisserColonneHaut(Calculs.random(0, map.getHauteurMap()), p_a_rajouter);	
					break;
			case BAS :
					map.glisserColonneBas(Calculs.random(0, map.getHauteurMap()), p_a_rajouter);
					break;
			case GAUCHE :
					map.glisserLigneGauche(Calculs.random(0, map.getLargeurMap()), p_a_rajouter);
					break;
			case DROITE :
					map.glisserLigneDroite(Calculs.random(0, map.getLargeurMap()), p_a_rajouter);
					break;
		
		}
	}
	
	
/*	
----------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------
			*** ACTIONS ***
----------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------
*/
	
	/**
	 * Action qui permet de faire avancer un perso en fonction de son orientation
	 * @param perso : perso que l'on fait avancer
	 */
	public void avancer(Personnage perso)
	{
		E_Orientation o=perso.getOrientation();
		//System.out.println(o);
		Case c1 =perso.getCase();
		int h=perso.getHauteur();
		int l=perso.getLargeur();
		int h_c2=h;
		int l_c2=l;
		Case c2;
		
		switch(o)
		{
			case HAUT : h_c2=h-1; break;
			case BAS : h_c2=h+1; break;
			case GAUCHE : l_c2=l-1; break;
			case DROITE : l_c2=l+1; break;
			default : c2=null; //System.out.println("ceci est impossible");
		}
		c2 =map.getCase(h_c2,l_c2);
		//Contenu temp =c1.getContenu();
		//c1.setContenu(c2.getContenu());
		//c2.setContenu(temp);
		//System.out.println("rentre dans collision");
		c2.getContenu().collision(perso, c1);
		//c2.entreDansLaCase(perso);
	}
//----------------------------------------------------------------------------------------
	/**
	 * Fait tourner le personnage dans la direction voulue
	 * @param perso : le personnage a faire tourner
	 */
	public void tourner_perso(Personnage perso)
	{
		if(derniere_touche_est_0_1_2_3(perso)){
			perso.setOrientation(E_Orientation.IntToEnum(perso.getDerniereTouchePressee_perso()));
			if(!mur(perso)){avancer(perso);}
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * Fait tourner la piece sur laquelle est le personnage dans la direction de sa derniere touche presse
	 * @param perso a qui il faut faire tourner sa piece
	 */
	public void tourner_piece(Personnage perso)
	{
		Piece piece;
		switch(perso.getDerniereTouchePressee_perso())
		{
			case 4 :
				piece = map.getPieceFromIndicesCase(perso.getHauteur(),perso.getLargeur());
				piece.rotationPieceGauche();
				perso.setDerniereTouchePressee_perso(-1);
				break;
			case 5 :
				piece = map.getPieceFromIndicesCase(perso.getHauteur(),perso.getLargeur());
				piece.rotationPieceDroite();
				perso.setDerniereTouchePressee_perso(-1);
				break;
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * Fonction permettant a un perso de se deplacer de facon automatique dans la map (decisions d'orientation aleatoires)
	 * @param p : perso a deplacer
	 */
	public void fureter(Personnage p)
	{
		
		int n = 0;
		boolean attente=false;
		//System.out.println("orient perso = "+p.getOrientation());
		if(mur(p))
		{
			n=Calculs.gaucheOuDroite();
			//System.out.println(" on commence par : "+n);
			if(mur(p,n))
			{
				n=Calculs.mod(n+2,4);
				if(mur(p, n))
				{
					n=2;
					if(mur(p, n))
					{
						attente=true;
					}
				}
			}
		}
		else
		{
			//System.out.println("passe ds carrefour");
			n=carrefour(p);
			
		}
		//System.out.println("n vaut : "+n);
		p.changerOrientationPerso(n);
		//System.out.println("nvlle orient perso = "+p.getOrientation());
		if(!attente)
		{
			avancer(p);
		}
		
		
	}

//----------------------------------------------------------------------------------------
	public void chasser(Personnage p)
	{
		E_Orientation orientation = p.getOrientation();
		//System.out.println("h_perso = " + p.getHauteur() + " l_perso = " + p.getLargeur());
		Couple<Boolean,E_Orientation> couple = proximiteXsurNcases(p.getHauteur(), p.getLargeur(), 0, 3, E_Contenu.PACMAN);
		//System.out.println(couple.getFirst());
		if(couple.getFirst()){
			orientation = couple.getSecond();
			//System.out.println("orientation = " + orientation);
			p.setOrientation(orientation);
			if(!mur(p)){avancer(p);}
		}
		else
		{
			fureter(p);
		}
	}
//----------------------------------------------------------------------------------------
	public void traquer(Personnage p)
	{
		int distance = map.getHauteurMap();
		if(Jeu.versionDemo == 2){
			distance*=3;
		}
		E_Orientation orientation = traque(p,E_Contenu.PACMAN, distance);
		//System.out.println("L'orientaion apres traque : " + orientation);
		p.setOrientation(orientation);
		if(!mur(p)){
			avancer(p);
		}
		else
		{
			fureter(p);
		}
	}
//----------------------------------------------------------------------------------------
	public void fuir(Personnage p)
	{
		E_Orientation orientation = p.getOrientation();
		//System.out.println("h_perso = " + p.getHauteur() + " l_perso = " + p.getLargeur());
		Couple<Boolean,E_Orientation> couple = proximiteXsurNcases(p.getHauteur(), p.getLargeur(), 0, 3, E_Contenu.PACMAN);
		//System.out.println(couple.getFirst());
		if(couple.getFirst()){
			orientation = couple.getSecond();
			//System.out.println("orientation = " + orientation);
			p.setOrientation(E_Orientation.getNvlleOrientation(orientation, 2));
			fureter(p);
		}
		else
		{
			fureter(p);
		}
	}
//----------------------------------------------------------------------------------------
	public E_Orientation traque(Personnage p, E_Contenu contenu, int distance)
	{
		E_Orientation orientation = p.getOrientation();
		
		LinkedList<ElementThermique> a_parcourir = new LinkedList<ElementThermique>();
		
		LinkedList<ElementThermique> parcours = new LinkedList<ElementThermique>();
		
		boolean pacman = false;
		
		a_parcourir.add(new ElementThermique(p.getOrientation(), p.getCase(), 0));
		ElementThermique courant = a_parcourir.element();
		
		while(a_parcourir != null && !pacman && courant.getTaille()<(distance-1)){
			courant = a_parcourir.element();
			if(courant.getCase_t().getContenu().getType().equals(contenu))
			{
				pacman = true;
			}
			else
			{
				for(ElementThermique c : voisins(courant,courant.getTaille()+1)){
					if(!parcours.contains(c)){
						a_parcourir.add(c);
					}
				}	
			}
			a_parcourir.remove();
			parcours.add(courant);
		}
		
		if(pacman)
		{
			ElementThermique element; 
			element = parcours.peekLast();
			
			while(element.getTaille()>1){
				element = element.getParent();
			}
			orientation = element.getOrientation();
		}
		
		return orientation;		
	}
	
//----------------------------------------------------------------------------------------		
	public LinkedList<ElementThermique> voisins(ElementThermique element,int taille){
		LinkedList<ElementThermique> voisins = new LinkedList<ElementThermique>();
		int h = element.getCase_t().getHauteurCase();
		int l = element.getCase_t().getLargeurCase();
		if(!mur(h,l,E_Orientation.HAUT)){
			voisins.add(new ElementThermique(element, E_Orientation.HAUT, map.getCase(h-1, l), taille));
		}
		if(!mur(h,l,E_Orientation.BAS)){
			voisins.add(new ElementThermique(element, E_Orientation.BAS, map.getCase(h+1, l), taille));
		}
		if(!mur(h,l,E_Orientation.GAUCHE)){
			voisins.add(new ElementThermique(element, E_Orientation.GAUCHE, map.getCase(h, l-1), taille));
		}
		if(!mur(h,l,E_Orientation.DROITE)){
			voisins.add(new ElementThermique(element, E_Orientation.DROITE, map.getCase(h, l+1), taille));
		}
		return voisins; 
	}
	
/*	
----------------------------------------------------------------------------------------
	
----------------------------------------------------------------------------------------
			*** FIN DES ACTIONS ***
----------------------------------------------------------------------------------------
	
----------------------------------------------------------------------------------------
*/	
	

	
/*	
----------------------------------------------------------------------------------------
	
----------------------------------------------------------------------------------------
			*** TRANSITIONS ***
----------------------------------------------------------------------------------------
		
----------------------------------------------------------------------------------------
*/

//----------------------------------------------------------------------------------------
	/**
	 * Permet de savoir si un perso peut aller dans la case suivante d'apres son orientation
	 * @param p : perso a faire avancer
	 * @return : true =le perso peut avancer ; false = le perso ne peut pas avancer car il y a un ou deux murs
	 */
	public boolean mur(Personnage p)
	{
		return(mur(p,p.getOrientation()));
	}
//----------------------------------------------------------------------------------------
	/**
	 * Permet de savoir si un perso peut aller dans la case suivante d'apres son orientation et la valeur de n
	 * @param p : perso a faire avancer
	 * @param n : 1=a droite de l'orientation du perso ; 2=demi tours ; 3 a gauche
	 * @return : true =le perso peut avancer ; false = le perso ne peut pas avancer car il y a un ou deux murs
	 */
	public boolean mur(Personnage p, int n)
	{
		return (mur(p,E_Orientation.getNvlleOrientation(p.getOrientation(), n)));
	}
//----------------------------------------------------------------------------------------
	/**
	 * Permet de savoir s'y il peut aller dans la case suivante selon l'orientation o
	 * @param p : perso a faire avancer
	 * @param o : haut=case en haut du perso ..
	 * @return : true =le perso peut avancer ; false = le perso ne peut pas avancer car il y a un ou deux murs
	 */
	public boolean mur(Personnage p, E_Orientation o)
	{
		return  mur(p.getHauteur(),p.getLargeur(),o);
	}
//----------------------------------------------------------------------------------------
	/**
	 * Permet de savoir s'il a un mur entre la case (h,l) et la case suivante selon la direction o
	 * @param h : hauteur de la case a tester
	 * @param l : largeur de la case a tester
	 * @param o : diretion ou aller (haut=aller dans la case au dessus de la case a tester)
	 * @return : true =on peut aller d'une case a l'autre ; false = on ne peut pas ay aller car il y a un ou deux murs
	 */
	public boolean mur(int h, int l, E_Orientation o)
	{
		boolean m=true;
		//System.out.println("passe dans mur");
		Case c1=map.getCase(h,l);
		Case c2=null;
		switch(o)
		{
			case HAUT : c2=map.getCase(h-1, l); m=(c1.getHaut() || c2.getBas());break;
			case BAS: c2=map.getCase(h+1,l); m=(c1.getBas() || c2.getHaut());break;
			case GAUCHE : c2=map.getCase(h,l-1); m=(c1.getGauche() || c2.getDroite());break;
			case DROITE : c2=map.getCase(h, l+1); m=(c1.getDroite()|| c2.getGauche());break;
		}
		//System.out.println("h_case = " + c2.getHauteurCase() + " l_case = " + c2.getLargeurCase());
		//System.out.println("m vaut "+m);
		return m;
	}
//----------------------------------------------------------------------------------------
	/**
	 * verifier si on a presse une touche de controle de ce perso
	 * @param perso : perso a verifier
	 * @return : true = on a presser une touche de controle de ce perso ; false= on ne l'a pas fait
	 */
	public boolean derniere_touche_est_null(Personnage perso)
	{
		return perso.getDerniereTouchePressee_perso()==-1;
	}
//----------------------------------------------------------------------------------------
	/**
	 * verifier si on a presse une touche de deplacement (haut, bas, gauche ou droite) de ce perso
	 * @param perso : perso a tester
	 * @return : true = on a presser une touche de deplacement de ce perso ; false= on ne l'a pas fait
	 */
	public boolean derniere_touche_est_0_1_2_3(Personnage perso)
	{
		boolean b1 = (perso.getDerniereTouchePressee_perso()==0) || (perso.getDerniereTouchePressee_perso()==1);
		boolean b2 = (perso.getDerniereTouchePressee_perso()==2) || (perso.getDerniereTouchePressee_perso()==3) ;
		return (b1 || b2);
	}
//----------------------------------------------------------------------------------------
	/**
	 * verifier si on a presse une touche de deplacement de la piece de ce perso
	 * @param perso : perso a tester
	 * @return :  true = on a presser une touche de deplacement de la piece de ce perso ; false= on ne l'a pas fait
	 */
	public boolean derniere_touche_est_4_5(Personnage perso)
	{
		return (perso.getDerniereTouchePressee_perso()==4) || (perso.getDerniereTouchePressee_perso()==5);
	}
//----------------------------------------------------------------------------------------
	/**
	 * Le personnage voit un autre personnage sur N=3 cases dans la direction de son orientation
	 * @param p : perso qui regarde
	 * @return : true=il y a un perso ; flase il n'y en a pas
	 */
	public boolean voitPC (Personnage p)
	{
		return (voitXsurNcases(p,3,E_Contenu.PACMAN).getBool());
	}
//----------------------------------------------------------------------------------------
	/**
	 * regarde si le personnage voit un E_Contenu X dans la direction de son orientation sur N cases
	 * @param p : perso :qui regarde
	 * @param N : nb case ou il peut voir
	 * @param X : contenu a regarder
	 * @return : true : le personnage voit un E_Contenu X dans la direction de son orientation sur N cases
	 */	
	public Triple<Boolean, Integer, Integer> voitXsurNcases (Personnage p, int N, E_Contenu X )
	{
		boolean vue=false;
		int i=p.getHauteur();
		int j=p.getLargeur();
		int l=-1,h=-1,nb=0;
		E_Orientation o = p.getOrientation();
		//System.out.println("je suis un personnage et je regarde en/a " + o);
		while( nb<N && (!vue))
		{
			Case c1=map.getCase(i,j);
			Case c2;
			switch(o)
			{
				case HAUT :
					c2=map.getCase(i-1, j);
					//System.out.println("h_case : " + c2.getHauteurCase() + " l_case : " + c2.getLargeurCase());
					if (!c1.getHaut() && !c2.getBas())
					{
						if(c2.getContenu().getType().equals(X))
						{
							vue = true;	
						}
						i=i-1;
					}
					else
					{
						nb = N;
					}
					break;
				case BAS :
					c2=map.getCase(i+1, j);
					//System.out.println("h_case : " + c2.getHauteurCase() + " l_case : " + c2.getLargeurCase());
					if (!c1.getBas() && !c2.getHaut())
					{
						if(c2.getContenu().getType().equals(X))
						{
							vue = true;	
						}
						i=i+1;
					}
					else
					{
						nb = N;
					}
					break;
				case GAUCHE : 
					c2=map.getCase(i, j-1);
					//System.out.println("h_case : " + c2.getHauteurCase() + " l_case : " + c2.getLargeurCase());
					if (!c1.getGauche() && !c2.getDroite())
					{
						if(c2.getContenu().getType().equals(X))
						{
							vue = true;	
						}
						j=j-1;
					}
					else
					{
						nb = N;
					}
					break;
				case DROITE :
					c2=map.getCase(i, j+1);
					//System.out.println("h_case : " + c2.getHauteurCase() + " l_case : " + c2.getLargeurCase());
					if (!c1.getDroite() && !c2.getGauche())
					{
						if(c2.getContenu().getType().equals(X))
						{
							vue = true;	
						}
						j=j+1;
					}
					else
					{
						nb = N;
					}
					break;
			}
			if (vue)
			{
				h=i; l=j;	
			}
			nb++;
			
		}
		//System.out.println("vue vaut "+vue);
		return new Triple<Boolean,Integer,Integer>(vue,h,l);
	}
//----------------------------------------------------------------------------------------	
	// proximite renvoie true et les coordonées(h,l) d'un E_Contenu X sur un chemin de N cases du personnage de coordonnées(i,j) 
	// A FAIRE COORDONEES
	/**
	 * 
	 * @param i
	 * @param j
	 * @param compteur
	 * @param N : nb case ou il peut voir
	 * @param X : contenu a regarder
	 * @return true et les coordonees(h,l) d'un E_Contenu X sur un chemin de N cases du personnage de coordonnees(i,j)
	 */
	public Couple<Boolean, E_Orientation> proximiteXsurNcases (int i, int j, int compteur, int N, E_Contenu X)
	{
		Couple<Boolean,E_Orientation> couple = new Couple<Boolean, E_Orientation>(false, E_Orientation.HAUT);
		boolean proche_X = false;
		int nb =compteur;
		while (nb<N-1 && !(proche_X) )
		{
			Case c=map.getCase(i,j);
			Case c_h=map.getCase(Calculs.mod(i-1, N), j);
			Case c_b=map.getCase(Calculs.mod(i+1, N), j);
			Case c_g=map.getCase(i, Calculs.mod(j-1, N));
			Case c_d=map.getCase(i,Calculs.mod(j+1, N));
						
			if( !(c.getHaut()) && !(c_h.getBas()) && !(couple.getFirst()) ) // RECHERCHE EN HAUT
			{
				//System.out.println("Haut : Peut aller a la case (" + c_h.getHauteurCase() + ", " + c_h.getLargeurCase() + ")");
				if (c_h.getContenu().getType().equals(X))
				{
					proche_X = true;
					couple.setFirst(proche_X);
					couple.setSecond(E_Orientation.HAUT);
				}
				else
				{
					couple = proximiteXsurNcases(Calculs.mod(i-1, N), j, nb+1, N, X);
					//System.out.println(couple.getFirst());
					proche_X = proche_X || (couple.getFirst());
					couple.setSecond(E_Orientation.HAUT);
				}
				
			}
			if ( !(c.getBas()) && !(c_b.getHaut()) && !(couple.getFirst()))
			{
				//System.out.println("Bas : Peut aller a la case (" + c_b.getHauteurCase() + ", " + c_b.getLargeurCase() + ")");
				if (c_b.getContenu().getType().equals(X))
				{
					proche_X = true;
					couple.setFirst(proche_X);
					couple.setSecond(E_Orientation.BAS);
				}
				else
				{
					couple = proximiteXsurNcases(Calculs.mod(i+1, N), j, nb+1, N, X);
					//System.out.println(couple.getFirst());
					proche_X = proche_X || (couple.getFirst());
					couple.setSecond(E_Orientation.BAS);
				}
			}
			if ( !(c.getGauche()) && !(c_g.getDroite()) && !(couple.getFirst()))
			{
				//System.out.println("Gauche : Peut aller a la case (" + c_g.getHauteurCase() + ", " + c_g.getLargeurCase() + ")");
				if (c_g.getContenu().getType().equals(X))
				{			
					proche_X = true;
					couple.setFirst(proche_X);
					couple.setSecond(E_Orientation.GAUCHE);
				}
				else
				{
					couple = proximiteXsurNcases(i, Calculs.mod(j-1, N), nb+1, N, X);
					//System.out.println(couple.getFirst());
					proche_X = proche_X || (couple.getFirst());
					couple.setSecond(E_Orientation.GAUCHE);
				}
			}
			if ( !(c.getDroite()) && !(c_d.getGauche()) && !(couple.getFirst()))
			{
				//System.out.println("Droite : Peut aller a la case (" + c_d.getHauteurCase() + ", " + c_d.getLargeurCase() + ")");
				if (c_d.getContenu().getType().equals(X))
				{
					proche_X = true;
					couple.setFirst(proche_X);
					couple.setSecond(E_Orientation.DROITE);
				}
				else
				{
					couple = proximiteXsurNcases(i, Calculs.mod(j+1, N), nb+1, N, X);
					//System.out.println(couple.getFirst());
					proche_X = proche_X || (couple.getFirst());
					couple.setSecond(E_Orientation.DROITE);
				}
			}
			nb++;
		}
		return couple;
	}
//----------------------------------------------------------------------------------------
	/**
	 * verifie que une pastille ne se trouve pas a N=2 cases du personnage
	 * @param p : perso qui regarde
	 * @return : true=une pastille se trouve a N=2 cases du personnage
	 */
	public boolean prochePastille (Personnage p)
	{
		int N=2;
		boolean proche_pastille = (proximiteXsurNcases( p.getHauteur(), p.getLargeur(), 0, N, E_Contenu.PASTILLE).getFirst());	
		//System.out.println("proche_pastille vaut "+ proche_pastille);
		return proche_pastille;
	}
//----------------------------------------------------------------------------------------
	/**
	 * verifie si le personnage est apeure
	 * @param p :perso a verifier
	 * @return : true si c'est un fantome apeure, false s'il s'agit d'un fantome apeure ou d'un pacman
	 */
	public boolean estApeure (Personnage p)
	{
		return p.getApeure();
	}
//----------------------------------------------------------------------------------------
	/**
	 * un perso (fantome rose) demande a ses pairs si ils voient un pacMan
	 * @param p : perso qui demande
	 * @return : true si un fantome voit un pacman
	 */
	public boolean estRepere(Personnage p)
	{
		boolean resultat=false;
		// liste des fantomes
		for(int i=0; i<persos.length ; i++)
		{
			if(persos[i].getType().equals(E_Contenu.FANTOME))
			{
				resultat = resultat || voitPC(persos[i]);
			}
		}
		return resultat;
	}
	
/*	
----------------------------------------------------------------------------------------
	
----------------------------------------------------------------------------------------
			*** FIN DES TRANSITIONS ***
----------------------------------------------------------------------------------------
		
----------------------------------------------------------------------------------------
*/
	
	
//----------------------------------------------------------------------------------------
	/**
	 * Effectue l'action d un personnage associe ï¿½ son etat
	 * @param p : le perso sur lequel on va effectuer l'action
	 */
	public void action(Personnage p)
	{
		//System.out.println(p.getTableauEtat()[p.getEtat()]);
		String s = p.getTableauEtat()[p.getEtat()];
		switch(s)
		{
			case "attendre" : ; break;
			case "avancer" : if(!mur(p)){avancer(p);} break;
			case "fureter" : fureter(p); break;
			case "tourner_perso" : tourner_perso(p); break;
			case "tourner_piece" : tourner_piece(p); break;	
			case "chasser" : chasser(p);break;
			case "eloigner" : fuir(p);break;
			case "traquer" : traquer(p);break;
			case "fuir" : fuir(p);break;
			
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * Verifie si un personnage doit changer d'etat et si oui, change l'etat de ce perso
	 * @param p : perso dont on va changer l etat (ou pas ^^)
	 */
	public void transition(Personnage p)
	{
		String s;
		for(Transition<String, Integer> t : p.getTableauTransition()[p.getEtat()])
		{
			s = t.getAction();
			switch(s)
			{
			
				case "E" : 
					//System.out.println("E");
					p.setEtat(t.getEtatSortie());
					//System.out.println("nv Etat = "+t.getEtatSortie());
					break;
				case "mur" : 
					if(mur(p))
					{
						//System.out.println("change d'etat car mur");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "non_mur" : 
					if(!mur(p))
					{
						//System.out.println("change d'etat car non_mur");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "non_mur_et_derniere_touche_est_null" : 
					if(!mur(p) && derniere_touche_est_null(p))
					{
						//System.out.println("change d'etat car non_mur_et_derniere_touche_est_null");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "derniere_touche_est_null" : 
					if(derniere_touche_est_null(p))
					{
						//System.out.println("change d'etat car derniere_touche_est_null");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "derniere_touche_est_0_1_2_3" : 
					if(derniere_touche_est_0_1_2_3(p))
					{
						//System.out.println("change d'etat car derniere_touche_est_0_1_2_3");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "derniere_touche_est_4_5" : 
					if(derniere_touche_est_4_5(p))
					{
						//System.out.println("change d'etat car derniere_touche_est_4_5");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "non_apeure_et_non_voitPC" : 
					if(!estApeure(p)&&!voitPC(p))
					{
						//System.out.println("change d'etat car non_apeureETnon_voitPC");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "non_apeure_et_voitPC" : 
					if(!estApeure(p)&&voitPC(p))
					{
						//System.out.println("change d'etat car non_apeureETvoitPC");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "prochePastille" : 
					if(prochePastille(p))
					{
						//System.out.println("change d'etat car prochePastille");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "non_prochePastille" : 
					if(!prochePastille(p))
					{
						//System.out.println("change d'etat car non_prochePastille");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "voitPC" : 
					if(voitPC(p))
					{
						//System.out.println("change d'etat car voitPC");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "non_voitPC" : 
					if(!voitPC(p))
					{
						//System.out.println("change d'etat car non_voitPC");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());
					}
					break;
				case "apeure" : 
					if(estApeure(p))
					{
						//System.out.println("change d'etat car apeure");
						p.setEtat(t.getEtatSortie());
						//System.out.println("nv Etat = "+t.getEtatSortie());	
					}
					break;
				case "repere" :
					if(estRepere(p))
					{
						p.setEtat(t.getEtatSortie());
					}
					break;
				case "repere_et_non_apeure" : 
					if(estRepere(p)&&!estApeure(p))
					{
						p.setEtat(t.getEtatSortie());
					}
					break;
			}
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * Ordonnanceur : Fonction controlant l'execution des actions du jeu
	 */
	public void ordonnanceur()
	{
		//System.out.println("debut ordonnanceur");
		for(Personnage p : persos)
		{
			for(int i=0; i<p.getVitesse();i++)
			{
				ordrePerso.add(p);
			}
		}
		TimerTask task = new TimerTask() 
		{
			int depMap=0;
			int compt = 0;
			int depMapAAtteindre;
			@Override
			public void run() 
			{
				if(Jeu.versionDemo==2)
				{
					debPartie = 2;
				}
				if(debPartie == 2)
				{
					if(!pause)
					{
						if(!yaunmort)
						{
							if(unePastilleAEteMange)
							{
								compt++;
								if(compt >= 18){
									unePastilleAEteMange = false;
									for(Personnage p : persos)
									{
										if(p.getType().equals(E_Contenu.FANTOME))
										{
											Fantome fantome = (Fantome) p;
											fantome.setApeure(false);
										}
									}
									compt = 0;
								}
							}
							executer();
							//System.out.println("NbPacGum = " + nbPacGum);
							//map.affichageMap();
							//System.out.println("depMap = "+depMap);
							if(Jeu.versionDemo==1)
							{
								depMapAAtteindre=15;
							}
							else
							{
								depMapAAtteindre=60;
							}
							if(depMap==depMapAAtteindre)
							{
								if(Jeu.versionDemo==1)
								{
									Piece p = new Piece(0,0,3);
									remetreNourriture(p);
									map.glisserLigneDroite(0, p);
								}
								else if (Jeu.versionDemo==3)
								{
									bougerMap();
								}
								depMap=0;
							}
							depMap++;
						}
						else
						{
							compt++;
							if(compt >= 3)
							{
								pacmanRePop();
								compt = 0;
							}
							
						}
					}
				}
				else
				{
					debPartie++;
				}
			}
		};
		if(Jeu.versionDemo==2)
		{
			t.scheduleAtFixedRate(task, 0, 1000);
		}
		else
		{
			if(Jeu.versionDemo==3)
			{
				t.scheduleAtFixedRate(task, 0, 400);
			}else{
				t.scheduleAtFixedRate(task, 0, 300);
			}
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * Fonction permettant d'executer les actions du jeu
	 */
	public void executer()
	{
		//System.out.println("--------------------------------------");
		
		for (Personnage p : ordrePerso)
		{
			//System.out.println("--------------------------------------");
			//System.out.println("h = " + p.getHauteur() + " l  = " + p.getLargeur());
			//System.out.println("debut executer");
			action(p);
			//System.out.println("passe a transition");
			transition(p);
			//System.out.println("fin d' executer");
			
		}
		
	}
//----------------------------------------------------------------------------------------

	
}
