import java.util.*;
import java.util.ArrayList;


public class Users {


    public String post_private_msg(String text, String email){
        querry = "INSERT INTO Messages(message_id, timestamp, text) ";
        message_id = "";//to do
        Date date=new Date();
        timestamp = date.getTime();
        querry += "VALUES (" + message_id + ", " + timestamp + ", "
                +  text +" ) ";
        querry += "INSERT INTO ";
        return querry;
    }

    public String post_ChatGroup_msg(String text, String chatGroup_name){
        querry = "INSERT INTO ChatGroup_msg(message_id, timestamp, text) ";
        message_id= "";
        Date date=new Date();
        timestamp = date.getTime();
        querry += "VALUES (" + message_id + ", " + timestamp + ", "
                +  text + ", " + " );";
        return querry;

    }
    public String create_ChatGroup(String name, int duration){
        querry = "INSERT INTO ChatGroups(name, duration, owner) ";
        querry += "VALUES (" + name + ", " + duration + ", " + this.email +");";
        return querry;
    }
    public String invite(String email, String chatGroup_name){
        post_private_msg("Invited to ChatGroup" + chatGroup_name,email);
        return "";
    }
    public String accept_ChatGroup_inv(int message_id) {

    return "";
    }
    public String get_message_id(String message_id){
        querry= "SELECT M.message_id FROM Messages M";
        querry+= "WHERE M.message_id = " + message_id;
        return querry;
    }
    public String display_more(){
        //"scroll" to display more than 7 messages
        return "";
    }
    public String delete_msg(){
        return "";
    }
    public String invite_friend(){
        return "";
    }
    public String modify_chatgroup(){
        return "";
    }













    public String search_msg_match_all(int n, String topic_words[]){
        querry = "SELECT * FROM Messages M, ";
    }
    public String search_user(String email){
        querry = "SELECT * FROM Users U ";
        querry += "WHERE U.email=" + email;
        return querry;
    }
    public String search_user(String topic_words[]){
        querry = "SELECT DISTINCT * FROM Users U, topic_words T ";
        querry += "WHERE U.email=T.email";
        for(int i=0;i<topic_words.length;i++){
            querry += (" AND " + topic_words[i] + " IN ");
            querry += "SELECT DISTINCT T.word FROM topic_m T" + i;
            querry += "WHERE T"+ i + ".email=U.email";
        }
        return querry;
    }
    public String search_user_days(int n){ //most recent posting is within the last n days
        querry = "SELECT DISTINCT * FROM Users U, Messages M ";
        querry += "WHERE U.email=M.sender AND "+ current_time + "-M.timestamp<=" + n;
        return querry;
    }
    public String search_user_msg(int m){ //n or more messages posted within the last 7 days
        querry = "SELECT DISTINCT * FROM Users U ";
        querry += "WHERE "+ n + "<=COUNT(SELECT * FROM Messages M WHERE U.email=M.sender";
        querry += " AND " + current_time + "-M.timestamp<=7";
        return querry;
    }

    public String accept_friend(String email){
        querry = "INSERT INTO friend(email1, email2) ";
        querry += "VALUES (" + email + ", " + this.email + ");";
        return querry;
    }
}
