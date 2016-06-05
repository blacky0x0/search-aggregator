# Search aggregator

### Purpose 
Get data (SERPs) from search engines by search phrase and aggregate this data using specified sorting algorithm. 

### Using
Before using you should get your own search engine API keys from companies. API keys need for creating crawler instances.  
Look at https://datamarket.azure.com/dataset/bing/search to get Bing API key and https://developers.google.com/custom-search/json-api/v1/overview to get Google API key with custom search engine id (the same as container id or cx).

To get results you have to use `SearchAggregator.searchPhrase(...)` with predefined parameters: search phrase, list of crawlers and sorting strategy.
Currently there are two crawlers (BingCrawler, GoogleCrawler) and two aggregation strategies (NaturalOrderAggregationStrategy, RoundRobinAggregationStrategy).
Search phrase will be always escaped by single quotes, for instance, 'cars'. 

```java
import com.blacky.sa.model.SearchResult;
import com.blacky.sa.crawler.*;
import com.blacky.sa.strategy.*;

// Minimal snippet of code

String bingApiKey = "bing_api_key";
String googleApiKey = "google_api_key";
String googleCx = "custom_search_engine_id";

// Create crawlers to get data from search engines
BingCrawler bingCrawler = new BingCrawler(bingApiKey);
GoogleCrawler googleCrawler = new GoogleCrawler(googleApiKey, googleCx);

// Place them to list
ArrayList<Crawler> crawlers = new ArrayList<Crawler>() {{
    add(bingCrawler);
    add(googleCrawler);
}};


List<SearchResult> aggregatedSerp = 
    SearchAggregator.searchPhrase("Search phrase", crawlers, new NaturalOrderAggregationStrategy());

```

### Restrictions 
Any single result (SERP) from any crawler will be contained from **10 items**. 
SearchAggregator will wait any response from search engine API for **5 seconds**, so if there is no response in time then an exception will be thrown.
The application throws only **RuntimeExceptions**.
