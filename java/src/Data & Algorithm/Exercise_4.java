import java.awt.*; // for using Desktop
import java.net.*; // for using URI
import java.util.*;
import java.io.*;


class MyBrowser {

    private ArrayDeque<String> FW, BW;

    public MyBrowser() {
        FW = new ArrayDeque<String>(); // tail (top of stack) = latest URL to show
        BW = new ArrayDeque<String>();
    }

    public void go() { // Simulate GO button in browser

        //(1) get URL string from user
        Scanner scan = new Scanner(System.in);
        System.out.print(" Enter URL : ");
        String URL = scan.nextLine();

        //(2) open URL in browser         
        OpenBrowser(URL);

        //(3) append this URL to FW
        FW.add(URL);

        //(4) FW keeps up to 4 URLs. If #URLs > 4, pop head (oldest) URLs from FW
        if (FW.size() > 4) FW.removeFirst();

    }

    public void backward() { // Simulate BACK button in browser

        //(1) if FW.size() > 1  pop tail URL from FW & append it to BW
        if (FW.size() > 1) {
            
            BW.add( FW.pollLast() );

        //(2) open URL at the tail of FW
            OpenBrowser(FW.peekLast());
        }

    }

    public void forward() { //Simulate FORWARD button in browser
        
        //(1) if BW.size() > 0  pop tail URL from BW & append it to FW
        if (BW.size() > 0) {
            
            FW.add( BW.pollLast());

        //(2) open URL at the tail of FW
            OpenBrowser(FW.peekLast());
        }
        
    }

    // ----- create method for OpenBrowser 
    public static void OpenBrowser(String URL) {

        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(URI.create(URL));
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

}

////////////////////////////////////////////////////////////////////////////////
public class Exercise_4 {

    public static void main(String[] args) {
        
        MyBrowser TestBrowser = new MyBrowser();
        String button;
        Scanner scan = new Scanner(System.in);
        
        do{ 
            
         System.out.print(" Go(g) | forward(f) | backward(b) | Quit(q)>>> ");
         button = scan.nextLine();

        if (button.equalsIgnoreCase("g"))     TestBrowser.go(); 
        else if (button.equalsIgnoreCase("b"))TestBrowser.backward();
        else if (button.equalsIgnoreCase("f"))TestBrowser.forward();
        
       }while(!button.equalsIgnoreCase("q"));
    }
     
}


/*
    FW.add(URL);หรือ .addLast()     เพิ่มต่อท้ายๆไปเรื่อยๆ
    FW.push(URL)            .addFrist();   
    FW.removeFirst();       ลบตัวแรก แบบไม่แสดงผล
    FW.size();              จำนวนสมาชิกใน FW
    FW.pollLast()           แสดง/ลบตัวท้าย
    FW.poll; FW.pollFrist() แสดง/ลบตัวแรก
    FW.peekLast()           แสดงตัวท้าย
    

// https://www.youtube.com
//https://www.google.co.th
//https://www.mahidol.ac.th/th/
//https://www.java.com/en/
//https://game.meemodel.com/

*/