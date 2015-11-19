package jeu.Coeur;

/**
 * Classe Couple 
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 * @param <K>
 * @param <V>
 */
public class Couple<K,V> {

	private K first;
	private V second;
	
	public Couple(K first, V second) {
		this.first = first;
		this.second = second;
		
	}
	
	public K getFirst()
	{
		return first;
	}

	public void setFirst(K newFirst)
	{
		first = newFirst;
	}
	
	public V getSecond()
	{
		return second;
	}

	public void setSecond(V newSecond)
	{
		second = newSecond;
	}
}
