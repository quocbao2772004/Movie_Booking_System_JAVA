
package movie;
import java.io.IOException;
import static movie.Login.Login_Interface;
import java.util.*;
public class Main 
{
    public static void main(String[] args) throws IOException 
    {
//          Login_Interface();
        FeedbackDatabase fbdtb = new FeedbackDatabase();
        ArrayList<Feedback> arl_feedback = new ArrayList<>(fbdtb.getFeedbacks("Venom: Den khi cai chet chia lia doi ta"));
        for(Feedback i: arl_feedback)
        {
            System.out.println(i.getUser());
            System.out.println(i.getFeedback());
        }
    }
}
