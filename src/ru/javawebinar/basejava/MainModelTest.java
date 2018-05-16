package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

public class MainModelTest {

    public static void main(String[] args) {
        Resume r = Resume.newBuilder("Василий Васильевич")
                .phone("+7991234567")
                .mobile("+7991234567")
                .homePhone("+dsadsds")
                .skype("privetVasya")
                .mail("vasya@sobaka")
                .linkedIn("linkedin/dsdsd")
                .gitHub("dssda")
                .stackOverflow("asdasddsa")
                .homePage("89eyhec83")
                .build();
        System.out.println(r);
    }



}
