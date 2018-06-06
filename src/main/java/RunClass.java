import java.util.*;

public class RunClass {

    public void Steps(){
        ReadExcel abc = new ReadExcel();
        EmpDetails emp = new EmpDetails();
        Rotation rotation = new Rotation();
        SortEmployees sortEmployees = new SortEmployees();
        List<EmpDetails> employeeDetailsList = new ArrayList<EmpDetails>();
        List<ProjectDetails> listProjectDetails = new ArrayList<ProjectDetails>();
        Map<String,Integer> locationCountRotation =new HashMap<String, Integer>();
        List<String> roles = new ArrayList<String>();
        roles = Roles();
        Map<String,List<String>> DesignationSort = new HashMap<String, List<String>>();
        Map<String,List<String>> EmployeePerProjectSort = new HashMap<String, List<String>>();

        employeeDetailsList= abc.readAllocation();
        listProjectDetails = abc.readProjectresource();

        System.out.println("employee Details"+ Arrays.toString(employeeDetailsList.toArray()));
        System.out.println("Project Details"+ Arrays.toString(listProjectDetails.toArray()));

        DesignationSort = sortEmployees.sortByDesignation(employeeDetailsList);
        System.out.println(Arrays.asList(DesignationSort));
        EmployeePerProjectSort = sortEmployees.employeePerProject(listProjectDetails,employeeDetailsList);
        System.out.println(Arrays.asList(EmployeePerProjectSort));
        System.out.println(EmployeePerProjectSort.size());
    for(String role: roles) {
    System.out.println("Alligning "+ role);
        locationCountRotation=rotation.checkLocationWise(listProjectDetails, EmployeePerProjectSort, role, DesignationSort, employeeDetailsList);
        System.out.println(locationCountRotation);
        rotation.checkTeamWise(listProjectDetails,EmployeePerProjectSort,role,DesignationSort,employeeDetailsList,locationCountRotation);
        rotation.rotateFree(employeeDetailsList);

        Assignment assignment= new Assignment();
        rotation.abc();
        assignment.assignPerLocation(listProjectDetails,EmployeePerProjectSort,role,DesignationSort,employeeDetailsList,locationCountRotation);
        //rotation.rotateTwoMonths(employeeDetailsList,listProjectDetails);
    }

//        JSONArray jsArray = new JSONArray(employeeDetailsList);
//        System.out.println("jsonArray classe use " +jsArray);
//
//
//        Gson gson = new Gson();
//        String jsonArray = gson.toJson(employeeDetailsList);
//        System.out.println("Gson use "+jsonArray);
//        System.out.println(jsonArray.length());
//        JSONObject json1 = new JSONObject();
//        System.out.println(jsonArray);

//json1.put("data",jsonArray);





        //jsonMake jsonview = new jsonMake();

//jsonview.abc();
    }
    public List<String> Roles(){
        List<String> roles = new ArrayList<String>();

        roles.add("QA");
        //roles.add("FE");
        //roles.add("Be");
        return roles;
    }
}
