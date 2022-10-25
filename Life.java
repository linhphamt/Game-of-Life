//Jennifer Pham - Assignment 2: Game of Life

import java.util.Arrays;

//Create a new public class called Life
public class Life {
    
    int[] gameArray; //Declare the array of cells 

    //Constructor; 
    public Life (int gameSize) {
        gameArray = new int [gameSize];

        //Loop through and randomly assign values to each index
        for (int i = 0; i < gameSize; i++) {
            gameArray[i] = (int)Math.floor(Math.random()*2);
        }
    }

    //Return the status (alive=1;dead=0) of cell at index i
    public boolean isAlive (int i) {
        return gameArray[i]==1;
    }

    //Return the status of cell at index i after the next step of simulation
    public boolean shouldDie (int i) {
        boolean result = false;
        if (isAlive(i)) {
            //Edge cases for the first and last cells; should die if it is alive and has one alive neighbor
            if (i==0) {
                if (isAlive(1)) {
                    result = true;
                }
            } else if (i==gameArray.length-1) {
                if (isAlive(i-1)) {
                    result = true;
                }
            } else {    //All other cells; should die if it is alive and has two alive neighbors
                if (isAlive(i-1) && isAlive(i+1)) {
                    result = true;
                }
            }
        } 
        return result;
    }
    

    //Return nothing; copy the value of the first array to the second array
    public static void copyArray (int[] firstArray, int[] secondArray) {
        for (int i = 0; i < firstArray.length; i++) {
            secondArray[i] = firstArray[i];
        }
    }

    //Return a string representing the array
    public String toString() {
        String gameString = Arrays.toString(gameArray);
        return gameString;

    }

    //Return nothing; carry out 1 simulation
    public void advanceTime() { 
        //Call the copyArray method; copy the values of the original array to the new empty array of the same length
        int[] copy_gameArray = new int [gameArray.length];
        copyArray(gameArray, copy_gameArray);

        //Move through and make changes on the copy array based on game rules
        for (int i = 0; i < copy_gameArray.length; i++) {
            //The cell should die if it is alive and has only alive neighbors 
            if (shouldDie(i)) {
                copy_gameArray[i] = 0;
            } else {
                if (isAlive(i)) {
                    if (i==0) { //Edge cases for the first and last cell; if the cell is alive and has a dead neighbor, then its neighbor becomes alive
                        copy_gameArray[1] = 1;
                    } else if (i==copy_gameArray.length-1) {
                        copy_gameArray[i-1] = 1;
                    } else {    //All other cells; if the cell is alive and has at least one dead neighbor, then raise all dead neighbors possible
                        if (gameArray[i-1] == 0) {
                            copy_gameArray[i-1] = 1;
                        } if (gameArray[i+1] == 0) {
                            copy_gameArray[i+1] = 1;
                        }
                    }
                }
            }
        }
        //Update the array of cells to the values in the copy
        copyArray(copy_gameArray,gameArray);
    }

    public static void main (String [] args) {
        int timeSteps = 10; //How many "generations" the simulation will run for
        int gameSize = 10;  //The size of the array of cells

        //Create a new Life object
        Life game = new Life(gameSize);  
        
        System.out.println("Initial values " + game.toString());

        //Run the game timeSteps times, print the array the end of each step
        for (int i = 1; i < timeSteps+1; i++) {
            game.advanceTime();
            System.out.println("Values after " + i + " timesteps " + game.toString());
        }
    }
}