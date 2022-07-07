#    TO_DAY    

>To-day. Everything you need to do today.*

>This To-day android application is an application where users can keep a list of everything they need to do. It is a
>so-called 'todo-list' application.

## Basic operation

When the app is opened, you will arrive at the login screen. Here you can log in with your existing login details or choose to register. When registering you can create your own unique username. This is so only you can see or edit your own tasks!

After entering the login details, you will arrive at your own personal task list. Here you can see at a glance which tasks you have. You can check your tasks to "done" or if you accidentally make a mistake, press "done" to view your checked tasks. Here you can "uncheck" your already checked tasks. The task then goes back to the "to do" tasks.

On this screen you can also tap the icons to sort and filter your tasks. This can be default, descending or ascending, according to the deadline, or you can filter by label. If you want to delete a task, you can delete the task by long press on the task in the task screen.
You can also easily create labels through this screen by simply pressing the label icon. This will take you to the label screen where you can create your own personal labels. You can use these labels when creating your task. You can delete a label by pressing the chosen label on the label screen.

A task consists of a title (name of the task), a deadline, possibly a chosen label, and a description. To view the task in full, press the task from your to do or done task list. This will take you to the view screen. You can only view the task here and not edit it. If you still want to edit the task, press the edit button. Here you come to the edit screen. You can now edit, rename the task. You can also return directly to your task list from this screen. 

If you want to create a task, click on the + at the bottom of your task list. Here you come to the add screen. Here you can create a task by filling in the empty fields and saving. The name of your task, the possible deadline, your own, existing or no label. You can also give an extra description of your task. Handy if, for example, you need to bring or pick up something to be able to carry out your task.

The job is saved in the database. When saving the task in the database, the username is included to protect the user data.
When the task is retrieved from the database, a search is first performed on username and then on task id.
When you're good and ready you can log out in the task list screen. So you know your tasks are private.

## Other information 

To create To-Day we use Android Studio® as Development Environment (IDE) for Android app development and use Java as code language. As minimum and target SDK we use API 29, this means the minimum required version of Android for running the app is 10. The compile SDK is 31 (as default by Android Studio 2021.2.1 Patch 1) and we used a Pixel 5 emulator for testing.
We choose to use localDateTime for the deadline. The tasks are retrieved from the database per method. We give the tasks through the intents with the taskId number and then retrieve the task from the database in the next view. Our application is designed with English as main language. However, if you change the settings on your device to NL, you can use the app in Dutch.
The app is designed with dark mode in mind, but it supports light mode just as well.
In our ERD diagram, there are sometimes uppercase letters while we use for the code consistency, variables with lowercase letters.


Quote *(assignment Haagse Hogeschool( nd.), To-Day application, APEP CASE)
