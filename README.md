# automation-examples
This repo represents my java-playwright practice:

1. Login-screenshot with threading:
This script demonstrates usage of threading (that is not natively offered in playwright for java)
written with documentation
This script logins into x.com and save screenshot with three different browsers in headed mode (actually launches browser window for user to track)
uses try because of construction in documentation
uses if else because x.com sometimes asks for nickname confirmation
replaced actual login information with fakes

2. Simple login-screenshot:
This script logins into x.com and save screenshot
uses try because of construction in documentation
uses if else because x.com sometimes asks for nickname confirmation
replaced actual login information with fakes

3. login with usage storage.json and using junit:
This script stores login session in .json file
also using notations with junit5
simple login into github - store session, close browser, open another and use store session to look at locator that is visible only for logged user
also learned launch in order with junit
