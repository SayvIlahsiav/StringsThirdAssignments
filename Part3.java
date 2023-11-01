import edu.duke.*;
import java.util.*;

/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Part3 
{
    // Finds the index of the given stop codon 
    // that occurs after the start index and is a multiple of 3 away
    public int findStopCodon(String dna, int startIndex, String stopCodon)
    {
        int currIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1)
        {
            int diff = currIndex - startIndex;
            if(diff % 3 == 0) return currIndex; 
            else currIndex = dna.indexOf(stopCodon, currIndex + 1);
        }
        return -1;   
    }
    
    // Finds a single gene in a strand of DNA
    public String findGene(String dna, int where)
    {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1) return "";
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        int[] stopIndices = {taaIndex, tagIndex, tgaIndex}; // Array containing all indices
        int minIndex = -1;  // Initialize to -1 for "not found"
        for (int index : stopIndices) {
            // If this index is found and (minIndex is not set or this index is smaller)
            if (index != -1 && (minIndex == -1 || index < minIndex)) minIndex = index;
        }
        if (minIndex == -1) return "";
        return dna.substring(startIndex, minIndex + 3);
    }
    
    // Retrieves all genes present in a strand of DNA
    public StorageResource getAllGenes(String dna)
    {
        StorageResource geneList = new StorageResource();
        int startIndex = 0;
        int geneCount = 0;
        while(true)
        {
            String gene = findGene(dna, startIndex);
            if (gene.isEmpty()) break;
            geneCount++;
            geneList.add(gene);
            System.out.println("Gene" + geneCount + ": " + gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
        }
        if(geneCount == 0) System.out.println("None!");
        System.out.println("Total genes: " + geneCount);
        return geneList;
    }
    
    public double cgRatio(String dna) 
    {
        double ratio = 0.0;
        int cCount = 0;
        int gCount = 0;
        int startIndex = 0;
        while (startIndex < dna.length()) {
            if (dna.charAt(startIndex) == 'C') cCount++;
            else if (dna.charAt(startIndex) == 'G') gCount++;
            startIndex++;
        }
        int totalCount = cCount + gCount;
        // To prevent division by zero
        if (dna.length() > 0) ratio = (double) totalCount / dna.length();
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
    
    // Processes a list of genes
    public void processGenes(StorageResource sr) {
        int countLong = 0;
        int countHighCg = 0;
        int longestGene = 0;
        for (String gene : sr.data()) {
            if (gene.length() > 60) {
                countLong++;
                System.out.println("Gene length > 60: " + gene);
            }
            if (cgRatio(gene) > 0.35) {
                countHighCg++;
                System.out.println("High C-G ratio: " + gene);
            }
            longestGene = Math.max(longestGene, gene.length());
        }
        System.out.println("Total genes > 60 characters: " + countLong);
        System.out.println("Total genes with high C-G ratio: " + countHighCg);
        System.out.println("Length of longest gene: " + longestGene);
    }
    
    // Test method
    public void testProcessGenes()
    {
        //FileResource fr = new FileResource("brca1line.fa");
        FileResource fr = new FileResource("GRch38dnapart.fa");
        //FileResource fr = new FileResource("test1.fa");
        //FileResource fr = new FileResource("test2.fa");
        String dna = fr.asString().toUpperCase();
        System.out.println("DNA Loaded: " + dna);
        System.out.println("Genes found:");
        StorageResource sr = getAllGenes(dna);
        System.out.println("");
        processGenes(sr);
        System.out.println("Total CTG Count: " + countCTG(dna));
    }
    
    public static void main(String[] args) 
    {
        Part3 part3 = new Part3();
        part3.testProcessGenes();
    }
}
