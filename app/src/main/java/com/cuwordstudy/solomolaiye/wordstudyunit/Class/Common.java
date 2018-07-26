package com.cuwordstudy.solomolaiye.wordstudyunit.Class;

import com.cuwordstudy.solomolaiye.wordstudyunit.Remote.ApiService;
import com.cuwordstudy.solomolaiye.wordstudyunit.Remote.RetrofitClient;

public class Common {

    private static String DB_NAME = "wordstudy";
    private static String POINTS_COLLECTION_NAME = "prayerpoints";
    private static String ANNOUN_COLLECTION_NAME = "announcements";
    private static String REQUEST_COLLECTION_NAME = "prayerrequests";
    private static String WORD_COLLECTION_NAME = "word";
    private static String QUESTION_COLLECTION_NAME = "questions";
    private static String PROFILE_COLLECTION_NAME ="profile";
    private static String ANSWERS_COLLECTION_NAME = "answers";
    private static String COMMENTS_COLLECTION_NAME = "comments";
    public static String currentToken ="";// "/topics/Meeting";
    public static  String baseURL = "https://fcm.googleapis.com/fcm/send";
    private static String API_KEY = "jXMzX0xlB-ShDNqKwyA3dRDyDszjVS4x";

    public static String getAddressSinglePoints(prayerpoints points)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/prayerpoints");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/" + points.get_id().getOid() + "?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiPoints()
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/prayerpoints");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddressSingleAnoun(announcements points)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/announcements");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/" + points.get_id().getOid() + "?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiAnoun()
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/announcements");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddressSinglereq(requests points)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/prayerrequests");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/" + points.get_id().getOid() + "?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiReq()
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/prayerrequests");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddressSingleTopic(topic points)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/word");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/" + points.get_id().getOid() + "?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApitopic()
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/word");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddressSingleQuest(questions points)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/questions");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/" + points.get_id().getOid() + "?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiQuest()
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/questions");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiQuestspecifictitle(String topic)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/questions");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?q={titleid:"+topic+"}&apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddressSingleAns(answers points)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/answers");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/" + points.get_id().getOid() + "?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiAns()
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/answers");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiAnsSpecificAns(String question)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/answers");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?q={questionid:"+question+"}&apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiAnsSpecificComment(String word)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/comments");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?q={textid:" + word + "}&apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiComment()
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/comments");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddressSingleComment(Coment points)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/comments");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/" + points.get_id().getOid() + "?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }


    public static String getAddresApiSpecificProfile(String usermail)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/profile");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?q={user_email:" + usermail + "}&apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddresApiProfile()
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/profile");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }
    public static String getAddressSingleProfile(Profile points)
    {
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/wordstudy/collections/profile");
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/" + points.get_id().getOid() + "?apiKey=" + API_KEY);
        return stringBuilder.toString();
    }




}
