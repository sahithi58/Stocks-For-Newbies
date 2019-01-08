**IMPORTANT**
In the folder which contains the jar,make sure the properties file named config.properties is in it.
The config.properties must have the path of the folder containing the files that the user wants to use.
The config.properties looks like if the files are available in Datafiles folder:
filePath = C:\\Users\\Documents\\Datafiles\\      :if Windows machine

The file names must be only the TICKER symbol and the format of the file must be a csv file consisting
of a header which has timestamp,open,high,low,close,volume followed by the data in this specific format.
eg: GOOG.csv is a valid file

Also if the user wants to use the save and retrieve functionality there are two folders
Portfolios and Strategy in the same location as his jar. These folders have the .json files for the
respective Object. Please refer the FILE-FORMAT-README.txt.

Next step:

For Windows Machine

1. Open the command prompt
2. Navigate to the location which has the jar and properties file
    eg: cd C:\Users\Documents\PDP\PDP Assignments\Assignment8\
3. Type: java -jar VirtualTradingApplication.jar
4. Ta-Da ! Enjoy learning trading :)
IMPORTANT : Due to some reasons we are unaware of first time when we run the jar the screen might
look blank, please minimize and maximize the application to view our it completely.

For Linux/Mac OS

1. Open the terminal
2. Navigate to the location which has the jar and properties file
    eg: cd cd /home/PDP/PDP Assignments/Assignment8/
3. Type: java -jar VirtualTradingApplication.jar
4. Ta-Da ! Enjoy learning trading :)

IMPORTANT : Due to some reasons we are unaware of first time when we run the jar the screen might
look blank, please minimize and maximize the application to view our it completely.