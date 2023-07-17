package com.sprinklr.InternProjectBot.HTML;

import org.apache.commons.text.StringEscapeUtils;

import java.util.Arrays;
import java.util.List;

public class HtmlContentCreator {

    public static String getAnchor(String href, String display){
        return "<a href=\""+href+"\">"+display+"</a>";
    }
    public static String bold(String content){
        return "<b>"+content + "</b>";
    }

    public static String italic(String content){
        return "<i>"+content + "</i>";
    }
    public static String newLine(int n){
        StringBuilder returnValue = new StringBuilder();
        for(int i = 0;i<n;i++){
            returnValue.append("<br>");
        }
        return returnValue.toString();
    }

    public static String makeTitle(String title){
        int fontSize = 25;
       return  "<p style=\"font-size: " + fontSize + "px;\">"+ title + "</p><br>";
    }

    public static String makeText(String text){
        return "<p style=\"background-color: #F9F9F9; padding: 10px;\">    <span style=\"color: #333;\">"+ text+ "</p>";
    }
    public static boolean isContentEmpty(String s){
        boolean yes = true;
        for(int i = 0;i<s.length();i++){
            if(s.charAt(i)>32){
                yes = false;
            }
        }
        return yes;
    }


    public static String stringToHtmlConverterForCode(String s){
        String output = "";
        String backgroundGreenColor = "#ecfdf0";
        String backgroundRedColor = "#fbe9eb";
        String otherCodeColor = "white";
        String[] newLineSeperated= s.split("\n");
        List<String> newLineSeperatedString = Arrays.asList(newLineSeperated);
        for(String line : newLineSeperatedString){
            String newLine = "";
            if(isContentEmpty(line)){
                newLine += "<div style=\"background-color:"+otherCodeColor + ";display: block;min-width: 400px\">";
                newLine += StringEscapeUtils.escapeHtml4(line);
                newLine += "</div>";
                newLine += "<br>";
            }
            else if(line.charAt(0) == '+'){
                newLine += "<div style=\"background-color:"+backgroundGreenColor + ";display: block;min-width: 400px\">";
                newLine += StringEscapeUtils.escapeHtml4(line);
                newLine += "</div>";
            }
            else if(line.charAt(0)=='-'){
                newLine += "<div style=\"background-color:"+backgroundRedColor + ";display: block;min-width: 400px\">";
                newLine += StringEscapeUtils.escapeHtml4(line);
                newLine += "</div>";
            }
            else{
                newLine += "<div style=\"background-color:"+otherCodeColor + ";display: block;min-width: 400px\">";
                newLine += StringEscapeUtils.escapeHtml4(line);
                newLine += "</div>";
            }
            output += newLine;
        }
        System.out.println("Output Starts here:");
        System.out.println(output);
        System.out.println("Output ends here:");

        return output;
    }
    public static String makeRed(String s){
        return "<span style=\"color: red;\">"+s+"</span>";
    }

    public static String makeGreen(String s){
        return "<span style=\"color: green;\">"+s+"</span>";
    }

    public static String stringToCodeConverter(String s){
       return  "<div style=\"max-height: 200px; overflow: auto;\">  <pre>    <code style=\"text-align: left;font-size: 12px;overflow-x: auto; white-space: nowrap;\"> " +
               stringToHtmlConverterForCode(s) +
               " </code>  </pre></div>";
    }
}
