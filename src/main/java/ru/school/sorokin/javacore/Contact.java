package ru.school.sorokin.javacore;

import java.util.Objects;

public class Contact {

    private String name;
    private String phone;
    private String email;
    private String group;


    public Contact(String name, String phone, String email, String group) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contact contact = (Contact) obj;
        return (Objects.equals(name, contact.name) && Objects.equals(phone, contact.phone) &&
                Objects.equals(email, contact.email) && Objects.equals(group, contact.group));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, group);
    }

        @Override
        public String toString () {
            return "Contact{" +
                    "name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", group='" + group + '\'' +
                    '}';
        }
    }
