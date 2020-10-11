import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ParentHashed produces a file employeeheir.html , which 
 * lists employs under manager in an indented table ,
 * with the input given as just list of employees name & id, with each emploee's 
 * immediate manager . The implementation can handle more than one manager 
 * situation for any single employee.
 */
public class ParentHashed {

	class InputLine {
		String employeename;
		String employeeId;
		String managerId;

		InputLine(String emp, String eid, String mid) {
			employeename = emp;
			employeeId = eid;
			managerId = mid;
		}

	}
/*
 * Input data is defined as an array of employee name , emmployee id , manager id 
 * 
 */
	InputLine[] inputData = new InputLine[] { new InputLine("Alan", "100", "150"),
			new InputLine("Martin", "220", "100"),
			new InputLine("Jamie", "150", ""),
			new InputLine("Alex", "275", "100"),
			new InputLine("Steve", "400", "150"),
			new InputLine("David", "190", "400")

	};
	
	
	// Declaring a
	static File htmlOut;
	static FileWriter htmlWrite;
	static BufferedWriter staticWriter;
	HashMap<String, ArrayList> parentchild = new HashMap<String, ArrayList>();

	ArrayList<InputLine> noManager = new ArrayList<InputLine>();

	private void write_html_start() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("employeeheir.html"));
			writer.write("<HTML>\n");
			writer.write("<body>");
			writer.write("<style>");
			writer.write("table {border-collapse: collapse;}");
			writer.write("td  {border: 1px solid #000000;}");
			
			writer.write("</style>");
			writer.write("<H1> =></H1>\n");
			writer.write("<Table>\n ");

			staticWriter = writer;

		} catch (IOException e) {
			System.out.println("An error occurred - writing opening html.");
			e.printStackTrace();
		} finally {
		}
	}

	private void write_html_row(String ename, int noOfTD) {
		try {

			staticWriter.write("<TR>\n");
			for (int ii = 0; ii < noOfTD; ii++) {
				staticWriter.write("<TD/>");

			}
			staticWriter.write("<TD>" + ename + "</TD>\n");
			staticWriter.write("<TR>\n");
			 

		} catch (IOException e) {
			System.out.println("An error occurred - writing opening html.");
			e.printStackTrace();
		} finally {
		}
	}

	private void write_html_end() {
		try {
			staticWriter.write("</Table>\n ");
			staticWriter.write("</BODY>\n ");
			staticWriter.write("</HTML>\n ");

			staticWriter.write("\n");
			staticWriter.close();
			 
		} catch (IOException e) {
			System.out.println("An error occurred- closing lines.");
			e.printStackTrace();
		}
	}

/**
 *  write_employee_path is a recursive method that recurse to check  an empid suppied is  present 
 *  in the hash map.if present it prints an html row for the hashmap entry ;It issues recursive call with an 
 *  increment of the current-depth;
 *  recursion continues until an employeeId fetched in the hash map entry  is not present as a key 
 *  in the hash map.
 * @param empid
 * @param empName
 * @param currentdepth
 */

	private void write_employee_path(String empid, String empName, int currentdepth) {

		
		 
		if (parentchild.containsKey(empid)) {

			ArrayList<InputLine> av0 = parentchild.get(empid);
			int count = 0;
			while (av0.size() > count) {
				//System.out.println("size avo for " + empid + av0.size());

				InputLine eil = av0.get(count);
				write_html_row(eil.employeename, currentdepth);
				//System.out.println("table:" + "td:" + count + eil.employeename + "depth:" + currentdepth);
			
				write_employee_path(eil.employeeId, eil.employeename, currentdepth + 1);

				count = count + 1;

			}

		}

	}

	/**
	 * 
	 * @param inl is an input line with three elements employeeName,employeeId ,managerId
	 * if an employeeId is null the record is skipped
	 * if an employeeName is null , it is given a value Emp-$$$ , where $$$
	 * is the employee id ; if managerId is null , it is assumed the employee is at top
	 * with no manager and that employeeId is added to noManager array
	 * 
	 */

	private void HashStore(InputLine inl) {
		if (inl.employeeId == "") {

			System.out.println("record skipped , no employee id supplied :" + inl);
		}
		else {
			if (inl.managerId == "") {

				noManager.add(inl);
				inl.managerId = "nomanager";
			}
		 
			if (inl.employeename == "") {

				inl.employeename = "Emp:" + inl.employeeId;
				System.out.println("emp name" + inl.employeename );	
			}

			if (parentchild.containsKey(inl.managerId)) {
				ArrayList<InputLine> av0 = parentchild.get(inl.managerId);
				av0.add(inl);
			} else {
				ArrayList<InputLine> av = new ArrayList<InputLine>();
				av.add(inl);
				parentchild.put(inl.managerId, av);

			}

		}
	}
	
	/**
	 * loadInputData takes each input triplet and puts in a hash map with 
	 * the key being manager id ;  
	 *
	 */
	private void loadInputData(InputLine[] inputData) {
		for (InputLine inputline : inputData) {

			HashStore(inputline);

		}

	}
/**
 * 
 * @param args - 'whitebox' as argument string runs  whitebox testing
 */
	public static void main(String[] args) {

		ParentHashed ph = new ParentHashed();
		ph.inputData = new InputLine[] {
		 ph.new InputLine("Alan", "100", "150"),
			ph.new InputLine("Martin", "220", "100"),
			ph.new InputLine("Jamie", "150", ""),
			ph.new InputLine("Alex", "275", "100"),
			ph.new InputLine("Steve", "400", "150"),
			ph.new InputLine("David", "190", "400")

	};
		
		InputLine[] inputData = ph.inputData;

//check for whitebox testing

		if (args.length  > 0 )  {
			if (args[0].equalsIgnoreCase("whitebox")) {
			ph.write_html_start();
			System.out.println("write_html_start success");
			int noOfInput = inputData.length;
			System.out.println("nomanager Arraylength is Zero ");
			assert (ph.noManager.size() == 0);
			System.out.println("parentchild Arraylength is Zero ");
			assert (ph.parentchild.size() == 0);
			inputData = new InputLine[] { ph.new InputLine("Alan", "100", "150"),
					ph.new InputLine("Martin", "220", ""),
					ph.new InputLine("Jamie", "150", ""), 
					ph.new InputLine("", "275", "100"),
					ph.new InputLine("Steve", "400", "150"),
					ph.new InputLine("Steve", "", "220"),
					ph.new InputLine("David", "190", "400")

			};
			System.out.println("parentchild hashmap loaded:martin Manager ");
			ph.loadInputData(inputData);

			assert (ph.parentchild.size() == 4) : "parent child size" + ph.parentchild.size();
			System.out.println("no of managers:martin Manager " + ph.noManager.size());

			assert (ph.noManager.size() == 2) : "Error should be 2 ";
			for (int mi = 0; mi < ph.noManager.size(); mi++) {
				InputLine managerline = (ph.noManager.get(mi));
				ph.write_html_row(managerline.employeename, 0);
				ph.write_employee_path(managerline.employeeId, managerline.employeename, 1);
				// above can be fine tuned for testing by creating intermediary parentchild
			}
			ph.write_html_end();

		}
		}

	 //Main run: with the inputdata
		
		
		else 
		{
		
		
		ph.write_html_start();
	 
	 
 
			ph.loadInputData(inputData);

			for (int mi = 0; mi < ph.noManager.size(); mi++) {
				InputLine managerline = (ph.noManager.get(mi));
				ph.write_html_row(managerline.employeename, 0);
				ph.write_employee_path(managerline.employeeId, managerline.employeename, 1);

			}
			ph.write_html_end();

		}
	}
}
