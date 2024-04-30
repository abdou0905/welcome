package welcome.ia;
import java.util.ArrayList;
import welcome.Jeu;
import welcome.Lotissement;
import welcome.Rue;
import welcome.utils.RandomSingleton;
import welcome.Travaux;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Strat106 extends Strat{

    public Strat106(){
    
    }
    
    @Override
    public String nomVille(){
        return "La_Soustas";
    }
    
    @Override
    public String nomJoueur(){
        return "Laura_DUNIAU";
    }
    
    //Choose between the 3 combo
    @Override
    public int choixCombinaison(Jeu j, int joueur){

        HashMap<Integer, Integer>  map = getMap(j, joueur);

        if(isTrivial(j)!=-1 && isBuildable(map,((Travaux)j.numeros[isTrivial(j)].top()).getNumero())){ //1-2-14-15 is an option && trivialIsFree(isTrivial(j),map)
            return isTrivial(j);

        } 
        else if(isGarden(j)!=-1 && isBuildable(map, ((Travaux)j.numeros[isGarden(j)].top()).getNumero())){ // && isCompaptible(isGarden(j),map)
            return isGarden(j);
        }
        else if(isStickyNeighbor(j,map)!=-1){ //a sticky neighbour is possible
            return isStickyNeighbor(j,map);
        } 
        else if(isBarriere(j) != -1 && isBuildable(map, ((Travaux)j.numeros[isBarriere(j)].top()).getNumero())){ // is Barriere
            
            return isBarriere(j);
        }
        else if (isValorisation(j) != -1 && isBuildable(map, ((Travaux)j.numeros[isValorisation(j)].top()).getNumero())){ //is Valorisation
            return isValorisation(j);
        }  
        else if(isIntermaire(j, map) != -1){ //Is interimaire
            return isIntermaire(j, map);
        }
        else {
            return RandomSingleton.getInstance().nextInt(3);
        }
    }

    //Never choose to do a Bis Number
    @Override
    public int choixBis(Jeu j, int joueur, ArrayList<Integer> placeValide){
        return 0; // choixBis is never use
    }
    
    //Chose in the available placement
    @Override
    public int choixEmplacement(Jeu j, int joueur, int numero, ArrayList<Integer> placeValide){
        HashMap<Integer, Integer>  map = getMap(j, joueur);

        if(trivialPlacement(numero, placeValide) != -1){ 
            return trivialPlacement(numero, placeValide); //num is Trivial and placement is free
        } 
        else if(goldenEightPlacement(numero,placeValide) != -1){
            return goldenEightPlacement(numero,placeValide); //the 3 golden 8 and placement is free
        } 
        else if(stickyNeighborPlacement(numero, map, placeValide) != -1){ //Sticky neighbor is possible !
            return stickyNeighborPlacement(numero, map, placeValide);
        } 
        else if(optimPlacement(numero, placeValide, map) != -1){
            return optimPlacement(numero,placeValide,map);
        }
        else{ 
            return RandomSingleton.getInstance().nextInt(placeValide.size());
        }
    }
    
    //Choose the best and available placement
    @Override
    public int choixNumero(Jeu j, int joueur, int numero){
        HashMap<Integer, Integer>  map = getMap(j, joueur);

        for(int k=-2; k<3; k++){
            if(numero +k>=1 && numero +k <= 15 && isBuildable(map, (numero+k))){
                return numero+k;
            }
        }
        return numero;
    }
    
    //Priority to size 5, 1 and 2
    @Override
    public int valoriseLotissement(Jeu j, int joueur){    
        if(j.joueurs[0].ville.avancementPrixLotissement[4]!=4){
            return 5;
        } else if (j.joueurs[0].ville.avancementPrixLotissement[0]!=1){
            return 1;
        }
        return 2;
    }
    
    //Fence for block of 5
    @Override
    public int choixBarriere(Jeu j, int joueur,  ArrayList<Integer> placeValide){
    
        if(placeValide.contains(205)){
            
            return placeValide.indexOf(205);
        
        } else if(placeValide.contains(210)){
        
            return placeValide.indexOf(210);
        
        } else if(placeValide.contains(105)){
           
            return placeValide.indexOf(105);
        
        } else if(placeValide.contains(110)){
        
            return placeValide.indexOf(110);
        
        } else if(placeValide.contains(5)){
        
            return placeValide.indexOf(5);
        
        } else { //No fence
        
            return 0;
        
        }

    }
    
    //Always valid a plan
    @Override
    public boolean validePlan(Jeu j, int joueur, int plan) {
        
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
     * isBarriere method implementation
     * @return the combo number containning a Barriere, -1 if no barriere in combo options
     */
    public int isBarriere(Jeu j){
        for(int i=0; i<3;i++){
            if(((Travaux)j.actions[i].top()).getAction() == 5){ //This is a Barriere !
                return i;
            }
        }
        //No Barriere in options
        return -1;
    }

    /**
     * isValorisation method implementation
     * @return the combo number containning a valorisation, -1 if no valorisation in combo options
     */
    public int isValorisation(Jeu j){
        for(int i=0; i<3;i++){
            if(((Travaux)j.actions[i].top()).getAction() == 4){ //This is a valorisation !
                return i;
            }
        }
        //No valorisation in options
        return -1;
    }

    /**
     * isIntermaire method implementation
     * @return the combo number containning a Interimaire, -1 if no Interimaire in combo options
     */
    public int isIntermaire(Jeu j, HashMap<Integer,Integer> map){
        for(int i=0; i<3;i++){
            if(((Travaux)j.actions[i].top()).getAction() == 1){ //This is a Interimaire !
                for(int k=-2; k<3; k++){
                    if(isBuildable(map, ((Travaux)j.numeros[i].top()).getNumero()+k)){
                        return i;
                    }
                }
            }
        }
        //No interimaire in options
        return -1;
    }

    /**
     * isBuildable method implementation
     * @param numero we want to evaluate
     * @return true if numero is buildable in the city 
     */
    public boolean isBuildable(HashMap<Integer,Integer> map, int numero){
        
        boolean street1 = true;
        boolean street2 = true;
        boolean street3 = true;
        
        for (int i = 0; i < 10; i++) { //street 1
            if (map.containsKey(i) && map.get(i) !=-1) {
                int value = map.get(i); 
                if(numero == value){ //IsAlready Test
                    street1 =false;
                    break;
                }
                if(i!=9){ //In Between Test
                    int valueNext=map.get(i+1);
                    if(numero > value && numero < valueNext && valueNext!=-1){
                        street1 = false;
                        break;
                    }
                }
                if(i==0 && numero <map.get(i)){ //Left Test
                    street1 = false;
                    break;
                }
                if(i==9 && numero > map.get(i)){ //Right Test
                    street1 = false;
                    break;
                }
            }
        }

        for (int i = 100; i < 111; i++) { //street 2
            if (map.containsKey(i)) {
                int value = map.get(i); 
                if(numero == value){ //IsAlready Test
                    street2 =false;
                    break;
                }
                if(i!=110){ //In Between Test 
                    int valueNext=map.get(i+1);
                    if(numero > value && numero < valueNext && valueNext!=-1){
                        street2 = false;
                        break;
                    }
                }
                if(i==100 && numero <map.get(i)){ //Left Test
                    street2 = false;
                    break;
                }
                if(i==110 && numero > map.get(i)){ //Right Test 
                    street2 = false;
                    break;
                }
            }
        }

        for (int i = 200; i < 212; i++) { //street 3
            if (map.containsKey(i)) {
                int value = map.get(i); 
                if(numero == value){ //IsAlready Test
                    street3 =false;
                    break;
                }
                if(i!=211){ //In Between Test
                    int valueNext=map.get(i+1);
                    if(numero > value && numero < valueNext && valueNext!=-1){
                        street3 = false;
                        break;
                    }
                }
                if(i==200 && numero <map.get(i)){ //Left Test
                    street3 = false;
                    break;
                }
                if(i==211 && numero > map.get(i)){ //Right Test
                    street3 = false;
                    break;
                }
            }
        }

        //Final return
        if(!street1 && !street2 && !street3){ //IS NOT 
            return false;
        } else { // IS BUILDABLE
            return true;
        }
    }
    
    /**
     * trivialPlacement method implemenation
     * Place a trivial number if free space
     * 
     * @return the placement to choose if conditions are reunited, -1 otherwise
     */

    public int trivialPlacement(int numero, ArrayList<Integer> placeValide){

        int[] dataLeftEnd= {200,100,0};
        int[] dataRightEnd= {211,110,9};

        if(numero == 1 || numero == 2 || numero ==14 || numero == 15){
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
    
        int[] dataGoldenEight = {206, 105, 4};
        
        if(numero == 8){
            for(int i = 0; i<dataGoldenEight.length; i++) {
                if(placeValide.contains(dataGoldenEight[i])){
                    return placeValide.indexOf(dataGoldenEight[i]);
                }
            }
        }

        return -1;//No golden 8 position free
    }

    /**
     * stickyNeighborPlacement method implementation
     * @param numero to place
     * @param map 
     * @param placeValide
     * @return the sticky placement for our numero
     */
    public int stickyNeighborPlacement(int numero, HashMap<Integer, Integer> map, ArrayList<Integer> placeValide){
        if(stickyStreet(200,204,numero,map,placeValide) != -1){
            return stickyStreet(200,204,numero,map, placeValide);
        } else if(stickyStreet(205,209,numero,map,placeValide) != -1){
            return stickyStreet(205,209,numero,map,placeValide);
        } else if(stickyStreet(100,104,numero,map,placeValide) != -1){
            return stickyStreet(100,104,numero,map,placeValide);
        } else if(stickyStreet(105,109,numero,map,placeValide) != -1){
            return stickyStreet(105,109,numero,map,placeValide);
        } else if(stickyStreet(0,4,numero,map,placeValide) != -1){
            return stickyStreet(0,4,numero,map,placeValide);
        } else if(stickyStreet(5,9,numero,map,placeValide) != -1){
            return stickyStreet(5,9,numero,map,placeValide);
        } else {
            return -1;
        }       
    }
    
    /**
     * stickyStreet methode implementation
     * @param min beging of block
     * @param max end of block
     * @param numero to place
     * @param map total house and number
     * @param placeValide available space
     * @return  the placement to make
     */
    public int stickyStreet(int min, int max, int numero, HashMap<Integer, Integer> map,ArrayList<Integer> placeValide){
        for (int i = min; i <= max; i++) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int diff = numero-map.get(i);
                if(placeValide.contains(entry.getValue()) && diff==1 || diff==-1 ){ //If no free space 
                    if(i == min){ //Left end of line
                        if (diff == 1){ //place on the right 
                            return placeValide.indexOf(i+1);
                        }
                    } else if(i == max){ //Right end of line
                        if(diff == -1){ //place on the left
                            return placeValide.indexOf(i-1); 
                        }
                    } else {
                        if(diff == 1){ //place on the right 
                            return placeValide.indexOf(i+1); 
                        } else if(diff == -1) { //place on the left
                            return placeValide.indexOf(i-1); 
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    /**
     * optimPlacement method implementation
     * @return the optim placement for numero
     */
    public int optimPlacement(int numero, ArrayList<Integer> placeValide, HashMap<Integer, Integer> map) {
        if(numero ==2 || numero == 1){
            if(placeValide.contains(200)){
                return placeValide.indexOf(200);
            } else if(placeValide.contains(100)){
                return placeValide.indexOf(100);
            } else if(placeValide.contains(0)){
                return placeValide.indexOf(0);
            }
        } else if (numero ==2 || numero == 3 ){
            if(placeValide.contains(201)){
                return placeValide.indexOf(201);
            } else if(placeValide.contains(101)){
                return placeValide.indexOf(101);
            } else if(placeValide.contains(1)){
                return placeValide.indexOf(1);
            }
        } else if(numero == 4 || numero == 5 ){
            if(placeValide.contains(203)){
                return placeValide.indexOf(203);
            } else if(placeValide.contains(102)){
                return placeValide.indexOf(102);
            } else if(placeValide.contains(2)){
                return placeValide.indexOf(2);
            }
        } else if(numero == 3 || numero ==4 && placeValide.contains(202)){
            return placeValide.indexOf(202);
        } else if(numero == 5 || numero ==6){
            if(placeValide.contains(204)){
                return placeValide.indexOf(204);
            } else if(placeValide.contains(103)){
                return placeValide.indexOf(103);
            }
        } else if(numero == 6 || numero ==7){
            if(placeValide.contains(205)){
                return placeValide.indexOf(205);
            } else if(placeValide.contains(104)){
                return placeValide.indexOf(104);
            } else if(placeValide.contains(3)){
                return placeValide.indexOf(3);
            }
        } else if(numero ==8){
            if(placeValide.contains(206)){
                return placeValide.indexOf(206);
            } else if(placeValide.contains(105)){
                return placeValide.indexOf(105);
            } else if(placeValide.contains(4)){
                return placeValide.indexOf(4);
            }
        } else if(numero ==9 || numero ==10){
            if(placeValide.contains(207)){
                return placeValide.indexOf(207);
            } else if(placeValide.contains(106)){
                return placeValide.indexOf(106);
            } else if(placeValide.contains(5)){
                return placeValide.indexOf(5);
            }
        } else if(numero ==10 || numero ==11){
            if(placeValide.contains(208)){
                return placeValide.indexOf(208);
            } else if(placeValide.contains(107)){
                return placeValide.indexOf(107);
            } else if(placeValide.contains(6)){
                return placeValide.indexOf(6);
            }
        } else if(numero ==11 || numero ==12 || numero == 10){
            if(placeValide.contains(209)){
                return placeValide.indexOf(209);
            } else if(placeValide.contains(108)){
                return placeValide.indexOf(108);
            } else if(placeValide.contains(7)){
                return placeValide.indexOf(7);
            }
        } else if(numero ==14 || numero == 15){
            if(placeValide.contains(110)){
                return placeValide.indexOf(110);
            } else if(placeValide.contains(211)){
                return placeValide.indexOf(211);
            } else if(placeValide.contains(9)){
                return placeValide.indexOf(9);
            }
        }
        else if(numero ==13 || numero ==14){
            if(placeValide.contains(109)){
                return placeValide.indexOf(109);
            } else if(placeValide.contains(210)){
                return placeValide.indexOf(210);
            } else if(placeValide.contains(8)){
                return placeValide.indexOf(8);
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
        return placeValide;
    }   
}