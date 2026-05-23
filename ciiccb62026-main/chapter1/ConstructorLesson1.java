package chapter1;

public class ConstructorLesson1 {
    public static void main(String[] args) {
        Chick andoks = new Chick();
        Chick chooks = new Chick();
        Chick gannam = andoks;
        andoks.Chick();
        Chicken jolChicken = new Chicken();
        System.out.println("end of program");
    }

}

class Chick{ //🥚
    public Chick(){
        System.out.println("🐣 in constructor");
    }
    public void Chick(){
        System.out.println("🐥💬 I'm a method");
    }
}

class Chicken{
    int numEggs = 0; //initialized on line, class variable or instance variable,
    String name;
    public Chicken(){
        name = "Duke"; //initialized on constructor
    }
}

class Swan{
    int numEggs;
    public static void main(String[] args) {
        Swan mother = new Swan();
        mother.numEggs = 4;//writting object fields
        System.out.println(mother.numEggs); //reading object fields
    }

}
class Name{
    String first = "Theodore";
    String last = "Moose";
    String full = first+last;
    public static void main(String[] args) {
        Name name1 = new Name();
        System.out.println(name1.full);
        name1.first = "Marc";
        name1.last = "Yim";
        System.out.println(name1.full);
    }
}