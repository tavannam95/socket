package com.a2m.mail.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class FileItemRegistry {
	public Map<String, File> map = new HashMap();
    private Log logger;
    static int idCounter = 0;
    int registryId;

    public String toString() {
        return "registryId=" + registryId +
               " nItems=" + map.size();
    }

    public FileItemRegistry(Log logger) {
        this.logger = logger;
        registryId  = idCounter++;
    }
    
    public FileItemRegistry() {
//        this.logger = logger;
        registryId  = idCounter++;
    }

    public void add(File item) {
//        logger.debug("Store item " + item.getName() + " with name " + item.getFieldName());
        map.put(item.getName(), item);
    }

    public void remove(String name) {
        remove(get(name));
    }

    public void remove(File item) {
        if (item != null) {
//            logger.debug("Remove item " + item.getName() + " with name " + item.getFieldName());
            map.remove(item.getName());
            // Remove temporary stuff
//            item.delete();
        }
    }

    public void clear() {
        for (Entry<String, File> e: map.entrySet())
            remove(e.getValue());
    }

    public File get(String name) {
//        logger.debug("Retrieve item " + name + " isNull=" + (map.get(name) == null));
        return map.get(name);
    }

    public int size() {
        return map.size();
    }
}
