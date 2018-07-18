package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("d/MM/yyyy");


    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeCollection(r.getContacts().entrySet(), dos, entry  -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(((ListSection) section).getItems(), dos, dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeOrganizationSection((OrganizationSection) section, dos);
                        break;
                }
            }
        }
    }

    private <T> void writeCollection(Collection<T> col, DataOutputStream dos, DataStreamWriter<T> f) throws IOException {
        dos.writeInt(col.size());
        for (T o : col) {
            f.write(o);
        }
    }

    private void writeOrganizationSection(OrganizationSection section, DataOutputStream dos) throws IOException {
        List<Organization> organizationList = section.getOrganizations();
        writeCollection(organizationList, dos, org -> {
            dos.writeUTF(org.getHomePage().getName());
            writeStringIfNull(org.getHomePage().getUrl(), dos);
            List<Organization.Position> positions = org.getPositions();
            writeCollection(positions, dos, pos -> {
                dos.writeUTF(pos.getStartDate().format(DTF));
                dos.writeUTF(pos.getEndDate().format(DTF));
                dos.writeUTF(pos.getTitle());
                writeStringIfNull(pos.getDescription(), dos);
            });
        });
    }


    private void writeStringIfNull(String s, DataOutputStream dos) throws IOException {
        dos.writeUTF(s == null ? "" : s);
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
                        resume.addSection(sectionType , new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType , new ListSection(readList(dis, dis::readUTF)));
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

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        OrganizationSection section = new OrganizationSection();
        section.setOrganizations(readList(dis, () ->{
            String name = dis.readUTF();
            String url = readStringIfNull(dis);
            List<Organization.Position> positionList = readList(dis, () -> {
                Organization.Position pos = new Organization.Position(
                        LocalDate.parse(dis.readUTF(), DTF),
                        LocalDate.parse(dis.readUTF(), DTF),
                        dis.readUTF(),
                        readStringIfNull(dis)
                );
                return pos;
            });
            Organization org = new Organization(name, url, positionList);
            return org;
        }));
        return section;
    }


    private String readStringIfNull(DataInputStream dis) throws IOException {
        String s = dis.readUTF();
        return s.equals("") ? null : s;
    }

    private <T> List<T> readList(DataInputStream dis, DataStreamReader<T> f) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(f.read());
        }
        return list;
    }

    @FunctionalInterface
    private interface DataStreamWriter<T> {
        void write(T obj) throws IOException;
    }

    @FunctionalInterface
    private interface DataStreamReader<T> {
        T read() throws IOException;
    }

}
