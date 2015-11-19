package jeu.Modeles;

import jeu.Coeur.*;
import jeu.Enum.E_Contenu;

public class Piece 
{
	private Case cases[][];
	private int hauteur;
	private int largeur;
	
//----------------------------------------------------------------------------------------
	/**
	 * Constructeur d'une piece : choisi aleatoirement une piece dans les modeles fournis
	 */
	public Piece(int h, int l)
	{
		hauteur=h;
		largeur=l;
		int n=Calculs.random(0, 3);
		creerPieceFromModele(n); // permet de generer aleatoirement un entier entre 0 et 3 compris (0 ou 1 ou 2 ou 3)
	}
//----------------------------------------------------------------------------------------
	/**
	 * Constructeur d'une piece : construit la piece choisis par le numero de modele
	 * @param modele : modele de la piece demande
	 */
	public Piece(int h, int l, int modele)
	{
		hauteur=h;
		largeur=l;
		creerPieceFromModele(modele);
	}
//----------------------------------------------------------------------------------------
	/**
	 * Fonction appele par les constructeurs : permet de construire une piece a partir d'un modele
	 * @param modele : modele de la piece demande
	 */
	public void creerPieceFromModele(int modele)
	{
		modele = Calculs.mod(modele,4);
		int hc=hauteur*3;
		int lc=largeur*3;
		int m_c[];
		switch (modele) 
		{
	        case 0: m_c = new int[]{0,14,1,
	        						7,7,7,
	        						8,7,9};
	                 break;
	        case 1: m_c = new int[]{4,14,5,
	        						14,14,14,
									8,14,9};
	                 break;
	        case 2: m_c = new int[]{0,7,14,
									9,3,9,
									1,1,4};
	                 break;
	        case 3: m_c = new int[]{8,14,9,
									1,12,0,
									5,14,4};
	                 break;
	        default: System.out.println("ERREUR : il n'est pas posible de rentrer dans ce cas");
	        m_c = new int[]{14,14,14,14,14,14,14,14,14};
	        		 break;
        }
		cases = new Case [][]
				{ 
				{(new Case(hc,lc,m_c[0])),(new Case(hc,(lc+1),m_c[1])),(new Case(hc,(lc+2),m_c[2]))},
				{(new Case((hc+1),lc,m_c[3])),(new Case((hc+1),(lc+1),m_c[4])),(new Case((hc+1),(lc+2),m_c[5]))},
				{(new Case((hc+2),lc,m_c[6])),(new Case((hc+2),(lc+1),m_c[7])),(new Case((hc+2),(lc+2),m_c[8]))}
				};
	}
//----------------------------------------------------------------------------------------
	public Case getCase(int h, int l)
	{
		h=Calculs.mod(h, 3);
		l=Calculs.mod(l, 3);
		return (cases[h][l]);
	}
//----------------------------------------------------------------------------------------
	public void prendLaValeurDeLaPiece(Piece val_piece_a_prendre)
	{
		for(int h=0;h<3;h++)
		{
			for(int l=0;l<3;l++)
			{
				getCase(h, l).prendLaValeurDeLaCase( val_piece_a_prendre.getCase(h, l));
			}
		}
	}
//----------------------------------------------------------------------------------------
	public void rajouterNourritureAleatoirement(Nourriture n)
	{
		int h=Calculs.random(0, 2);
		int l=Calculs.random(0, 2);
		Case c = cases[l][h];
		System.out.println("Piece a rajouter (case : h = " + h + " l = " +  l  +  " : ");
		affichagePiece();
		while(!c.getContenu().getType().equals(E_Contenu.LIBRE))
		{
			System.out.println("contenu pas libre");
			h=Calculs.random(0, 2);
			l=Calculs.random(0, 2);
			c = cases[l][h];
		}
		c.setContenuDuDessus(n);
	}
	
//----------------------------------------------------------------------------------------
	public void rotationPieceDroite()
	{
		Case[][] temp = new Case[3][3];
		for (int i = 0; i < temp.length; i++) 
		{
			for (int j = 0; j < temp.length; j++) 
			{
				temp[i][j] = new Case(2-j, i);
				temp[i][j].prendLaValeurDeLaCase(cases[2-j][i]);
			}
		}
		for (int i = 0; i < temp.length; i++) 
			{
				for (int j = 0; j < temp.length; j++) 
				{
					temp[i][j].tournerCaseADroite();
					cases[i][j].prendLaValeurDeLaCase(temp[i][j]);
				}
			}
	}
//----------------------------------------------------------------------------------------
	public void rotationPieceGauche()
	{
		Case[][] temp = new Case[3][3];

		for (int i = 0; i < temp.length; i++) 
		{
			for (int j = 0; j < temp.length; j++) 
			{
				temp[i][j] = new Case(j, 2-i);
				temp[i][j].prendLaValeurDeLaCase(cases[j][2-i]);
			}
		}
		for (int i = 0; i < temp.length; i++) 
			{
				for (int j = 0; j < temp.length; j++) 
				{
					temp[i][j].tournerCaseAGauche();
					cases[i][j].prendLaValeurDeLaCase(temp[i][j]);
				}
			}
	}
	
	public int getHauteur()
	{
		return hauteur;
	}
	
	public int getLargeur()
	{
		return largeur;
	}
	
	
	
	
	
//----------------------------------------------------------------------------------------
	public void estEjecteDeLaMap()
	{
		for(int h=0;h<3;h++)
		{
			for(int l=0;l<3;l++)
			{
				getCase(h, l).sortDeLaMap();
			}
		}
	}
//----------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------
	public void affichagePiece()
	{
		for(int i = 0; i<3;i++)
		{
			for(int j = 0; j<3;j++)
			{
				cases[i][j].affichageCase();
				System.out.print(" ");
			}
		}
	}
}
