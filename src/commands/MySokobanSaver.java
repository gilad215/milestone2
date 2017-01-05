package commands;

import levels.Level;
import util.LevelSaver;
import util.OBJUtil;
import util.TXTUtil;
import util.XMLUtil;

import java.io.IOException;
import java.util.HashMap;

public class MySokobanSaver implements Saver{
    private HashMap<String, LevelSaver> fileTypes;
    private Level lvl;
    private String p;

    MySokobanSaver(Level l,String path)
    {
            fileTypes= new HashMap<String, LevelSaver>();
            fileTypes.put("txt", new TXTUtil());
            fileTypes.put("xml", new XMLUtil());
            fileTypes.put("obj", new OBJUtil());
            this.p=path;
            this.lvl=l;
    }

    @Override
    public void save() {
        String extension = p;
        int i = extension.lastIndexOf('.');
        if (i > 0) {
            extension = extension.substring(i+1);
        }
        if(!fileTypes.containsKey(extension))
        {
            System.out.println("Invalid Extension!");
            return;
        }
        LevelSaver saver=fileTypes.get(extension);
        if(saver!=null)
        {
            try {
                saver.save(lvl,p);
                System.out.println("Saved level with type:"+extension.toUpperCase());
            } catch (IOException e) {
                System.out.println("SaveLevelCommand Failed!");
            }
        }
    }
}
