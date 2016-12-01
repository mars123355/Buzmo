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

   public String findTopMsg(){
       querry="SELECT count(R.message_id) AS numReceive, R.message_id\n" +
               "FROM Receives R,MyCircle_msg C\n" +
               "WHERE R.message_id = C.circle_message_id\n" +
               "GROUP BY R.message_id\n" +
               "ORDER BY numReceive DESC;";
       return querry;

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
