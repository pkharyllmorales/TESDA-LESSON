package chapter1;
import otherFolder.a.*;
// import otherFolder.a.A;
// import otherFolder.a.B;
// import otherFolder.a.C;
// import otherFolder.a.D;
import otherFolder.*;
// import otherFolder.SunFlower;

import java.util.*;
import java.sql.Date;

/*  ✅
*   import java.sql.Date;
*   !!variable declaration
*   Date sqlDate;
*   java.util.Date date1;
*/

/*  ✅
*   import java.util.Date; 
*   
*   !!variable declaration
*   java.sql.Date sqlDate;
*   Date date1;
*/

/*  ✅
*   
*   
*   !!variable declaration
*   java.sql.Date sqlDate;
*   java.util.Date date1;
*/

/*  ❌
*   import java.util.*;
*   import java.sql.*;
*   
*   !!variable declaration
*   Date date;❌

*   
*/

public class NamingConflictLesson {
    public static void main(String[] args) {
        Date sqlDate;
        java.util.Date date1;
        A a;
        B b;
        C c;
        D d;
        SunFlower sunF;
        Random r = new Random();
        // ArrayList list = new ArrayList();
        // Scanner scanner;
    }

}
