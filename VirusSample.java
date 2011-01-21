/* Stores a list of Viruses that have sampled during the course of the simulation */

import java.util.*;
import java.io.*;

public class VirusSample {

	// fields
	private static List<Virus> sample = new ArrayList<Virus>();
		
	// static methods
	public static void add(Virus v) {
		sample.add(v);
	}
	public static void clear() {
		sample.clear();
	}
	
	public static void printTips() {
		
		try {
			File tipFile = new File("out.tips");
			tipFile.delete();
			tipFile.createNewFile();
			PrintStream tipStream = new PrintStream(tipFile);
			for (int i = 0; i < sample.size(); i++) {
				Virus v = sample.get(i);
				double b = v.getBirth();
				if (b >= 0) {
					Phenotype p = v.getPhenotype();
					tipStream.printf("%.4f\t%s\n", b, p);
				}
			}
			tipStream.close();
		} catch(IOException ex) {
			System.out.println("Could not write to file"); 
			System.exit(0);
		}
		
	}
	
	public static void printPaths() {
		
		try {
			File pathFile = new File("out.paths");
			pathFile.delete();
			pathFile.createNewFile();
			PrintStream pathStream = new PrintStream(pathFile);
			for (int i = 0; i < sample.size(); i++) {
				double chanceOfSuccess = Parameters.pathSamplingProportion;
				if (Random.nextBoolean(chanceOfSuccess)) {
					Virus v = sample.get(i);
					double b = v.getBirth();
					boolean t = v.isTrunk();
					Phenotype p = v.getPhenotype();
					if (b >= 0) {
						while (b >= 0 && v.getParent() != null) {
							pathStream.printf("{%.4f,%d,%s}\t", b, (t)?1:0, p);
							v = v.getParent();
							b = v.getBirth();
							t = v.isTrunk();
							p = v.getPhenotype();
						}
						pathStream.println();
					}
				}
			}
			pathStream.close();
		} catch(IOException ex) {
			System.out.println("Could not write to file"); 
			System.exit(0);
		}
		
	}	

}