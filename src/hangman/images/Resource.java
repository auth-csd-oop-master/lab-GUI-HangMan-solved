package hangman.images;

import java.net.URL;


public class Resource {
    
    public static URL getURL(String imageFilename) {
        return Resource.class.getResource(imageFilename);        
    }

}