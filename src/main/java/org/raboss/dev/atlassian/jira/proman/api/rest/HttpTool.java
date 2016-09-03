package org.raboss.dev.atlassian.jira.proman.api.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bossekr on 16.07.16.
 */
public class HttpTool {
    /**
     * Parser for comma-delimited list of elements as described in [RFC7230] section 7. ABNF List Extension
     * @param value String as used in HTTP header
     * @return List of Strings with comma-delimited values
     */
    static public List<String> parseEtagValue(final String value) {
        if (value == null)
            return null;
        List<String> l = new ArrayList<>();
        for(String s : value.split("[\"\\s,]+")) {
            if (s.isEmpty())
                continue;
            l.add(s);
        }
        return l;
    }
}
