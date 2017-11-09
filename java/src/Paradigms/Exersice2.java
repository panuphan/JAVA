
import java.util.*;
import java.io.*;

abstract class FilmBase {

    protected String name;
    protected int age;                // from release year to 2017

    public FilmBase(String n, int a) {
        name = n;
        age = a;
    }

    public void print() {}
}

/////////////////////////////////////////////////////////////////////////////////////////////
class Film extends FilmBase {

    protected String director;
    protected double grossDollars;

    public Film(String n, int a, String d, double gd) {
        super(n, a);
        director = d;
        grossDollars = gd;
    }

    
    public void print() {
        System.out.printf("%-20s(%d)", name, age);
        System.out.printf("%18s director = %-18s opening gross = %,5d million Baths\n", "", director, (int)grossDollars);
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////
class Animation extends FilmBase {

    protected int lengthMinutes;

    public Animation(String n, int a, int lm) {
        super(n, a);
        lengthMinutes = lm;
    }

    
    public void print() {
    // ----- (5)  hr = min/60 , min = min%60  
        System.out.printf("%-20s(%d)", name, age);
        System.out.printf(" %d hrs, %d mins \n", lengthMinutes / 60, lengthMinutes % 60);
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////
public class Exersice2 {

    public static void main(String[] args) {

        // ----- (1) create array 10 FB
        FilmBase[] FB = new FilmBase[10];

        // ----- (2) Read input file
        String infile = "proposition\\Paradigms\\EX2/input.txt";
        try {

            Scanner scan = new Scanner(new File(infile));

            for (int i = 0; scan.hasNext(); i++) {

                String line = scan.nextLine();
                String[] buf = line.split(",");
        
        // ----- (3) calculate release year from age
                String type = buf[0];
                String name = buf[1].trim();
                int age = 2017 - Integer.parseInt(buf[2].trim());

                if (type.equalsIgnoreCase("f")) {
        // ----- (4) 1 Dollar = 33 Baths
                        String director = buf[3];
                        double grossDollar = 33 * Double.parseDouble(buf[4]);
                        FB[i] = new Film(name, age, director, grossDollar);
                }
                
                else{
                    
                        int lengthMinutes = Integer.parseInt(buf[3].trim());
                        FB[i] = new Animation(name,age, lengthMinutes);
                
                }
                
            }

        // ----- (6) print all in reverse order
            for (int i = FB.length - 1; i >= 0; i--) FB[i].print();
            
            
        scan.close();

        } catch (Exception e) {
            System.err.println("An error occurs. End program.");
            System.exit(-1);
        }
    }

}

/*
    double %d ใช้ไม่ได้ ต้อง %.0f และ เมื่อหาร doubleจะทำการ "ปัดขึ้น" เช่น หารได้ 1.8 แสดงเป็น .0f จะได้ 2 !!
            ข้อดี ในการอ่านไฟลน์ การแปลง string เป็น double จะไม่เกิดปัญหา จากการเว้นวรรคใดๆ
    integer %d เท่านั้น เมื่อหาร intจะทำการ "ปัดลง"  เช่น 1.8 แสดงเป็น 1 !!
            ข้อเสีย ในการอ่านไฟลน์ การแปลง string เป็น int จะมีปัญหาจากการเว้นวรรคในไฟลน์
    String เวลาอ่านจากไฟลน์ ถ้ามีการกด tab/space ก็จะรับมาด้วย !!
    Override หรือกรณี มีf() ที่ได้รับมาจากแม่ จะทำการเพิมเติม เราจะไม่ไปยุ่งกับแม่ แค่ใส่ super.f() คือการใช้ f() แม่
             super(n,a) คือการใส่ constructor ให้แม่นั่นเอง ซึ่งลูกจำเป็นต้องใส่ให้แม่ทุกครั้งในconstructorตัวเอง
    buf[2].trim()  trim คือ การตัด space ใน input file ทำให้ สามารถรับค่าได้ 
    %,5d  ถ้าเลขเลย 3 หลักก็จะใส่ , ให้เอง   5d คือ การเว้นช่องให้พิมปกติ
     if (type.equalsIgnoreCase("f")) type เป็น string ที่ตรงกับ f,F  
*/
    