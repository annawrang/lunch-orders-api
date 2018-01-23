# Lunch Orders

The team at Rungway are creatures-of-habit, and so when we come to get team lunch from our favourite Thai restaurant on a Friday we all tend to order the same thing. To make our lives easier, we made an API to record our preferences so we don't need to all respond in Slack every week. 

## Building & Running

The API is backed by MongoDB. The simplest way to get this is through [Docker](https://www.docker.com/):

```
$ docker run -p 127.0.0.1:27017:27017 -d mongo
```

The API can then be built and run using:

```
gradle bootRun
```

The app boostraps some data for our favourite Thai restaurant when it initialises, which you can see with this call:

```
$ curl localhost:8080/restaurants/spicy-basil/orders
{"Endre":"Thai Green Curry","Ana":"Prawn Pad Thai","Harry D":"Chilli Chicken Fried Rice","Rohith":"Spidy Chicken Pad Thai"}
```

## Requirements

We'd like to make some improvements to this system:

- Some people want to change their preference (shock!), or add a preference for a new restaurant. We'd like the API to support a call to set the food preference for a specific person at a specific restaurant. For simplicity, you can assume everyone's name is unique. 
- To aid with the above, people would like to be able to list all their preferences from the different restaurants. Add an endpoint which returns the orders each person would make at all the restaurants they've specified.
