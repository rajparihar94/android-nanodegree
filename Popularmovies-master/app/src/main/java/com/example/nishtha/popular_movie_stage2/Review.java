package com.example.nishtha.popular_movie_stage2;

/**
 * Created by nishtha on 13/4/16.
 */
public class Review {
    String id,author,content,url;
    public Review(){

    }

    public Review(String id,String author,String content,String url){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
