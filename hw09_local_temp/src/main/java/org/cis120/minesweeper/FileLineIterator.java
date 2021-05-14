package org.cis120.minesweeper;

import java.util.Iterator;
import java.util.*;
import java.io.*;

public class FileLineIterator implements Iterator<String> {
    private BufferedReader file;
    private String nextElement;

    public FileLineIterator(String filePath) {
        try {
            this.file = new BufferedReader(new FileReader(filePath));
            nextElement = file.readLine();
        } catch (FileNotFoundException e) {
            this.file = null;
        } catch (IOException e) {
            nextElement = null;
        }
    }

    @Override
    public boolean hasNext() {
        if (nextElement != null) {
            return true;
        } else if (file != null) {
            try {
                file.close();
                return false;
            } catch (IOException e) {
                System.out.println("Error Closing");
            }
        }
        return false;
    }

    @Override
    public String next() {
        if (hasNext()) {
            String element = nextElement;
            try {
                nextElement = file.readLine();
            } catch (IOException e) {
                System.out.println("No Next Element.");
            }
            return element;
        } else {
            throw new NoSuchElementException();
        }
    }
}
