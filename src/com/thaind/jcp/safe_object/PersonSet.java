package com.thaind.jcp.safe_object;

import java.util.HashSet;
import java.util.Set;

public class PersonSet {
    private final Set<Person> MY_SET = new HashSet<>();

    public synchronized void addPerson(Person person) {
        MY_SET.add(person);
    }

    public synchronized boolean containsPerson(Person person) {
        return MY_SET.contains(person);
    }

    public void remove(Person person){
        //private lock
        //when remove, check if there is another thread is holding the monitor, sync only the object to optimize the performance
        synchronized(person){
            MY_SET.remove(person);
        }
    }
}

class Person {
    private String id;
    private String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)) {
            return false;
        }
        return this.id.equals(((Person) obj).getId());
    }
}