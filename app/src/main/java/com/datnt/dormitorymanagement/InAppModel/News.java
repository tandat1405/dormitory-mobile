package com.datnt.dormitorymanagement.InAppModel;

public class News {
    private int news_Id;
    private String news_Title, news_Content;

    public News(int news_Id, String news_Title, String news_Content) {
        this.news_Id = news_Id;
        this.news_Title = news_Title;
        this.news_Content = news_Content;
    }

    public int getNews_Id() {
        return news_Id;
    }

    public void setNews_Id(int news_Id) {
        this.news_Id = news_Id;
    }

    public String getNews_Title() {
        return news_Title;
    }

    public void setNews_Title(String news_Title) {
        this.news_Title = news_Title;
    }

    public String getNews_Content() {
        return news_Content;
    }

    public void setNews_Content(String news_Content) {
        this.news_Content = news_Content;
    }
}
