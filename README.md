# Companies News

A Simple Spring Boot project to search all the possible company names (from a csv dataset) in a group of articles provided.

# Requirements

Java > 1.8 is mandatory to run the project. If you want also to check and debug the code, you can use an IDE like IntelliJ
(recommended) or Eclipse.

# Installation 🛠️

You can clone the project from this link:

```sh
git clone https://github.com/valerio65xz/reprisk.git
```

# Usage ℹ️

If you want to just execute the project, open a terminal in your installation folder and type:

```sh
java -jar CompaniesNews.jar
```

then if you use Postman, you may import `Reprisk.postman_collection.json` to import the collection.

There is just one API
* `GET companies`: Get the IDs of the companies found in the articles

if you don't have or don't want to use postman:
* The URL to call `http://localhost:8080/companies/find`
* Body type: JSON
* Params:
  * pathOfArticles: the absolute path of the articles directory
  * pathOfDataset: the absolute path of the csv companies dataset

# Docs 📚

You can find the detailed documentation in `Docs.pdf` and the OpenAPI 3.0 Swagger at `http://localhost:8090/swagger-ui/index.html`.
