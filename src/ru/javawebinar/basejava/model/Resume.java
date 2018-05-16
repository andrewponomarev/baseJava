package ru.javawebinar.basejava.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * com.urise.webapp.ru.javawebinar.basejava.model.ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "contacts=" + contacts +
                ", sections=" + sections +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    public static ResumeBuilder newBuilder(String fullName) {
        return new Resume(fullName).new ResumeBuilder();
    }

    public class ResumeBuilder {

        private ResumeBuilder() {

        }

        public ResumeBuilder phone(String s) {
            Resume.this.contacts.put(ContactType.PHONE, s);
            return this;
        }

        public ResumeBuilder mobile(String s) {
            Resume.this.contacts.put(ContactType.MOBILE, s);
            return this;
        }

        public ResumeBuilder homePhone(String s) {
            Resume.this.contacts.put(ContactType.HOME_PHONE, s);
            return this;
        }

        public ResumeBuilder skype(String s) {
            Resume.this.contacts.put(ContactType.SKYPE, s);
            return this;
        }

        public ResumeBuilder mail(String s) {
            Resume.this.contacts.put(ContactType.MAIL, s);
            return this;
        }

        public ResumeBuilder linkedIn(String s) {
            Resume.this.contacts.put(ContactType.LINKEDIN, s);
            return this;
        }

        public ResumeBuilder gitHub(String s) {
            Resume.this.contacts.put(ContactType.GITHUB, s);
            return this;
        }

        public ResumeBuilder stackOverflow(String s) {
            Resume.this.contacts.put(ContactType.STATCKOVERFLOW, s);
            return this;
        }

        public ResumeBuilder homePage(String s) {
            Resume.this.contacts.put(ContactType.HOME_PAGE, s);
            return this;
        }


        public Resume build() {
            return Resume.this;
        }

    }
}
