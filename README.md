# BRAGI
This Task for Bragi company interview Process.

#Architecture
- I used MVI architecture with
	- intents to send the user or the view actions
	- state to change the state of the UI
	- effects to send effects from the view-model to the view like navigation or pop
- used a shared ViewModel for handling the 3 screens because they shared the same logic

#Notes
- the 3 screens shared the same connection states and each screen display the pop on it's viewwhen they subscribe to the states or effects changes
- I made alsothe  made intent for resting the connection and restarting it again if you want to start a new connection on each screen check  my comment on the onPause method in the login fragment.
- in step 2 I applied the command design pattern with 2 different ways:
	- first one with kotlin coroutines.
	- second with RX java check and run the main function on 
```
CommandProcessor
```

#testing
- I did a sample test for the repository and the viewmodel.
