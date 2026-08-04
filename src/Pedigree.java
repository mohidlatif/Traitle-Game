package src;
import java.util.*;
public class Pedigree{
   private Heiarchy heiarchy;
   private int affectedFemaleCount;
   private int affectedMaleCount;
   public Pedigree(Heiarchy h){
	heiarchy=h;
	affectedFemaleCount = 0;
	affectedMaleCount = 0;
   }
   public Heiarchy getHeiarchy(){
	return heiarchy;
   }
   //Loops through the datasets and checks if pedigree is valid for specific disease
    public void evaluateDiseases() {
    for (DiseaseType type : DiseaseType.values()) {
        if (isValidFor(type)) {
            heiarchy.addDisease(type);
        }
    }
}
	public void resetTypes(){
		affectedFemaleCount=0;
		affectedMaleCount = 0;
	}

	public boolean checkM(Person male, Person female){
		if(female != null && female.hasParent()){
				boolean wifeParentAffected = female.getParent().getWife().isAffected();
				if(female.isAffected() ^ wifeParentAffected)
					return false;
				}
				if(male != null && male.hasParent()){
				boolean husbandParentAffected = male.getParent().getWife().isAffected();
				if(male.isAffected() ^ husbandParentAffected)
					return false;
				}
				if(female != null && female.hasChildren()){
					for(int p=0; p<female.getChildren().size(); p++){
						if(female.isAffected() ^ female.getChildren().get(p).isAffected())
							return false;
					}
				}
				return true;
	}

	public boolean checkXLD(Person male, Person female){
		if(male != null && male.hasParent()){
					boolean maleParentAffected = male.getParent().getWife().isAffected();
					if(male.isAffected() && !maleParentAffected){
						return false;
					}
				}
				if(male != null && male.hasChildren()){
					for(int p=0; p<male.getChildren().size(); p++){
					Person child = male.getChildren().get(p);
					if(!child.getGender() && male.isAffected() && !child.isAffected()){
							 return false;
						}
					}
				}
			return true;
	}

	public boolean checkXLR(Person male, Person female){
		if(female != null && female.hasParent()){
					boolean fatherAffected = female.getParent().getHusband().isAffected();
					if(female.isAffected() && !fatherAffected){
						return false;
					}          
				}
				if(female != null && female.hasChildren()){
					for(int p=0; p<female.getChildren().size(); p++){
					Person child  = female.getChildren().get(p);
					if(child.getGender() && female.isAffected() && !child.isAffected()){
							return false;
						}
					}
				}
			return true;
	}

	public boolean checkYL(Person male, Person female){
		if(male != null && male.hasParent()){
				boolean husbandParentAffected = male.getParent().getHusband().isAffected();
				if(male.isAffected() ^ husbandParentAffected)
					return false;
				}
				if(female != null && female.isAffected())
					return false;
				if(male != null && male.hasChildren()){
					for(int p=0; p<male.getChildren().size(); p++){
						if(male.isAffected() ^ male.getChildren().get(p).isAffected() && male.getChildren().get(p).getGender())
							return false;
					}
				}
		return true;
	}

	public boolean checkAD(Person male, Person female){
		if (female != null && female.hasParent()) {
            boolean fatherAffected = female.getParent().getHusband().isAffected();
            boolean motherAffected = female.getParent().getWife().isAffected();
				if((!fatherAffected && !motherAffected) && female.isAffected())
							return false;
                        }
                if (male != null && male.hasParent()) {
                            boolean fatherAffected = male.getParent().getHusband().isAffected();
                            boolean motherAffected = male.getParent().getWife().isAffected();
				if((!fatherAffected && !motherAffected) && male.isAffected())
								return false;
            }
			return true;
	}

	public boolean checkAR(Person male, Person female){
		 if (female != null && female.hasParent()) {
                        boolean fatherAffected = female.getParent().getHusband().isAffected();
                        boolean motherAffected = female.getParent().getWife().isAffected();
                        if ((fatherAffected && motherAffected) && !female.isAffected())
                            return false;
                        }
                    if (male != null && male.hasParent()) {
                        boolean fatherAffected = male.getParent().getHusband().isAffected();
                        boolean motherAffected = male.getParent().getWife().isAffected();
                        if ((fatherAffected && motherAffected) && !male.isAffected())
                            return false;
                        }
		return true;
	}
   public boolean isValidFor(DiseaseType disease){
	int type = disease.getType();
	resetTypes();
	ArrayList<Generation> list = heiarchy.getHeiarchy();
	for(int x=0; x<list.size(); x++){
		ArrayList<Couple> clist = list.get(x).getGeneration();
		for(int y=0; y<clist.size(); y++){
			//List of all couples in a generation
			Couple c = clist.get(y);
			Person male = c.getHusband();
			Person female = c.getWife();
			if (male != null && male.isAffected()) 
				affectedMaleCount++;
            if (female != null && female.isAffected()) 
				affectedFemaleCount++;
			switch(type){
				case 1: 
				if(!checkM(male, female))
					return false;
				break;
				case 2:
				if(!checkXLD(male, female))
					return false;
				break;
				case 3:
				if(!checkXLR(male, female))
					return false;
				break;
				case 4:
				if(!checkYL(male, female))
					return false;
				break;
				case 5: 
                if(!checkAD(male, female))
					return false;
				break;
                case 6: 
				if(!checkAR(male, female))
					return false;
				break;
			}
			if (countExceeded()) 
				return false;
		}
	}
	
	return true;
   }

   private boolean countExceeded() {
    return affectedFemaleCount >= 9 || affectedMaleCount >= 9;
}

}
