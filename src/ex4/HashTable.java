package ex4;

// Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
// Modified by Fernando Porrino Serrano for academic purposes.

import java.util.ArrayList;

/**
 *
 */
public class HashTable extends Main {
    private int INITIAL_SIZE = 16;
    private int size = 0;
    private boolean modificando=false;
    private HashEntry[] entries = new HashEntry[INITIAL_SIZE];

    /**
     * @return nos devuelve cuantos valores hay en la tabla (el tamaño de la tabla)
     */
    public int size(){
        return this.size;
    }

    /**
     * @return nos devuelve el tamaño real de la tabla, siempre será 16
     */
    public int realSize(){
        return this.INITIAL_SIZE;
    }

    /**
     * @param key clave para los valores que agregemos a la tabla
     * @param value valor que añadiremos a la tabla
     */
    public void put(String key, String value) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        if(entries[hash] == null) {
            entries[hash] = hashEntry;
        }
        else {
            HashEntry temp = entries[hash];
            if(temp.key.equals(key)){
                modificando = true;
                temp.value = hashEntry.value;
            } else {
                while(temp.next != null){
                    temp = temp.next;
                    if(temp.key.equals(key)){
                        modificando = true;
                        temp.value = hashEntry.value;
                        return;
                    }
                }
                temp.next = hashEntry;
                hashEntry.prev = temp;
            }
        }

        if(modificando){
            size++;
        }
    }

    /**
     * @param key clave por la cual buscaremos un valor en la tabla
     * @return nos devuelve el valor encontrado con la clave añadida
     */
    public String get(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {
            HashEntry temp = entries[hash];

            while( !temp.key.equals(key))
                temp = temp.next;

            return temp.value;
        }

        return null;
    }

    /**
     * @param key clave por la cual buscaremos un valor y lo eliminaremos de la tabla
     */
    public void drop(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {

            HashEntry temp = entries[hash];
            while( !temp.key.equals(key))
                temp = temp.next;

            if(temp.prev == null) entries[hash] = temp.next;             //esborrar element únic (no col·lissió)
            else{
                if(temp.next != null) temp.next.prev = temp.prev;   //esborrem temp, per tant actualitzem l'anterior al següent
                temp.prev.next = temp.next;                         //esborrem temp, per tant actualitzem el següent de l'anterior
            }
        }
        size--;
    }

    /**
     * @param key clave por la cual buscaremos un valor en la tabla
     * @return coge el hash de la clave y la divide en 16
     */
    private int getHash(String key) {
        // piggy backing on java string
        // hashcode implementation.
        return Math.abs(key.hashCode()) % INITIAL_SIZE;
    }

    /**
     * Clase que coge la clave y el valor de un elemento para la utilidad de los métodos
     */
    private class HashEntry {
        String key;
        String value;

        // Linked list of same hash entries.
        HashEntry next;
        HashEntry prev;

        public HashEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    /**
     * @return devuelve los elementos añadidos a la tabla
     */
    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if(entry == null) {
                bucket++;
                continue;
            }
            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while(temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    /**
     * @param key
     * @return
     */
    public ArrayList<String> getCollisionsForKey(String key) {
        return getCollisionsForKey(key, 1);
    }

    /**
     * @param key
     * @param quantity
     * @return
     */
    public ArrayList<String> getCollisionsForKey(String key, int quantity){
        /*
          Main idea:
          alphabet = {0, 1, 2}

          Step 1: "000"
          Step 2: "001"
          Step 3: "002"
          Step 4: "010"
          Step 5: "011"
           ...
          Step N: "222"

          All those keys will be hashed and checking if collides with the given one.
        * */

        final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ArrayList<Integer> newKey = new ArrayList();
        ArrayList<String> foundKeys = new ArrayList();

        newKey.add(0);
        int collision = getHash(key);
        int current = newKey.size() -1;

        while (foundKeys.size() < quantity){
            //building current key
            String currentKey = "";
            for(int i = 0; i < newKey.size(); i++)
                currentKey += alphabet[newKey.get(i)];

            if(!currentKey.equals(key) && getHash(currentKey) == collision)
                foundKeys.add(currentKey);

            //increasing the current alphabet key
            newKey.set(current, newKey.get(current)+1);

            //overflow over the alphabet on current!
            if(newKey.get(current) == alphabet.length){
                int previous = current;
                do{
                    //increasing the previous to current alphabet key
                    previous--;
                    if(previous >= 0)  newKey.set(previous, newKey.get(previous) + 1);
                }
                while (previous >= 0 && newKey.get(previous) == alphabet.length);

                //cleaning
                for(int i = previous + 1; i < newKey.size(); i++)
                    newKey.set(i, 0);

                //increasing size on underflow over the key size
                if(previous < 0) newKey.add(0);

                current = newKey.size() -1;
            }
        }

        return  foundKeys;
    }

    /**
     * @param msg mensaje que queremos imprimir por consola
     */
    static void log(String msg) {
        System.out.println(msg);
    }
}