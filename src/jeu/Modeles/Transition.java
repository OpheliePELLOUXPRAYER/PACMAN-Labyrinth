package jeu.Modeles;

public class Transition<K, V >
{
	private K action;
	private V etat_sortie;
	
	public Transition (K action, V etat_sortie)
	{
		this.action=action;
		this.etat_sortie=etat_sortie;
	}
	
	public K getAction ()
	{
		return this.action;
	}
	
	public V getEtatSortie ()
	{
		return this.etat_sortie;
	}
}
