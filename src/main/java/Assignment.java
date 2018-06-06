import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Assignment {

    List<String> AssignedEmployee = new ArrayList<String>();

    public void assignPerLocation(List<ProjectDetails> listProjectDetails, Map<String, List<String>> employeePerProjectSort, String role, Map<String, List<String>> designationSort, List<EmpDetails> employeeDetailsList, Map<String, Integer> locationCountRotation) {
        List<EmpDetails> empAvalRotation = Rotation.rotationEmployee;
        for (ProjectDetails projectDetails : listProjectDetails) {
            int vacancy = 0;   //1 project
            if (projectDetails.getTypeOfProject().equalsIgnoreCase("Rotation")) {
                if (!projectDetails.getProjectLocation().equalsIgnoreCase("all")) {  //any location
                    System.out.println(projectDetails.getProjectName());
                    int roleCount = getRolecount(role, projectDetails);

                    if (Rotation.stayBackEmp.get(projectDetails.getProjectName()) != null) {
                        vacancy = roleCount - Rotation.stayBackEmp.get(projectDetails.getProjectName()).size();
                    }
                    System.out.println(Rotation.rotationEmployee);
                    System.out.println(role+ roleCount+"-->"+vacancy);

                   vacancy= assigncheckLocation(projectDetails,vacancy,false,empAvalRotation);
                    if (vacancy != 0) {
                        vacancy = assigncheckLocation(projectDetails, vacancy, true,empAvalRotation);
                    }
                    if (vacancy != 0){
                        AssignedEmployee.add(projectDetails.getProjectName()+ " did not got fully Ramped up " + vacancy +"left");
                    }
                }
            }
            System.out.println(AssignedEmployee);
        }
    }

    public void assignProject(EmpDetails empDetails, ProjectDetails projectDetails, List<EmpDetails> empAvalRotation) {
        empAvalRotation.remove(empDetails);
        empDetails.setPrevProject(empDetails.getCurrentProject());
        empDetails.setCurrentProject(projectDetails.projectName);
        AssignedEmployee.add(empDetails.getName() + " Moved to " + projectDetails.getProjectName());
    }

    public int assigncheckLocation(ProjectDetails projectDetails, int vacancy, boolean sameprojFlag,List<EmpDetails> empAvalRotation ) {

        //for (EmpDetails empDetails : empAvalRotation)
        Iterator<EmpDetails> empDetail = empAvalRotation.iterator();
        while(empDetail.hasNext()) {
            EmpDetails empDetails = empDetail.next();
            String loc = empDetails.getLocation();
            if (projectDetails.getProjectLocation().equals(loc)) {
                if (!sameprojFlag) {
                    if (!empDetails.getCurrentProject().equals(projectDetails.projectName)) {
                        empDetail.remove();
                        empDetails.setPrevProject(empDetails.getCurrentProject());
                        empDetails.setCurrentProject(projectDetails.projectName);
                        AssignedEmployee.add(empDetails.getName() + " Moved to " + projectDetails.getProjectName());
                        //assignProject(empDetails, projectDetails,empAvalRotation);
                        --vacancy;
                    }
                } else {
                    empDetail.remove();
                   // empAvalRotation.remove(empDetails);
                    empDetails.setPrevProject(empDetails.getCurrentProject());
                    empDetails.setCurrentProject(projectDetails.projectName);
                    AssignedEmployee.add(empDetails.getName() + " Moved to " + projectDetails.getProjectName());
                    --vacancy;
                }
            }

            if (vacancy == 0) {
                break;
            }
        }
        return vacancy;
    }

    public int getRolecount(String role, ProjectDetails projectDetails) {//checking
        if (role.equals("QA")) {
            return projectDetails.getProjQACount();
        }
        if (role.equals("FE")) {
            return projectDetails.getProjFECount();
        }
        if (role.equals("BE")) {
            return projectDetails.getProjBECount();
        }
        return 0;
    }
}
