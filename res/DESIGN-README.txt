A text description (DESIGN-README.txt) of your design.

Our design is based on the MVC Architecture.
There are overall four packages in my src.
1. model
2. view
3. controller
4. data
5. StockDemo(MainClass).

1. Model Classes:

StockPortalOperations: Interface which is a public interface, it will have the
following functionality:
This class also has the list of portfolios added by the user using this class.
a) Create Portfolio which will take name of the portfolio as input and returns void.
b) Buy Share functionality will take the name of the portfolio, ticker symbol, amount you want to
buy the stocks for in dollars, the local date and time in the past, you want to buy.
c) Get cost basis method will return the total cost basis at a certain date. It is basically the
cumulative sum of the total cost basis, till the day they requested.
d) This method will get the total value at a certain date. It is the sum of all the shares based on
the requested date shares price.

StockPortal: This class implements the StockPortalOperations, this is public class as the object
has to be created for this in main class.

Portfolio Operations: This interface is package private which will be used by the stock portal, when
operations specific to each portfolio had to be made.
It carries list of stocks, as multiple stocks can be bought under each portfolio. One bought stocks
cannot be modified.
The operations in this include:
a) Get portfolio, returns the portfolio requested by user to view.
b) Get Stocks, will get the stocks in the portfolio requested by the user.
c) AddStock to portfolio, when a new stock has been bought then it has to be added to the respective
portfolio.

Portfolio: This class implements the Portfolio Operations interface.

Stock: This is final class which will be created with the attributes of the stock that has been
bought, its attributes include ticker, costbasis of that share, date of the purchased share, number
of shares that has been brought.
Assumption here is: The number of shares can be fractional.

2. View:
This package has view related interface and its implementation class.

IStockPortalView: This is a public class which will have method to validate the basic conditions.
The functionality include:
a) Get portfolio name from the user.
b) Get ticker symbol from the user
c) Get the date entered by the user in yyyy-mm-dd format.
d) Get the time entered by the user in hh:mm:ss format.
e) Get menu from the user just gives information an option 0, which will give menu.
f) Get the input scanner object from the controller.
g) Get Data choice from the user waits for word API or file from the user.
h) Get message from controller to show to the user.
i) show options prints the options available to the user, to choose and do.
In these methods until this gets the valid input the view waits and once it receives valid input it
sends to the controller.

StockPortalView: This class implements the IStockPortalView, this is public class as the object
has to be created for this in main class.

3 Data:
This package will hold all the data retrieval operations either from the API or FILE for now.
The interface is common for both these methods, and new Data Retrieval type comes in future like
Database, it just needs to implement this interface.

The functionality includes getting ticker symbol and retrieving the entry corresponding to that in
the data.
For API: To reduce the number of calls to the API, We have maintained a cache, and after the cache
with a maximum limit.
When cache has reached its maximum limit then we remove the entries with least used ticker symbols.
For FILE: We have files specific to the ticker symbol, this serves as sample/offline data when the
API is offline or user is offline to still work on this application user can chose data as FILE Type.

4 Controller:

The controller has one method which will be called by the main method in StockDemo class, this will
act as intermediate layer between viewing and logic(model).
It takes input of the type of data(FILE or API) he wants to use in that session and uses the same
data for the whole session.
Once user chooses the data type then we show them the operations they can perform on that particular
data.

It has a public interface IStockPortalController, which takes View and Model as its
constructor parameters.


StockDemo Main Class:
This main class will create objects for
Model: It doesn't take any value as constructor parameter.
View: It takes Readable and Appendable as constructor parameters.
Controller: It will take model and view as constructor parameter and the handleRequest method of
controller is called.

----------------------------------------------------------------------------------------------------

CHANGES FOR EXISTING
1. Changed the return type of get portfolio and get portfolios to object(portfolio) an unmodifyable
instead of string, to let the controller make the changes of formatting.
2. Changed the quit to return to menu, instead of exiting the method, to maintain the uniformity
across the application and to act more user friendly.
3. Fixed the defect, related to 31st september showing as valid date and not letting user enter any
future date as input.
4. Added $ symbol for letting user know to buy shares in dollar.
5. When user tries to buy a stock on weekend or holiday then the stock will be bought on the next
working day.

STOCK PLUS PLUS:
Advanced Stock Portal

CONTROLLER:

1. Advanced Stock Portal Controller
This new controller will extend the existing old controller, and when clicked on buying shares, to
provide three options to buy share
They are: Buy a share with commission, Make a fixed investment on existing portfolio, Make a dollar
cost averaging on existing portfolio and also create new portfolio and applying recurring function
on it.
Commission is taken for every different type of buy, assuming user wants to try out different
commissions for different type of investments.

Dollar Cost Averaging:
For this the user will be asked for the name of portfolio, frequency of investments(like every
10days), start date, End dat(if Any if not mentioned current date is considered as end date),
if wants to invest equally on all share or custom percents:

a) If non existing user will be asked to enter number of stock he wants to add,
later asks to enter ticker code, commission which if valid asks for percent of total amount
that should be assigned to it one by one.
b) If non existing and chose to equally invest on all about to give stock, user will only be asked
to enter only tickers.
c) If an existing portfolio, then the user will be asked to enter percents for existing tickers of
portfolio only, and the choice of equally will work same as non existing one.
d) If the portfolio is existing and no entries are present in it, then this function will ask user
to enter stock like its a new portfolio.

Fixed Investment:
This function will let user to only make a fixed investment on existing portfolio with stocks in it,
else this method will not let go further than entering portfolio name.
a) When asked to invest equally on all stock this method will not ask user to enter percents.
b) When asked to invest custom stocks then it will specifically asks the user to enter percents.

Buy With Commission:
This method will ask the user for commission, the commission will not be included in the amount to
buy shares of a stock, instead it will be taken down as extra money that was cut for a transaction.

MODEL

2. Advanced Stock Portal Operations:
This interface will extend existing interface of stock portal and add new functions required for
advanced application.
Which are:
1. Buy share with commission
2. Check if the ticker is valid, this method goes to selected data type class and checks if that
ticker exists and valid. For API after checking its validity it adds the stock to cache.

VIEW:

3. IAdvanced View:
This interface will extend the existing interface and the implementing class for this will extend
existing class and implement this interface, to add new functions on top of existing.

This view checks for new requirements, such as choice of investing eqally or not, selecting end date
or not. Commission, Percent of weights, number of stocks and one method to display the new options
added.

CHANGES FOR EXISTING
1. Created a Strategy class to save and retrieve strategies.
2. Moved the Strategies from command design controller to model.

STOCK PLUS PLUS:

Advanced Plus Stock Portal

The user gets additional features of saving a portfolio, retrieving a portfolio, saving a strategy,
retrieving a strategy and applying it to a portfolio. All these actions need to be taken by the user.
This application does not support auto save feature.

It also gives the additional feature of plotting the performance of the portoflio as line graph.
In performance we considered the total value of portfolio at every day.

EXTERNAL LIBRARIES:
As part of this assignment, we have used two external libraries, which are GSON and JFreeChart,
which are free and publicly available for academic use.

CONTROLLER:

1. Two controllers are created one for GUI and other for overriding then create portfolio button to
perform the CRUD Operations like create Portfolio, Save Portfolio, Retrieve portfolio.
The operations such as retrieve the strategy and asks if we want to apply on recurring strategy,
when the recurring strategy is called.
2. Both these controllers will be extending the existing controller.
Similarly after performing the recurring strategy, we give user the choice to save it in strategies,
if at all he is interested.
3. The GUI Controller will have composition relation with ButtonListener class, so whenever a action
defined listener button is clicked then the method in controller gets activated and starts assigning
 and reassigning the objects
4. In controller we have a package private interface called "Validation" which will contain the
common code for from both the controllers, this will be in association relationship with the
GUI and Command Controllers.


MODEL:
1. We created a new Model Class "AdvancedPlusStockPortal", for save and retrieval operations, we
have created a DAO Layer which and query and save the values to the files.
2. We have two separated folders for portfolio and Strategy, which will store the JSON Files.


VIEW:
1. In view to have a track of different methods, and to not have methods in an interface, which it
doesn't need I create a new Class for View, which will extend a GUICmndView which will have the
common methods of Command and GUI, in future if there are any common methods to be implemented,
they can be added to this interface.
2. For GUI we have IGUIView Interface and GUI view which implemented this interface.
3. The GUI view persists, the valid inputs and only removes invalid inputs when in submit.
Options visible to user are:
1. Plot Graph: This will plot the portfolio performance.
2. Options:
     Under this we have 1. Create Portfolio, 2. Save Portfolio, 3. Retrieve Portfolio
3. Buy Options:
       Under this we have 1.Buy Share 2. Make One time investment 3. Make Recurring Investments.
4. Cost Basis:
        This gets the cost basis for the respective portfolio user wants to view on particular day.
5. Total Value:
        This gets the cost basis for the respective portfolio user wants to view on particular day.
6.  Get Portfolio: shows a drop down and allows user to query.
7. Get all portfolios: shows all the portfolios that the user has
8. exit: exits the application.
9. the close button option on the top also exits the application gracefully.