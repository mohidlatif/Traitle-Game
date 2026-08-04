package src;
public class Gen{
    private Person mother;
    private Person father;
    private Disease disease;
    public Gen(Person p1, Person p2, Disease d){
        if(!p1.getGender())
        mother = p1;
        else
        father = p1;
        if(p2.getGender())
        father = p2;
        else
        mother=p2;
        
        disease = d;
    }
    public Gen(Couple c, Disease d){
        mother = c.getWife();
        father = c.getHusband();
        disease = d;
    }
    public Person getMother(){
        return mother;
    }
    public Person getFather(){
        return father;
    }
    public Disease getDisease(){
        return disease;
    }
    //Gives an Array with n possible children of the mother and father
    public Person[] nextGen(int n){
        Person[] people = new Person[n];
        for(int x=0; x<people.length; x++){
            people[x] = Tester.possiblePerson(new Couple(mother, father), disease);
        }
        return people;
        }
    }
