
package movie;
import java.io.*;
import java.util.*;

public class Init 
{
    public static ArrayList<Movie> arl_movie = new ArrayList<>();
    
    public void Init_information() throws FileNotFoundException, IOException
    {
        ArrayList<Movie> arl = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("DATA_movie.txt"), "UTF-8"));
        String line = "";
        String id = "";
        ArrayList<String> date = new ArrayList<>();
        String name = "";
        String cinema_name = "";
        ArrayList<String> time = new ArrayList<>(); 
        ArrayList<Cinema> arl_cinema = new ArrayList<>();
        
        while((line = reader.readLine())!=null)
        {
            String[] s = line.split("\\s+");
            String first_word = s[0];
            StringBuilder sb = new StringBuilder();
            for(int i=1;i<=s.length - 1 ; i++)
            {
                sb.append(s[i]);
                sb.append(" ");
            }
            String content = sb.toString().trim();
            if(first_word.equals("ID:"))
            {
                id = content;
            }
            else if(first_word.equals("Name:"))
            {
                name = content;
            }
            else if(first_word.equals("Date:"))
            {
                date.add(content);
            }
            else if(first_word.equals("Cinema:"))
            {
                if(time.size()!=0 && !cinema_name.equals(""))
                {
                    ArrayList<String> temp_time = new ArrayList<>(time); 
                    Cinema c1 = new Cinema(cinema_name, temp_time);
                    arl_cinema.add(c1);
                    time.clear();
                }
                cinema_name = content;
            }
            else if(first_word.equals("Time:"))
            {
                time.add(content);
            }

            else if(first_word.equals("Image:"))
            {
                String url = content;
                arl_cinema.add(new Cinema(cinema_name, new ArrayList<>(time)));
                arl_movie.add(new Movie(url, id, name, new ArrayList<>(arl_cinema), new ArrayList<>(date))); 
                date.clear();
                time.clear();
                id = "";
                name = "";
                cinema_name = "";
                arl_cinema.clear();
            }  
        }
       
    }
    public void print_Infor_Movie() throws IOException
    {
        
        for(Movie i: arl_movie)
        {
            System.out.println(i);
        }
    }
    
    
}
