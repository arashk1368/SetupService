/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.utils.logging;

import cloudservices.brokerage.utils.formatters.HTMLFormatter;
import cloudservices.brokerage.utils.properties_utils.PropertiesWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
* @author Arash Khodadadi http://www.arashkhodadadi.com/  
 */
public class LoggerSetup {

    private static FileHandler fileTxt;
    private static SimpleFormatter formatterTxt;
    private static FileHandler fileHTML;
    private static Formatter formatterHTML;

    public static void setup(String txtOutputAddress,String htmlOutputAddress) throws IOException {

        // Get the global logger to configure it
        Logger logger = Logger.getLogger("");

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler(txtOutputAddress);
        fileHTML = new FileHandler(htmlOutputAddress);

        // create txt Formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

        // create HTML Formatter
        formatterHTML = new HTMLFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
    }
    
    public static void log4jSetup(String log4jPropAddress,String logFileAddress) throws IOException{
        PropertiesWriter.write(log4jPropAddress, "log4j.appender.file.File", logFileAddress);
    }
}
