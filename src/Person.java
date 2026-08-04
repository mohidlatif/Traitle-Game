
package src;
import java.util.*;
public class Person{
private boolean allele1;
private boolean allele2;
private boolean gender;
private Disease disease; 
private static int type;
private Couple parent;
private ArrayList<Person> children;

public Person(boolean a1, boolean a2, boolean g, Disease d){
    allele1=a1;
    allele2=a2;
    gender=g;
    disease=d;
    type = disease.getType();
    parent =null;
    children = new ArrayList<>();
}

public Person(boolean a1, boolean a2, boolean g, Disease d, Couple p){
    allele1=a1;
    allele2=a2;
    gender=g;
    disease=d;
    type = disease.getType();
    parent = p;
    children = new ArrayList<>();
}

public Person(boolean a1, boolean a2, boolean g, Disease d, Couple p, ArrayList<Person> c){
    allele1=a1;
    allele2=a2;
    gender=g;
    disease=d;
    type = disease.getType();
    parent =null;
    parent = p;
    children = c;
}

public boolean getA1(){
    return allele1;
}

public boolean getA2(){
    return allele2;
}

public boolean getGender(){
    return gender;
}

public Disease getDisease(){
    return disease;
}

public void addParent(Couple p){
    parent = p;
}

public void addChild(Person p){
    children.add(p);
}

public Couple getParent(){
    if(parent!=null)
    return parent;
    else
    throw new NullPointerException("No Parent Saved");
}

public boolean hasParent(){
    return parent!=null;
}

public ArrayList<Person> getChildren(){
    if(!children.isEmpty())
    return children;
    else
    throw new NullPointerException("No Child Saved");
}

public boolean hasChildren(){
    return !children.isEmpty();
}

public boolean sameParent(Person p){
    return this.parent == p.getParent();
}

 public boolean isAffected(){
    switch(disease.getDiseaseType()){
        case M -> {
            return allele1||allele2;
        }
        case  XLD -> {
            return gender ? allele1 : (allele1 || allele2);
        }
        case XLR -> {
            return gender ? !allele1 : !allele1 && ! allele2;
        }
        case YL -> {
            return gender && (allele1||allele2);
        }
        case AD -> {
            return allele1||allele2;
        }
        case AR -> {
            return !allele1&&!allele2;
        }
        default -> throw new IllegalArgumentException("Disease Type does not exsist");
    }
    }

public static Person random(boolean gender){
    if(!gender && type!=4 )
    return new Person(Math.random()>=0.5, Math.random()>=0.5, gender, new Disease(type));
    else
    return new Person(false, false, gender, new Disease(type));
}


public String toString(){
    String s = "";
    if(allele1){
        s = s + "Domiant ";
    }
    else{
        s = s+"Recessive ";
    }
    if(allele2){
        s = s + "Domiant ";
    }
    else{
        s = s+"Recessive ";
    }
    if(gender){
        s = s + "Male ";
    }
    else{
        s = s+"Female ";
    }
    return s;
} 
}