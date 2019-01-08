We allow the user to Save and Retrieve Portfolios and Strategies in json format. The user can also
create a json file by himself and paste it in the folder later calling retrieve on the file to use
it in the application. Each portfolio/strategy is saved in separate json files using the
portfolioName/strategyName which have to be unique, else if it is an already existing file it will be
overwritten by the new data given by the user. The format for the json files for both objects is mentioned below:

Portfolio:
Consists of list of stocks and the portfolio name.
Each stock contains:
costBasis : cost at which the stock has been bought,
ticker : ticker of the company whose stock has to be bought,
datePurchased : the date and time the transaction of buying stock,
noOfShares : number of shares that have to been purchased successfully,
commission : the amount that is considered as commission to the user
The file name is the portfolio Name given by the user.
Example :
FileName: User Portfolio.json
{"stocks":[{"costBasis":83758.0,"ticker":"GOOG","datePurchased":{"date":{"year":2018,"month":8,"day":3},"time":{"hour":11,"minute":30,"second":0,"nano":0}},"noOfShares":68.44595533255428,"commission":0.0}],"portFolioName":"User Portfolio"}

Strategy:
Strategy json file must have:
strategyName : unique name for the strategy,
strategyType : The strategyType field can only be of two types i.e. RECURRINGINVESTMENT, ONETIMEINVESTMENT,
startDate : the date the strategy implementation should start,
endDate :  the date the strategy implementation should end,
commission : the amount that is considered as commission to the user,
amount : amount to be invested in the portfolio in this strategy,
frequency : frequency of applying the strategy on the portfolio in the given time period,
tickerWeightMap : the map of all the tickers and their percentage weights that are associated with the strategy.
File name is the strategy Name given by the user.
Example :
FileName: Strategy.json
{"strategyName":"strategy","strategyType":"RECURRINGINVESTMENT","startDate":{"date":{"year":2018,"month":9,"day":19},"time":{"hour":10,"minute":0,"second":0,"nano":0}},"endDate":{"date":{"year":2018,"month":12,"day":6},"time":{"hour":10,"minute":0,"second":0,"nano":0}},"commission":5.0,"amount":2000.0,"frequency":20,"tickerWeightMap":{"FB":100.0}}