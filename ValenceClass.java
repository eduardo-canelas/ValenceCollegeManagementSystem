import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ValenceClass 
{
    private static College valenceCollege;
    private static Scanner scanner = new Scanner(System.in);
    
    private static String mainMenu() 
    {
        System.out.println("Choose from the following options:");
        System.out.println("\t1- Add a new student");
        System.out.println("\t2- Add/Delete a course");
        System.out.println("\t3- Search for a student");
        System.out.println("\t4- Print fee invoice");
        System.out.println("\t5- Print fee invoice sorted by crn");
        System.out.println("\t0- Exit program");
        System.out.print("Enter your selection: ");
        return scanner.nextLine();
    }

    private static String subMenu(String studentName) 
    {
        System.out.println("\nChoose from:");
        System.out.println("A- Add a new course for [" + studentName + "]");
        System.out.println("D- Delete a course from [" + studentName + "]'s schedule");
        System.out.println("C- Cancel operation");
        System.out.print("Enter your selection: ");
        return scanner.nextLine();
    }

    public static void main(String[] args) 
    {
        valenceCollege = new College();
        String option;
        //Do while loop for function calls depending on user input
        do {
            option = mainMenu();
            switch (option) {
                case "1":
                    addNewStudent();
                    break;
                case "2":
                    addOrDeleteCourse();
                    break;
                case "3":
                    searchForStudent();
                    break;
                case "4":
                    printFeeInvoice();
                    break;
                case "5":
                    printFeeInvoiceSortedByCRN();
                    break;
                case "0":
                    System.out.println("\nGoodbye!");
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.\n");
            }
        } while (!option.equals("0"));
    }

    private static void addNewStudent() 
    {
        int studentId;
        System.out.print("\nEnter the student’s id: ");
        studentId = Integer.parseInt(scanner.nextLine());
        
        if (!valenceCollege.searchById(studentId)) 
        {
            System.out.print("Enter student’s name: ");
            String studentName = scanner.nextLine();
            
            System.out.print("\nEnter how many courses " + studentName + " is taking? ");
            int numOfCourses = Integer.parseInt(scanner.nextLine());
            
            //Error handling for invalid inputs
            while(numOfCourses <= 0)
            {
            	System.out.print("Enter how many courses " + studentName + " is taking? ");
                numOfCourses = Integer.parseInt(scanner.nextLine());
            }
            
            ArrayList<Integer> listOfCrns = new ArrayList<>();
            System.out.println("Enter the course numbers separated by space");
            
            String[] courseNumbers = scanner.nextLine().split("\\s+");
            
            for (String courseNumber : courseNumbers) 
            {
                listOfCrns.add(Integer.parseInt(courseNumber));
            }
            System.out.print("Enter " + studentName + "’s current gpa: ");
            double gpa = Double.parseDouble(scanner.nextLine());
            
            if (gpa < 3.5) 
            {
            	System.out.println("\n" + studentName + "is not eligible for the 25% discount");
            } 
            
            valenceCollege.enrollStudent(new Student(studentName, studentId, gpa, listOfCrns));
            System.out.println("\nStudent added successfully!\n");
        } 
        else 
        {
            System.out.println("\nSorry, " + studentId + " is already assigned to another student\n");
        }
    }

    private static void addOrDeleteCourse() 
    {
        int studentId;
        System.out.print("\nEnter the student’s id: ");
        studentId = Integer.parseInt(scanner.nextLine());
        
        if (valenceCollege.searchById(studentId)) 
        {
            Student student = valenceCollege.getStudentById(studentId);
            System.out.println("\nHere are the courses " + student.getStudentName() + " is taking:");
            student.printCourses();
            String choice = subMenu(student.getStudentName());
            
            switch (choice.toLowerCase()) 
            {
                case "a":
                	String option;
                    System.out.print("Enter the course number to add: ");
                    int crnToAdd = Integer.parseInt(scanner.nextLine());
                    String courseInfo = Student.setCourseToDeleteOrAdd(crnToAdd);
                    System.out.println("\n["+ courseInfo +"]"+ "was added successfully!\n");
                    valenceCollege.addCourse(studentId, crnToAdd);
                    System.out.println("Want to display new invoice? Y/N: ");
                    option = scanner.nextLine();
                    
                    //Error handling for invalid user inputs for the add course operation
                    while (!"N".equalsIgnoreCase(option))
                    {
	                    if ("Y".equalsIgnoreCase(option))
	                    {
	                    	student.printFeeInvoice();
	                    	break;
	                    }
	                    else
	                    {
	                    	System.out.println("Invalid input");
	                    	System.out.println("Want to display new invoice? Y/N: ");
	                    	option = scanner.nextLine(); 
	                    }
                    }
                    break;
                    
                case "d":
                    System.out.print("Enter the course number to delete: ");
                    int crnToDelete = Integer.parseInt(scanner.nextLine());
                    courseInfo = Student.setCourseToDeleteOrAdd(crnToDelete);
                    System.out.println("\n["+ courseInfo +"]"+ "is deleted successfully!\n");
                    valenceCollege.deleteCourse(studentId, crnToDelete);
                    System.out.println("Want to display new invoice? Y/N: ");
                    option = scanner.nextLine();
                    
                  //Error handling for invalid user inputs for the delete course operation
                    while (!"N".equalsIgnoreCase(option))
                    {
	                    if ("Y".equalsIgnoreCase(option))
	                    {
	                    	student.printFeeInvoice();
	                    	break;
	                    }
	                    else
	                    {
	                    	System.out.println("Invalid input");
	                    	System.out.println("Want to display new invoice? Y/N: ");
	                    	option = scanner.nextLine(); 
	                    }
                    }
                    break;
                    
                case "c":
                    break;
                    
                default:
                    System.out.println("Invalid choice.");
            }
        } 
        else 
        {
            System.out.println("No student found with id " + studentId);
        }
    }

    private static void searchForStudent() 
    {
        int studentId;
        System.out.print("Enter the student’s id: ");
        studentId = Integer.parseInt(scanner.nextLine());
        
        if (valenceCollege.searchById(studentId)) 
        {
            System.out.println("Student found!");
        } 
        else 
        {
            System.out.println("No Student found!");
        }
    }

    private static void printFeeInvoice() 
    {
        int studentId;
        System.out.print("Enter the student’s id: ");
        studentId = Integer.parseInt(scanner.nextLine());
        valenceCollege.printInvoice(studentId);
    }

    private static void printFeeInvoiceSortedByCRN() 
    {
        int studentId;
        System.out.print("Enter the student’s id: ");
        studentId = Integer.parseInt(scanner.nextLine());
        valenceCollege.printSortedInvoice(studentId);
    }
}

class Student 
{
    private int studentId;
    private String studentName;
    private double gpa;
    private ArrayList<Integer> listOfCrns;
    
    //Student class constructor
    public Student(String studentName, int studentId, double gpa, ArrayList<Integer> listOfCrns) 
    {
        this.studentName = studentName;
        this.studentId = studentId;
        this.gpa = gpa;
        this.listOfCrns = listOfCrns;
    }

    public void setStudentId(int studentId) 
    {
        this.studentId = studentId;
    }

    public int getStudentId() 
    {
        return studentId;
    }

    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }

    public void setGpa(double gpa) 
    {
        this.gpa = gpa;
    }

    public double getGpa() 
    {
        return gpa;
    }

    public ArrayList<Integer> getListOfCrns() 
    {
        return listOfCrns;
    }
    
    //Method that returns the total payment amount
    public double calculateTotalPayment() 
    {
        double totalPayment = 0;
        for (int crn : listOfCrns) 
        {
            // Calculate payment for each course based on credit hours
            totalPayment += calculatePaymentForCourse(crn);
        }
        // Adding Health & id fees
        totalPayment += 35.00;
        return totalPayment;
    }

    private double calculatePaymentForCourse(int crn) 
    {
        double costPerCreditHour = 120.25;
        // Switch case to determine credit hours for each course
        switch (crn) 
        {
            case 4587: // MAT 236
                return 4 * costPerCreditHour;
            case 4599: // COP 220
                return 3 * costPerCreditHour;
            case 8997: // GOL 124
            case 4580: // MAT 136
            case 1997: // CAP 424
                return 1 * costPerCreditHour;
            case 9696: // COP 100
            case 2599: // COP 260
            case 3696: // KOL 110
                return 3 * costPerCreditHour;
            default:
                return 0; // Unknown CRN
        }
    }

    public void printFeeInvoice() 
    {
        System.out.println("\nVALENCE COLLEGE");
        System.out.println("ORLANDO FL 10101");
        System.out.println("------------------------------------------------\n");
        System.out.println("Fee Invoice Prepared for Student:");
        System.out.println(studentId + "-" + studentName);

        double totalPayment = calculateTotalPayment();

        System.out.println("\n1 Credit Hour = $120.25\n");
        System.out.println("CRN CR_PREFIX CR_HOURS");

        for (int crn : listOfCrns) 
        {
        	double creditHours = calculatePaymentForCourse(crn);
            String courseInfo = getCourseInfo(crn);
            System.out.println(crn + " " + courseInfo + " " + String.format("\t$"+"%.2f", creditHours));
        }
        
        System.out.println("\n\tHealth & id fees $ 35.00\n");
        System.out.println("------------------------------------------------");
        
     //If eligible for a discount
        if (gpa >= 3.5 && totalPayment > 700.0) 
        {
            double discount = totalPayment * 0.25;
            System.out.println("\t\t\t $" + totalPayment);
            System.out.println("\t\t\t-$" + discount);
            System.out.println("------------------------------------------------\n");
            System.out.println("\tTotal Payments $ " + String.format("%.2f", (totalPayment - discount))+ "\n");
        }
        else
        {
        	System.out.println("\tTotal Payments $ " + String.format("%.2f", totalPayment) + "\n");
        }
    }
    private static String getCourseInfo(int crn) 
    {
        switch (crn) 
        {
            case 4587:
                return "MAT 236 \t4";
            case 4599:
                return "COP 220 \t3";
            case 8997:
                return "GOL 124 \t1";
            case 9696:
                return "COP 100 \t3";
            case 4580:
                return "MAT 136 \t1";
            case 2599:
                return "COP 260 \t3";
            case 1997:
                return "CAP 424 \t1";
            case 3696:
                return "KOL 110 \t2";
            default:
                return "Course information not found";
        }
    }
    
    private static String getCourseToDeleteOrAdd(int crn) 
    {
        switch (crn) 
        {
            case 4587:
                return "4587/MAT 236";
            case 4599:
                return "4599/COP 220";
            case 8997:
                return "8997/GOL 124";
            case 9696:
                return "9696/COP 100";
            case 4580:
                return "4580/MAT 136";
            case 2599:
                return "2599/COP 260";
            case 1997:
                return "1997/CAP 424";
            case 3696:
                return "3696/KOL 110";
            default:
                return "Course information not found";
        }
    }
    
    public static String setCourseToDeleteOrAdd(int crn)
    {
    	return getCourseToDeleteOrAdd(crn);
    }
    
    public void printCourses() 
    {
    	//Separated by tabs
        System.out.println("CRN\tPREFIX CR.\tHOURS");
        for (int crn : listOfCrns) 
        {
            String courseInfo = getCourseInfo(crn);
            System.out.println(crn + "\t" + courseInfo);
        }
    }
    
}

class College 
{
    private ArrayList<Student> list;
    
    public College() 
    {
        this.list = new ArrayList<>();
    }
    
    //Adds students to the ArrayList
    public void enrollStudent(Student student) 
    {
        list.add(student);
    }
    
    //returns true if studentId is found (false otherwise)
    public boolean searchById(int studentId) 
    {
        for (Student student : list) {
            if (student.getStudentId() == studentId) 
            {
                return true;
            }
        }
        return false;
    }

    public Student getStudentById(int studentId) 
    {
        for (Student student : list) 
        {
            if (student.getStudentId() == studentId) 
            {
                return student;
            }
        }
        return null;
    }
    
    //return true if course can be added depending on the student id
    public boolean addCourse(int studentId, int crn) 
    {
        for (Student student : list) 
        {
            if (student.getStudentId() == studentId) 
            {
                student.getListOfCrns().add(crn);
                return true;
            }
        }
        return false;
    }
    
    //returns true if a course can be deleted
    public boolean deleteCourse(int studentId, int crn) 
    {
        for (Student student : list) 
        {
            if (student.getStudentId() == studentId) 
            {
                return student.getListOfCrns().remove(Integer.valueOf(crn));
            }
        }
        return false;
    }

    public void printInvoice(int studentId) 
    {
        for (Student student : list) 
        {
            if (student.getStudentId() == studentId) 
            {
                student.printFeeInvoice();
                return;
            }
        }
        System.out.println("No student found with id " + studentId);
    }

    public void printSortedInvoice(int studentId) 
    {
        for (Student student : list) 
        {
            if (student.getStudentId() == studentId) 
            {
                Collections.sort(student.getListOfCrns());
                student.printFeeInvoice();
                return;
            }
        }
        System.out.println("No student found with id " + studentId);
    }
}
