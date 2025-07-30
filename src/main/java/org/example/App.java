package org.example;

/**
 * @author Viktor Shvidkiy
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Создадим HashMap и добавим 3 записи");
        CustomHashMap<String, Integer> myMap = new CustomHashMap<>();
        myMap.put("Иван", 22);
        myMap.put("Сергей", 35);
        myMap.put("Максим", 18);
        System.out.println(myMap);
        System.out.println("Изменим возраст Максима");
        System.out.println(myMap.put("Максим", 19));
        System.out.println(myMap);
        System.out.println("Возраст Ивана: "+myMap.get("Иван"));
        System.out.println("Попробуем удалить существующую и несуществующую персону");
        System.out.println(myMap.remove("Сергей"));
        System.out.println(myMap.remove("Алексей"));
        System.out.println("Остались:");
        System.out.println(myMap);
    }
}
