

import java.util.Iterator;
import java.util.NoSuchElementException;
public class HashedDictionarySC<K, V> implements DictionaryInterface<K, V> {
    // The dictionary:
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5; // Must be prime
    private static final int MAX_CAPACITY = 10000;

    // The hash table:
    private Node<K, V>[] hashTable;
    private int tableSize; // Must be prime
    private static final int MAX_SIZE = 2 * MAX_CAPACITY;
    private boolean initialized = false;
    private static final double MAX_LOAD_FACTOR = 0.9; // Fraction of hash table that can be filled

    public HashedDictionarySC() {
        this(DEFAULT_CAPACITY);
    } // end default constructor

    public HashedDictionarySC(int initialCapacity) {
        checkCapacity(initialCapacity);
        numberOfEntries = 0; // Dictionary is empty

        // Set up hash table:
        // Initial size of hash table is same as initialCapacity if it is prime;
        // otherwise increase it until it is prime size
        int tableSize = getNextPrime(initialCapacity);
        checkSize(tableSize);

        // The assignment is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        Node<K, V>[] temp = new Node[tableSize];
        hashTable = temp;

        initialized = true;
    } // end constructor

    // -------------------------
    // We've added this method to display the hash table for illustration and testing
    // -------------------------
    public void displayHashTable() {
        checkInitialization();
        for(int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null) {
                System.out.println("null");
            }
            else {
                Node<K, V> current = hashTable[i];   
                
                while(current != null) {
                    System.out.print(current.getKey() + " " + 
                            current.getValue() + " ");
                    current = current.getNextNode();
                }
                
                System.out.println();
            }
        }
        System.out.println();
    } // end displayHashTable

    public V add(K key, V value) {
        checkInitialization();
        V prev = null;   
        int index = getHashIndex(key);
        if(hashTable[index] == null) {
            hashTable[index] = new Node<K, V>(key, value);
            numberOfEntries++;
        }
        else {
            Node<K, V> current = hashTable[index];
            Node<K, V> last = null;
        
            while((current != null) && !key.equals(current.getKey())) {
                last = current;
                current = current.getNextNode();
            }
            
            if(current == null) {
                Node<K, V> newN = new Node<K, V>(key, value);
                last.setNextNode(newN);
                numberOfEntries++;
            }
            else {
                prev = current.getValue();
                current.setValue(value);
            }
        }
        return prev;
    } // end add

    public V remove(K key) {
        checkInitialization();
        V prev = null;
        int index = getHashIndex(key);
       
        Node<K, V> current = hashTable[index];
        Node<K, V> last = null;
        
        while((current != null) && !key.equals(current.getKey())) {
            last = current;
            current = current.getNextNode();
        }
        
        if(current != null) {
            prev = current.getValue();
            
            if(last == null) {
                hashTable[index] = current.getNextNode();
            }
            else {
                last.setNextNode(current.getNextNode());
            }
            numberOfEntries--;
        }
        return prev;
    } // end remove

    public V getValue(K key) {
        checkInitialization();
        V result = null;

        int index = getHashIndex(key);

        // Search chain beginning at hashTable[index] for a node that contains key
        Node<K, V> currentNode = hashTable[index];

        while ( (currentNode != null) && !key.equals(currentNode.getKey()) )
            currentNode = currentNode.getNextNode();

        if (currentNode != null) // Key found; get value for return
            result = currentNode.getValue();
        // Else not found; result is null

        return result;
    } // end getValue

    // The following methods that appear between the dashed lines
    // are the same as for open addressing
    // ----------------------------------------------------------
    public boolean contains(K key) {
        return getValue(key) != null;
    } // end contains

    public boolean isEmpty() {
        return numberOfEntries == 0;
    } // end isEmpty

    public int getSize() {
        return numberOfEntries;
    } // end getSize

    public final void clear() {
        for (int index = 0; index < hashTable.length; index++)
            hashTable[index] = null;

        numberOfEntries = 0;
    } // end clear

    public Iterator<K> getKeyIterator() {
        return new KeyIterator();
    } // end getKeyIterator

    public Iterator<V> getValueIterator() {
        return new ValueIterator();
    } // end getValueIterator

    private int getHashIndex(K key) {
        int hashIndex = key.hashCode() % hashTable.length;

        if (hashIndex < 0)
            hashIndex = hashIndex + hashTable.length;

        return hashIndex;
    } // end getHashIndex
    // -------------------------

    // Increases the size of the hash table to a prime >= twice its old size.
    // In doing so, this method must rehash the table entries.
    // Precondition: checkInitialization has been called.
    private void enlargeHashTable() {
        Node<K, V>[] oldTable = hashTable;
        int oldSize = oldTable.length;
        int newSize = getNextPrime(oldSize + oldSize);
        checkSize(newSize);

        // The assignment is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        Node<K, V>[] tempTable = new Node[newSize];
        hashTable = tempTable;
        numberOfEntries = 0; // Reset number of dictionary entries, since
        // it will be incremented by add during rehash

        // Rehash dictionary entries from old array to new, bigger array.
        for (int index = 0; index < oldSize; ++index) {
            // Rehash chain in old table
            Node<K, V> currentNode = oldTable[index];

            while (currentNode != null) {// Skip empty lists 
                add(currentNode.getKey(), currentNode.getValue());
                // Easy, but deallocates entry and reallocates it **********************
                currentNode = currentNode.getNextNode();
            } // end while
        } // end for
    } // end enlargeHashTable

    // Returns true if lambda > MAX_LOAD_FACTOR for hash table;
    // otherwise returns false.
    private boolean isHashTableTooFull() {
        return numberOfEntries > MAX_LOAD_FACTOR * hashTable.length;
    } // end isHashTableTooFull

    private int getNextPrime(int integer) {
        if (integer % 2 == 0) // If even, add 1 to make odd
            integer++;

        while(!isPrime(integer)) // Test odd integers
            integer = integer + 2;

        return integer;
    } // end getNextPrime

    // Returns true if the given integer is prime.
    private boolean isPrime(int integer) {
        boolean result;
        boolean done = false;


        if ( (integer == 1) || (integer % 2 == 0) ) // 1 and even numbers are not prime
            result = false;
        else if ( (integer == 2) || (integer == 3) ) // 2 and 3 are prime
            result = true;
        else { // Integer is odd and >= 5 
            assert (integer % 2 != 0) && (integer >= 5);

            // A prime is odd and not divisible by every odd integer up to its square root
            result = true; // Assume prime
            for (int divisor = 3; !done && (divisor * divisor <= integer); divisor = divisor + 2) {
                if (integer % divisor == 0) {
                    result = false; // Divisible; not prime
                    done = true;
                } // end if
            } // end for
        } // end else

        return result;
    } // end isPrime

    // Throws an exception if this object is not initialized.
    private void checkInitialization() {
        if (!initialized)
            throw new SecurityException ("HashedDictionary object is not initialized properly.");
    } // end checkInitialization

    // Ensures that the client requests a capacity
    // that is not too small or too large.
    private void checkCapacity(int capacity) {
        if (capacity < DEFAULT_CAPACITY)
            capacity = DEFAULT_CAPACITY;
        else if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a dictionary " +
                    "whose capacity is larger than " + MAX_CAPACITY);
    } // end checkCapacity

    // Throws an exception if the hash table becomes too large.
    private void checkSize(int size) {
        if (tableSize > MAX_SIZE)
            throw new IllegalStateException("Dictionary has become too large.");
    } // end checkSize

    private class KeyIterator implements Iterator<K> {
        private int currentIndex; // Current index in hash table
        private Node<K, V> currentNode;

        private KeyIterator()
        {
            currentIndex = -1;
            currentNode = getNextChain(); // Set to first non-empty chain
        } // end default constructor

        private Node<K, V> getNextChain()
        {
            // Skip empty lists
            while ( (currentNode == null) && (currentIndex < hashTable.length - 1) )
            {
                currentIndex++;
                currentNode = hashTable[currentIndex];
            } // end while

            return currentNode;
        } // end getNextChain

        public boolean hasNext()
        {
            return currentNode != null;
        } // end hasNext

        public K next()
        {
            K result = null;

            if (hasNext())
            {
                result = currentNode.getKey();
                currentNode = currentNode.getNextNode(); // Follow chain

                if (currentNode == null) // If at end of chain
                    currentNode = getNextChain();
            }
            else
                throw new NoSuchElementException();

            return result;
        } // end next

        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    } // end KeyIterator

    private class ValueIterator implements Iterator<V>
    {
        private int currentIndex; // Current position in hash table
        private Node<K, V> currentNode;

        private ValueIterator()
        {
            currentIndex = -1;
            currentNode = getNextChain(); // Set to first non-empty chain
        } // end default constructor

        private Node<K, V> getNextChain()
        {
            // Skip empty lists
            while ( (currentNode == null) && (currentIndex < hashTable.length - 1) )
            {
                currentIndex++;
                currentNode = hashTable[currentIndex];
            } // end while

            return currentNode;
        } // end getNextChain

        public boolean hasNext()
        {
            return currentNode != null;
        } // end hasNext

        public V next()
        {
            V result = null;

            if (hasNext())
            {
                result = currentNode.getValue();
                currentNode = currentNode.getNextNode();
                if (currentNode == null)
                    currentNode = getNextChain();
            } // end if
            else
                throw new NoSuchElementException();

            return result;
        } // end next

        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        } // end remove
    } // end getValueIterator

    private class Node<S, T>
    {
        private S key;
        private T value;
        private Node<S, T> next;

        private Node(S searchKey, T dataValue)
        {
            key = searchKey;
            value = dataValue;
            next = null;
        } // end constructor

        private Node(S searchKey, T dataValue, Node<S, T> nextNode)
        {
            key = searchKey;
            value = dataValue;
            next = nextNode;
        } // end constructor

        private S getKey()
        {
            return key;
        } // end getKey

        private T getValue()
        {
            return value;
        } // end getValue

        // No setKey method!!*****

        private void setValue(T newValue)
        {
            value = newValue;
        } // end setValue

        private Node<S, T> getNextNode()
        {
            return next;
        } // end getNextNode

        private void setNextNode(Node<S, T> nextNode)
        {
            next = nextNode;
        } // end setNextNode
    } // end Node
} // end HashedDictionarySC