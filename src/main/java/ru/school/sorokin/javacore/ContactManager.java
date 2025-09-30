package ru.school.sorokin.javacore;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(Contact.class.getName());
    private static final List<Contact> contactList = new ArrayList<>();
    private static final Set<Contact> setContact = new HashSet<>();
    private static final Map<String, List<Contact>> categoryContacts = new HashMap<>();

    public void menu() {
        int point;
        while (true) {
            choosePointOfMenu();
            point = getUserChoice();
            getUserAction(point);
        }
    }

    public void getUserAction(int point) {
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

    public void addContact() {

        String name = getContactName();
        String phone = getContactPhone();
        String email = getContactEmail();
        String group = getContactGroup();

        Contact contact = new Contact(name, phone, email, group);
        boolean isInContacts = setContact.contains(contact);
        List<Contact> familyList = new ArrayList<>();
        List<Contact> jobList = new ArrayList<>();
        List<Contact> friendsList = new ArrayList<>();
        List<Contact> favoritesList = new ArrayList<>();
        if (!isInContacts) {
            contactList.add(contact);
            setContact.add(contact);
            switch (group) {
                case "Семья" -> categoryContacts.computeIfAbsent(group, k -> familyList).add(contact);
                case "Работа" -> categoryContacts.computeIfAbsent(group, k -> jobList).add(contact);
                case "Друзья" -> categoryContacts.computeIfAbsent(group, k -> friendsList).add(contact);
                case "Избранное" -> categoryContacts.computeIfAbsent(group, k -> favoritesList).add(contact);
            }
        } else {
            System.out.println("Такой контакт уже содержится в списке контактов");
        }
    }

    public void getAllContacts() {
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

    public void removeContactByName() {
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

    public void findContactByName() {
        String name = getContactName();
        Iterator<Contact> iterator = setContact.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().equals(name)) {
                System.out.println(contact);
            } else {
                System.out.println("Нет такого имени в списке контактов");
            }
        }
    }

    public void findContactsByGroup() {
        String group = getContactGroup();
        Iterator<Map.Entry<String, List<Contact>>> iterator = categoryContacts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<Contact>> itr = iterator.next();
            if (itr.getKey().equals(group)) {
                System.out.println(itr);
                break;
            }
        }
        System.out.println("В этой группе нет контактов");
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
                System.out.println("Вы ничего не указали. Повторите ввод категории контакта");
            } else if (group.equals("Семья") || group.equals("Работа") ||
                    group.equals("Друзья") || group.equals("Избранное")) {
                break;
            } else {
                System.out.println("Такой категории нет. Выберите и введите категорию из списка категорий");
            }
        }
        return group;
    }
}



