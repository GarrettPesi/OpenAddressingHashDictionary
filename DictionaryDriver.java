
import java.util.Iterator;

public class DictionaryDriver {

    public static void main(String[] args) {
        System.out.append("Creating dictionary\n");
        Dictionary<String, Integer> d = new OpenAddressingHashDictionary<>(7);
        System.out.println("Putting values");
        d.put("Apple", 1);
        d.put("Orange", 3);
        d.put("Banana", 5);
        d.put("Grapes", 10);
        System.out.println("Checking size (should be 4): " + d.size());
        System.out.println("Get (should be 1): " + d.get("Apple"));
        System.out.println("Get (should be 5): " + d.get("Banana"));
        System.out.println("Get (should be 10): " + d.get("Grapes"));
        System.out.println("Get (should be null): " + d.get("Pear"));
        System.out.println("Check key/value pairs using iterators...");
        Iterator<String> kit = d.keys().iterator();
        Iterator<Integer> vit = d.values().iterator();
        while(kit.hasNext() && vit.hasNext()) {
            System.out.println(kit.next() + ", " + vit.next());
        }
        
        System.out.println("Remove (should be 5): " + d.remove("Banana"));
        //Slows down here
        System.out.println("Get removed (should be null): " + d.get("Banana"));
        System.out.println("Get size (should be 3): " + d.size());
        System.out.println("Reinsert");
        //Slows down here
        d.put("Banana", 5);
        System.out.println("Get (should be 5): " + d.get("Banana"));
        System.out.println("Change using put");
        d.put("Banana", -10);
        System.out.println("Get (should be -10): " + d.get("Banana"));
        System.out.println("Size (should be 4): " + d.size());
        System.out.println("Check key/value pairs using iterators...");
        kit = d.keys().iterator();
        vit = d.values().iterator();
        while(kit.hasNext() && vit.hasNext()) {
            System.out.println(kit.next() + ", " + vit.next());
        }
        System.out.println("Checking both key and vaule iterators are spent : " + !(kit.hasNext() || vit.hasNext()));
    }
    
}
