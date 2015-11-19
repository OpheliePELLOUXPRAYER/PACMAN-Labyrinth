open Parser;;

let extension=".png";;
(* **************************** Automate Pacman***********************************)

let transition0_etat0=(0,Mur,0);;
let transition1_etat0=(1, DerniereTouchePresseeEst0123, 2);;
let transition2_etat0=(2, DerniereTouchePresseeEst45, 3);;

let transition0_etat1 = (0, Mur, 0);;
let transition1_etat1 = (1, DerniereTouchePresseeEst0123 , 2);;
let transition2_etat1 = (2, NonMurEtDerniereToucheEstNull,1);;
let transition3_etat1 = (3, DerniereTouchePresseeEst45, 3);;

let transition0_etat2 =(0,Mur,0);;
let transition1_etat2 =(1, DerniereTouchePresseeEst45, 3);;
let transition2_etat2 =(2, DerniereToucheEstNull,1);;
let transition3_etat2 = (3, DerniereTouchePresseeEst0123 , 2);;

let transition0_etat3=(0,E,0);;
let transition1_etat3 =(1, DerniereTouchePresseeEst45, 3);;

let etat0 = (0,3,[Attendre], [transition0_etat0;transition1_etat0;transition2_etat0]);;

let etat1 = (1,4, [Avancer], [transition0_etat1;transition1_etat1;transition2_etat1;transition3_etat1]);;

let etat2 = (2,4,[Tourner_perso], [transition0_etat2;transition1_etat2;transition2_etat2;transition3_etat2]);;

let etat3 = (3,2,[Tourner_piece], [transition0_etat3;transition1_etat3]);;

let automate_pc = (Pacman,4,"pacman",extension,[Fleche_haut;Fleche_bas;Fleche_gauche;Fleche_droite;Deux_points;Point_exclamation],[etat0;etat1;etat2;etat3]);;

let automate_pc2 = (Pacman,4,"pacwoman",extension,[Touche_Z;Touche_S;Touche_Q;Touche_D;Touche_A;Touche_E],[etat0;etat1;etat2;etat3]);;

(* ****************************** Automate Fantome_Bleu*****************************)
let transition1_etat0_FB =(0, ProchePastille, 2);;
let transition3_etat0_FB = (1, VoitPC,3);;
let transition2_etat0_FB =(2,Apeure ,1);;

let transition1_etat1_FB =(0,NonApeureEtNonVoitPC , 0);;
let transition2_etat1_FB = (1, NonApeureEtVoitPC,3);;

let transition1_etat2_FB =(0,NonProchePastille,2);;
let transition2_etat2_FB = (1, Apeure,1);;

let transition1_etat3_FB =(0,NonVoitPC, 0);;
let transition2_etat3_FB =(1,ProchePastille,2);;
let transition3_etat3_FB = (2, Apeure,1);;

let etat0_FB = (0,3,[Fureter],[transition1_etat0_FB;transition2_etat0_FB;transition3_etat0_FB]);;
let etat1_FB = (1,2,[Fuir],[transition1_etat1_FB;transition2_etat1_FB]);;
let etat2_FB = (2,2,[Eloigner],[transition1_etat2_FB;transition2_etat2_FB]);;
let etat3_FB = (3,3,[Chasser],[transition1_etat3_FB;transition2_etat3_FB;transition3_etat3_FB]);;

let automate1 = (FantomeBleu,4,"FantomeBleu",extension,[],[etat0_FB;etat1_FB;etat2_FB;etat3_FB]);;

(* ****************************** Automate Fantome_Orange *****************************)
let transition1_etat0_FO =(0, VoitPC, 1);;
let transition2_etat0_FO =(1,Apeure ,2);;

let transition1_etat1_FO =(0,NonVoitPC,0);;
let transition2_etat1_FO =(1,Apeure, 2);;

let transition1_etat2_FO =(0,NonApeureEtNonVoitPC , 0);;
let transition2_etat2_FO = (1, NonApeureEtVoitPC,1);;


let etat0_FO = (0,2,[Fureter],[transition1_etat0_FO;transition2_etat0_FO]);;
let etat1_FO = (1,2,[Chasser],[transition1_etat1_FO;transition2_etat1_FO]);;
let etat2_FO = (2,2,[Fuir],[transition1_etat2_FO;transition2_etat2_FO]);;

let automate2 = (FantomeOrange,3,"FantomeOrange",extension,[],[etat0_FO;etat1_FO;etat2_FO]);;

(* ****************************** Automate Fantome_Rouge *****************************)
let transition1_etat0_FR =(0,VoitPC,2);;
let transition2_etat0_FR =(1, Apeure, 1);;

let transition1_etat1_FR =(0,NonApeureEtNonVoitPC, 0);;
let transition2_etat1_FR =(1,NonApeureEtVoitPC,2);;

let transition1_etat2_FR =(0,NonVoitPC,0);;
let transition2_etat2_FR = (1, Apeure,1);;


let etat0_FR = (0,2,[Traquer],[transition1_etat0_FR;transition2_etat0_FR]);;
let etat1_FR = (1,2,[Fuir],[transition1_etat1_FR;transition2_etat1_FR]);;
let etat2_FR = (2,2,[Chasser],[transition1_etat2_FR;transition2_etat2_FR]);;

let automate3 = (FantomeRouge,3,"FantomeRouge",extension,[],[etat0_FR;etat1_FR;etat2_FR]);;

(* ****************************** Automate Fantome_Rose*****************************)
let transition1_etat0_FROZ =(0, VoitPC, 1);;
let transition2_etat0_FROZ =(1,NonRepere ,2);;
let transition3_etat0_FROZ = (2, Apeure,3);;

let transition1_etat1_FROZ =(0,NonVoitPC, 0);;
let transition2_etat1_FROZ =(1,Apeure,3);;

let transition1_etat2_FROZ =(0,VoitPC,1);;
let transition3_etat2_FROZ = (1, NonRepere,0);;
let transition2_etat2_FROZ = (2, Apeure,3);;

let transition1_etat3_FROZ =(0,NonApeureEtNonVoitPC, 0);;
let transition2_etat3_FROZ =(1,RepereEtNonApeure,2);;
let transition3_etat3_FROZ = (2, NonApeureEtVoitPC,1);;

let etat0_FROZ = (0,3,[Fureter],[transition1_etat0_FROZ;transition2_etat0_FROZ;transition3_etat0_FROZ]);;
let etat1_FROZ = (1,2,[Chasser],[transition1_etat1_FROZ;transition2_etat1_FROZ]);;
let etat2_FROZ = (2,3,[Traquer],[transition1_etat2_FROZ;transition2_etat2_FROZ;transition3_etat2_FROZ]);;
let etat3_FROZ = (3,3,[Fuir],[transition1_etat3_FROZ;transition2_etat3_FROZ;transition3_etat3_FROZ]);;

let automate4 = (FantomeRose,4,"FantomeRose",extension,[],[etat0_FROZ;etat1_FROZ;etat2_FROZ;etat3_FROZ]);;

let monAutomate = (6,[automate_pc;automate_pc2;automate1;automate2;automate3;automate4]);;

ecriretout monAutomate;




