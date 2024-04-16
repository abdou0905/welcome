package welcome.ia;
import java.util.ArrayList;
import welcome.Jeu;
import welcome.Rue;
import welcome.utils.RandomSingleton;
import welcome.Travaux;
import java.util.HashMap;

//LE PLACEMENT DES STICKY NE FONCTIONNE PAS --> sticky placement
public class Strat106 extends Strat{

    public Strat106(){
    }
    
    @Override
    public String nomVille(){
        return "Dudzland";
    }
    
    @Override
    public String nomJoueur(){
        return "La_Dudz";
    }
    
    //Choisir parmi les 3 numéros dispos
    @Override
    public int choixCombinaison(Jeu j, int joueur){
        HashMap<Integer, Integer>  map = getMap(j, joueur);

        if(isTrivial(j)!=-1){ //1-2-14-15 is an option
            
            // System.out.println("choice number"+isTrivial(j));
            System.out.println("choosed is trivial "+((Travaux)j.numeros[isTrivial(j)].top()).getNumero());
            return isTrivial(j);

        } else if(isGarden(j)!=-1){ //A garden is an action
            
            System.out.println("choosed is garden "+((Travaux)j.numeros[isGarden(j)].top()).getNumero());
            return isGarden(j);
        
        }
        else if(isStickyNeighbor(j,map)!=-1){ //a sticky neighbour is possible
            
            System.out.println("sticky is chosed"+((Travaux)j.actions[isStickyNeighbor(j,map)].top()).getNumero());
            return isStickyNeighbor(j,map);
        
        } 
        // else if(){ //a fake neighbour is possible //TO DO. hints: use Map variable

        // }         
        else {
            System.out.println("Random is choosed...");
            return RandomSingleton.getInstance().nextInt(3);
        }
    }
    
    //Choisir de placer un numéro bis: jamais
    @Override
    public int choixBis(Jeu j, int joueur, ArrayList<Integer> placeValide){
        return 0; // choixBis is never use
    }
    
    //Choisir parmi les emplacements dispos
    @Override
    public int choixEmplacement(Jeu j, int joueur, int numero, ArrayList<Integer> placeValide){
        HashMap<Integer, Integer>  map = getMap(j, joueur);

        if(trivialPlacement(numero, placeValide) != -1){ 
            
            return trivialPlacement(numero, placeValide); //num is Trivial + Trivial placement is free

        } else if(goldenEightPlacement(numero,placeValide) != -1){ 
            
            return goldenEightPlacement(numero,placeValide); //the 3 golden 8
        
        }
        //  else if(stickyNeighborPlacement(numero, map) != -1){ //Sticky neighbor is possible !
        //     return stickyNeighborPlacement(numero, map);
        // } 
        // else if(){ //FakeNighbor is possible
            // return
        // }
         // else if(){ //Use interimaire
            // return
        // }
        else {
            return RandomSingleton.getInstance().nextInt(placeValide.size());
        }

    }
    
    //Choisir le même numéro que celui de la carte quand l'action est un intérimaire
    @Override
    public int choixNumero(Jeu j, int joueur, int numero){
        int res=-1;
        
        //A COMPLETER
        
        if((res<(numero-2) || res>(numero+2)) || res<0)
            res=Math.max(0, RandomSingleton.getInstance().nextInt(5) + numero - 2) ;
        return res;
    }
    
    //Valorise toujours une taille de lotissements de 5
    @Override
    public int valoriseLotissement(Jeu j, int joueur){        

        return 5;
    }
    
    //Met une barrière à une position aléatoire
    @Override
    public int choixBarriere(Jeu j, int joueur,  ArrayList<Integer> placeValide){
        
        if(placeValide.contains(5)){//Fence block A and B
            
            return placeValide.indexOf(5);
        
        } else if(placeValide.contains(105)){//Fence block C 
        
            return placeValide.indexOf(105);
        
        } else if(placeValide.contains(110)){//Fence block D
           
            return placeValide.indexOf(110);
        
        } else if(placeValide.contains(205)){//Fence block E
        
            return placeValide.indexOf(205);
        
        } else if(placeValide.contains(210)){//Fence block F
        
            return placeValide.indexOf(210);
        
        } else { //No fence 
        
            return 0;
        
        }

    }
    
    //Valide toujours un plan
    @Override
    public boolean validePlan(Jeu j, int joueur, int plan) {
        // boolean res = true;

        // System.out.println(plan);
        
        //A COMPLETER
        
        return true;
    }
    
    @Override
    public void resetStrat(){
    };

    /**
     * isTrivial method implementation
     * @return the linked choice if there is number 1/2/14/15, -1 otherwise
     */
    public int isTrivial(Jeu j){
        for (int i = 0; i<3; i++) {
            int num = ((Travaux) j.numeros[i].top()).getNumero();
            if (num == 1 || num == 2 || num == 14 || num == 15) { // There is trivial number
                return i;
            } 
        }
        //No trivial number in options
        return -1;
    }

    /**
     * isGarden method implementation
     * @return the right choice if garden, -1 otherwise
     */
    public int isGarden(Jeu j) {
        for(int i=0; i<3;i++){
            if(((Travaux)j.actions[i].top()).getAction() == 3){ //This is a Garden !
                return i;
            }
        }
        //No garden in options
        return -1;
    }
    
    /**
     * isStickyNeighbor method implementation
     * 
     * @return the right choice for sticky neighbor, -1 otherwise
     */
    public int isStickyNeighbor(Jeu j, HashMap<Integer, Integer> map){

        for (int i = 0; i<3; i++) { //Going accross the choiced numbers

            int num = ((Travaux) j.numeros[i].top()).getNumero();
            
            //Going accross the map
            for (Integer cle : map.keySet()) {
                if(map.get(cle) != -1) { //Compare a non free house
                    int diff = num - map.get(cle);
                
                    if(cle == 0 || cle == 100 || cle == 200){ //Left end --> only right neighboor
                        if(diff == 1 && map.get(cle+1)==-1){//Greatter and right space is free
                            return i; //Right Sticky neighbor
                        }
                    } else if(cle==9 ||cle==110 ||cle==211){ //Right end --> only left neighboor
                        if(diff == -1 && map.get(cle-1)==-1){ //Smaller and left space is free
                            return i; //Left Sitcky neighbor
                        }
                    } else { //Otherwise, right and left neighboor
                        if(diff == -1 && map.get(cle-1)==-1){ //Smaller and left space is free
                            return i; //Left Sitcky neighbor
                        } else if(diff == 1 && map.get(cle+1)==-1){//Gretter and right space is free
                            return i; //Right Sticky neighbor
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    /**
     * trivialPlacement method implemenation
     * Place a trivial number if free space
     * 
     * @return the placement to choose if conditions are reunited, -1 otherwise
     */

    public int trivialPlacement(int numero, ArrayList<Integer> placeValide){

        int[] dataLeftEnd= {0,100,200};
        int[] dataRightEnd= {9,110,211};

        if(numero == 1 || numero == 2 || numero ==14 || numero == 15){
            // System.out.println("lets place a trivial number");
            boolean isMin = numero == 1 || numero == 2;

            if(isMin) {//1 or 2
                for(int i = 0; i<dataLeftEnd.length; i++) {
                    if(placeValide.contains(dataLeftEnd[i])){
                        return placeValide.indexOf(dataLeftEnd[i]);
                    }
                }
            } else {//14 or 15
                for(int i = 0; i<dataRightEnd.length; i++) {
                    if(placeValide.contains(dataRightEnd[i])){
                        return placeValide.indexOf(dataRightEnd[i]);
                    } 
                }
            }
        }
        return -1;
    }

    /**
     * goldenEightPlacement method implementation
     * if number is 8 and golden 8 placements are free
     * 
     * @return the placement to choose if conditions are reunited, -1 otherwise 
     */
    public int goldenEightPlacement(int numero, ArrayList<Integer> placeValide){
    
        int[] dataGoldenEight = {4, 105, 206};
        
        if(numero == 8){
            for(int i = 0; i<dataGoldenEight.length; i++) {
                if(placeValide.contains(dataGoldenEight[i])){
                    return placeValide.indexOf(dataGoldenEight[i]);
                }
            }
        }

        return -1;//No golden 8 position free
    }


    public int stickyNeighborPlacement(int numero,HashMap<Integer, Integer> map){

        //REFAIRE EN PARCOURANT LES 6 DIFFERENTS BLOCKS DE 5 et avec map

        if(stickyStreet(0,4,numero,map) != -1){

            return stickyStreet(0,4,numero,map);
        
        } else if(stickyStreet(5,9,numero,map) != -1){
        
            return stickyStreet(5,9,numero,map);
        
        } else if(stickyStreet(100,104,numero,map) != -1){
        
            return stickyStreet(100,104,numero,map);
        
        } else if(stickyStreet(105,109,numero,map) != -1){
        
            return stickyStreet(105,109,numero,map);
        
        } else if(stickyStreet(200,204,numero,map) != -1){
        
            return stickyStreet(200,204,numero,map);
        
        } else if(stickyStreet(205,209,numero,map) != -1){
        
            return stickyStreet(205,209,numero,map);
        
        } else {
        
            return -1;
        
        }

        // System.out.println("blockA");
        // for (int i = 0; i <= 4; i++) {
        //     int diff = numero-map.get(i);
        //     if(diff==1 || diff==-1){
        //         return i;
        //     }
        //     // System.out.println(i + ": " + map.get(i));
        // }
                
    }

    //DISFONCTIONNEMENT ICI 
    public int stickyStreet(int min, int max, int numero, HashMap<Integer, Integer> map){
        for (int i = min; i <= max; i++) {
            if(map.get(i) != -1) {
                int diff = numero-map.get(i);
    
                if(map.get(i) == min){ //Left end of line
                    if (diff == 1){ //place on the right 
                        return i;
                    }
                } else if(map.get(i) == max){ //Right end of line
                    if(diff == -1){ //place on the left
                        return i;
                    }
                } else if(diff == 1 || diff == -1) {
                    return i;
                }
            }
        } 
        return -1;
        
    }
    
    /**
     * getMap method implementation
     * 
     * @return "house number": "number" (-1 if empty)
     */
    public HashMap<Integer, Integer>  getMap(Jeu j, int joueur){
        
        HashMap<Integer, Integer> placeValide = new HashMap<>();
        //Gettering all streets
        Rue rue1 = j.joueurs[joueur].ville.rues[0];
        Rue rue2 = j.joueurs[joueur].ville.rues[1];
        Rue rue3 = j.joueurs[joueur].ville.rues[2];
        Rue[] rue={rue1,rue2,rue3};
        
        //Building the Map
        for(int i = 0; i < 3; i++){ //Loop on 3 streets
            for(int k = 0; k <rue[i].maisons.length; k++) { //Loop on street's houses
                if(i == 0){ //1st street: from 0 to 9
                    placeValide.put(rue[i].maisons[k].position, rue[i].maisons[k].numero);
                } else if(i == 1){ //2nd street: from 100 to 110
                    placeValide.put(rue[i].maisons[k].position+100, rue[i].maisons[k].numero);
                } else if(i == 2){ //3rd street: from 200 to 211
                    placeValide.put(rue[i].maisons[k].position+200, rue[i].maisons[k].numero);
                }
            }
        }

        //Print all houses and number (-1) if empty
        // for (Integer cle : placeValide.keySet()) {
        //     int valeur = placeValide.get(cle);
        //     System.out.println("Maison : " + cle + ", Numero : " + valeur);
        // }

        return placeValide;
    }
    
}




    // /**OLD VERSION
    //  * stickyNeighborPlacement method implementation
    //  * if a number follow perfectly a placed house
    //  * 
    //  * @return the placement of the sticky neighboor, -1 otherwise
    //  */
    // public int stickyNeighborPlacement(int numero, ArrayList<Integer> placeValide){
        // for (int i = 0; i < placeValide.size(); i++) { //going throw all the free spaces
        //     int diff = numero - placeValide.get(i);

        //     if(placeValide.get(i) == 0 || placeValide.get(i) == 100 || placeValide.get(i) == 200){ //Left end of line
        //         if (diff == 1){ //place on the right 
        //             return i;
        //         }
        //     } else if(placeValide.get(i) == 9 || placeValide.get(i) == 110 || placeValide.get(i) == 2011){ //Right end of line
        //         if(diff == -1){ //place on the left
        //             return i;
        //         }
        //     } else if(diff == 1 || diff == -1) {
        //         return i;
        //     }
        // }
    // }