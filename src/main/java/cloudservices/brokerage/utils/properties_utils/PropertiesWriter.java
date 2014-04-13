/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.utils.properties_utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class PropertiesWriter {

    public static void write(String fileName, String key, String value)
            throws IOException {
        Properties prop = PropertiesReader.loadProperties(fileName);
        OutputStream output = null;

        try {

            output = new FileOutputStream(fileName);

            // set the properties value
            prop.setProperty(key, value);


            // save properties to project root folder
            prop.store(output, null);
        } finally {
            if (output != null) {
                output.close();
            }

        }
    }
}
