package client.event;

import java.io.File;

public class DeleteFile implements Runnable {
    File file;

    public DeleteFile(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        while (file.delete() == false) {
            file.delete();
        }
    }

}
