package ch.enterag.utils.mime;

import org.apache.tika.mime.MimeTypeException;

import static org.apache.tika.mime.MimeTypes.getDefaultMimeTypes;

public class MimeTypes {

    public static String getExtension(String mimeType) throws MimeTypeException {
        String extension = getDefaultMimeTypes().forName(mimeType).getExtension();
        if (extension.isEmpty()) {
            extension = "bin";
        } else if (extension.startsWith(".")) {
            extension = extension.substring(1);
        }
        return extension;
    }
}


