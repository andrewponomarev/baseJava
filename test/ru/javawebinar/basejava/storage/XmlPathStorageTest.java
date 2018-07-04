package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest  {

    public XmlPathStorageTest() {
        super(new PathStorage(SORAGE_DIR_STRING, new XmlStreamSerializer()));
    }

}