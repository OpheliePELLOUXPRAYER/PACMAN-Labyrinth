package jeu.Modeles;

import jeu.Coeur.*;

public class Map 
{
	private int hauteur_map;
	private int largeur_map;
	private Piece pieces[][];
	
//----------------------------------------------------------------------------------------
	/**
	 * Constructeur de map
	 * @param h : hauteur de la map
	 * @param l : largeur de la map
	 */
	public Map(int h, int l)
	{
		hauteur_map = h;
		largeur_map = l;
		pieces = new Piece[hauteur_map][largeur_map];
		
		for (int i = 0; i < hauteur_map; i++) 
		{
			for (int j = 0; j < largeur_map; j++) 
			{
				if(Jeu.versionDemo==2)
				{
					pieces[i][j] = new Piece(i,j,3);
				}
				else
				{
					pieces[i][j] = new Piece(i,j);
				}
			}
		}
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de la hauteur de la map
	 * @return la hauteur de la map
	 */
	public int getHauteurMap()
	{
		return(hauteur_map);
	}
//----------------------------------------------------------------------------------------
	/**
	 * Getteur de la largeur de la map
	 * @return la largeur de la map
	 */
	public int getLargeurMap()
	{
		return(largeur_map);
	}
//----------------------------------------------------------------------------------------
	public Case getCase(int h, int l)
	{	
		return (getPieceFromIndicesCase(h,l).getCase(h,l));
	}
//----------------------------------------------------------------------------------------
	public Piece getPieceFromIndicesCase(int h, int l)
	{
		return (pieces[getHauteurPieceFromHauteurCase(h)][getLargeurPieceFromLargeurCase(l)]);
	}
//----------------------------------------------------------------------------------------
	public int getHauteurPieceFromHauteurCase(int h)
	{
		int res=((h- Calculs.mod(h, 3))/3);
		return(Calculs.mod(res, hauteur_map));
	}
//----------------------------------------------------------------------------------------
	public int getLargeurPieceFromLargeurCase(int l)
	{
		int res=((l- Calculs.mod(l, 3))/3);
		return(Calculs.mod(res, largeur_map));
	}
//----------------------------------------------------------------------------------------
	public Piece getPieceFromIndicePiece(int h, int l)
	{
		h=Calculs.mod(h, hauteur_map);
		l=Calculs.mod(l, largeur_map);
		return(pieces[h][l]);
	}
	
//----------------------------------------------------------------------------------------
	public void glisserColonneHaut(int l, Piece p_a_rajouter)
	{
		System.out.println("");
		System.out.println("          GLISSER COLONNE HAUT : "+l);
		l=Calculs.mod(l, largeur_map);
		System.out.println("");
		System.out.println("          GLISSER COLONNE HAUT : "+l);
		getPieceFromIndicePiece(0,l).estEjecteDeLaMap();
		for(int h=0;h<hauteur_map-1;h++)
		{
			getPieceFromIndicePiece(h, l).prendLaValeurDeLaPiece(getPieceFromIndicePiece(h+1, l));
		}
		getPieceFromIndicePiece(hauteur_map-1, l).prendLaValeurDeLaPiece(p_a_rajouter);
		affichageMap();
	}
//----------------------------------------------------------------------------------------
	public void glisserColonneBas(int l, Piece p_a_rajouter)
	{
		System.out.println("");
		System.out.println("          GLISSER COLONNE BAS : "+l);
		l=Calculs.mod(l, largeur_map);
		System.out.println("");
		System.out.println("          GLISSER COLONNE BAS : "+l);
		getPieceFromIndicePiece(hauteur_map-1,l).estEjecteDeLaMap();
		for(int h=hauteur_map-1;h>0;h--)
		{
			getPieceFromIndicePiece(h, l).prendLaValeurDeLaPiece(getPieceFromIndicePiece(h-1, l));
		}
		getPieceFromIndicePiece(0, l).prendLaValeurDeLaPiece(p_a_rajouter);
		affichageMap();
	}	
//----------------------------------------------------------------------------------------
	public void glisserLigneGauche(int h, Piece p_a_rajouter)
	{
		System.out.println("");
		System.out.println("          GLISSER LIGNE GAUCHE : "+h);
		h=Calculs.mod(h, hauteur_map);
		System.out.println("");
		System.out.println("          GLISSER LIGNE GAUCHE : "+h);
		getPieceFromIndicePiece(h,0).estEjecteDeLaMap();
		for(int l=0;l<largeur_map-1;l++)
		{
			getPieceFromIndicePiece(h, l).prendLaValeurDeLaPiece(getPieceFromIndicePiece(h, l+1));
		}
		getPieceFromIndicePiece(h,largeur_map-1).prendLaValeurDeLaPiece(p_a_rajouter);
		affichageMap();
	}	
//----------------------------------------------------------------------------------------
	public void glisserLigneDroite(int h, Piece p_a_rajouter)
	{
		System.out.println("");
		System.out.println("          GLISSER LIGNE DROITE : "+h);
		h=Calculs.mod(h, hauteur_map);
		System.out.println("");
		System.out.println("          GLISSER LIGNE DROITE : "+h);
		getPieceFromIndicePiece(h,largeur_map-1).estEjecteDeLaMap();
		for(int l=largeur_map-1;l>0;l--)
		{
			getPieceFromIndicePiece(h, l).prendLaValeurDeLaPiece(getPieceFromIndicePiece(h, l-1));
		}
		getPieceFromIndicePiece(h,0).prendLaValeurDeLaPiece(p_a_rajouter);
		affichageMap();
	}	
//----------------------------------------------------------------------------------------
	public void rotationPieceDroite(Personnage p)
	{
		
	}
	
	
	
	
	//----------------------------------------------------------------------------------------

	public void affichageMap(){
		System.out.println("--------------- MAP -------------------");
		for (int i = 0; i < hauteur_map*3; i++) 
		{
			if(i%3==0)
			{
				System.out.print("----------------------------------------------------------------");
				System.out.println();
			}
			for (int j = 0; j < largeur_map*3; j++) 
			{
				if(j%3==0)
				{
					System.out.print("|");
				}
				getCase(i,j).affichageCase();
			}
			System.out.println();
		}
		System.out.println();
	}
}
