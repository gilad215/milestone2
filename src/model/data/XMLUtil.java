package model.data;

import java.io.*;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

public class XMLUtil implements LevelSaver {

    public Object decode(InputStream fileName) throws IOException,
            ClassNotFoundException
    {
        XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(fileName));
        Object obj=decoder.readObject();
        return obj;
    }

    @Override
    public void save(Object obj, String fileName) throws IOException {
        FileOutputStream fos= new FileOutputStream(fileName);
        XMLEncoder encoder=new XMLEncoder(fos);
        encoder.writeObject(obj);
        encoder.close();
        fos.close();
    }
}
