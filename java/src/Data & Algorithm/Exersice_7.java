
import java.util.*;
import java.io.*;

class Passage {

    private HashMap<String, Integer> HM;   // all unique words & counts
    private int totalCount;
    private String infile ;

    public Passage(String in) {
        HM = new HashMap<String, Integer>();
        totalCount = 0;
        infile = in;

    }

    public void readPassage() {
        
        // Check input'file
        boolean openfile = true;
        Scanner scan = null;
        
        do {
            try {
                scan = new Scanner(new File("proposition/Data/EX7/"+infile));
                openfile = false;
            } catch (Exception e) {
                System.err.println(" Error file : "+ infile );
                System.err.print(" Enter file : proposition/Data/EX7/");
                Scanner scan_err = new Scanner(System.in);
                infile = scan_err.nextLine();
            }
        } while (openfile);
        
        
        // Read the passage from text file. Check each word
        while (scan.hasNext()) {

            //String word = scan.next().toLowerCase().replaceAll("\\p{P}", "");
            
            String word = scan.next().toLowerCase();
            word = SetStrips.Remove(". , ; : ! ?",word);
            
            // (1) If it doesn’t exist in HM, put it in HM
            if (!HM.containsKey(word)) {
                HM.put(word, 1);
            } 
            // (2) If it already exists in HM, update its count
            else {
                HM.put(word, HM.get(word) + 1);
            }
            
            // (3) Calculate total count (of all words)
            totalCount++;
        }
        
        scan.close();
    }

    public HashMap<String, Integer> getWordsAndCounts() {
        return HM;
    }

    public HashSet<String> getWords() {
        HashSet<String> keys = new HashSet<String>(HM.keySet());
        return keys;
    }
    
    public int getTotalCount(){
        return totalCount;
    }
    
    //Create method to print HashMap
    public void printMap(){
        printMap( HM.keySet() );
    }
    
    public void printMap(Set<String> A ) {
        ArrayList<String> AL = new ArrayList<>(A);
        Collections.sort(AL);
        int n = 0;
        for (String key : AL) {
            System.out.printf("%15s (%2d)", key, HM.get(key));
            n++;
            if (n % 5 == 0) {
                System.out.println("");
            }
        }
        System.out.println("\n");
    }
    
}

////////////////////////////////////////////////////////////////////////////////
class SetOperations {

    public static HashSet<String> intersect(HashSet<String> H1, HashSet<String> H2) {
        
        // find words exist in both H1 and H2
        HashSet<String> intersect = new HashSet<String>(H1);
        intersect.retainAll(H2);
        return intersect;  
    }

    public static HashSet<String> difference(HashSet<String> H1, HashSet<String> H2) {
        
        // find words exist in H1 but not in H2
        HashSet<String> difference = new HashSet<String>(H1);
        difference.removeAll(H2);
        return difference; 
    }
}

////////////////////////////////////////////////////////////////////////////////

//Create class to Replece strip punctuation (. , ; : ! ?) 
class SetStrips{
   public static String Remove(String store,String word){
       String[] buf = store.split("\\s+");
       
       for(int i = 0 ; i < buf.length ; i++){
           word = word.replace(buf[i],"");
       }
       return word;
   }
}

////////////////////////////////////////////////////////////////////////////////

public class Exersice_7 {

    public static void main(String[] args) {

        // Create 2 Passage Objects & call method readPassage() of each object
        Passage p1 = new Passage("dolphin.tsxt");
        Passage p2 = new Passage("whale.txst");
        p1.readPassage(); p2.readPassage();

        //Print total number of words & total number of unique words in each passage
        System.out.printf("%-10s>> #word = %3d,#unique words = %3d\n", "Dophin", p1.getTotalCount(), p1.getWordsAndCounts().size());
        System.out.printf("%-10s>> #word = %3d,#unique words = %3d\n", "Whale", p2.getTotalCount(), p2.getWordsAndCounts().size());

        // Use methods in SetOperations to find words (and counts) 
        //existing in both passage
        System.out.println("Words existing in both passage");
        System.out.println("Dophin");
        p1.printMap( SetOperations.intersect(p1.getWords(), p2.getWords()) );
        System.out.println("Whale");
        p2.printMap( SetOperations.intersect(p1.getWords(), p2.getWords()) );
        
        //existing only in each passage
        System.out.println("Words existing only in dolphin");
        p1.printMap( SetOperations.difference(p1.getWords(), p2.getWords()) );
        System.out.println("Words existing only in whale");
        p2.printMap( SetOperations.difference(p2.getWords(), p1.getWords()) );

    }
}







/*

วางทับแทนที่ ในทุกๆเครื่องหมาย
.replaceAll("\\p{P}","");

วางแทบแทนที่บางเครื่องหมาย
check & strip punctuation (. , ; : ! ?) 
.replace(".","").replace(",","").replace(";","").replace(":","").replace("!","").replace("?","")

HM.containsKey(word); check ว่ามี key นี้ใน HM หรือไม่ 
HM.put(word, 1); นำเข้า key = word , value = 1;
HM.get(word); จะ return value ของ key นั้นๆ
HM.keySet()  = Set<> ใช้ควบคู่กับ for(String key : HM.keySet() ) เพื่อ report โดย key = คำแต่ละคำ
แปลง Set -> HashSet  โดย new HashSet<String>(HM.keySet())
แปลง Set/HashSet -> AL โดย new ArrayList<>(A) | A = HM.keyset(),HashSet;

พิมพ์ 5 คำ แล้ว ขึ้นบรรทัดใหม่  
        int n = 0;
        for (String key : AL){
            System.out.printf(...);
            n++;
            if (n % 5 == 0)System.out.println("");
        }

H1.retainAll(H2); return H1 = HM ที่มีคำที่ซ้ำกับ H2 เท่านั้น
H1.removeAll(H2); return H1 = HM ที่มีคำที่ไม่ซ้ำกับ H2 เลย

*/
