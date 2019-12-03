package by.epam.task02.util.Impl;

import by.epam.task02.util.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientParser implements Parser<List<String>,String> {

    private static final Logger logger= LogManager.getLogger(ClientParser.class);
    private final Pattern pattern=Pattern.compile("[+]?\\d+([.]\\d+)?\\s((true)|(false))\\s[+]?\\d+([.]\\d+)?\\scash\\d+\\s?");

    @Override
    public List<String> parse(String string) {
        List<String> listResult=new ArrayList<>();

        Matcher matcher=pattern.matcher(string);
        if(matcher.matches()){
            String splitter=" ";
            String[] data=string.split(splitter);

            listResult.addAll(Arrays.asList(data));
        }else{
            logger.warn("String ("+string+") doesn't match the regex at ClientParser");
        }

        return listResult;
    }
}
