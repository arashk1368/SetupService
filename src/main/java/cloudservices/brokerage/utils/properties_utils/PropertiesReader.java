/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.utils.properties_utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class PropertiesReader {
    
    public static Properties loadProperties(String fileName) 
            throws IOException {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(fileName);
            //load a properties file from class path, inside static method
            prop.load(input);
            return prop;
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
}
