package chapter1;

public class PrimitivesLesson {
    public static void main(String[] args) {
        boolean isRaining =false; // true : false
        char c = 65535; ////2^16 'a' 1 //no negative value
        byte b = 127; // 2^8=256 signed -128,0 127
        short s = 32767; //2^16=65536 signed -32768 ,0 32767
        int i = 1000000; //2^32=4b signed
        long l = 1; //2^64
        float f = 1.0f; //2^32 floating point value
        double d = 1.0; //2^64 floating point value
        System.out.println(c);
        
        System.out.println("Character.MAX_VALUE="+ Character.MAX_VALUE);
        System.out.println("Character.MIN_VALUE="+ Character.MIN_VALUE);
        System.out.println("Byte.MAX_VALUE="+ Byte.MAX_VALUE);
        System.out.println("Byte.MIN_VALUE="+ Byte.MIN_VALUE);
        System.out.println("Short.MAX_VALUE="+ Short.MAX_VALUE);
        System.out.println("Short.MIN_VALUE="+ Short.MIN_VALUE);
        System.out.println("Integer.MAX_VALUE="+ Integer.MAX_VALUE);
        System.out.println("Integer.MIN_VALUE="+ Integer.MIN_VALUE);
        System.out.println("Long.MAX_VALUE="+ Long.MAX_VALUE);
        System.out.println("Long.MIN_VALUE="+ Long.MIN_VALUE);
        System.out.println("Float.MAX_VALUE="+ Float.MAX_VALUE);
        System.out.println("Float.MIN_VALUE="+ Float.MIN_VALUE);
        System.out.println("Double.MAX_VALUE="+ Double.MAX_VALUE);
        System.out.println("Double.MIN_VALUE="+ Double.MIN_VALUE);
        System.out.println(1_0_00_00_0.0_0);


    }
}
