package ru.school.sorokin.javacore;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactManager {

    private Scanner scanner;
    private static final Logger logger = Logger.getLogger(ContactManager.class.getName());
    private List<Contact> contactList;
    private Set<Contact> setContact;
    private Map<String, List<Contact>> categoryContacts;

    public ContactManager() {
        this.contactList = new ArrayList<>();
        this.setContact = new HashSet<>();
        this.categoryContacts = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void menu() {
        int point;
        while (true) {
            choosePointOfMenu();
            point = getUserChoice();
            getUserAction(point);
        }
    }

    private void getUserAction(int point) {
        switch (point) {
            case 1 -> addContact();
            case 2 -> removeContactByName();
            case 3 -> getAllContacts();
            case 4 -> findContactByName();
            case 5 -> findContactsByGroup();
            case 0 -> {
                System.out.println("Выход из программы");
                System.exit(0);
            }
        }
    }

    private void addContact() {

        String name = getContactName();
        String phone = getContactPhone();
        String email = getContactEmail();
        String group = getContactGroup();

        Contact contact = new Contact(name, phone, email, group);
        boolean isInContacts = setContact.contains(contact);

        if (!isInContacts) {
            contactList.add(contact);
            setContact.add(contact);
            if (group.equals(Group.FAMILY.getGroupName()) || group.equals(Group.JOB.getGroupName()) ||
                    group.equals(Group.FRIENDS.getGroupName()) || group.equals(Group.FAVORITES.getGroupName())) {
                categoryContacts.computeIfAbsent(group, k -> new ArrayList<>()).add(contact);
            }
        } else {
            System.out.println("Такой контакт уже содержится в списке контактов");
        }
    }

    private void getAllContacts() {
        System.out.println("Список");
        Iterator<Contact> iterator = contactList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("Set");
        Iterator<Contact> iterator1 = setContact.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());
        }
        System.out.println("Map");
        for (Map.Entry<String, List<Contact>> entry : categoryContacts.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", value: " + entry.getValue());
        }
    }

    private void removeContactByName() {
        String name = getContactName();
        Iterator<Contact> iterator = setContact.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().equals(name)) {
                iterator.remove();
                contactList.remove(contact);
            }
        }

        Iterator<Map.Entry<String, List<Contact>>> iter = categoryContacts.entrySet().iterator();
        while (iter.hasNext()) {
            iter.next().getValue().removeIf(e -> e.getName().equals(name));
        }
    }

    private void findContactByName() {
        String name = getContactName();
        if (setContact.isEmpty()) {
            System.out.println("Нет такого имени в списке контактов");
            return;
        }
        Iterator<Contact> iterator = setContact.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().equals(name)) {
                System.out.println(contact);
                break;
            } else {
                System.out.println("Нет такого имени в списке контактов");
            }
        }
    }

    private void findContactsByGroup() {
        String group = getContactGroup();
        Iterator<Map.Entry<String, List<Contact>>> iterator = categoryContacts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<Contact>> itr = iterator.next();
            if (itr.getKey().equals(group)) {
                System.out.println(itr);
                break;
            } else if (itr.getKey().isEmpty()) {
                System.out.println("В группе " + itr.getKey() + " контактов нет");
            }
        }
    }

    private void choosePointOfMenu() {
        System.out.println("""
                Выберите пункт меню:
                1: Добавить контакт
                2: Удалить контакт
                3: Посмотреть все контакты
                4: Найти контакт
                5: Посмотреть контакты по группе
                0: Выход
                
                Введите цифру от 1 до 5:
                """);
    }

    private int getUserChoice() {
        int point;
        while (true) {
            try {
                point = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                logger.log(Level.SEVERE, "Вы ввели не число. Повторите ввод");
            }
        }
        return point;
    }

    private String getContactName() {
        String name;
        while (true) {
            System.out.println("Введите имя контакта: ");
            name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Вы нечего не ввели. Повторите ввод");
            } else {
                break;
            }
        }
        return name;
    }

    private String getContactPhone() {
        String phone;
        while (true) {
            System.out.print("Введите телефон: ");
            phone = scanner.nextLine();
            if (phone.trim().isEmpty()) {
                System.out.println("Вы ничего не ввели. Повторите ввод телефона");
            } else {
                break;
            }
        }
        return phone;
    }

    private String getContactEmail() {
        String email;
        while (true) {
            System.out.print("Введите email: ");
            email = scanner.nextLine();
            if (email.trim().isEmpty()) {
                System.out.println("Вы ничего не указали. Повторите ввод email");
            } else {
                break;
            }
        }
        return email;
    }

    private String getContactGroup() {
        String group;
        while (true) {
            System.out.println("""
                    Введите группу:
                    Семья
                    Работа
                    Друзья
                    Избранное
                    """);
            group = scanner.nextLine();
            if (group.trim().isEmpty()) {
                System.out.println("Вы ничего не указали. Повторите ввод группы контакта");
            } else if (group.equals(Group.FAMILY.getGroupName()) || group.equals(Group.JOB.getGroupName()) ||
                    group.equals(Group.FRIENDS.getGroupName()) || group.equals(Group.FAVORITES.getGroupName())) {
                break;
            } else {
                System.out.println("Такой группы контактов нет. Выберите и введите группу контактов");
            }
        }
        return group;
    }
}



