/**
 * Created by Mars on 11/16/16.
 */
public class Managers extends Users {


    public String findActiveUsers(){
        querry= "SELECT *\n" +
                "FROM (SELECT sender, COUNT(sender) AS numMessages\n" +
                "FROM Messages\n" +
                "GROUP BY sender\n" +
                "ORDER BY numMessages DESC)\n" +
                "WHERE ROWNUM<=3;";
        return querry;
    }

   public void findTopMsg(){

   }
   public String findInactiveUsers(){
        querry="SELECT sender, COUNT(sender) AS numMessages\n" +
                "FROM Messages\n" +
                "GROUP BY sender\n" +
                "HAVING COUNT(sender)<4\n" +
                "ORDER BY numMessages ASC;";
        return querry;
   }
   public void displayReport(){

   }
}
