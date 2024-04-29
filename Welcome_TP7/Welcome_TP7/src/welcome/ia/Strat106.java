package welcome.ia;
import java.util.ArrayList;
import welcome.Jeu;
import welcome.Rue;
import welcome.utils.RandomSingleton;
import welcome.Travaux;
import java.util.HashMap;
import java.util.Map;

public class Strat106 extends Strat{

    public static int eight =0;
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
    
    //Choisir parmi les 3 numÃ©ros dispos
    @Override
    public int choixCombinaison(Jeu j, int joueur){


        // j.joueurs[joueur].ville.lotissements.; parcourrir les lotissements pour voir s'ils sont pleins

        HashMap<Integer, Integer>  map = getMap(j, joueur);
        System.out.println("les choix sont");
        for(int i=0; i<3;i++){
            
            System.out.println(((Travaux) j.numeros[i].top()).getNumero()+"avec action "+((Travaux) j.actions[i].top()).getAction());
        }

        // if(isTrivial(j)!=-1 && isBuildable(map,((Travaux)j.numeros[isTrivial(j)].top()).getNumero())){ //1-2-14-15 is an option && trivialIsFree(isTrivial(j),map)
        if(isTrivial(j)!=-1 && isBuildable(map,((Travaux)j.numeros[isTrivial(j)].top()).getNumero())){ //1-2-14-15 is an option && trivialIsFree(isTrivial(j),map)
           
            // System.out.println("choice number"+isTrivial(j));
            System.out.println("choosed is trivial "+((Travaux)j.numeros[isTrivial(j)].top()).getNumero());
            return isTrivial(j);

        } 
        
        if(isGarden(j)!=-1 && isBuildable(map, ((Travaux)j.numeros[isGarden(j)].top()).getNumero())){ // && isCompaptible(isGarden(j),map)
            System.out.println("choosed is garden "+((Travaux)j.numeros[isGarden(j)].top()).getNumero());
            return isGarden(j);
        }

        else if(isStickyNeighbor(j,map)!=-1){ //a sticky neighbour is possible
            
            System.out.println("sticky is chosed"+((Travaux)j.numeros[isStickyNeighbor(j,map)].top()).getNumero());
            return isStickyNeighbor(j,map);
            
        } 

        else if(isBarriere(j) != -1 && isBuildable(map, ((Travaux)j.numeros[isBarriere(j)].top()).getNumero())){//ajouter is comptaptible? 
            
            return isBarriere(j);
        }

        else if (isValorisation(j) != -1 && isBuildable(map, ((Travaux)j.numeros[isValorisation(j)].top()).getNumero())){ //ajouter is comptaptible? 
            return isValorisation(j);
        }  
        
        else if(isIntermaire(j, map) != -1){
            return isIntermaire(j, map);
        }
    
        //faire un if le numero fait remplir un lotissement
        else {
            System.out.println("Random is choosed...");
            return RandomSingleton.getInstance().nextInt(3);
        }
        
            
        
    }
    //return true if a trivial place is free
    // public boolean trivialIsFree(int numero,  HashMap<Integer, Integer> map){
    //     if(numero == 1){
    //         if(map.get(200)==-1 ||map.get(100)==-1 ||map.get(0)==-1){
    //             return true;
    //         }
    //     } else if(numero ==15){
    //         if(map.get(211)==-1 ||map.get(110)==-1 ||map.get(9)==-1){
    //             return true;
    //         }
    //     } else if(numero == 2){
    //         if(map.get(200)==-1 ||map.get(100)==-1 ||map.get(0)==-1){
    //             if((map.get(201)!=2 ||map.get(101)!=2 ||map.get(1)!=2)){
    //                 return true;
    //             }
    //         } 
    //     } else if(numero == 14){
    //         if(map.get(209)==-1 ||map.get(110)==-1 ||map.get(9)==-1){
    //             if((map.get(208)!=14 ||map.get(109)!=14 ||map.get(8)!=14)){
    //                 return true;
    //             }
    //         } 
    //     }
        
    //     return false;
    // }

    //return true si peut se potionner
    // public boolean isCompaptible(int numero,  HashMap<Integer, Integer> map){
    //     //si ligne vide ok
    //     //si sticky ok (ce que j'ai fais ci dessous)
    //     int instance = 0;
    //     int colles = 0;
        
    //     int[][] lignes={{0,9},{100,110},{200,211}};
    //     for(int k=0; k<lignes.length;k++){
    //         for(int i=lignes[k][0]; i<=lignes[k][1];i++){
    //             //si numero existe deja dans la ligne --> finito
                
    //             if(map.get(i)==numero){
    //                 instance ++;
    //                 // return false;   
    //             }

    //             if(i+1<=lignes[k][1]){
    //                 //si numero -1 et +1 collés --> finito
    //                 if(map.get(i)==numero-1 && map.get(i+1)==numero+1){
    //                     colles ++;
    //                     // return false;                    
    //                 }
    //             }
    //         }
            
    //     }

    //     if(instance>=2 || colles >=2){
    //         return false;
    //     } else {
    //         return true;
    //     }


    //     // //On evalue le nombre d'instances du numero 

    //     // for (int key : map.keySet()) {
    //     //     if(map.get(key) == numero){
    //     //         instance ++;
    //     //     }
    //     // }

    //     // if(instance<=2){
    //     //     // int num = ((Travaux) j.numeros[i].top()).getNumero();
            
    //     //     //Going accross the map
    //     //     for (Integer cle : map.keySet()) {
    //     //         if(map.get(cle) != -1) { //Compare a non free house
    //     //             int diff = numero - map.get(cle);
    //     //             // System.out.println("diff= "+diff);
    //     //             // System.out.println("cle= "+cle);
                
    //     //             if(cle == 0 || cle == 100 || cle == 200){ //Left end --> only right neighboor
    //     //                 if(diff == 1 && map.get(cle+1)==-1){//Greatter and right space is free
    //     //                     return true; //Right Sticky neighbor
    //     //                 }
    //     //             } else if(cle==9 ||cle==110 ||cle==211){ //Right end --> only left neighboor
    //     //                 if(diff == -1 && map.get(cle-1)==-1){ //Smaller and left space is free
    //     //                     return true; //Left Sitcky neighbor
    //     //                 }
    //     //             } else { //Otherwise, right and left neighboor
    //     //                 if(diff == -1 && map.get(cle-1)==-1){ //Smaller and left space is free
    //     //                     return true; //Left Sitcky neighbor
    //     //                 } else if(diff == 1 && map.get(cle+1)==-1){//Gretter and right space is free
    //     //                     return true; //Right Sticky neighbor
    //     //                 }
    //     //             }
    //     //         }
    //     //     }
    //     // }
    //     // return false;

    // }
    
    //Choisir de placer un numÃ©ro bis: jamais
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

        } 
        else if(goldenEightPlacement(numero,placeValide) != -1){ 
            
            return goldenEightPlacement(numero,placeValide); //the 3 golden 8
        
        } 
        //peut etre otpim et stickyy a interferer
        else if(stickyNeighborPlacement(numero, map, placeValide) != -1){ //Sticky neighbor is possible !
            return stickyNeighborPlacement(numero, map, placeValide);
        } 
        else if(optimPlacement(numero, placeValide, map) != -1){
            return optimPlacement(numero,placeValide,map);
        }
        else{ 
            System.out.println("ramdom placement");
            return RandomSingleton.getInstance().nextInt(placeValide.size());
        }
    }
    
    //Choisir le mÃªme numÃ©ro que celui de la carte quand l'action est un intÃ©rimaire
    @Override
    public int choixNumero(Jeu j, int joueur, int numero){
        HashMap<Integer, Integer>  map = getMap(j, joueur);

        for(int k=-2; k<3; k++){
            if(numero +k>=1 && numero +k <= 15 && isBuildable(map, (numero+k))){
                return numero+k;
            }
        }
        // int res=-1;
        return numero;
        
        //A FAIRE ADELE !!
        
        // if((res<(numero-2) || res>(numero+2)) || res<0)
        //     res=Math.max(0, RandomSingleton.getInstance().nextInt(5) + numero - 2) ;
        // return res;
    }
    
    //Valorise toujours une taille de lotissements de 5
    @Override
    public int valoriseLotissement(Jeu j, int joueur){    
        System.out.println("valeur lotrissement de 5 est "+ j.joueurs[joueur].ville.avancementPrixLotissement[4]);
        if(j.joueurs[0].ville.avancementPrixLotissement[4]!=4){
            return 5;
        } else if (j.joueurs[0].ville.avancementPrixLotissement[0]!=1){
            return 1;
        }
        return 2;
    }
    
    //Met une barriÃ¨re Ã  une position alÃ©atoire
    @Override
    public int choixBarriere(Jeu j, int joueur,  ArrayList<Integer> placeValide){
        
        // for (Integer valeur : placeValide) {
        //     System.out.println("valeur= "+valeur+" index= "+placeValide.indexOf(valeur));
        // }
        
        //ligne 3: 205 210
        //ligne 2: 105 110
        //ligne 1:
    
        if(placeValide.contains(205)){//Fence block A and B
            
            return placeValide.indexOf(205);
        
        } else if(placeValide.contains(210)){//Fence block C 
        
            return placeValide.indexOf(210);
        
        } else if(placeValide.contains(105)){//Fence block E
           
            return placeValide.indexOf(105);
        
        } else if(placeValide.contains(110)){//Fence block D
        
            return placeValide.indexOf(110);
        
        } else if(placeValide.contains(5)){//Fence block F
        
            return placeValide.indexOf(5);
        
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
                    // System.out.println("diff= "+diff);
                    // System.out.println("cle= "+cle);
                
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
     * 
     * 
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

    public int isEight(Jeu j, HashMap<Integer,Integer> map){
        for (int i = 0; i<3; i++) {
            int num = ((Travaux) j.numeros[i].top()).getNumero();
            if (num == 8) { // There is eight number
                return i;
                // if(map.get(4)!=-1){//there is free space for egiht
                //     return i;
                // } else if (map.get(105)!=-1){
                //     return i;
                // } else if(map.get(206)!=-1){return i;}
            } 
        }
        //No eight golden number in options
        return -1;

    }

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

    //return true if number is buildable
    public boolean isBuildable(HashMap<Integer,Integer> map, int numero){
        
        boolean street1 = true;
        boolean street2 = true;
        boolean street3 = true;
        
        for (int i = 0; i < 10; i++) { //street 1
            if (map.containsKey(i) && map.get(i) !=-1) {
                int value = map.get(i); 
                
                //test presence 
                if(numero == value){
                    System.out.println("street1 is false");

                    street1 =false;
                    break;
                }

                //test collé
                if(i!=9){
                    int valueNext=map.get(i+1);
                    if(numero > value && numero < valueNext && valueNext!=-1){
                        System.out.println("street1 is false");
                        
                        street1 = false;
                        break;
                    }
                }
                
                //test gauche
                if(i==0 && numero <map.get(i)){
                    System.out.println("street1 is false");
                    street1 = false;
                    break;
                }
                //test droit
                if(i==9 && numero > map.get(i)){
                    System.out.println("street1 is false");
                    street1 = false;
                    break;
                }

            }
        }

        for (int i = 100; i < 111; i++) { //street 2
            if (map.containsKey(i)) {
                int value = map.get(i); 
                
                //test presence 
                if(numero == value){
                    System.out.println("street2 is false");

                    street2 =false;
                    break;
                }

                //test collé
                if(i!=110){
                    int valueNext=map.get(i+1);
                    if(numero > value && numero < valueNext && valueNext!=-1){
                        System.out.println("street2 is false");
                        street2 = false;
                        break;
                    }
                }

                //test gauche
                if(i==100 && numero <map.get(i)){
                    System.out.println("street2 is false");
                    street2 = false;
                    break;
                }
                
                //test droit
                if(i==110 && numero > map.get(i)){
                    System.out.println("street2 is false");
                    street2 = false;
                    break;
                }

            }
        }

        for (int i = 200; i < 212; i++) { //street 3
            if (map.containsKey(i)) {
                int value = map.get(i); 
                
                //test presence 
                if(numero == value){
                    System.out.println("street3 is false");
                    
                    street3 =false;
                    break;
                }

                //test collé
                if(i!=211){
                    int valueNext=map.get(i+1);
                    if(numero > value && numero < valueNext && valueNext!=-1){
                        System.out.println("street3 is false");
                        street3 = false;
                        break;
                    }
                }
                //test gauche
                if(i==200 && numero <map.get(i)){
                    System.out.println("street3 is false");
                    street3 = false;
                    break;
                }
                //test droit
                if(i==211 && numero > map.get(i)){
                    System.out.println("street3 is false");
                    street3 = false;
                    break;
                }

            }
        }
        

        if(!street1 && !street2 && !street3){
            System.out.println(numero + "n'est pas buildable");
            return false;
        } else {
            System.out.println(numero  + "Est buildable");
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

        // System.out.println("blockA");
        // for (int i = 0; i <= 4; i++) {
        //     int diff = numero-map.get(i);
        //     if(diff==1 || diff==-1){
        //         return i;
        //     }
        //     // System.out.println(i + ": " + map.get(i));
        // }
                
    }
    
    public int optimPlacement(int numero, ArrayList<Integer> placeValide, HashMap<Integer, Integer> map) {
        // int[] dataLeft1= {1,2,3};
        // int[] dataLeft2= {101,102,103,104};
        // int[] dataLeft3= {201,202,203,204,205};
        // int[][] dataLeft={dataLeft1,dataLeft2,dataLeft3};

        // int[] dataRight1= {5,6,7,8};
        // int[] dataRight2= {106,107,108,109};
        // int[] dataRight3= {207,208,209,210,211};
        // int[][] dataRight={dataRight1,dataRight2,dataRight3};

        // int[] eightPos = {4,105,206};

        // if(numero>=2 && numero <=7){
        //     int diff = map.get(0)-numero;
        //     for (int i = 0; i <= 4; i++) {
        //         if(diff)
        //     }            
        // }
        System.out.println("les places valides sont");
        System.out.println(placeValide);
        if(numero ==2 || numero == 1){
            if(placeValide.contains(200)){
                return placeValide.indexOf(200);
            } else if(placeValide.contains(100)){
                return placeValide.indexOf(100);
            } else if(placeValide.contains(0)){
                return placeValide.indexOf(0);
            }
        } else if (numero ==2 || numero == 3 ){ //pos 1
            if(placeValide.contains(201)){
                return placeValide.indexOf(201);
            } else if(placeValide.contains(101)){
                return placeValide.indexOf(101);
            } else if(placeValide.contains(1)){
                return placeValide.indexOf(1);
            }
        } else if(numero == 4 || numero == 5){//pos2
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
        } else if(numero ==11 || numero ==12){
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
            System.out.println("optim placement is impossible");
            return -1;
        

        // if(numero>=2 && numero <=7){ //placer entre 1-3 OU 101-104 OU 201-205
        //     for(int k=0; k<3;k++){
        //         for(int i=0; i<dataLeft[k].length;i++){
        //             if(placeValide.contains(dataLeft[k][i])){
        //                 return placeValide.indexOf(dataLeft[k][i]);
        //             }
        //         }
        //     }
        //     return -1;
        // } 
        // else if( numero>=9 && numero<=14){//placer entre 5-8 OU 106-109 OU 207-210
        //     for(int k=0; k<3;k++){
        //         for(int i=0; i<dataRight[k].length;i++){
        //             if(placeValide.contains(dataRight[k][i])){
        //                 return placeValide.indexOf(dataRight[k][i]);
        //             }
        //         }
        //     }
        //     return -1;
        // } else {
        //     return -1;
        // }
    }
    

    // public int optimPlacement(int numero, ArrayList<Integer> placeValide,HashMap<Integer, Integer> map){
        
        // System.out.println(placeValide);
        // System.out.println(map);
        // if (numero>=3 && numero <=8){ 
            //Block Gauche
           
            // int diff = numero -map.get(0);
            // System.out.println("index"+map.get(0));
            // int min =0;
            // for(int i=1;i<5;i++){
            //     if((diff > numero - i) && (map.get(i)!=-1)){
            //         min = i;
            //     }
            // }
            //min contient le meilleur emplacement

        // } else {//Block droit

        // }
        // System.out.println(placeValide.contains(placeValide));
        // return 0;
    // }

    /**
     * 
     * @param min beging of block
     * @param max end of block
     * @param numero to place
     * @param map total house and number
     * @param placeValide available space
     * @return  the placement to make
     */
    public int stickyStreet(int min, int max, int numero, HashMap<Integer, Integer> map,ArrayList<Integer> placeValide){
        
        // System.out.println("placeValide");
        // System.out.println(placeValide);
        // System.out.println("map");
        // System.out.println(map);

        for (int i = min; i <= max; i++) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                // System.out.println("Clé : " + entry.getKey() + ", Valeur : " + entry.getValue());
                
                int diff = numero-map.get(i);
                if(placeValide.contains(entry.getValue()) && diff==1 || diff==-1 ){ //si place dispo 
                    // System.out.println(entry.getValue());
                    if(i == min){ //Left end of line
                        if (diff == 1){ //place on the right 
                            // System.out.println("index actuel = "+i+" num = "+map.get(i));
                            // System.out.println("index choisi"+ (i+1) +"valeur = "+numero);
                            // System.out.println("renvoie= "+placeValide.indexOf(i+1));
                            return placeValide.indexOf(i+1);
                        }
                    } else if(i == max){ //Right end of line
                        if(diff == -1){ //place on the left
                            // System.out.println("index actuel = "+i+" num = "+map.get(i));
                            // System.out.println("index choisi"+ (i-1) +"valeur = "+numero);
                            // System.out.println("renvoie= "+placeValide.indexOf(i-1));
                            return placeValide.indexOf(i-1); 
                        }
                    } else {
                        if(diff == 1){ //place on the right 
                            // System.out.println("index actuel = "+i+" num = "+map.get(i));
                            // System.out.println("index choisi"+ (i+1) +"valeur = "+numero);
                            // System.out.println("renvoie= "+placeValide.indexOf(i+1));
                            return placeValide.indexOf(i+1); 
                        } else if(diff == -1) { //place on the left
                            // System.out.println("index actuel = "+i+" num = "+map.get(i));
                            // System.out.println("index choisi"+ (i-1) +"valeur = "+numero);
                            // System.out.println("renvoie= "+placeValide.indexOf(i-1));
                            return placeValide.indexOf(i-1); 
                        }
                    }
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