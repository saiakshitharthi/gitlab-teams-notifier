package com.sprinklr.InternProjectBot.StringUtilities;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class StaticMessages {
    public static final String helpContent ="<table style=\"table-layout: fixed;\">\n" +
            "  <tr>\n" +
            "    <th style=\"height: 50px;\">Command</th>\n" +
            "    <th style=\"height: 50px;\">Functionality</th>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td style=\"height: 50px;\">help</td>\n" +
            "    <td style=\"height: 50px;\">Display available commands and their functionalities.</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td style=\"height: 50px;\">get_token</td>\n" +
            "    <td style=\"height: 50px;\">Get the currently set GitLab authentication token.</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td style=\"height: 50px;\">get_handle</td>\n" +
            "    <td style=\"height: 50px;\">Get the currently set GitLab handle.</td>\n" +
            "  <tr>\n" +
            "    <td style=\"height: 50px;\">unsubscribe</td>\n" +
            "    <td style=\"height: 50px;\">Unsubscribe from certain topics.</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td style=\"height: 50px;\">subscribe</td>\n" +
            "    <td style=\"height: 50px;\">Subscribe to certain topics.</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td style=\"height: 50px;\">login</td>\n" +
            "    <td style=\"height: 50px;\">Log in to GitLab.</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td style=\"height: 50px;\">logout</td>\n" +
            "    <td style=\"height: 50px;\">Log out from GitLab.</td>\n" +
            "  </tr>\n" +
            "</table>";

    public static final String wrongFormat = "Please type something which are in the commands. Type help to get help";
    public static final String tokenSet = "GitLab authentication token set successfully";
    public static final String unsubscribed = "Succesfully unsubscribed selected options";
    public static final String subscribed = "Succesfully subscribed selected options";
    public static final String welcomeMessage = "Hello!, Thank you for installing this app. This app Notifies you on gitLab updates. Type help to get help.";
    public static final String invalidInput = "This input is invalid!";
    public static final String loggedIn = "You are logged in as ";
    public static final String loginFailed = "Login failed. Please try again";
    public static final String alreadyLoggedIn = "You are already logged in as ";
    public static final String successfulLogout = "You are successfully logged out";
    public static final String notLoggedIn = "You are not logged in";
    public static final String codeContentSubTitle = "Code Content on which the comment is made:";
    public static final String nothingToSelectContent = "There is nothing to select";
    public static final String selectContent = "Select the topics you want to subscribe to:";
    public static final String adaptiveCardContentType = "application/vnd.microsoft.card.adaptive";
    public static Map<String, Item> map = new HashMap<>();

    static {
        map.put("1", new Item("Comment on issue"));
        map.put("2", new Item("Comment on merge request"));
        map.put("3", new Item("Comment on commit"));
        map.put("4", new Item("Comment on snippet"));
        map.put("5", new Item("Merge Request Merged"));
        map.put("6", new Item("Closing Merge Request"));
        map.put("7", new Item("Opening Merge request"));
        map.put("8", new Item("Reopening Merge request"));
        map.put("9", new Item("Approving Merge request"));
        map.put("10", new Item("Unapproving merge request"));
        map.put("11", new Item("Updating merge request"));
        map.put("12", new Item("Pipeline success"));
        map.put("13", new Item("Pipeline failed"));
        map.put("14", new Item("Job success"));
        map.put("15", new Item("Job failed"));
    }

    public static class Item {
        public String title;
        public String keyword;

        public Item(String title) {
            this.title = resolveUnevenCharacters(title);
            this.keyword = generateKeyword(this.title);
        }

        public String getTitle() {
            return title;
        }

        public String getKeyword() {
            return keyword;
        }

        private String resolveUnevenCharacters(String text) {
            String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
            return normalizedText.replaceAll("[^\\p{ASCII}]", "");
        }

        private String generateKeyword(String text) {
            String trimmedText = text.trim();
            String[] words = trimmedText.split("\\s+");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < words.length; i++) {
                if (i == 0) {
                    sb.append(words[i].substring(0, 1).toUpperCase());
                    sb.append(words[i].substring(1).toLowerCase());
                } else {
                    sb.append(words[i].substring(0, 1).toUpperCase());
                    sb.append(words[i].substring(1).toLowerCase());
                }
            }
            return sb.toString();
        }
    }
}


