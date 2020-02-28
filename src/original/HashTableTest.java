package original;

import ex1.HashTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HashTableTest {

    HashTable hashTable = new HashTable();

    @Test
    void size() {
        hashTable.put("0", "Andres");
        hashTable.put("1", "Andres");

        // modifico el valor con el key "1"
        hashTable.put("1", "Andresd");

        hashTable.put("2", "Angel");

        // añado un nuevo valor en el bucket[0]
        hashTable.put("77", "pedro");

        hashTable.put("3", "Paco");

        //En total tenemos 4 tablas de hash, el 1º guarda 2 valores, y los siguientes solamente 1

        // Compruebo que tenemos 4 tablas de hash
        Assertions.assertEquals(4, hashTable.size());
    }

    @Test
    void realSize() {
    }

    @Test
    void put() {
        Assertions.assertEquals("", hashTable.toString());
        hashTable.put("0", "Andreo");
        hashTable.put("1", "Andresito");

        //modifico el valor con key "1" (error corregido)
        hashTable.put("1", "Andresa");

        hashTable.put("2", "Angel");

        //compruebo que puedo añadir otro valor en la misma posición que el valor con key "2" (error corregido)
        hashTable.put("12", "Andresdgsa");

        //compruebo que puedo añadir otro valor en la misma posición que el valor con key "0" (error corregido)
        hashTable.put("77", "pedro");

        //compruebo que puedo añadir diferentes tipos de caracteres como key
        hashTable.put("pedro", "77");

        //compruebo que puedo añadir numero negativos como key (error corregido)
        hashTable.put("-5", "hola");

        //compruebo que todo se ha añadido con normalidad y sin errores
        Assertions.assertEquals("\n" +
                " bucket[0] = [0, Andreo] -> [77, pedro]\n" +
                " bucket[1] = [1, Andresa] -> [12, Andresdgsa]\n" +
                " bucket[2] = [2, Angel]\n" +
                " bucket[8] = [-5, hola]\n" +
                " bucket[12] = [pedro, 77]", hashTable.toString());
    }

    @Test
    void get() {
        hashTable.put("1", "Andres");
        hashTable.put("2", "Angel");
        hashTable.put("1234", "");
        hashTable.put("-5", "hola");


        Assertions.assertEquals("Andres", hashTable.get("1"));
        Assertions.assertEquals("", hashTable.get("1234"));
    }

    @Test
    void drop() {
        hashTable.put("0", "Andres");
        hashTable.put("1", "Andres");
        hashTable.put("1", "pedro");
        hashTable.put("2", "Angel");
        hashTable.put("77", "pedro");
        hashTable.put("3", "Paco");
        hashTable.put("-5" , "hola");


        hashTable.drop("-5");

        Assertions.assertEquals("\n" +
                " bucket[0] = [0, Andres] -> [77, pedro]\n" +
                " bucket[1] = [1, pedro]\n" +
                " bucket[2] = [2, Angel]\n" +
                " bucket[3] = [3, Paco]", hashTable.toString());

        Assertions.assertEquals(4, hashTable.size());
    }
}