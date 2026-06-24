package chapter1;

public class InstanceIniLesson {
    
    int code = 21; // instance variable
        
    public static void main(String[] args) {
        InstanceIniLesson ini1 = new InstanceIniLesson();
        { //not instance initializer block, but use for scope
              int z=100;
            System.out.println("Feathers");
        }
        {
            System.out.println(ini1.code);
        }
    }
    { //instance initializer block
        System.out.println(code);
        System.out.println("Ini 1");
        code = 2;
        code2=1000;
    }
    { //instance initializer block
        System.out.println("Snowy");
    }
    public InstanceIniLesson(){
        code=1000;
        System.out.println(code2);
    }
    int code2= 46;
}
//class
//class var
//instance ini1
//method

//instance ini2
//instance ini3

//order of ini
//class var
//instance ini block
//constructor