import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Person {
    int height;
    int weight;
    Person(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("{height: %s, weight: %s}", height, weight);
    }
}

class CircusComparator implements Comparator<Person> {
    public int compare(Person o1, Person o2) {
        if (o1.height < o2.height && o1.weight < o2.weight)
            return -1;
        else if (o1.height > o2.height && o1.weight > o2.weight)
            return 1;
        else
            return 0;
    }
}

class Circus {
    public static void main(String args[]) {
        Person p1 = new Person(65, 100);
        Person p2 = new Person(70, 150);
        Person p3 = new Person(56, 90);
        Person p4 = new Person(75, 190);
        Person p5 = new Person(60, 95);
        Person p6 = new Person(68, 110);
        List<Person> people = new ArrayList<Person>();
        people.add(p1); people.add(p2); people.add(p3); people.add(p4);
        people.add(p5); people.add(p6);
        CircusComparator comparator = new CircusComparator();
        Collections.sort(people, comparator);

        int numberOfPeople = 1;
        for (int i = 1; i < people.size(); i++) {
            if (comparator.compare(people.get(i), people.get(i-1)) <= 0) break;
            numberOfPeople++;
        }

        System.out.println(people);
        System.out.println(numberOfPeople);
    }
}