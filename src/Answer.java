package src;
import java.util.*;
public class Answer {
    public static boolean checkAnswer(String answer, ArrayList<DiseaseType> d) {
        HashSet<Boolean> hs = new HashSet<>();
        int type;
        for(int x=0; x<d.size(); x++){
        type=d.get(x).getType();
            switch (type) {
                case 1 -> hs.add(answer.equalsIgnoreCase("Mitochondrial")||answer.equalsIgnoreCase("M")||answer.equalsIgnoreCase("1"));
                case 2 -> hs.add(answer.equalsIgnoreCase("X-Linked Dominant") ||  answer.equalsIgnoreCase("X Linked Dominant") || answer.equalsIgnoreCase("XLinked Dominant") || answer.equalsIgnoreCase("XLD") || answer.equalsIgnoreCase("2"));
                case 3 -> hs.add(answer.equalsIgnoreCase("X-Linked Recessive") ||  answer.equalsIgnoreCase("X Linked Recessive") || answer.equalsIgnoreCase("XLinked Recessive") || answer.equalsIgnoreCase("XLR") || answer.equalsIgnoreCase("3"));
                case 4 -> hs.add(answer.equalsIgnoreCase("Y-Linked") ||  answer.equalsIgnoreCase("Y Linked") || answer.equalsIgnoreCase("YL") || answer.equalsIgnoreCase("4"));
                case 5 -> hs.add(answer.equalsIgnoreCase("Autosomal Dominant")||answer.equalsIgnoreCase("AD") || answer.equalsIgnoreCase("5"));
                case 6 -> hs.add(answer.equalsIgnoreCase("Autosomal Recessive")||answer.equalsIgnoreCase("AR") || answer.equalsIgnoreCase("6"));
                default -> {
                }
            }
    }
    boolean b = false;
    for(Boolean bool : hs)
        b |= bool;
    return b;   
    }
}