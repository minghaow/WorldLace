package nanshen.utils;

/**
 * String utility class
 *
 * @author WANG Minghao
 */
public class StringUtils {

    /**
     * Given regex to separate src string to string list.
     *
     * <br/>
     *
     * <strong>NOTE:<strong/>
     * 1) In Nanshen system design, the default usage is for the communication
     * between frontend and backend, especially for the id list. The only string version supported
     * by this method is "id1,id2,id3,id4,". The last valid bit of the string should be the regex
     * 2) The method will check null and "" and "  " conditions
     *
     * @param src src string
     * @param regex for separate
     * @return
     */
    public static String[] getStringListFromString(String src, String regex) {
        if (org.apache.commons.lang.StringUtils.isBlank(src)) {
            return new String[]{};
        }
        src = src.substring(0, src.length() - 1);
        return src.split(regex);
    }

}
