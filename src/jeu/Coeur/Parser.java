package jeu.Coeur;
import java.io.File;

import java.io.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import java.util.Iterator;
import java.util.List;

/**
 * Parser Java
 * @author BERTRAND-DALECHAMP Adèle & DARRIGOL Marie & KLIPFFEL Tararaina & MULAC Ambre & PELLOUX-PRAYER Ophélie & SAMBE Ndeye Arame
 * @version 1
 * @date 22/06/2014
 * @allrightreserved
 */
public class Parser
{
	private Element racine;
	private int nb_automates;
	

	public Parser(String nom_fichier)
	{
		
		Document document=null;
	    SAXBuilder sxb = new SAXBuilder();
	     // Creation du fichier lecture     
	     try
	     {
	    	 //On cree un nouveau document JDOM avec en argument le fichier XML
	    	 //System.out.println(System.getProperty("user.dir")+"/src/prototype1/"+ nom_fichier);
	    	 document = sxb.build(new File(System.getProperty("user.dir")+"/src/jeu/Xml/"+ nom_fichier));	    	
	      }
	      catch(Exception e){}
	      //Recuperation de la racine du document
	     this.racine=document.getRootElement();
	     //System.out.println(this.racine.getAttributeValue("nb_automates"));
	     this.nb_automates=Integer.parseInt(this.racine.getAttributeValue("nb_automates"));
	}
	

  public Element getRacine() {
	   return this.racine;
   }


 public int getNbAutomates() {
	   return this.nb_automates;
  }
   
}
