# FeedReader

FeedReader is a simple Spring-Boot-Application, which uses Vaadin to show preferred feeds, which are editable in the feed.properties-file. 

One Page shows the channels from the feeds and their entries, which can be stored in an in-memory-database. A second page lists these archived entries. 

The structure of this application is inspired from the [Vaadin Tutorial](https://vaadin.com/learn/tutorials/modern-web-apps-with-spring-boot-and-vaadin).

## Usage
* Use `mvn spring-boot:run` to start the application
* Open `localhost:8080` in a browser to use the application