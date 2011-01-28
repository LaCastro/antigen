/* Virus infection that has genotype, phenotype and ancestry */

import java.util.*;

public class Virus {

	// fields
	private Virus parentVirus;
	private Phenotype antigenicType;
	private double birth;	// measured in years relative to burnin
	private boolean trunk;	// fill this at the end of the simulation
	private int location;
	
	// initialization
	public Virus() {
//		antigenicType = new Phenotype();
		antigenicType = PhenotypeFactory.makeVirusPhenotype();
	}
	
	// replication, copies the virus, but remembers the ancestry
	public Virus(Virus v, int d) {
		parentVirus = v;
		antigenicType = v.getPhenotype();
		birth = Parameters.getDate();
		location = d;
	}
	
	public Virus(Virus v, int d, Phenotype p) {
		parentVirus = v;
		antigenicType = p;
		birth = Parameters.getDate();
		location = d;
	}
	
	// methods
	public Phenotype getPhenotype() {
		return antigenicType;
	}
	public void setPhenotype(Phenotype p) {
		antigenicType = p;
	}	
	public double getBirth() {
		return birth;
	}
	public Virus getParent() {
		return parentVirus;
	}
	public boolean isTrunk() {
		return trunk; 
	}
	public void makeTrunk() {
		trunk = true;
	}
	public int getLocation() {
		return location;
	}	
	
	// returns a mutated copy, original virus left intact
	public Virus mutate() {
	
		Phenotype mutP = antigenicType.mutate();		// mutated copy
		Virus mutV = new Virus(this,location,mutP);
		return mutV;
		
	}
	
	public Virus commonAncestor(Virus virusB) {
		
		// go through current virus's history and add to a set
		Virus lineage = this;
		Set<Virus> ancestry = new HashSet<Virus>();
		while (lineage.getParent() != null) {
			lineage = lineage.getParent();
			ancestry.add(lineage);
		}
		
		// go through other virus's history and add to this set, stop when duplicate is encountered and return this duplicate
		lineage = virusB;
		while (lineage.getParent() != null) {
			lineage = lineage.getParent();
			if (!ancestry.add(lineage)) {
				break;
			}
		}		
		
		
		return lineage;
		
	}
	
	public double distance(Virus virusB) {
		Virus ancestor = commonAncestor(virusB);
		double distA = getBirth() - ancestor.getBirth();
		double distB = virusB.getBirth() - ancestor.getBirth();
		return distA + distB;
	}
	
	public String toString() {
		return Integer.toHexString(this.hashCode());
	}

}