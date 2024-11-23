
package movie;
import java.io.*;
import java.util.*;
public class Cinema 
{
    private String id, name;
    private ArrayList<String> time;

    public Cinema(String id, String name, ArrayList<String> time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }
    public Cinema(String name, ArrayList<String> time) {
        
        this.name = name;
        this.time = time;
    }
    public String getName()
    {
        return this.name;
    }
    public String getTime() {
        
        StringBuilder sb = new StringBuilder();
        
        for(String i: time)
        {
            sb.append(i);
            sb.append("\n");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }
    public ArrayList<String> getTimeList() {
        return new ArrayList<>(this.time); 
    }
    
}
