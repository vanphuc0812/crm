package cybersoft.java18.crm.service;

import javax.servlet.http.Part;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServiceIml implements FileService {
    private final Path root = Paths.get("avatars");

    @Override
    public void init() {
        
    }

    @Override
    public void save(Part file) {

    }
}
