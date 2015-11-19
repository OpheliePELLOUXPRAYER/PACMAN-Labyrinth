(*open String;;*)
let sortie = open_out "Automate.xml";;

type action = Attendre|Avancer|Chasser|Eloigner|Fuir|Fureter|Tourner_perso|Tourner_piece|Traquer;;
type condition  = E|Mur
|NonMur|NonDerniereToucheEstNull|NonMurEtDerniereToucheEstNull 
|ProchePastille|NonProchePastille|Apeure|NonApeureEtNonVoitPC|VoitPC|NonVoitPC|Manger|NonApeureEtVoitPC|NonProchePastilleEtNonVoitPC
|NonProchePastilleEtVoitPC|DerniereTouchePresseeEst0123|DerniereTouchePresseeEst45|Repere|NonRepere|DerniereToucheEstNull|RepereEtNonApeure ;;
        
let action_to_string a = match a with
|Avancer -> "avancer"
|Attendre->"attendre"
|Chasser->"chasser"
|Eloigner->"eloigner"
|Fuir -> "fuir"
|Fureter -> "fureter"
|Tourner_perso -> "tourner_perso"
|Tourner_piece -> "tourner_piece"
|Traquer -> "traquer";;

let condition_to_string c = match c with
|E -> "E"
|Mur -> "mur"
|NonMur -> "non_mur"
|NonDerniereToucheEstNull -> "non_derniere_touche_est_null"
|NonMurEtDerniereToucheEstNull  -> "non_mur_et_derniere_touche_est_null"
|ProchePastille ->"prochePastille"
|NonProchePastille ->"non_prochePastille"
|Apeure ->"apeure"
|NonApeureEtNonVoitPC->"non_apeure_et_non_voitPC"
|VoitPC->"voitPC"
|NonVoitPC->"non_voitPC"
|Manger ->"manger"
|NonApeureEtVoitPC -> "non_apeure_et_voitPC"
|NonProchePastilleEtNonVoitPC -> "non_prochePastille_et_non_voitPC"
|NonProchePastilleEtVoitPC -> "non_prochePastille_et_voitPC"
|DerniereTouchePresseeEst0123 -> "derniere_touche_est_0_1_2_3"
|DerniereTouchePresseeEst45 -> "derniere_touche_est_4_5"
|Repere ->"repere"
|NonRepere ->"non_repere"
|DerniereToucheEstNull ->"derniere_touche_est_nulle"
|RepereEtNonApeure ->"repere_et_non_apeure";;

let action act = 
	let valeur = act
	in 
	output_string sortie "\t\t\t<action>" ;
	output_string sortie (action_to_string (valeur));
	output_string sortie "</action>" ;
	output sortie "\n" 0 1 ;;

let rec liste_action p = match p with
|[]->()
|[t] -> action t
|t::q ->action t; liste_action q;;

type touche = Touche_Z|Touche_S|Touche_D|Touche_Q|Touche_E|Touche_A|Fleche_haut|Fleche_bas|Fleche_gauche|Fleche_droite|Point_exclamation|Deux_points|NULL
 
let touche t = match t with
|Touche_Z -> "z"
|Touche_S -> "s"
|Touche_D -> "d"
|Touche_Q -> "q"
|Touche_E -> "e"
|Touche_A -> "a"
|Fleche_haut -> "haut"
|Fleche_bas -> "bas"
|Fleche_gauche ->"gauche"
|Fleche_droite ->"droite"
|Point_exclamation ->"!"
|Deux_points -> ":"
|NULL -> "null";;

(*let test_condition valeur = if touche1(valeur)==haut then*) 
let touche1_t t=
	let valeur = t
	in
	output_string sortie"\t\t\t<haut>";
	output_string sortie (touche(valeur));
	output_string sortie "</haut>" ;
	output sortie "\n" 0 1;;
	
let touche2_t t=
	let valeur = t
	in
	output_string sortie"\t\t\t<bas>";
	output_string sortie (touche(valeur));
	output_string sortie "</bas>" ;
	output sortie "\n" 0 1;;

let touche3_t t=
	let valeur = t
	in
	output_string sortie"\t\t\t<gauche>";
	output_string sortie (touche(valeur));
	output_string sortie "</gauche>" ;
	output sortie "\n" 0 1;;

let touche4_t t=
	let valeur = t
	in
	output_string sortie"\t\t\t<droite>";
	output_string sortie (touche(valeur));
	output_string sortie "</droite>" ;
	output sortie "\n" 0 1;;

let touche5_t t=
	let valeur = t
	in
	output_string sortie"\t\t\t<rotation_gauche>";
	output_string sortie (touche(valeur));
	output_string sortie "</rotation_gauche>" ;
	output sortie "\n" 0 1 ;;

let touche6_t t=
	let valeur = t
	in
	output_string sortie"\t\t\t<rotation_droite>";
	output_string sortie (touche(valeur));
	output_string sortie "</rotation_droite>" ;
	output sortie "\n" 0 1;;

let liste_touche t = match t with
  |[] ->  
    touche1_t NULL;
    touche2_t NULL;
    touche3_t NULL;
    touche4_t NULL;
    touche5_t NULL;
    touche6_t NULL; 
  |[t1] -> 
    touche1_t t1;
    touche2_t NULL;
    touche3_t NULL;
    touche4_t NULL;
    touche5_t NULL;
    touche6_t NULL; 
  |[t1;t2] -> 
    touche1_t t1;
    touche2_t t2;
    touche3_t NULL;
    touche4_t NULL;
    touche5_t NULL;
    touche6_t NULL; 
  |[t1;t2;t3] -> 
    touche1_t t1;
    touche2_t t2;
    touche3_t t3;
    touche4_t NULL;
    touche5_t NULL;
    touche6_t NULL;
  |[t1;t2;t3;t4] -> 
    touche1_t t1;
    touche2_t t2;
    touche3_t t3;
    touche4_t t4;
    touche5_t NULL;
    touche6_t NULL;
  |[t1;t2;t3;t4;t5] -> 
    touche1_t t1;
    touche2_t t2;
    touche3_t t3;
    touche4_t t4;
    touche5_t t5;
    touche6_t NULL;
  |[t1;t2;t3;t4;t5;t6;] -> 
    touche1_t t1;
    touche2_t t2;
    touche3_t t3;
    touche4_t t4;
    touche5_t t5;
    touche6_t t6;;

let touches t=	
	let valeur = t
	in
	output_string sortie"\t\t<touches>\n";
	liste_touche valeur;
	output_string sortie "\t\t</touches>" ;
	output sortie "\n" 0 1 ;;

let etat_sortie sorti =
	let (valeur) = sorti
	in 
	output_string sortie "\t\t\t\t<etat_sortie>";
	output_string sortie (string_of_int (valeur));
	output_string sortie "</etat_sortie>";
	output_string sortie "\n" ;;

let condition cond =
	let (valeur) = cond 
	in 
	output_string sortie "\t\t\t\t<action>" ;
	output_string sortie (condition_to_string valeur);
	output_string sortie "</action>";
        output_string sortie "\n" ;;

let transition trans =
	let (id,cond, sorti) = trans
	in 
	output_string sortie "\t\t\t<transition id=\"" ;
	output_string sortie (string_of_int (id));
	output_string sortie "\">\n" ;
	condition cond;
	etat_sortie sorti;
	output_string sortie "\t\t\t</transition>" ;
        output sortie "\n" 0 1 ;;

let rec ecrirelistetransition p = match p with

|[]-> ()
|[t]-> transition t
|t::q-> transition t; ecrirelistetransition q ;;

let etat  e =
	let (id, nb_trans,act, trans) = e
	in 
	output_string sortie "\t\t<etat id=\"" ;
	output_string sortie (string_of_int (id));
	output_string sortie "\" nb_transition=\"";
	output_string sortie (string_of_int (nb_trans));
	output_string sortie "\">\n" ;
	liste_action act;
	ecrirelistetransition trans;
	output_string sortie "\t\t</etat>" ;
	output_string sortie "\n" ;;

let rec ecrirelisteetat p = match p with

|[]-> ()
|[t]-> etat t
|t::q-> etat t; ecrirelisteetat q ;;


type automate= Pacman|FantomeBleu|FantomeOrange|FantomeRose|FantomeRouge;;

let automate_to_string p = match p with

|Pacman -> "pacman"
|FantomeBleu ->"fantome_bleu"
|FantomeOrange ->"fantome_orange"
|FantomeRose->"fantome_rose"
|FantomeRouge->"fantome_rouge";;

let extension=".extension";;
let automate personnage =
	let (classe,nb_etat,image,extension, t, etat) = personnage 
	in
	output_string sortie "\t<automate classe=\"";
	output_string sortie (automate_to_string(classe));
	output_string sortie "\" nb_etat=\"";
	output_string sortie (string_of_int (nb_etat));
	output_string sortie "\" image=\"";
	output_string sortie (image);
	output_string sortie (extension);
	output_string sortie "\">\n" ;
	touches t;
        ecrirelisteetat etat ;
	output_string sortie "\t</automate>";
	output_string sortie "\n" ;;

let rec ecrirelisteautomate p = match p with

|[] -> ()
|[t] -> automate t
|t::q -> automate t; ecrirelisteautomate q;;

let creerAutomate auto =
	let (nb_automates,proto) = auto
	in
	output_string sortie "<MonAutomate";
	output_string sortie "\ nb_automates=\"";
	output_string sortie (string_of_int (nb_automates));
	output_string sortie "\">\n" ;
	ecrirelisteautomate proto;
	output_string sortie "</MonAutomate>" ;
	output_string sortie "\n" ;;

let version = "1.0";;
let encoding ="UTF-8";;

let ecriretout automate=
	let auto = automate 
	in 
	output_string sortie "<?xml version=\"";
	output_string sortie (version);
	output_string sortie "\" encoding=\"";
	output_string sortie encoding;
	output_string sortie "\"?>";
	output_string sortie "\n";
	creerAutomate auto;
	close_out sortie;;
