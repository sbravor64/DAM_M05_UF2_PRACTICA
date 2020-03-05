package ex4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HashTableTest {

    HashTable hashTable = new HashTable();

    @Test
    void size() {
        //Compruebo el tamaño de size antes de añadir valores o borrarlos
        Assertions.assertEquals(0, hashTable.size());

        //Agrego varios valores para comprobar que el size aumenta
        // correspondiendo a los buckets que se van creando
        hashTable.put("1", "Adrian");
        hashTable.put("1", "Alex");
        hashTable.put("0", "Andres");
        hashTable.put("77", "pedro");
        hashTable.put("11", "juan");
        hashTable.put("pedro", "77");
        hashTable.put("-5", "hola");

        //El tamaño de size será 6
        Assertions.assertEquals(6, hashTable.size());

        //Borramos algunos valores para comprobar que el size cambia
        //correspondiendo a los buckets que se van borrando
        hashTable.drop("77");
        hashTable.drop("11");
        hashTable.drop("-5");

        //El tamaño de size será 6
        Assertions.assertEquals(3, hashTable.size());

        Assertions.assertEquals("\n" +
                " bucket[0] = [0, Andres]\n" +
                " bucket[1] = [1, Alex]\n" +
                " bucket[12] = [pedro, 77]", hashTable.toString());

    }

    @Test
    void realSize() {
        //Agrego varios valores para comprobar que el realSize no se cambie
        hashTable.put("0", "Andres");
        hashTable.put("1", "Adrian");
        hashTable.put("1", "Alex");
        hashTable.put("2", "");
        hashTable.put("77", "pedro");
        hashTable.put("11", "juan");
        hashTable.put("77", "pedrito");
        hashTable.put("pedro", "77");
        hashTable.put("-5", "hola");

        //El tamaño de realSize siempre será 16
        Assertions.assertEquals(16, hashTable.realSize());

        //Borramos algunos valores para comprobar que el realSize no se cambie
        hashTable.drop("77");
        hashTable.drop("11");
        hashTable.drop("-5");

        //El tamaño de realSize siempre será 16
        Assertions.assertEquals(16, hashTable.realSize());

    }

    @Test
    void put() {
        hashTable.put("0", "Andres");
        hashTable.put("1", "Adrian");

        // modifico el valor con el key "1"
        hashTable.put("1", "Alex");
        Assertions.assertEquals("Alex", hashTable.get("1"));

        // añado un valor vacio
        hashTable.put("2", "");
        Assertions.assertEquals("", hashTable.get("2"));

        // añado dos nuevos valores en el bucket[0] para comprobar las colisiones
        hashTable.put("77", "pedro");
        hashTable.put("11", "juan");
        Assertions.assertEquals("pedro", hashTable.get("77"));

        //modifico el valor que se encuentra en medio
        hashTable.put("77", "pedrito");

        //compruebo que puedo añadir diferentes tipos de caracteres como key
        hashTable.put("pedro", "77");

        //compruebo que puedo añadir numero negativos como key (error corregido)
        hashTable.put("-5", "hola");

        //En total tenemos 4 tablas de hash, el 1º guarda 2 valores, y los siguientes solamente 1

        //Compruebo que tenemos 6 tablas de hash
        Assertions.assertEquals(6, hashTable.size());

        //Compruebo que todos los valores se encuentren en los buckets correspondientes
        Assertions.assertEquals("\n" +
                " bucket[0] = [0, Andres] -> [77, pedrito] -> [11, juan]\n" +
                " bucket[1] = [1, Alex]\n" +
                " bucket[2] = [2, ]\n" +
                " bucket[8] = [-5, hola]\n" +
                " bucket[12] = [pedro, 77]", hashTable.toString());
    }

    @Test
    void get() {
        hashTable.put("1", "Andres");
        hashTable.put("2", "Angel");
        hashTable.put("1234", "");
        hashTable.put("-5", "hola");

        // añado 3 valores en el bucket [0]
        hashTable.put("0", "Andres");
        hashTable.put("77", "pedro");
        hashTable.put("11", "juan");

        //Compruebo el valor del key "1" y key "1234"
        Assertions.assertEquals("Andres", hashTable.get("1"));
        Assertions.assertEquals("", hashTable.get("1234"));

        //Compruebo si puedo buscar los keys con numeros negativos
        Assertions.assertEquals("hola", hashTable.get("-5"));

        //Compruebo el valor que se encuentra en medio, en el bucket [0]
        Assertions.assertEquals("pedro", hashTable.get("77"));


        Assertions.assertEquals("\n" +
                " bucket[0] = [0, Andres] -> [77, pedro] -> [11, juan]\n" +
                " bucket[1] = [1, Andres]\n" +
                " bucket[2] = [2, Angel] -> [1234, ]\n" +
                " bucket[8] = [-5, hola]", hashTable.toString());
    }

    @Test
    void drop() {
        hashTable.put("1", "Andres");
        hashTable.put("2", "Angel");
        hashTable.put("1234", "");
        hashTable.put("-5", "hola");

        //Compruebo que puedo borrar un valor
        hashTable.drop("1");

        //añado 3 valores en el bucket [0]
        hashTable.put("0", "Andres");
        hashTable.put("77", "pedro");
        hashTable.put("11", "juan");

        //Compruebo si puedo borrar el valor de en medio
        hashTable.drop("77");

        //Compruebo si puedo borrar el ultimo valor del bucket [0] en medio
        hashTable.drop("11");

        //Compruebo si puedo borrar un valor con un key de 4 digitos
        hashTable.drop("1234");

        //
        Assertions.assertEquals("\n" +
                " bucket[0] = [0, Andres]\n" +
                " bucket[2] = [2, Angel]\n" +
                " bucket[8] = [-5, hola]", hashTable.toString());



    }
}