package com.company;

import com.sun.istack.internal.Nullable;

/**
 * Created by Dalton R Jones on 2/18/2018.
 */
public class Craft {
    private String title;
    private String[] categories;
    private String[] links;
    private String content;

    public Craft(){}

    public Craft(String title, String[] categories, String[] links, String content) {
        this.title = title;
        this.categories = categories;
        this.links = links;
        this.content = content;
    }
    public Craft(String title,String content)
    {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    //To string method, different return depending on which variable is null
    public String toString() {
        if(categories == null && links == null)
        {
            return title + "\n" + content;
        }
        if(categories == null && content == null)
        {
            return title + "\n" + links;
        }
        if(categories == null)
        {
            return title + "\n" + links + "\n" + content;
        }
        if(links==null)
        {
            return title + "\n" + categories + "\n" + content;
        }
        if(content==null)
        {
            return title + "\n" + categories +"\n" + links + "\n";
        }
        else
        {
            return title + "\n" + categories + "\n" + links +"\n" + content;
        }
    }
}
