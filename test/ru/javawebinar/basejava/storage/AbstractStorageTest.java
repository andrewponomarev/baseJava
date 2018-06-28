package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = new File("/Users/ponomarevandrew/My projects/JavaOps/basejava/storage");

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String NAME_1 = "name1";
    private static final String NAME_2 = "name2";
    private static final String NAME_3 = "name3";
    private static final String NAME_4 = "name4";

    private static final Resume RESUME_1 = new Resume(UUID_1, NAME_1);
    private static final Resume RESUME_2 = new Resume(UUID_2, NAME_2);
    private static final Resume RESUME_3 = new Resume(UUID_3, NAME_3);
    private static final Resume RESUME_4 = new Resume(UUID_4, NAME_4);

    private static final String MAIL_1 = "m1@mail.ru";
    private static final String MAIL_2 = "m2@mail.ru";
    private static final String MAIL_3 = "m3@mail.ru";
    private static final String MAIL_4 = "m4@mail.ru";

    private static final String PHONE_1 = "+71111";
    private static final String PHONE_2 = "+72222";
    private static final String PHONE_3 = "+73333";
    private static final String PHONE_4 = "+74444";

    private static final String[] ACHIEVMENTS = {"Achievment1", "Achievment2", "Achievment3"};
    private static final String[] QUALIFICATIONS = {"Qualification1", "Qualification2", "Qualification3" };
    private static final Organization ORGANIZATION_1 = new Organization("Google.com",
            "http://google.com")
            .withPosition(new Organization.Position(
                    LocalDate.of(2005, Month.NOVEMBER, 21),
                    LocalDate.of(2007, Month.APRIL, 22),
                    "Стажер", "Плевал в потолок"))
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


    public AbstractStorageTest() {

    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        assertEquals(3, storage.size());
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(UUID_1)));
    }

    @Test
    public void getAll() throws Exception {
        List<Resume> result = storage.getAllSorted();
        assertEquals(3, result.size());
        assertEquals(RESUME_1, result.get(0));
        assertEquals(RESUME_2, result.get(1));
        assertEquals(RESUME_3, result.get(2));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertEquals(4, storage.size());
        assertEquals(RESUME_4, storage.get(RESUME_4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistResume() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test
    public void get() throws Exception {
        assertEquals(RESUME_1,  storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

}