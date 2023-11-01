import java.util.*;

/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 
{
    public double cgRatio(String dna) 
    {
        double ratio = 0.0;
        int cCount = 0;
        int gCount = 0;
        int startIndex = 0;
    
        while (startIndex < dna.length()) {
            if (dna.charAt(startIndex) == 'C') {
                cCount++;
            } else if (dna.charAt(startIndex) == 'G') {
                gCount++;
            }
            startIndex++;
        }
    
        int totalCount = cCount + gCount;
        if (dna.length() > 0) { // To prevent division by zero
            ratio = (double) totalCount / dna.length();
        }
        return ratio;
    }
    
    public int countCTG(String dna)
    {
        int count = 0;
        int currIndex = dna.indexOf("CTG");
        while (currIndex != -1) {
            count++;
            currIndex = dna.indexOf("CTG", currIndex + 3);
        }
        return count;
    }

    public void testCgRatio(String dna)
    {
        double r = cgRatio(dna);
        System.out.println("CG to DNA ratio: " + r);
    }
    
    public void testCountCTG(String dna)
    {
        int c = countCTG(dna);
        System.out.println("Total CTG Count: " + c);
    }
    
    public static void main(String[] args) {
        Part2 part2 = new Part2();
        Scanner scn = new Scanner(System.in);
        System.out.print("Enter DNA: ");
        String dna = scn.nextLine();
        part2.testCgRatio(dna);
        part2.testCountCTG(dna);
    }
}
