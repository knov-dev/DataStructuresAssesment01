
package spaceproject;

/**
 *
 * @author duncanwalker
 */
public class Cargo 
{
   private String id;
   private String content;
   private int weight;

    public Cargo(String id, String content, int weight) 
     {
        this.id = id;
        this.content = content;
        this.weight = weight;
     }

    public Cargo() 
     {
        
     }

    public boolean checkValidPodtype(String id)
     {
      boolean valid = false;
     
      return true;
     }
            
    
    
    public String getId() 
     {
        return id;
     }

    public void setId(String id) 
     {
        this.id = id;
     }

    public String getContent() 
     {
        return content;
     }

    public void setContent(String content) 
     {
        this.content = content;
     }

    public int getWeight() 
     {
        return weight;
     }

    public void setWeight(int weight) 
     {
        this.weight = weight;
     }

    @Override
    public String toString() 
     {
        return "\nCargo[id: " + id + "; content=" + content+ "; weight=" + weight + "]";
     }

}
