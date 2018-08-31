import java.io.*;
import java.util.*;
import java.lang.Math;
/**
 * This is the class the class for NewChutesAndLadders. This class will use many different data structures to help manipulate things such as the 
 * board length. This is because the board length is not a fixed value and can be of any positive size. Spins are not done by just random numbers they are
 * read in from a file and added to an array list of deck of cards. Those cards are drawn and indicate how many spaces on the board you need to move.
 * Evertime you land on a space that value is incremented in an array that represens the board. After the simulation is over various methods will be called. 
 * to calculcate things like the average number of spins per game, the average number of chutes per game, the average number of ladders per game, the minimum 
 * and maximum numbers of values for the chutes, ladders, and spins/ 
 * 
 * @author Matthew MacFadyen
 * @version February 17, 2017
 */
public class NewChutesAndLadders
{
    // instance variables
    Scanner fileIn = null; 
    Scanner fileIn2 = null;
    private String filename_board; 
    private String filename_cards;
    private int boardlength;
    private int numLadders; 
    private int numChutes;
    private int simulationNum; 
    private int numSpins;
    private ArrayList<Integer> deck = new ArrayList<Integer>();
    private ArrayList<Integer> discard_Pile = new ArrayList<Integer>();
    private ArrayList<Integer> spins_game = new ArrayList<Integer>();
    private ArrayList<Integer> chutes_game = new ArrayList<Integer>();
    private ArrayList<Integer> ladders_game = new ArrayList<Integer>();
    private ArrayList<Integer> start = new ArrayList<Integer>();
    private ArrayList<Integer> end = new ArrayList<Integer>(); 
    private int[] board;
    /**
     * Constructor for objects of class NewChutesAndLadders. The constructor of this class will take 
     * two Strings that are the text files that will be read in. These text tiles hold the information 
     * for the board and the information for the deck of cards. The board can be of any length 
     * and can have any number of chutes and ladders. The deck of cards will be used to move spaces on the
     * board, this will be used for the spins in the game. The constructor will also construct things such as two array lists of the 
     * same size with the starting and ending points of a chute and ladder. The deck of cards will be simulated by an array list. 
     * 
     * @param the first param will be a string of the file name for the board info, the second param 
     * will be the string of the filename for the card info
     */
    public NewChutesAndLadders(String filename_board, String filename_cards)
    {
        // make the board 

        // create a new scanner of the input file to be manipulated
        try{
            fileIn = new Scanner(new File(filename_board) );
            fileIn2 = new Scanner(new File(filename_cards) );
        }
        catch(Exception e)
        {
            System.out.println("The file does not exist.");
            System.exit(0);
        }
        //get the first number in the file and set it as the length of the board 

        boardlength = fileIn.nextInt();
        // create an array with the appropriate size, this arrray will be used to tally the landed on spaces  
        board = new int[boardlength+1]; 

        // get the second number in the file which is how many chutes their are in the board 
        numChutes = fileIn.nextInt();

        int Start;
        int End;

        // loop through the number of chutes that the file has 
        for(int i = 0; i < numChutes; i++)
        {
            // a variable for where the chute starts 
            Start = fileIn.nextInt();

            // add the starting space to the start array 
            start.add(Start);

            // a varialbe for where the chute ends 
            End = fileIn.nextInt(); 

            // add the ending space to the end array
            end.add(End);

        }
        // get how many ladders their are on the board 
        numLadders = fileIn.nextInt();

        // loop through the number of ladders th
        for(int i = 0; i < numLadders; i++)
        {
            // a variable where the ladder starts
            Start = fileIn.nextInt();

            // add the starting space to the start array 
            start.add(Start);

            // a variable where the ladder ends 
            End = fileIn.nextInt(); 

            // add the ending space to the end array
            end.add(End);

        }
        // read in the cards from the test file now
        // a variable for how many cards their are in the deck
        int numCards = fileIn2.nextInt();

        // a variable for how many different kinds of cards their are 
        int card_kinds = fileIn2.nextInt();

        // loop through th distinct kinds of cards 
        for(int i = 0; i < card_kinds; i++)
        {
            // read the first number which tells us how many cards of that type exist 
            int numcards_of_type = fileIn2.nextInt();

            // the second number will tell us actual value of the card 
            int numcard_value = fileIn2.nextInt();

            // loop through the numcard_types and add them too the deck of cards 
            for(int j = 0; j < numcards_of_type; j++)
            {
                // add the num to the deck of cards the required amount of times 
                deck.add(numcard_value);
            }
        }

    }

    /** 
     * This method will be used to run the Chutes and Ladders Simulation. It will use the array list of cards and randomly draw 
     * indicies in the deck to draw random card values. These cards will tell it how many spaces the player needs to move. A negative 
     * space can also occur so that is handled as well. It checks to see if you have landed on a special space, a chute or a ladder. It 
     * differenties between a chute and a ladder by checking if the start is greater than the end of vice versa. The deck of cards will 
     * be put in the discard pile array list. If the deck is empty then the discard pile will put all of the cards back into the deck
     * then it will continue drawing random indicies of the deck array list. It will continue to run through the simulaiton the given 
     * number of times in the paramter
     * 
     * @param simulation count, the number of simulations of chutes and ladders to be played 
     */
    public void runSimulation(int simulationCount)
    {

        // start playing the game 
        // create a new Random object to use on the deck of cards
        Random rand = new Random();

        // variable to keep track of the current space on the board
        int currentSpace = 0; 

        // this is the loop that will play the game itself
        for(int i = 0; i < simulationCount; i++)
        {
            // keep looping until the last space in the game has been reached
            while(currentSpace < boardlength-1) 
            {
                // if the deck of cards are empty put all the cards back and then reshuffle 
                if(deck.isEmpty() ) 
                {
                    // put all the cards back into the array list
                    while(discard_Pile.size() > 0)
                    {
                        // remove the card from the discard pile 
                        int removedCard = discard_Pile.remove(discard_Pile.size()-1);

                        // add the card back to the deck of cards
                        deck.add(removedCard);
                    }
                }

                // time to draw a random card from the deck of cards
                int card = rand.nextInt(deck.size() );

                //increment the spin count
                numSpins++;

                // store the value of the random card that was drawn
                int spin = deck.get(card);

                // move to that space on the board 
                currentSpace = currentSpace + spin;

                // remove the card from the deck so that it isn't drawn again from the deck 
                int removedCard = deck.remove(card);

                // add that card to the discard pile
                discard_Pile.add(removedCard); 

                // keep looping as long as the current space does not equal the length of the board 
                if(currentSpace != boardlength - 1) 
                {
                    // make sure that the spin does does not exceed the length of the board

                    if(currentSpace > boardlength - 1)
                    {
                        // if the board does exceed the length of the board go back to where you started
                        currentSpace = currentSpace - spin;
                    }
                    // if you are on space zero and you draw a negative number
                    else if(currentSpace < 0)
                    {
                        currentSpace = currentSpace + Math.abs(spin);

                    }
                    else if(currentSpace == board.length)
                    {
                        // if it equals 100 do nothing and do not tally 

                    }
                    // if the starting space array list contains current space it is a special space
                    else if(start.contains(currentSpace) )
                    {
                        // if the space in the array list is equal to the current space

                        int index = start.indexOf(currentSpace);

                        // is it a chute or is it a ladder 
                        // is the start greater than the end it is a chute 
                        if(start.get(index) > end.get(index) )
                        {
                            // fall down the chute to the corrresponding end space 
                            currentSpace = end.get(index);

                            //increment the land count
                            board[currentSpace]++;

                            //increment the chute count 
                            numChutes++;

                        }
                        // if the start is less than the end it is ladder 
                        else if (start.get(index) < end.get(index) )
                        {
                            //climb up the ladder to the corresponding end space 
                            currentSpace = end.get(index);

                            //increment the land count
                            board[currentSpace]++;

                            //increment the ladder count 
                            numLadders++;

                        }
                    }
                    // if it is not a special space it is just a blank space 
                    else {
                        // incremene the land count
                        board[currentSpace]++;
                    }
                }

            }
            //add the number of spins from that game to the array list 
            spins_game.add(numSpins);

            // add the number of chutes from the game to the chutes array list 
            chutes_game.add(numChutes);

            // add the number of ladders to the ladders array list 
            ladders_game.add(numLadders);

            //reset the number of spins back to zero for the next game simulation 
            numSpins = 0; 

            // reset the number of chutes back to zero for the next simulation
            numChutes = 0;

            // reset the number of ladders back to zero for the next simulation 
            numLadders = 0;

            // increment the game count
            simulationNum++; 

            // current space must be set back to zero once the game has been won 
            currentSpace = 0;
        }
    }

    /**
     * This method will be used to calculate the average number of spins it takes for a player to in a game of chutes and ladders
    It will do this by looping through the array list of spins for each game and add them all together to a variable called sum. 
    It will then divide the sum by the nubmer of simulations that were run
     * @return a double of the average number of spins per game 
     */
    public double getAverageSpins() 
    {
        double averageSpins = 0;

        //initialize a variable that will be used to add all the numSpins per game
        double sum = 0.0;

        //take all of the numSPins from each game and divide by the number of simulations 
        for(int i = 0; i < spins_game.size(); i++)
        {
            sum = sum + spins_game.get(i); 
        }

        // divide the sum of spins by the number of simulations
        averageSpins = sum / simulationNum;

        return averageSpins;
    }

    /**
     * This method will be used to calculate the average number of chutes that are landed on in a game of chutes and ladders. It will 
     * add up all of the chutes that were landed on in the simulation to a variable called sum. It will then divide the sum by the 
     * number of simulations that were run.
     * @return 
     */
    public double getAverageChutes() 
    {
        // initialize the average chutes variable 
        double averageChutes = 0.0;

        //initialize a variable that will be used to add all the numSpins per game
        double sum = 0.0;

        //take all of the numSPins from each game and divide by the number of simulations 
        for(int i = 0; i < chutes_game.size(); i++)
        {
            sum = sum + chutes_game.get(i); 
        }

        // divide the sum of spins by the number of simulations
        averageChutes = sum / simulationNum;

        return averageChutes;

    }

    /**
     * This method will get the average number of ladders that are stepped on in a game of chutes and ladders. It will loop through the 
     * array list of ladders landed upon per game. It will add up all of these ladder counts to a seperate sum variable and then divide 
     * the sum by the number of simulation numbers to get the average number of ladders landed upon in the given number of simulations 
     */
    public double getAverageLadders() 
    {
        double averageLadders = 0.0;

        //initialize a variable that will be used to add all the numSpins per game
        double sum = 0.0;

        //take all of the numSPins from each game and divide by the number of simulations 
        for(int i = 0; i < ladders_game.size(); i++)
        {
            sum = sum + ladders_game.get(i); 
        }

        // divide the sum of spins by the number of simulations
        averageLadders = sum / simulationNum;
        return averageLadders;
    }

    /**
     * This method will get the three most landed upon spaces in the simulation. This will be done by creating three index's that are set to zero at the 
     * beginning. Check the first index and the second index and update the max and min in each instance. After an update has been done the max and mins 
     * are shifed down one. So if it is the 2nd max then the 2nd original is shifted down and made the 3rd index/ 
     * @return an array of the three most common landed spaces 
     */
    public int[] getMostCommonSpaces()
    {
        int[] mostCommonSpaces = new int [3];

        int max1 = 0;
        int max2 = 0;
        int max3 = 0;

        // loop through the board and check for the 3 most landed upon spaces
        for(int i = 0; i < board.length; i++)
        {
            // if the board index is greater than the first maximum 
            if(board[i] > board[max1])
            {
                // shift it all down 
                max3 = max2;
                max2 = max1; 
                max1 = i; 

            }
            // if the board index is greater than the second value 
            else if(board[i] > board[max2])
            {
                // shift it all down 
                max3 = max2; 
                max2 = i;
            }
            else if(board[i] > board[max3])
            {
                // make the max 3
                max3 = i;
            }
        }
        // store the values into the array 
        mostCommonSpaces[0] = max1;
        mostCommonSpaces[1] = max2;
        mostCommonSpaces[2] = max3;

        return mostCommonSpaces;
    }

    /**
     * This method will calculcate the max number of spins by looping through the spins per game and seeing which one game has the most amount of spins 
     * @return the maxspins 
     */
    public int getMaxSpins() 
    {
        // initialize the max number of spins 
        int maxSpins = 0;

        for(int i = 0; i < spins_game.size(); i++)
        {
            if(spins_game.get(i) > maxSpins )
            {
                maxSpins = spins_game.get(i);
            }
        }

        return maxSpins;
    }

    /**
     * This method will calcualte the min number of spins by looping through the spins per game and seeing which one is the smallest 
     * @return the minspins 
     */
    public int getMinSpins() 
    {
        // min spins can be initialized to the first index in the array list 
        int minSpins = spins_game.get(0);

        for(int i = 1; i < spins_game.size(); i++)
        {
            if(minSpins > spins_game.get(i) )
            {
                minSpins = spins_game.get(i);
            }
        }
        return minSpins;
    }

    /**
     * This method will calculcate the max number of chutes by looping through the chutes per game and seeing which one game has the most amount of chutes 
     * @return the maxchutes 
     */
    public int getMaxChutes() 
    {
        // initialize the max number of chutes
        int maxChutes = 0;

        for(int i = 0; i < chutes_game.size(); i++)
        {
            if(chutes_game.get(i) > maxChutes )
            {
                maxChutes = chutes_game.get(i);
            }
        }

        return maxChutes;
    }

    /**
     * This method will calculcate the min number of chutes by looping through the chutes per game and seeing which one game has the least amount of chutes 
     * @return the minChutes
     */
    public int getMinChutes()
    {
        int minChutes = chutes_game.get(0);

        for(int i = 1; i < chutes_game.size(); i++)
        {
            if(minChutes > chutes_game.get(i) )
            {
                minChutes = chutes_game.get(i);

            }
        }
        return minChutes;
    }

    /**
     * This method will calculcate the max number of ladders by looping through the ladders per game and seeing which one game has the most amount of ladders 
     * @return the maxladders 
     */
    public int getMaxLadders()
    {
        // initialize the max number of ladders
        int maxLadders = 0;

        // loop through the number of ladders per game which is stored in an array list 
        for(int i = 0; i < ladders_game.size(); i++)
        {
            // if the index is greater than the current maxLadders
            if(ladders_game.get(i) > maxLadders )
            {
                // update max ladder
                maxLadders = ladders_game.get(i);
            }
        }

        return maxLadders; 
    }

    /**
     * This method will calculcate the min number of ladders by looping through the ladders per game and seeing which one game has the least amount of ladders 
     * @return the minLadders 
     */
    public int getMinLadders()
    {
        int minLadders = ladders_game.get(0);

        for(int i = 0; i < ladders_game.size(); i++)
        {
            if(minLadders > ladders_game.size() )
            {
                minLadders = ladders_game.get(i);

            }
        }
        return minLadders;
    }
}

