
import java.util.*;
import java.io.*;

// ----- (1) Complete this class to make it concrete
class Country implements Comparable<Country> {

    public Country(String n, int g, int s, int b) {
        name = n;
        gold = g;
        silver = s;
        bronz = b;
        total = g + s + b;
    }
    private String name;
    private int gold, silver, bronz, total;

    public int getgold() {
        return gold;
    }

    public int getsilver() {
        return silver;
    }

    public int getbronz() {
        return bronz;
    }

    public int gettotal() {
        return total;
    }

    public void report() {
        System.out.printf("%-18s%d golds %5d silver %5d bronz %5s(total = %3d) \n", name, gold, silver, bronz, "", total);
    }

    public int compareTo(Country other) {
        return name.compareToIgnoreCase(other.name);
    }

}

/////////////////////////////////////////////////////////////////////////////////////////////
class SortCountryByMedalsName implements Comparator<Country> {

    public int compare(Country c1, Country c2) {
        if (c1.getgold() < c2.getgold()) {
            return 1;
        } else if (c1.getgold() > c2.getgold()) {
            return -1;
        } else {
            if (c1.getsilver() < c2.getsilver()) {
                return 1;
            } else if (c1.getsilver() > c2.getsilver()) {
                return -1;
            } else {
                if (c1.getbronz() < c2.getbronz()) {
                    return 1;
                } else if (c1.getbronz() > c2.getbronz()) {
                    return -1;
                } else {
                    return c1.compareTo(c2);
                }
            }
        }
    }

}

/////////////////////////////////////////////////////////////////////////////////////////////
class SortCountryByTotalMedalsName implements Comparator<Country> {

    
    public int compare(Country c1, Country c2) {
        if (c1.gettotal() < c2.gettotal()) {
            return 1;
        } else if (c1.gettotal() > c2.gettotal()) {
            return -1;
        } else {
            return c1.compareTo(c2);
        }
    }

}

/////////////////////////////////////////////////////////////////////////////////////////////
public class Exersice4 {

    public static void main(String[] args) {

        // ----- (2) create arrayList
        ArrayList<Country> AL = new ArrayList<Country>();

        String infile = "proposition\\Paradigms\\EX4/medals_error.txt";       
        String criterion;
        
        Scanner scan = null;
        boolean openfile = true;
        do{
            // get file name from user
            try {
                System.out.print("Enter file : ");
                
                scan = new Scanner(System.in);
                infile = scan.nextLine();
                
                scan = new Scanner(new File(infile));
                
       // ถ้า ข้างบนผิด มันจะดักไว้ไม่มาคำสั่งข้างล่าง ดังนั้น openfile จะยังคงเป็น false
                openfile = false;
                
            } catch (Exception e) {
                System.err.println("Error enter file ");
                //e.printStackTrace();
            }
        }while (openfile);
        

        // ----- (2.1) add this Country to the ArrayList.
            

        while (scan.hasNext()) {
            
            String line = scan.nextLine();
            String[] buf = line.split("\\s+");
            
            try {
                
                String name = buf[0];
                int gold = Integer.parseInt(buf[1]);
                int silver = Integer.parseInt(buf[2]);
                int bronz = Integer.parseInt(buf[3]);

                AL.add(new Country(name, gold, silver, bronz));
                
            } catch (RuntimeException e) {
                System.err.println( line );  
                e.printStackTrace();
                continue;
            }

        }
            
            scan.close();
            
        // ----- (3) shoose sort arrayList
            scan = new Scanner(System.in);
            do {
                do {
                    System.out.print("\nSort by name(N) | gold-silver-bronz(G) | total(T) | quit(Q) >> ");
                    criterion = scan.nextLine();
                } while (check(criterion));

        // ----- (3.1) sort arrayList by name
                if (criterion.equalsIgnoreCase("N")) {
                    Collections.sort(AL);
                }

        // ----- (3.2) sort arrayList by gold-silver-bronz
                if (criterion.equalsIgnoreCase("G")) {
                    Collections.sort(AL,new SortCountryByMedalsName() );
                }

        // ----- (3.3) sort arrayList by total
                if (criterion.equalsIgnoreCase("T")) {
                    Collections.sort(AL,new SortCountryByTotalMedalsName() );
                }
                
                
        // ----- (4) report from arrayList
                if( !criterion.equalsIgnoreCase("Q") )
                for (int i = 0; i < AL.size(); i++) {
                    AL.get(i).report();
                }

            } while ( !criterion.equalsIgnoreCase("Q") );


    }
 
    // ----- create method for check criterion
    public static boolean check(String c) {
        return !(c.equalsIgnoreCase("q") || c.equalsIgnoreCase("n") || c.equalsIgnoreCase("g") || c.equalsIgnoreCase("t"));
    }

}


/*

--- implements Comparable<Oj> && compareTo(Oj other){}
implements Comparable<Country> ในclass ต้องมี public int compareTo(Country other) {} เป็นเงื่อนไขการจัดเรียง
และจะทำการจัดเร่ยงเมื่อ Collections.sort(AL , กรณี เป็น class Country ไม่ต้องใส่ก็ได้);

--- implements Comparator<Oj> && compare(Oj o1, Oj o2){}
implements Comparator<Country> ในclass ต้องมี public int compare(Country c1, Country c2){} เป็นเงื่อนไขการจัดเรียง
และจะทำการจัดเร่ยงเมื่อ Collections.sort(AL , ชื่อ class ที่ implements );

--- จะเป็นการเรียงตัวอักษรตามชื่อ
return name.compareToIgnoreCase(other.name); 

--- เรียงจากมากไปน้อยจะใช้คำสั่ง   คือ c1 น้อยกว่า จะต้องอยู่ล่างๆ ก็คือ  1
                           c1 มากกว่า จะต้องอยู่บนๆ ก็คือ -1 
      if (c1.gettotal() < c2.gettotal())return 1;
 else if (c1.gettotal() > c2.gettotal())return -1;            
else return 0; 

--- นำเข้า class ไปใน AL แบบต่อท้ายๆ
AL.add( new Country(name, gold, silver, bronz) );

---  จะทำการข้าม spacให้อัตโนมัติ
String line = scan.nextLine();
String[] buf = line.split("\\s+");

--- รับค่าเป็น string คำแรก เช่น One Two TRERE จะรับเพียง 'One'
criterion = scan.next();

--- รับค่าเป็น string ทั้งบรรทัด
criterion = scan.nextLine();

--- ตัวแปรไม่ใช่ Q จะทำงาน
 while ( !criterion.equalsIgnoreCase("Q") );

--- ใช้งาน method ภายใน class ที่อยู่ในAL
for (int i = 0; i < AL.size(); i++) AL.get(i).report();

---ข้อดี AL 
ไม่ต้องประกาศ Array of class และไม่ต้องประกาศจำนวนของ AL ด้วย
ยัดๆเข้ามาเรื่อยๆก็ไม่มีปัญหา 

-- แสดงผลที่ผิด 
e.printStackTrace();

-- ตัวแดง
System.err.println("Error enter file ");

-- NumberFormatException = ผิดที่สตริงที่แปลงเป็น(int)ไม่ได้
NumberFormatException: For input string: "o"
t java.lang.Integer.parseInt

-- ArrayIndexOutOfBoundsException = arrayindex 3 ไม่มีอะไรให้ใส่ข้าไป 
java.lang.ArrayIndexOutOfBoundsException: 3
-- FileNotFoundException = ไม่พบไฟล์ จึง ไม่สามรถเปิดไฟล์ได้
FileNotFoundException: sadsada 
FileInputStream.open0
*/