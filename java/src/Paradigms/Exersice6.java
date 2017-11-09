package Paradigms;

import java.util.*;
import java.io.*;

class WithdrawThread extends Thread implements Comparable<WithdrawThread>  {
private Account account;
private int totalWithdraw;

public WithdrawThread(String n, Account a) { super(n); account = a;}
public void run()
{
    //Add a loop that
    do {

//----- 1. Random the amount of money requested by thread. You may set the
//         range of randomed values, e.g. 1-10
        Random r1 = new Random();
        int requeste = r1.nextInt(10 - 1) + 1;

        
//----- 2. Call account.update(…) to withdraw the money & update balance
        totalWithdraw += account.update(requeste,totalWithdraw);
        

//----- 3. Stop when the account’s balance is 0
    } while (account.getBalance() > 0);


}
    

    public int getTotalWithdraw() {
        return totalWithdraw;
    }
    
    public int compareTo(WithdrawThread other) {

        if (totalWithdraw > other.getTotalWithdraw())       return -1;
        else if (totalWithdraw < other.getTotalWithdraw())  return 1;
        return 0;

    }


} // end WithdrawThread

////////////////////////////////////////////////////////////////////////////////

class Account {
private int balance;
private PrintWriter out;
private Scanner scanfile;
public Account(int b) {
balance = b;
try { out = new PrintWriter(new File("proposition\\Paradigms\\EX6/balance.txt")); }
catch(Exception e) { out = new PrintWriter(System.out); }

try { scanfile = new Scanner(new File("proposition\\Paradigms\\EX6/balance.txt")); }
catch(Exception e) {}
}

synchronized public int getBalance(){
    return balance;
}
        
synchronized public int update(int request,int total)
{
    int get = 0;
    
//----- 1. If request <= balance, the thread can get money as it requests
    if( request <= balance )        get = request;
    
//----- 2. If request > balance, the thread can only get available money
    else if( request > balance )    get = balance;
    
    balance-=get;
    
    
//----- 3. Print thread’s name, money requested, money withdrawn, current balance
//         to file balance.txt
    
    while(scanfile.hasNext())out.printf("\r\n");
    out.printf("%-10s >> request = %3d, get = %3d, balance = %4d, total withdraw = %3d\r\n",Thread.currentThread().getName(),request,get,balance,(get+total) );
    if(balance == 0 )out.close();
    
    
 //----- 4. You may return some value to WithdrawThread
    return get;

}
} // end Account

////////////////////////////////////////////////////////////////////////////////


public class Exersice6 {


    public static void main(String[] args) {

 //---- Get initial balance from user. Create an Account with this initial balance
        System.out.print("Initial balance : ");
        Scanner scan = new Scanner(System.in);
        int balance  = scan.nextInt();
        Account account = new Account(balance);
        
//---- Create 3 WithdrawThreads and let them compete to withdraw money from this Account
//     All threads sees the same Account object and write to the same output file  
        WithdrawThread T1 = new WithdrawThread("Thread 1", account);
        WithdrawThread T2 = new WithdrawThread("Thread 2", account);
        WithdrawThread T3 = new WithdrawThread("Thread 3", account);      
        T1.start();  T2.start(); T3.start();
        
        
        try{
            T1.join();T2.join();T3.join();
        }catch (InterruptedException e) { }
        
        
//---- After all threads finish their jobs, report the total money withdrawn by each
//     thread. Sort the output in either increasing or decreasing order of total money        
        ArrayList<WithdrawThread> AL = new ArrayList<>();
        AL.add(T3);AL.add(T2);AL.add(T1);
        Collections.sort(AL);
        
        for(int i = 0 ; i<AL.size() ; i++ ){
         System.out.printf("%-10s  Withdraws %3d \n",AL.get(i).getName(), AL.get(i).getTotalWithdraw());     
        }
                   
    }
}



    /*out.append(Thread.currentThread().getName() +
                "   >> request = " + request +
                ", get = "      +get      +
                ", balance = "  + balance +
                ", total withdraw = "+ (get+total) + "\r\n");
 */