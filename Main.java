import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

 class Main {
    static int choice;


    public  static void  main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //creating obj:
        AddingTasks obj = new AddingTasks();
        Addsection ob1 = new Addsection();
        Display display = new Display();
        TimerControl timerControl = new TimerControl();
        DeleteTask deleteTask = new DeleteTask();

        //Arraylist created:
        ArrayList<AddingTasks> taskList = new ArrayList<>();
        HashMap<String, ArrayList<AddingTasks>> sections = new HashMap<>();
        taskList.add(new AddingTasks("Sample Task 1"));
        sections.put("General", taskList);



        //Welcomcing Message!
        System.out.println("---------Welcome to MyToDoApp---------");
        System.out.println("💬 Quote of the Day: \" Motivation. Get it done!\" 💥");
        boolean check = true;
          while(check){
              System.out.println("\n┌──────────────────────────────────────────────────────────┐");
              System.out.println("│                    🧠  TO-DO TASK MANAGER                  │");
              System.out.println("├────────────────────────────────────────────────────────────│");
              System.out.println("│ ---------------------------------------------------------- │");
              System.out.println("│ 1. ➕ Add Task to General Section                          │");
              System.out.println("│ 2. 🧩 Add a New Section                                    │");
              System.out.println("│ 3. 📋 Display All Tasks                                    │");
              System.out.println("│ 4. ✅ Display Completed Tasks                              │");
              System.out.println("│ 5. ⏳ Display Pending Tasks                                │");
              System.out.println("│ 6. ⏳ Display Ongoing Tasks                                │");
              System.out.println("│ 7. ✋ Start/Stop Timer for a Task                          │");
              System.out.println("│ 8. 🗑️  Remove a Task                                       │");
              System.out.println("│ 9. ❌ Exit                                                 │");
              System.out.println("└────────────────────────────────────────────────────────────┘");

              System.out.print("🎯 Enter your choice (1–10): ");
             try{
                  choice = sc.nextInt();
                 if(choice <1 || choice > 10){
                     InvalidNumberException.InvalidChoiceException(choice);
                 }
             }
             catch(InvalidNumberException e){
                 System.out.println(e.getMessage());
             }

              switch (choice) {
                  case 1:
                      // Add Task to General Section
                      System.out.println("You chose to add a task to the General Section.");
                      try {
                          obj.addTask(taskList , "General");
                      } catch (InvalidNumberException e) {
                          System.out.println(e.getMessage());
                      }
                      // Add your logic for adding a task here
                      break;

                  case 2:
                      // Schedule Task in a Specific Section
                      System.out.println("📌 You chose to schedule a task in a new section.");
                      try {
                          ob1.createSectionWithTasks(sections);
                      } catch (InvalidNumberException e) {
                          System.out.println("❌ Error: " + e.getMessage());
                      }
                      break;

                  case 3:
                      // Display All Tasks
                      System.out.println("You chose to display all tasks.");
                      // Add your logic for displaying all tasks here
                      display.displayAllTasks(sections);
                      break;

                  case 4:
                      // Display Completed Tasks
                      System.out.println("You chose to display completed tasks.");
                      display.displayCompletedTasks(sections);
                      break;

                  case 5:
                      // Display Pending Tasks
                      System.out.println("You chose to display pending tasks.");
                      display.displayPendingTasks(sections);
                      break;

                  case 6:
                      // Display Ongoing Task:
                      System.out.println("You chose to display pending tasks");
                      display.displayOngoingTasks(sections);
                      break;

                  case 7:
                      // Stop Timer for a Task
                      System.out.println("You chose to Start or stop the timer for a task.");
                      timerControl.manageTaskTimer(sections);
                      break;


                  case 9:
                      // Remove a Task
                      System.out.println("You chose to remove a task.");
                      deleteTask.removeTaskFromSection(sections);
                      break;

                  case 10:
                      // Exit
                      System.out.println("Exiting the system. Goodbye!");
                      check = false;
                      break;

                  default:
                      System.out.println("Invalid choice. Please select a valid option (1-10).");
              }
          }
        }
}


//all exception classes
class InvalidNumberException extends Exception{
   InvalidNumberException(String s){
       super(s);
   }
    static void InvalidChoiceException(int num) throws InvalidNumberException{
        throw new InvalidNumberException("Entered a wrong choice !!..Renter again");
    }
}
class InvalidDeadlineAddedException extends Exception {
    InvalidDeadlineAddedException(String message) {
        super(message);
    }

     static boolean isValidDate(String date) throws InvalidDeadlineAddedException{
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            throw new InvalidDeadlineAddedException("❌ Invalid date format! Use yyyy-MM-dd.");
        }
    }

    static boolean isValidTime(String time) throws InvalidDeadlineAddedException {
        try {
            LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            throw new InvalidDeadlineAddedException("❌ Invalid time format! Use HH:mm in 24hr format.");
        }
    }
}

class AddingTasks {
    Scanner sc = new Scanner(System.in);
    public TaskTimer timer; // ⏱️ Timer associated with each task
    AddingTasks(){
        //default:
    }
    // Constructor
    public AddingTasks(String taskName) {
        this.taskName = taskName;
        this.timer = new TaskTimer(); // initialize the timer
    }

    String taskName;
    String status;
    String deadline , date , time;

    boolean checkYesorNo() {
        System.out.println("Do you want to proceed?\n1] Yes \n2] No");
        int num = sc.nextInt();
        if (num == 1) {
            return true;
        } else {
            return false;
        }
    }

    void addTask(ArrayList<AddingTasks> taskList,  String sectionName) throws InvalidNumberException {
        System.out.println("\n✨ You are now entering the Add Task section ✨");

        System.out.print("Enter the number of tasks you want to add: ");
        int taskCount = sc.nextInt();

        sc.nextLine(); // Clear newline

        for (int i = 0; i < taskCount; i++) {
            AddingTasks newTask = new AddingTasks();

            System.out.print("\n📝 Enter Task " + (i + 1) + " Title: ");
            newTask.taskName = sc.nextLine();


            // Status Selection
            sc.nextLine();
            System.out.println("Select Task Status:");
            System.out.println("1. ✅ Completed");
            System.out.println("2. ⏳ Pending");
            System.out.println("3. 🔄 Ongoing");
            System.out.print("Enter your choice (1–3): ");
            int statusChoice = sc.nextInt();


            switch (statusChoice) {
                case 1:
                    newTask.status = "Completed";
                    break;
                case 2:
                    newTask.status = "Pending";
                    break;
                case 3:
                    newTask.status = "Ongoing";
                    break;
                default:
                    throw new InvalidNumberException("Invalid status choice. Please enter 1, 2, or 3.");
            }

            // Deadline Choice
            System.out.println("🕒 Do you want to add a deadline?");
            System.out.println("1️⃣ Yes\n2️⃣ No");
            int deadlineChoice = sc.nextInt();


            if (deadlineChoice == 1) {
                boolean deadlineSet = true;
                boolean dateSet = true;
                boolean timeSet = true;

                while (deadlineSet) {
                    System.out.println("📌 Select deadline type:");
                    System.out.println("1. 📅 Date (YYYY-MM-DD)");
                    System.out.println("2. ⏰ Time (HH:MM)");

                    int selectChoice = sc.nextInt();


                    if (selectChoice == 1){
                        System.out.println("Enter 📅 Date (YYYY-MM-DD) in this Format: ");
                        while(dateSet) {
                            date = sc.next();
                            try {

                               boolean checkAns = InvalidDeadlineAddedException.isValidDate(date);
                               if(checkAns){
                                   dateSet = false;
                               }

                            } catch (InvalidDeadlineAddedException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        if(dateSet == false){
                            System.out.println(date + " : Deadline Added Succesfully!");
                            deadlineSet = false;
                        }
                    }

                    if (selectChoice == 2){
                        System.out.println("Enter ⏰ Time in (HH:MM) in this Format: ");
                        while(timeSet) {
                            time = sc.next();
                            try {
                                boolean checkAns = InvalidDeadlineAddedException.isValidTime(time);
                                if (checkAns) {
                                    timeSet = false;
                                }

                            } catch (InvalidDeadlineAddedException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        if(timeSet == false){
                            System.out.println(time +" : Deadline Added Succesfully!");
                            deadlineSet = false;
                        }
                    }
                }
            }

        }
    }
}

class Addsection{
     AddingTasks  obj1 = new AddingTasks();
    public void createSectionWithTasks(HashMap<String, ArrayList<AddingTasks>> sections) throws InvalidNumberException {
        Scanner sc = new Scanner(System.in);
        boolean continueManaging = true;

        while (continueManaging) {
            System.out.println("📂 Enter a new section name: ");
            String sectionName = sc.nextLine();

            // Check if section exists
            if (sections.containsKey(sectionName)) {
                System.out.println("⚠️ Section '" + sectionName + "' already exists. Please choose another name.");
            } else {
                // Create new section with empty task list
                ArrayList<AddingTasks> newTaskList = new ArrayList<>();
                sections.put(sectionName, newTaskList);
                System.out.println("✅ Section '" + sectionName + "' created successfully!");

                // Ask user if they want to add tasks to this section
                System.out.println("🧩 Do you want to add tasks to this section? (1️⃣ Yes / 2️⃣ No)");
                int addChoice = sc.nextInt();
                sc.nextLine(); // consume newline

                if (addChoice == 1) {
                    // Call your original addTask method but use this new task list
                    obj1.addTask(sections.get(sectionName), sectionName);
                }
            }

            // Ask user if they want to display all sections
            System.out.println("📑 Do you want to display all sections and their tasks? (1️⃣ Yes / 2️⃣ No)");
            int displayChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (displayChoice == 1) {
                System.out.println("\n📚 All Sections with Their Tasks:");
                for (String sec : sections.keySet()) {
                    System.out.println("\n📂 Section: " + sec);
                    ArrayList<AddingTasks> taskList = sections.get(sec);
                    if (taskList.isEmpty()) {
                        System.out.println("   (No tasks yet)");
                    } else {
                        for (int i = 0; i < taskList.size(); i++) {
                            AddingTasks t = taskList.get(i);
                            System.out.println("🔸 Task " + (i + 1) + ": " + t.taskName);
                            System.out.println("🔸 Status: " + t.status);
                            System.out.println("🔸 Deadline: " + t.deadline);
                        }
                    }
                }
            }
            // Ask user if they want to add another section
            System.out.println("📂 Do you want to add another section? (1️⃣ Yes / 2️⃣ No)");
            int continueChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (continueChoice == 2) {
                continueManaging = false; // Exit the loop
                System.out.println("Exiting the section creation process.");
            }
        }
    }
}



 class Display {

    // 🔹 Display ALL tasks grouped by section
    public void displayAllTasks(HashMap<String, ArrayList<AddingTasks>> sections) {
        if (sections.isEmpty()) {
            System.out.println("❗ No sections or tasks to display.");
            return;
        }

        System.out.println("\n📚 All Sections with Their Tasks:");
        for (String sec : sections.keySet()) {
            System.out.println("\n📂 Section: " + sec);
            ArrayList<AddingTasks> taskList = sections.get(sec);
            if (taskList.isEmpty()) {
                System.out.println("   (No tasks yet)");
            } else {
                for (int i = 0; i < taskList.size(); i++) {
                    AddingTasks t = taskList.get(i);
                    System.out.println("🔸Task " + (i + 1) + ": " + t.taskName);
                    System.out.println("🔸Status: " + t.status);
                    System.out.println("🔸Deadline: " + (t.deadline == null ? "N/A" : t.deadline));
                }
            }
        }
    }

    // ✅ Display only Completed Tasks
    public void displayCompletedTasks(HashMap<String, ArrayList<AddingTasks>> sections) {
        displayByStatus(sections, "Completed");
    }

    // ⏳ Display only Pending Tasks
    public void displayPendingTasks(HashMap<String, ArrayList<AddingTasks>> sections) {
        displayByStatus(sections, "Pending");
    }

    // 🔄 Display only Ongoing Tasks
    public void displayOngoingTasks(HashMap<String, ArrayList<AddingTasks>> sections) {
        displayByStatus(sections, "Ongoing");
    }

    // Helper method to filter by status
    private void displayByStatus(HashMap<String, ArrayList<AddingTasks>> sections, String filterStatus) {
        boolean found = false;

        System.out.println("\n📋 " + filterStatus + " Tasks:");

        for (String sectionName : sections.keySet()) {
            ArrayList<AddingTasks> taskList = sections.get(sectionName);

            for (AddingTasks task : taskList) {
                if (task.status.equalsIgnoreCase(filterStatus)) {
                    found = true;
                    System.out.println("📂 Section: " + sectionName);
                    System.out.println("   🔸 Task: " + task.taskName);
                    System.out.println("      Deadline: " + (task.deadline == null ? "N/A" : task.deadline));
                }
            }
        }

        if (!found) {
            System.out.println("❌ No tasks found with status: " + filterStatus);
        }
    }
}



 class TaskTimer {
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;
    private Timer timer;

    // Constructor
    public TaskTimer() {
        this.isRunning = false;
        this.elapsedTime = 0;
    }

    // Start the timer
    public void start() {
        if (isRunning) {
            System.out.println("⏱️ Timer is already running!");
            return;
        }

        isRunning = true;
        startTime = System.currentTimeMillis() - elapsedTime; // account for previous time elapsed
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime = System.currentTimeMillis() - startTime;
            }
        }, 0, 1000); // update every second

        System.out.println("⏱️ Timer started!");
    }

    // Stop the timer and return the elapsed time
    public long stop() {
        if (!isRunning) {
            System.out.println("⏱️ Timer is not running!");
            return 0;
        }

        isRunning = false;
        long finalElapsedTime = elapsedTime;
        timer.cancel(); // stop the timer
        System.out.println("⏸️ Timer stopped!");
        return finalElapsedTime;
    }

    // Get the elapsed time in seconds
    public long getElapsedTimeInSeconds() {
        return elapsedTime / 1000; // convert from milliseconds to seconds
    }

    // Reset the timer
    public void reset() {
        isRunning = false;
        elapsedTime = 0;
        startTime = 0;

        if (timer != null) {
            timer.cancel(); // cancel the timer if it was running
        }

        System.out.println("🔄 Timer reset!");
    }
}



 class TimerControl {

    // Method to manage timer for tasks in sections
    public void manageTaskTimer(HashMap<String, ArrayList<AddingTasks>> sections) {
        Scanner sc = new Scanner(System.in);
        boolean isValidTask = false;
        AddingTasks selectedTask = null;

        // Ask user if they want to add a timer to a specific task or randomly
        System.out.println("Do you want to add a timer for a specific task or any random task?");
        System.out.println("1️⃣ Specific Task");
        System.out.println("2️⃣ Random Task");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        // If user wants to add timer to a specific task
        if (choice == 1) {
            System.out.println("Enter the section name to choose a task from: ");
            String sectionName = sc.nextLine();

            // Check if the section exists
            if (sections.containsKey(sectionName)) {
                ArrayList<AddingTasks> tasks = sections.get(sectionName);
                System.out.println("Choose a task by number: ");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i).taskName);
                }
                int taskChoice = sc.nextInt() - 1;
                sc.nextLine(); // consume newline
                selectedTask = tasks.get(taskChoice);
                isValidTask = true;
            } else {
                System.out.println("⚠️ Section does not exist!");
            }
        }

        // If user wants to add timer to a random task
        if (choice == 2) {
            System.out.println("Enter the section name to choose a random task from: ");
            String sectionName = sc.nextLine();

            // Check if the section exists
            if (sections.containsKey(sectionName)) {
                ArrayList<AddingTasks> tasks = sections.get(sectionName);
                if (!tasks.isEmpty()) {
                    // Select a random task
                    int randomTaskIndex = (int) (Math.random() * tasks.size());
                    selectedTask = tasks.get(randomTaskIndex);
                    isValidTask = true;
                } else {
                    System.out.println("⚠️ No tasks in the section!");
                }
            } else {
                System.out.println("⚠️ Section does not exist!");
            }
        }

        // Now that we have the selected task, let's manage its timer
        if (isValidTask && selectedTask != null) {
            // Ask if user wants to start timer
            System.out.println("Do you want to start the timer for task: " + selectedTask.taskName + "?");
            System.out.println("1️⃣ Yes");
            System.out.println("2️⃣ No");
            int startChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (startChoice == 1) {
                selectedTask.timer.start();

                // Ask user to stop the timer
                boolean stopTimer = false;
                while (!stopTimer) {
                    System.out.println("Do you want to stop the timer? (Yes/No)");
                    String stopChoice = sc.nextLine();
                    if (stopChoice.equalsIgnoreCase("Yes")) {
                        long elapsedTime = selectedTask.timer.stop();

                        // Display elapsed time and ask for further action
                        System.out.println("⏱️ Elapsed time: " + elapsedTime + " seconds.");
                        System.out.println("Do you want to restart the timer? (Yes/No)");
                        String restartChoice = sc.nextLine();
                        if (restartChoice.equalsIgnoreCase("Yes")) {
                            selectedTask.timer.reset();
                            selectedTask.timer.start();
                        } else {
                            stopTimer = true; // exit while loop
                        }
                    } else {
                        System.out.println("⏱️ Timer is still running...");
                    }
                }

                // Ask if user wants to return to the main menu
                System.out.println("Do you want to return to the main menu? (Yes/No)");
                String exitChoice = sc.nextLine();
                if (exitChoice.equalsIgnoreCase("Yes")) {
                    return; // exit to main menu
                }
            }
        }
    }
}

 class DeleteTask {

    Scanner sc = new Scanner(System.in);

    public void removeTaskFromSection(HashMap<String, ArrayList<AddingTasks>> sections) {
        if (sections.isEmpty()) {
            System.out.println("🚫 No sections or tasks available to remove.");
            return;
        }

        // Display all sections
        System.out.println("\n📂 Available Sections:");
        int index = 1;
        for (String sectionName : sections.keySet()) {
            System.out.println(index++ + ". " + sectionName);
        }

        // Select section
        System.out.print("📌 Enter the name of the section from which you want to remove a task: ");
        String sectionChoice = sc.nextLine();

        if (!sections.containsKey(sectionChoice)) {
            System.out.println("❌ Section not found!");
            return;
        }

        ArrayList<AddingTasks> tasksInSection = sections.get(sectionChoice);

        if (tasksInSection.isEmpty()) {
            System.out.println("📭 No tasks found in this section.");
            return;
        }

        // Display tasks
        System.out.println("\n🗂️ Tasks in Section '" + sectionChoice + "':");
        for (int i = 0; i < tasksInSection.size(); i++) {
            AddingTasks task = tasksInSection.get(i);
            System.out.println((i + 1) + ". " + task.taskName + " [Status: " + task.status + ", Deadline: " + task.deadline + "]");
        }

        // Select task
        System.out.print("🗑️ Enter the task number you want to remove: ");
        int taskIndex = sc.nextInt();
        sc.nextLine(); // consume newline

        if (taskIndex < 1 || taskIndex > tasksInSection.size()) {
            System.out.println("❌ Invalid task number!");
            return;
        }

        AddingTasks removedTask = tasksInSection.remove(taskIndex - 1);
        System.out.println("✅ Task '" + removedTask.taskName + "' removed successfully from section '" + sectionChoice + "'.");
    }
}

