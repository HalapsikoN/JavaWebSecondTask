package by.epam.task02.util.Impl;

import by.epam.task02.util.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CashDeskParser implements Parser<String,String> {

    private static final Logger logger= LogManager.getLogger(CashDeskParser.class);
    private final Pattern pattern=Pattern.compile("cash\\d+\\s?");

    @Override
    public String parse(String string) {
        String result="";

        Matcher matcher=pattern.matcher(string);
        if(matcher.matches()){
            result=string;
        }else{
            logger.warn("String ("+string+") doesn't match the regex at CashDeskParser");
        }

        return result;
    }
}
