
import java.util.*;
import java.io.*;

public class Exersice1 {

    public static void main(String[] args) {

        String infile = "proposition\\Paradigms\\EX1/planets.txt";
        String outfile = "proposition\\Paradigms\\EX1/output.txt";
        double weight;

        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter weight  = ");
            weight = scan.nextDouble();

            scan = new Scanner(new File(infile));
            PrintWriter write = new PrintWriter(outfile);

            System.out.printf("%-8s%11s%9s\r\n", "Planet", "Gravity", "Weight");
            System.out.printf("============================\r\n");
            write.printf("%s\t\t%s\t\t%s\r\n", "Planet", "Gravity", "Weight");
            write.printf("========================================\r\n");

            while (scan.hasNext()) {
                String name = scan.next();
                double gravity = scan.nextDouble();
                System.out.printf("%-8s%10.3f%10.2f\r\n", name, gravity, weight * gravity);
                write.printf("%s\t\t%.3f\t\t%.2f\r\n", name, gravity, weight * gravity);
                
            }
            write.flush();
            scan.close();
            write.close();
        } catch (Exception e) {
            System.err.println("An error occurs. End program.");
            System.exit(-1);
        }
    }

}
/*
    %-10s คือ    การติดขอบซ้าย หรือ การจองช่องทางขวา ใส่ไปเพื่อให้ gravity สามารถจัดเรียงได้อย่างสวยงาม
                ยิ่งติดลบมากๆ gravity จะยิ่งห่างจาก planet มากขึ้นๆ
                แต่ถ้าปรับน้อยไปจะทำให้ จัดเรียงไม่สวยงาม(เหมือนอย่างตอนไม่ใส่ -10)
    %10.3f      เว้นช่องซ้ายมือไว้10ช่องรวมถึงช่วงที่เราต้องใช้ไปด้วย
                .3 คือ ต้องการ 3 ทศนิยม
    %s\t%.3f    \t ใช้เหมือน tab จะง่ายต่อความสวยงาม แต่ในการเรียนลงไปใน text ไม่นิยมใช้
 */
