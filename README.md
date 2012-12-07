LogProbe
========

This is the repository for an ohmage probe that collects logging events and pushes them to the ohmage server's Observer API.

To use LogProbe in your application, download the source and make it available to your application as a library project. Participants will also need to have ohmage running on their phone (version 2.13 or greater) and it will need to be logged in to the server where you want to collect the logs.

The best way to use the logger is to initialize it in your Application onCreate() function:
LogProbe logger = new LogProbe();
logger.connect(this);

and close() the connection after you are done logging. This can be placed in the onTerminate() function of your application or you can close the connection after you are done logging if you are sure you wont be logging again soon (as you will need to reconnect to send logs).