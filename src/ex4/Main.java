package ex4;

//REFACCIÓ: he aplicado el método de refacción main por qué
// he considerado que lo ideal es hacerlo independiente de la clase HasTable
// y tenerlo todo más organizado


/**
 * Clase Main, es donde llamamos a la clase HashTable y procedemos a crear los elementos
 */
public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable();

        // Put some key values.
        for(int i=0; i<30; i++) {
            final String key = String.valueOf(i);
            hashTable.put(key, key);
        }

        // Print the HashTable structure
        HashTable.log("****   HashTable  ***");
        HashTable.log(hashTable.toString());
        HashTable.log("\nValue for key(20) : " + hashTable.get("20") );
    }
}
