import java.util.*;
import edu.duke.*;

/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public int findStopCodon(String dna, int startIndex, String stopCodon)
    {
        int currIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1)
        {
            int diff = currIndex - startIndex;
            if(diff % 3 == 0)
            {
                return currIndex; 
            }
            else
            {
                currIndex = dna.indexOf(stopCodon, currIndex + 1);
            }
        }
        return -1;   
    }
    
    public String findGene(String dna, int where)
    {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1)
        {
            return "No start codon found.";
        }
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        int[] stopIndices = {taaIndex, tagIndex, tgaIndex}; // Array containing all indices
        int minIndex = -1;  // Initialize to -1 for "not found"
        for (int index : stopIndices) {
            // If this index is found and (minIndex is not set or this index is smaller)
            if (index != -1 && (minIndex == -1 || index < minIndex)) {
                minIndex = index;
            }
        }
        if (minIndex == -1)
        {
            return "No valid stop codon found.";
        }
        return dna.substring(startIndex, minIndex + 3);
    }
    
    public void printAllGenes(String dna)
    {
        int startIndex = 0;
        int i = 0;
        while(true)
        {
            String gene = findGene(dna, startIndex);
            if (gene.isEmpty() || "No start codon found.".equals(gene) || "No valid stop codon found.".equals(gene))
            {
                break;
            }
            i++;
            System.out.println("Gene" + i + ": " + gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
        }
        if(i == 0) 
        {
            System.out.println("None!");
        }
    }
    
    public StorageResource getAllGenes(String dna)
    {
        StorageResource geneList = new StorageResource();
        int startIndex = 0;
        int i = 0;
        while(true)
        {
            String gene = findGene(dna, startIndex);
            if (gene.isEmpty() 
            || "No start codon found.".equals(gene) 
            || "No valid stop codon found.".equals(gene))
            {
                break;
            }
            i++;
            geneList.add(gene);
            //System.out.println("Gene" + i + ": " + gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
        }
        if(i == 0) 
        {
            System.out.println("None!");
        }
        return geneList;
    }
    
    public void testFindStopCodon()
    {
        String dna = "xxxyyyzzzTAAxxxyyyzzzTAAxx";
        int dex = findStopCodon(dna, 0, "TAA");
        if (dex != 9) System.out.println("error on 9");
        dex = findStopCodon(dna, 9, "TAA");
        if (dex != 21) System.out.println("error on 21");
        dex = findStopCodon(dna, 1, "TAA");
        if (dex != -1) System.out.println("error on 26");
        dex = findStopCodon(dna, 0, "TAG");
        if (dex != -1) System.out.println("error on 26 TAG");
        System.out.println("Tests finished.");
    }
    
    public void testFindGene()
    {
        String[] dnas = {
            "ACCTAACCCGTG",        // DNA with no "ATG" and no valid stop codon
            "ACCTAAACCCGTG",       // DNA with no "ATG" but with a valid stop codon
            "ACCATGTAAACCCGTG",    // DNA with "ATG" and one valid stop codon
            "ACCATGTAGTAAACCCGTGTAA", // DNA with "ATG" and multiple valid stop codons
            "ACCATGGTACCCGTG",     // DNA with "ATG" and no valid stop codons
            "ACCATGTGATAGACCCGTG", // DNA with "ATG" and one valid stop codon, but length not a multiple of 3
            "",                    // Empty DNA string
            "ATG",                 // DNA with only "ATG"
            "TAA",                 // DNA with only a valid stop codon
            "XYZXYZXYZXYZ"         // DNA with random letters not forming any valid codons
        };
        for (int i = 0; i < dnas.length; i++) {
            System.out.println("DNA " + (i + 1) + ": " + dnas[i]);
            System.out.println("Gene " + (i + 1) + ": " + findGene(dnas[i], 0) + "\n");
        }
        System.out.println("_._._._._._._._._._._._._._._._._._._._._._");
    }
    
    public void testPrintAllGenes()
    {
        //String dna = "CCCATGAGCTAAAGCTAGATGGTCTAGATGGCTGAACCATGAGCTAATTTAA";
        Scanner scn = new Scanner(System.in);
        System.out.print("Enter DNA: ");
        String dna = scn.nextLine();
        System.out.println("Finding Genes...");
        System.out.println("Genes Found:");
        printAllGenes(dna);
    }
    
    public void testGetAllGenes()
    {
        //String dna = "CCCATGAGCTAAAGCTAGATGGTCTAGATGGCTGAACCATGAGCTAATTTAA";
        Scanner scn = new Scanner(System.in);
        System.out.print("Enter DNA: ");
        String dna = scn.nextLine();
        System.out.println("Finding Genes...");
        System.out.println("Genes Found:");
        StorageResource genes = getAllGenes(dna);
        int i = 0;
        for(String g: genes.data())
        {
            i++;
            System.out.println("Gene" + i + ": " + g);
        }
    }
    
    public static void main(String[] args) {
        Part1 part1 = new Part1();
        part1.testGetAllGenes();
    }
}
