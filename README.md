# Microsoft Teams Notifier on GitLab Events




### Brief Description about the project:

This project is a Java (Spring boot) based application, and it uses [Bot Framework](https://dev.botframework.com), which can be used to send notifications to Microsoft Teams personal chat, whenever a new trigger occurs in Gitlab repository. <span  style="color: red;" >**Note: This was my internship Project.** </span>




### Details about the application's implementation:





#### 1. Gitlab Webhook:

Gitlab webhook is used to trigger the application whenever a new event occurs in Gitlab repository.
The Application is currently running on my local host, so I have used ngrok to expose my local host to the internet.
The ngrok url is used as the webhook url in Gitlab repository settings.
The endpoint /api/gitlab is used to receive the webhook request from Gitlab.

#### 2. Events in Gitlab:

The following events occurred in gitlab are handled in this application:

- Comment on issue
- Comment on merge request
- Comment on commit
- Comment on snippet
- Accepting merge request
- Closing merge request
- Opening merge request
- Reopening merge request
- Approving merge request
- Unapproving merge request
- Updating merge request
- Pipeline success
- Pipeline failed
- Job success
- Job failed


#### 3. Microsoft Bot Framework:

This Application uses Microsoft Bot Framework, to handle messaging from the [Middleware server](src%2Fmain%2Fjava%2Fcom%2Fsprinklr%2FInternProjectBot%2FController%2FGitlabController.java) to Microsoft Teams. 

#### 4. Server side implementation:

The following are the steps which are followed in the server side implementation:

- The application is implemented using Java (Spring boot) and is running on my local host (Exposing my local host to the internet using ngrok).
- There are two classes of End point controllers:
    1. GitlabController.java
    2. MicrosoftController.java
- GitlabController.java is used to receive the webhook request from Gitlab from the endpoint /api/gitlab, process the request and send the notifications to Microsoft Teams personal chat (In an abstract way, this is what it does, but there are many classes under it which performs the operations).
- The function receivePostRequest(), which is present in GitlabController.java ,does these jobs:
    1. Receives the webhook request from Gitlab.
    2. Tell the factory class to create the object of the class which is responsible for processing the request.
    3. The created object will process the request data, and gives the details about what message is to be sent to whom.
    4. The details are then passed to MicrosoftMessageSender interface.
    5. The messages are sent by parsing the userEmail, and the data received.


### Features of the application:

- Notification through teams for various GitLab events : Comments, Merge Request events, Pipeline events, Job events.
- Notifying the people in the discussion thread when a new comment is added.
- Notifying the people when someone got mentioned in a comment.
- Links are provided in the Notification to visit the event.
- Code content on which the comment is made is visible with proper colors displaying the diff.
- Last comment in the discussion thread is visible.
- Reply on the thread from the Microsoft Teams.
- Resolve the Discussion thread from Microsoft Teams.
- User Authentication.
- Persisting the conversation state in the database.
- Subscribe and Unsubscribe feature.
- Used Cards to display rich text and HTML content in Teams chat.


### How to use this application:



- Download this file, and paste this file in [Archive.zip](Archive.zip) , then follow the below steps from the images:

- Goto Apps section.
![Screenshot 2023-07-14 at 11.57.51 AM.png](Screenshots%2FScreenshot%202023-07-14%20at%2011.57.51%20AM.png)

- Click on Manage your apps.
![Screenshot 2023-07-14 at 11.57.57 AM.png](Screenshots%2FScreenshot%202023-07-14%20at%2011.57.57%20AM.png)
- Click on Upload upload an app.
![Screenshot 2023-07-14 at 11.58.04 AM.png](Screenshots%2FScreenshot%202023-07-14%20at%2011.58.04%20AM.png)
- Click on Upload a customized app.
![Screenshot 2023-07-14 at 11.58.10 AM.png](Screenshots%2FScreenshot%202023-07-14%20at%2011.58.10%20AM.png)

Please follow the above steps in Microsoft teams.

### Implementation Details and other documents: 

The doc below contains how I implemented this app:
[Project Report](Project_Report.pdf)

The below link contains all the implementaiton details of this application:
https://sprinklr-my.sharepoint.com/personal/nabagata_saha_sprinklr_com/_layouts/15/onedrive.aspx?ct=1689313750026&or=Teams%2DHL&ga=1&id=%2Fpersonal%2Fnabagata%5Fsaha%5Fsprinklr%5Fcom%2FDocuments%2FIntegration%20Interns%2FSai%20Akshith%20Arthi&view=0

