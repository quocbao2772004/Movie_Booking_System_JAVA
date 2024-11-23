
package movie;
import java.util.*;

public class Movie 
{
    private String url;
    private String id, name;
    private ArrayList<Cinema> cinema_infor;
    private ArrayList<String> movieDate, hall, seat;
    private String price;

    public Movie(String url, String id,String name, ArrayList<Cinema> cinema_infor, ArrayList<String> movieDate) {
        this.url = url;
        this.name = name;
        this.id = id;
        this.cinema_infor = cinema_infor;
        this.movieDate = movieDate;
    }
    public String getUrl()
    {
        return url;
    }
    public String getMovieDate()
    {
        StringBuilder sb = new StringBuilder();
        
        for(String i: movieDate)
        {
            sb.append(i);
            sb.append("\n");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }
    public String getName()
    {
        return name;
    }

    public String getId() {
        return id;
    }
    
    public ArrayList<String> getMovieDateList() 
    {
        return new ArrayList<>(movieDate); 
    }
    public String getCinemaName(int index)
    {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Cinema cinema : cinema_infor) 
        {
            if(count == index) return cinema.getName();
            count+=1;
            
        }
        return cinema_infor.get(cinema_infor.size()-1).getName();
    }
    public String getCinemaTime(int index1, int index2)
    {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Cinema cinema : cinema_infor) 
        {
            
            if(count == index1) return cinema.getTimeList().get(index2);
            count+=1;
            
        }
        return cinema_infor.get(cinema_infor.size()-1).getTimeList().get(index2);
    }
    public String getCinemaInfor() 
    {
        StringBuilder sb = new StringBuilder();
        for (Cinema cinema : cinema_infor) {
            sb.append(cinema.getName());
            sb.append("\n");
            sb.append(cinema.getTime());
            sb.append("\n");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }
    
    @Override
    public String toString()
    {
        return String.format("%s %s \n%s \n%s", this.id, this.name,this.getMovieDate(), this.getCinemaInfor());
    }
    
    
}
