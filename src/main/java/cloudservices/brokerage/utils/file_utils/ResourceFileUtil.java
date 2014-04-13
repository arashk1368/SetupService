/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.utils.file_utils;

import java.net.URL;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class ResourceFileUtil {

    public static String getResourcePath(String resourceName) {
        ClassLoader cl = ResourceFileUtil.class.getClassLoader();
        URL url = cl.getResource(resourceName);
        return url.getPath();
    }
    
}
