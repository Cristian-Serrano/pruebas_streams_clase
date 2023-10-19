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
}