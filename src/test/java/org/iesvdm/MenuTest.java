package org.iesvdm;

import static java.util.stream.Collectors.toList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MenuTest {
    List<Dish> menu;
    @BeforeEach
    void setUp(){
        this.menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH) );
    }

    @Test
    public void streamJava7Test(){
        List<Dish> lowCaloricDishes = new ArrayList<>();

        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            public int compare(Dish dish1, Dish dish2) {
                return Integer.compare(dish1.getCalories(), dish2.getCalories());
            }
        });
        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish dish : lowCaloricDishes) {
            lowCaloricDishesName.add(dish.getName());
        }

        Assertions.assertTrue(true);
    }

    @Test
    void pruebaFilter()
    {
        List<Dish> vegetarianDishes = menu.stream()
                .filter(Dish::isVegetarian)  // <-> .filter(  dish -> dish.isVegetarian() )
                // en filter los elementos del stream que no cumplen el predicado se eliminan
                .collect(toList());

        List<Dish> nonVegetarianDishes = menu.stream()
                .filter(d -> !d.isVegetarian())  // <-> .filter(  dish -> dish.isVegetarian() )
                // en filter los elementos del stream que no cumplen el predicado se eliminan
                .collect(toList());

        vegetarianDishes.forEach(d -> System.out.println(d));
        nonVegetarianDishes.forEach(System.out::println);
    }
    @Test
    void sortedTest() {

        List<Dish> lowCaloricDishesName = menu
                .parallelStream()
                .filter(d -> d.getCalories() < 400)
                //.sorted(Comparator.comparing(Dish::getCalories)) //// .sorted(// Comparator.comparing(d -> d.getCalories())// ) .sorted((d1, d2) -> d1.getCalories() - d2.getCalories())
                // comparing genera una lambda de tipo Comparator<T> sobre el método de getCalories de Dish .collect(toList());

                // lowCaloricDishesName.forEach(System.out::println); lowCaloricDishesName.forEach(dish -> System.out.println(dish));
                .collect(toList());
    }
    @Test
    void distinctTest(){
        //          asList CREA COLECCION MUTABLE
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);

        //          of CREA COLECCION INMUTABLE
        //List<Integer> numbersInmutable = List.of(1, 2, 1, 3, 3, 2, 4);
        //numbers.stream()
        //        .filter(i -> i % 2 == 0)
        //        .peek(i -> System.out.println("peek"+i))
        //        .distinct()
        //        .forEach(System.out::println);

        List<Integer> salida = numbers.parallelStream()
                .filter(i -> i % 2 == 0)
                .peek(i -> System.out.println("peek"+i))
                .distinct()
                .collect(toList()); //toList();

        salida.forEach(i -> System.out.println(i));
    }

    @Test
    void takeWhileTest(){
        List<Dish> specialMenu = Arrays.asList( new Dish("seasonal fruit", true, 120, Dish.Type.OTHER)
                , new Dish("prawns", false, 300, Dish.Type.FISH)
                , new Dish("rice", true, 350, Dish.Type.OTHER)
                ,new Dish("chicken", false, 400, Dish.Type.MEAT)
                , new Dish("french fries", true, 530, Dish.Type.OTHER));
        //Fíjate que specialMenu está ordenado de menor a mayor calorías..

        List<Dish> filteredMenu = specialMenu.stream()
                .sorted(Comparator.comparing(Dish::getCalories))
                .takeWhile(dish -> dish.getCalories() < 320)    //Selecciona hasta que deja de cumplirse por 1a vez el predicado.
                //tomaMientras (secuencialmente) -sólo con sentido en colecciones ordenadas.
                .collect(toList());
        // En filteredMenu tendremos solo: seasonal fruit, prawns
        filteredMenu.forEach(System.out::println);
    }

    @Test
    void dropWhileTest(){
        List<Dish> specialMenu = Arrays.asList( new Dish("seasonal fruit", true, 120, Dish.Type.OTHER)
                , new Dish("prawns", false, 300, Dish.Type.FISH)
                , new Dish("rice", true, 350, Dish.Type.OTHER)
                ,new Dish("chicken", false, 400, Dish.Type.MEAT)
                , new Dish("french fries", true, 530, Dish.Type.OTHER));

    //Fíjate que specialMenu está ordenado de menor a mayor calorías
        List<Dish> filteredMenu = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)   //Descarta hasta que deja de cumplirse por 1a vez el predicado, a partir de ahí devuelve todo.
                //descartaMientras (secuencialmente) -sólo con sentido en colecciones ordenadas
                .collect(toList());
    // En filteredMenu tendremos solo: seasonal fruit, prawns

        filteredMenu.forEach(System.out::println);
    }

    @Test
    void limit(){
        List<Dish> specialMenu = Arrays.asList( new Dish("seasonal fruit", true, 120, Dish.Type.OTHER)
                , new Dish("prawns", false, 300, Dish.Type.FISH)
                , new Dish("rice", true, 350, Dish.Type.OTHER)
                ,new Dish("chicken", false, 400, Dish.Type.MEAT)
                , new Dish("french fries", true, 530, Dish.Type.OTHER));

        List<Dish> dishes = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .sorted(Comparator.comparing(Dish::getCalories))
                .limit(2) //Se queda con los tres primeros del flujo, en este caso, que hayan pasado por el predicado de filter
                //Los 3 platos con menos calorias pero que tengan mas de 300
                .collect(toList());

        dishes.forEach(dish -> System.out.println(dish));
    }

    @Test
    void skip(){
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2) //Descarta los 2 primeros del flujo, en este caso, que hayan pasado por el predicado de filter
                .collect(toList());
    }

    @Test
    void map(){
        List<String> dishNames = menu.stream()
                .map(Dish::getName)  //Aplica a cada elemento del flujo una función, en este caso, Dish::getName
                // TRANSFORMA EL FLUJO DE UN TIPO DE OBJETO A OTRO, DICIÉNDOSELO POR EL LAMBDA
                // Mapear se puede interpretar por transformar, el elemento se mapea con el resultado de la función (se transforma)
                .peek(s -> System.out.println(s))
                .collect(toList());
        dishNames.forEach(s -> System.out.println(s));

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");

        List<Integer> wordLengths = words.stream()
                .map(s -> s.length())
                //Aplica a cada elemento del flujo
                .peek(integer -> System.out.println(integer))
                .collect(toList());

    }

    @Test
    void flatMap(){
        String[] words = new String[]{ "Hello", "World" };

        List<String[]> list = Arrays.stream(words)
                .map(word -> word.split("")) //Aplica a cada palabra del array, pero word.split devuelve un array de String, de modo que
                // map ha transformado el flujo de Stream<String> a Stream<String[]>
                .distinct()
                .peek(strings -> System.out.println(strings))
                .collect(toList());

        Arrays.stream(words)
                .map(word -> word.split(""))
                .map(strings -> Arrays.stream(strings)) //Va a convertir cada elemento del stream de tipo array en un stream. Tendremos streams Stream<String> dentro del stream principal.
                .peek(
                        stringStream -> System.out.println(stringStream)
                )
                .distinct()
                .collect(toList()); // El resultado será List<Stream<String>>

        List<String> uniqueCharacters = Arrays.stream(words)
                .map(word -> word.split(""))
                .flatMap(strings -> Arrays.stream((strings)))
                .distinct()
                .peek(s -> System.out.println(s))
                .collect(toList());
    }
}