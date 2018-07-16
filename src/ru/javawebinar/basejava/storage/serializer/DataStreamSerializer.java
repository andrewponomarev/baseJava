package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("d/MM/yyyy");


    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeContacts(r.getContacts(), dos);

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writeTextSection((TextSection) section, dos);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection((ListSection) section, dos);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeOrganizationSection((OrganizationSection) section, dos);
                        break;
                }
            }
        }
    }

    private <T> void writeList(List<T> stringList, DataOutputStream dos, DataStreamWriter<T> f) throws IOException {
        dos.writeInt(stringList.size());
        for (T o : stringList) {
            f.write(o, dos);
        }
    }

    private void writeString(String s, DataOutputStream dos) throws IOException {
        dos.writeUTF(s);
    }

    private void writeContacts(Map<ContactType, String> contacts, DataOutputStream dos) throws IOException {
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private void writeTextSection(TextSection section, DataOutputStream dos) throws IOException {
        String content = section.getContent();
        writeString(content, dos);
    }

    private void writeListSection(ListSection section, DataOutputStream dos) throws IOException {
        List<String> sectionItems = section.getItems();
        writeList(sectionItems, dos, this::writeString);
    }

    private void writeOrganizationSection(OrganizationSection section, DataOutputStream dos) throws IOException {
        List<Organization> organizationList = section.getOrganizations();
        writeList(organizationList, dos, this::writeOrganization);
    }

    private void writeOrganization(Organization org, DataOutputStream dos) throws IOException {
        dos.writeUTF(org.getHomePage().getName());
        writeStringIfNull(org.getHomePage().getUrl(), dos);
        List<Organization.Position> positions = org.getPositions();
        dos.writeInt(positions.size());
        for (Organization.Position pos : positions) {
            dos.writeUTF(pos.getStartDate().format(DTF));
            dos.writeUTF(pos.getEndDate().format(DTF));
            dos.writeUTF(pos.getTitle());
            writeStringIfNull(pos.getDescription(), dos);
        }
    }

    private void writeStringIfNull(String s, DataOutputStream dos) throws IOException {
        if (s == null) {
            writeString("null", dos);
        }
        else writeString(s, dos);
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType ,readTextSection(dis));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType ,readListSection(dis));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        resume.addSection(sectionType ,readOrganizationSection(dis));
                        break;
                }
            }

            return resume;
        }
    }

    private TextSection readTextSection(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private ListSection readListSection(DataInputStream dis) throws IOException{
        int size = dis.readInt();
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            stringList.add(dis.readUTF());
        }
        return new ListSection(stringList);
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        OrganizationSection section = new OrganizationSection();
        int size = dis.readInt();
        List<Organization> organizationList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String name = dis.readUTF();
            String url = readStringIfNull(dis);
            List<Organization.Position> positionList = new ArrayList<>();
            int size2 = dis.readInt();
            for (int j = 0; j < size2; j++) {
                Organization.Position pos = new Organization.Position(
                        LocalDate.parse(dis.readUTF(), DTF),
                        LocalDate.parse(dis.readUTF(), DTF),
                        dis.readUTF(),
                        readStringIfNull(dis)
                );
                positionList.add(pos);
            }
            Organization org = new Organization(name, url, positionList);
            organizationList.add(org);
        }
        section.setOrganizations(organizationList);

        return section;
    }

    private String readStringIfNull(DataInputStream dis) throws IOException {
        String s = dis.readUTF();
        if (s.equals("null")) {
            return null;
        } else {
            return s;
        }
    }

    @FunctionalInterface
    private interface DataStreamWriter<T> {
        void write(T obj, DataOutputStream dos) throws IOException;
    }

}
