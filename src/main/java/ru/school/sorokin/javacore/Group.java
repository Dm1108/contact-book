package ru.school.sorokin.javacore;

import java.util.NoSuchElementException;

public enum Group {

    FAMILY("Семья"),
    FRIENDS("Друзья"),
    JOB("Работа"),
    FAVORITES("Избранное");

    private String groupName;

    Group(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupByName(String name) {
        for (Group e : Group.values()) {
            if (e.groupName.equals(name)) {
                return e.groupName;
            } else {
                throw new NoSuchElementException("Нет такой группы контактов");
            }
        }
        return name;
    }
}
