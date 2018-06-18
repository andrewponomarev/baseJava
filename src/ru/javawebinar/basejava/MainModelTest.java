package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

public class MainModelTest {

    public static void main(String[] args) {
        Resume r = new Resume("Василий Васильевич");
        r.addContact(ContactType.PHONE, "+7991234567");
        r.addContact(ContactType.MOBILE, "+7991234567");
        r.addContact(ContactType.HOME_PHONE, "+7991234567");
        r.addContact(ContactType.SKYPE, ("privetVasya"));
        r.addContact(ContactType.MAIL, "vasya@sobaka");
        r.addContact(ContactType.LINKEDIN, "linkedin/dsdsd");
        r.addContact(ContactType.GITHUB, "dssda");
        r.addContact(ContactType.STATCKOVERFLOW, "asdasddsa");
        r.addContact(ContactType.HOME_PAGE, "89eyhec83");

        r.addSection(SectionType.PERSONAL, new TextSection("Personal info"));
        r.addSection(SectionType.OBJECTIVE, new TextSection("Objective"));
        r.addSection(SectionType.ACHIEVEMENT, new ListSection(
                "Achievment1", "Achievment2", "Achievment3" ));
        r.addSection(SectionType.QUALIFICATIONS, new ListSection(
                "Qualification1", "Qualification12", "Qualification13e23edwd" ));
//        r.addSection(SectionType.EXPERIENCE, new OrganizationSection(
//                new Organization("H", "www.h", LocalDate.now(),
//                        LocalDate.now(), "Title", "very important company"),
//                new Organization("U", "www.u", LocalDate.now(),
//                        LocalDate.now(), "AGA", "not very important company")
//        ));
//        r.addSection(SectionType.EDUCATION, new OrganizationSection((
//                new Organization("PTU", "www.ptu", LocalDate.now(), LocalDate.now(),
//                        "PTU" , "PTU")
//                )));

        System.out.println(r);
    }



}
