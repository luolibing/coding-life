package cn.tim.thinking.collection.simple11.collections;

/**
 * Created by LuoLiBing on 16/9/28.
 */
public class Person implements Comparable<Person> {
    Long id;
    String name;
    Integer score;

    public Person(Long id, String name, Integer score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Person o) {
        if(o == null) {
            return 1;
        }
        return Long.compare(id, o.id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }
}
