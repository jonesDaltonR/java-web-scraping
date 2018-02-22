package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton R Jones on 2/18/2018.
 */

public class Main {

    public static void main(String[] args) throws IOException {
        //Printable Activities
        //Get HTML from url provided
        Document document = Jsoup.connect("https://www.kdl.org/early-lit/printable-activities").get();
        //List of craft objects
        List<Craft> craftArrayList = new ArrayList<>();
        //Placeholder craft object to be filled later
        Craft craft = new Craft();

        //Code to get the title, link, and description for each printable activity
        for(Element h:document.select(".region-content div.field-item.even h4"))
        {
            //Set placeholder craft object to a new Craft object with information from website
            craft = new Craft(h.text(),null,new String[] {h.selectFirst("a").attr("abs:href")},h.nextElementSibling().text());
            //Add craft object to craft list
            craftArrayList.add(craft);
        }
        //Code to get the title and link of each ul element in printable activities
        for (Element h:document.select(".region-content div.field-item.even ul li a"))
        {
            //Set placeholder craft object to a new Craft object with information from website
            craft = new Craft(h.text(),null,new String[] {h.selectFirst("a").attr("abs:href")},null);
            //Add craft object to craft list
            craftArrayList.add(craft);
        }

        //Around the House
        //Get HTML from url provided
        document = Jsoup.connect("https://www.kdl.org/early-lit/around-the-house").get();
        //Get div containing important info
        Element h = document.select(".region-content div.field-item.even").first();
        //Placeholder string
        String age;
        //Iterate through children of div element
        for(Element d:h.children())
        {
            //if child is h2 element
            if(d.is("h2"))
            {
                //Get age from h2 element text
                age = d.text().substring(4);
                //Get h2 element children
                Element b = d.nextElementSibling();
                //Iterate through list of children
                for(Element c:b.children())
                {
                    //Set placeholder craft object to a new Craft object with information from website
                    craft = new Craft(c.text(),new String[]{age},new String[] {c.selectFirst("a").attr("abs:href")},null);
                    //Add craft object to craft list
                    craftArrayList.add(craft);
                }
            }
        }
        //Simple Crafts
        //Get HTML from url provided
        document = Jsoup.connect("https://www.kdl.org/early-lit/simple-crafts").get();
        //Get div containing important info
        h = document.select(".region-content div.field-item.even").first();

        //Placeholder string
        String content = "";
        //List of strings for the category of craft object, for multi-category crafts
        List<String> category = new ArrayList<>();
        //List of strings for the links of craft object, for crafts whose materials contain links
        List<String> links = new ArrayList<>();
        //Iterate through children of div
        for(Element b:h.children())
        {
            //If child is an header 3 element
            if(b.is("h3"))
            {
                //Separate title and categories from h3 element
                String[] split = b.text().split(" \\(");
                //Set title of placeholder craft
                craft.setTitle(split[0]);

                //If there are any categories
                if(split.length >= 2)
                {
                    //Remove last ")" from string
                    split[1] = split[1].substring(0,split[1].length()-1);
                    //split string, iterate through split list and add all to category list
                    for (String s : split[1].split(" and ")) {
                        category.add(s);
                    }
                }
                //set categories of craft object
                craft.setCategories(category.toArray(new String[category.size()]));
                //Clear category list
                category.clear();

                //Get next sibling element
                Element g = b.nextElementSibling();
                //Loop, getting new children element, until child element is a horizontal rule
                while(!g.is("hr"))
                {
                    //If element is a header 4
                    if(g.is("h4"))
                    {
                        //add to content placeholder string
                        content += g.text() + "\n";
                    }
                    //If element is a list element
                    if(g.is("ul"))
                    {
                        //Iterate through children of list items
                        for(Element li:g.children())
                        {
                            //If list item has a link element
                            if(li.select("a").size() != 0)
                            {
                                //Iterate through link elements
                                for (Element a:li.children())
                                {
                                    //Add link url to link list
                                    links.add(a.attr("abs:href"));
                                }
                            }
                            //Add list item content to content string
                            content += li.text() + "\n";
                        }
                        //Set links of craft object
                        craft.setLinks(links.toArray(new String[links.size()]));
                        //Clear link array list
                        links.clear();
                    }
                    //If element is a list element
                    if(g.is("ol"))
                    {
                        //Iterate through children of list items
                        for(Element li:g.children())
                        {
                            //Add list item content to content string
                            content += li.text() + "\n";
                        }
                    }
                    //If there is no next sibling element
                    if(g.nextElementSibling() == null)
                    {
                        //Set content of craft object
                        craft.setContent(content);
                        //Add craft object to craft list
                        craftArrayList.add(craft);
                        //Reset content string
                        content = "";
                        //Reset craft object
                        craft = new Craft();
                        //Break from loop
                        break;
                    }
                    //If there is a next sibling element
                    else
                    {
                        //Get next sibling element and set to element g
                        g = g.nextElementSibling();
                        //If the next sibling element is a hr element
                        if(g.is("hr"))
                        {
                            //Set content of craft object
                            craft.setContent(content);
                            //Add craft object to craft list
                            craftArrayList.add(craft);
                            //Reset content string
                            content = "";
                            //Reset craft object
                            craft = new Craft();
                        }
                    }
                }
            }
        }
        //Iterate through craft objects and print to screen
        for (Craft c : craftArrayList) {
            System.out.println(c);
        }
    }
}

