
import java.util.*;
import java.io.*;


class DiceThread extends Thread
{
private int target, sum,round;
private PrintWriter out;
public DiceThread(String n, int t) { super(n); target = t; }

public void report(){
    System.out.printf("%s end in %d rounds\n",this.getName(),round-1);
}

public void run()
{
   //1. Create PrintWriter object to print output to file 
    String outfile = "proposition\\Paradigms\\EX5/"+this.getName() +".txt";
    try {
        
        out = new PrintWriter(outfile);
        sum = 0;
        
    //2. Execute a loop until sum reaches target. In each round of the loop    
        for (round = 1 ; sum < target ; round++) {
    //-- roll 2 dice
           Random r1 = new Random();
           Random r2 = new Random();       
           int rand1 =  r1.nextInt(10-1) + 1;
           int rand2 =  r2.nextInt(10-1) + 1;
           
    //-- if both are odd  reset sum to 0
           if(rand1 %2 != 0 && rand2 %2 !=0 ) sum = 0;
    
    //-- if both are even  add the bigger score to sum       
           else if(rand1 %2 == 0 && rand2 %2 ==0 ){
               if(rand1 >= rand2)   sum+=rand1;
               else                 sum+=rand2;
           }
    //-- if one is odd, the other is even  add both scores to sum      
           else sum += (rand1+rand2);
    
    //-- print round number, dice scores, and current sum to file
        out.printf("%s%5d >> dice = %d  %d  sum = %5d \r\n","round",round,rand1,rand2,sum);
        }  
        out.close();
        
    } catch (Exception e) {
            System.err.println("Error outputfile");
            System.exit(-1);
        }
    
   //3. Report number of rounds to the screen
        report();
    
}

}

////////////////////////////////////////////////////////////////////////////////
public class Exersice5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.print("Enter sum = ");
        Scanner scan = new Scanner(System.in);
        int sum = scan.nextInt();
        
        DiceThread DT1 = new DiceThread("Dice1",sum);
        DiceThread DT2 = new DiceThread("Dice2",sum);
        DiceThread DT3 = new DiceThread("Dice3",sum);
        DT1.start();  DT2.start(); DT3.start();
                
    }
    
}
