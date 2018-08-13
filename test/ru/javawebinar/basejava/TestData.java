package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.time.Month;

public class TestData {


    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";

    public static final String NAME_1 = "name1";
    public static final String NAME_2 = "name2";
    public static final String NAME_3 = "name3";
    public static final String NAME_4 = "name4";

    public static final Resume RESUME_1 = new Resume(UUID_1, NAME_1);
    public static final Resume RESUME_2 = new Resume(UUID_2, NAME_2);
    public static final Resume RESUME_3 = new Resume(UUID_3, NAME_3);
    public static final Resume RESUME_4 = new Resume(UUID_4, NAME_4);

    public static final String MAIL_1 = "m1@mail.ru";
    public static final String MAIL_2 = "m2@mail.ru";
    public static final String MAIL_3 = "m3@mail.ru";
    public static final String MAIL_4 = "m4@mail.ru";

    public static final String PHONE_1 = "+71111";
    public static final String PHONE_2 = "+72222";
    public static final String PHONE_3 = "+73333";
    public static final String PHONE_4 = "+74444";

    public static final String[] ACHIEVMENTS = {"Achievment1", "Achievment2", "Achievment3"};
    public static final String[] QUALIFICATIONS = {"Qualification1", "Qualification2", "Qualification3" };
    public static final Organization ORGANIZATION_1 = new Organization(
            "Google.com")
            //        "http://google.com")
            .withPosition(new Organization.Position(
                    LocalDate.of(2005, Month.NOVEMBER, 21),
                    LocalDate.of(2007, Month.APRIL, 22),
                    "Стажер"))
            //, "Плевал в потолок"))
            .withPosition(new Organization.Position(
                    LocalDate.of(2007, Month.APRIL, 29),
                    LocalDate.now(),
                    "Инженер", "Мою потолок"
            ));

    static {
        RESUME_1.addContact(ContactType.MAIL, MAIL_1);
        RESUME_2.addContact(ContactType.MAIL, MAIL_2);
        RESUME_3.addContact(ContactType.MAIL, MAIL_3);
        RESUME_4.addContact(ContactType.MAIL, MAIL_4);

        RESUME_1.addContact(ContactType.PHONE, PHONE_1);
        RESUME_2.addContact(ContactType.PHONE, PHONE_2);
        RESUME_3.addContact(ContactType.PHONE, PHONE_3);
        RESUME_4.addContact(ContactType.PHONE, PHONE_4);

        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("PersonalInfo 1"));
        RESUME_2.addSection(SectionType.PERSONAL, new TextSection("PersonalInfo 2"));
        RESUME_3.addSection(SectionType.PERSONAL, new TextSection("PersonalInfo 3"));
        RESUME_4.addSection(SectionType.PERSONAL, new TextSection("PersonalInfo 4"));

        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection(ACHIEVMENTS));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));
        RESUME_2.addSection(SectionType.ACHIEVEMENT, new ListSection(ACHIEVMENTS));
        RESUME_2.addSection(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));
        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListSection(ACHIEVMENTS));
        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));
        RESUME_4.addSection(SectionType.ACHIEVEMENT, new ListSection(ACHIEVMENTS));
        RESUME_4.addSection(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));

        RESUME_1.addSection(SectionType.EDUCATION, new OrganizationSection(ORGANIZATION_1));
        RESUME_2.addSection(SectionType.EXPERIENCE, new OrganizationSection(ORGANIZATION_1));
    }
}