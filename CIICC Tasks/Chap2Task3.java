public class Chap2Task3 {
    public static void main(String[] args) {
      
        String a = "Wow";
        String b = a;           
        String c = "Different"; 
        String d = "Wow!";     

        boolean b1 = a == b; 
        
        boolean b2 = d.equals(b + "!"); 
        
        boolean b3 = !c.equals(a); 

        System.out.println("b1 (a == b): " + b1);
        System.out.println("b2 (d.equals(b + \"!\")): " + b2);
        System.out.println("b3 (!c.equals(a)): " + b3);
        System.out.println("-------------------------");

        if (b1 && b2 && b3) {
            System.out.println("Success!");
        } else {
            System.out.println("Failure!");
        }
    }
}