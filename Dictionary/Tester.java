import java.util.Iterator;

public class Tester {
    public static void main(String[] args) {
        DictionaryInterface<String, String> dictNames = new HashedDictionarySC<String, String>();
        System.out.println("Create a dictionary:\n");
        if(dictNames.isEmpty()) {
            System.out.println("The dictionary is empty");
            System.out.println();
            
            System.out.println("Testing add():");
            dictNames.add("Bill", "555-1765");
            dictNames.add("Sam", "111");
            dictNames.add("Lin", "222");
            displayHashTable(dictNames);

            System.out.println("\nTesting remove():");
            System.out.println();
            System.out.println("Removing Bill");
            System.out.println();
            System.out.println("The number of Bill was: " + dictNames.remove("hello"));
            System.out.println();
            System.out.println("Replacing phone number of Peter.");

            String oldNumber = dictNames.add("Peter", "333");
            System.out.println("Peter's old number " + oldNumber + " is replaced by 444");
            displayHashTable(dictNames);     
        }
    }

    
    public static void displayHashTable(DictionaryInterface<String, String>myDict) {
        Iterator<String> keyIterator = myDict.getKeyIterator();
        myDict.getKeyIterator();
        Iterator<String> valueIterator = myDict.getValueIterator();
        while(keyIterator.hasNext() && valueIterator.hasNext()) {
            System.out.println(keyIterator.next() + ":" + valueIterator.next());
        }
        System.out.println();
    }
    
}
