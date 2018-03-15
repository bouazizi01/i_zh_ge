package ma.najeh.ibnouzouhr.constant;


public class Constant {
    public static final String ATTACHMENT_FILENAME = "Attachment;Filename=\"";
    public static final String Content_Disposition = "Content-Disposition";
    public static final String ABSOLUTE_DIR = "C:\\uiz\\";
    public static final String ABSENCE_CLASSNAME = "m-fc-event--danger";
    public static final String HOMEWORK_CLASSNAME = "m-fc-event--solid-warning";
    public interface Date {
        String DATE_FORMAT = "yyyy-MM-dd";
    }
    public interface Status {
        String SUCCESS = "success";
        String ERROR = "error";
    }
    public interface InputError {
        String GROUP = "group";
        String TEACHER = "teacher";
        String SALLE = "salle";
    }
    public interface ROLE {
        String SUPER_ADMIN = "super_admin";
        String TEACHER = "teacher";
        String STUDENT = "student";
        String SCOLARITY = "scolarity";
        String CONFIG = "config";
    }

    public interface URL {

        String ADMIN_STUDENTS_ALL = "/admin/students/all";
        String ADMIN_STUDENTS = "/admin/students";
        String ADMIN_STUDENTS_ADD = "/admin/students/add";
        String ADMIN_STUDENTS_BY_BRANCH = "/admin/students/branch/{branchId}";
        String ADMIN_STUDENTS_ADD_XLS = "/admin/students/xls-add";
        String ADMIN_STUDENTS_ADD_XLS_GLOBAL = "/admin/students/xls-add-global";
        String ADMIN_STUDENTS_EDIT = "/admin/students/edit/{id}";
        String ADMIN_STUDENTS_UPDATE = "/admin/students/edit";
        String STUDENT_MY_PROFILE = "/student/my-profile";
        String ADMIN_STUDENT_PROFILE = "/admin/students/profile/{id}";
        String SCOLARITY_STUDENT_PROFILE = "/scolarity/students/profile/{id}";
        String ADMIN_BRANCHES_ADD = "/admin/branches/add";
        String ADMIN_BRANCHES_ALL = "/admin/branches/all";
        String ADMIN_BRANCHES = "/admin/branches";
        String ADMIN_BRANCHES_DELETE = "/admin/branches/delete/{id}";
        String ADMIN_BRANCHES_EDIT = "/admin/branches/edit/{id}";
        String ADMIN_BRANCHES_UPDATE = "/admin/branches/edit";

        String ADMIN_ADMINS_ALL = "/admin/admins/all";
        String ADMIN_ADMINS = "/admin/admins";
        String ADMIN_ADMINS_ADD = "/admin/admins/add";
        String ADMIN_ADMINS_EDIT = "/admin/admins/edit";
        String ADMIN_ADMINS_UPDATE = "/admin/admins/edit/{id}";
        String ADMIN_ADMINS_DELETE = "/admin/admins/delete/{id}";

        String ADMIN_GROUPS_ADD = "/admin/groups/add";
        String ADMIN_GROUPS_ALL = "/admin/groups/all";
        String ADMIN_STUDENTS_OF_GROUP = "/admin/group/{id}/students";
        String ADMIN_STUDENTS_DELETE = "/admin/students/delete/{id}";

        String ADMIN_MODULES="/admin/modules";
        String ADMIN_MODULES_ALL="/admin/modules/all";
        String ADMIN_MODULES_STUDENTS="/admin/module/{id}/students";
        String ADMIN_MODULES_ADD="/admin/modules/add";
        String ADMIN_MODULES_EDIT="/admin/modules/edit";
        String ADMIN_MODULES_UPDATE="/admin/modules/edit/{id}";
        String ADMIN_MODULES_DELETE="/admin/modules/delete/{id}";

        String ADMIN_MODULES_SET_TEACHER="/admin/modules/set-teacher/{id}";
        String ADMIN_MODULES_UDAPTE_TEACHER="/admin/modules/set-teacher";

        String ADMIN_SALLES="/admin/salles";
        String ADMIN_SALLES_ALL="/admin/salles/all";
        String ADMIN_SALLES_ADD="/admin/salles/add";
        String ADMIN_SALLES_ADD_AMPHI="/admin/salles/add-amphi";
        String ADMIN_SALLES_DELETE="/admin/salles/delete/{id}";
        String ADMIN_SALLES_EDIT="/admin/salles/edit/{id}";
        String ADMIN_SALLES_UPDATE="/admin/salles/edit";



        String SCOLARITY_DOCUMENTS_ALL = "/scolarity/documents/all";
        String SCOLARITY_DOCUMENTS = "/scolarity/documents";
        String STUDENT_DOCUMENTS_ALL = "/student/documents/all";
        String STUDENT_DOCUMENTS = "/student/documents";
        String STUDENT_MY_DOCUMENTS = "/student/my-documents";
        String STUDENT_DOCUMENTS_ADD = "/student/documents/add";
        String SCOLARITY_DOCUMENTS_REFUSE = "/scolarity/documents/refuse/{id}";
        String SCOLARITY_DOCUMENTS_VALIDATE = "/scolarity/documents/validate/{id}";

        String ADMIN_SEMESTERS_ALL = "/admin/semesters/all";
        String ADMIN_SEMESTERS_ADD = "/admin/semesters/add";
        String ADMIN_SEMESTERS_DELETE = "/admin/semesters/delete/{id}";
        String ADMIN_SEMESTERS_EDIT = "/admin/semesters/edit/{id}";
        String ADMIN_SEMESTERS_UPDATE = "/admin/semesters/edit";

        String SCOLARITY_ANNONCE = "/scolarity/annonces";
        String SCOLARITY_ANNONCE_ALL = "/scolarity/annonces/all";
        String SCOLARITY_ANNONCE_ADD = "/scolarity/annonces/add";
        String SCOLARITY_ANNONCE_DELETE = "/scolarity/annonces/delete/{id}";
        String SCOLARITY_ANNONCE_EDIT = "/scolarity/annonces/edit/{id}";
        String SCOLARITY_ANNONCE_UPDATE = "/scolarity/annonces/edit";

        String SCOLARITY_EVENT = "/scolarity/events";
        String SCOLARITY_EVENT_ALL = "/scolarity/events/all";
        String SCOLARITY_EVENT_ADD = "/scolarity/events/add";
        String SCOLARITY_EVENT_DELETE = "/scolarity/events/delete/{id}";
        String SCOLARITY_EVENT_EDIT = "/scolarity/events/edit/{id}";
        String SCOLARITY_EVENT_UPDATE = "/scolarity/events/edit";

        String ADMIN_TEACHERS_ALL = "/admin/teachers/all";
        String ADMIN_TEACHERS_ADD = "/admin/teachers/add";
        String ADMIN_TEACHERS_DELETE = "/admin/teachers/delete/{id}";
        String ADMIN_TEACHERS_EDIT = "/admin/teachers/edit/{id}";
        String ADMIN_TEACHERS_UPDATE = "/admin/teachers/edit";
        String ADMIN_TEACHERS_ADD_XLS = "/admin/teachers/xls-add";


        String ADMIN_NOTES_ADD_XLS = "/admin/notes/xls-add";
        String ADMIN_NOTES_ADD_XLS_ZIP = "/admin/notes/xls-add/zip";
        String ADMIN_NOTES_ADD = "/admin/notes/add";
        String STUDENT_MY_NOTES = "/student/my-notes";
        String NEW_STUDENTS = "/admin/students/new";
        String NEW_STUDENTS_DELETE = "/admin/students/new/delete/{id}";
        String NEW_STUDENTS_SAVE = "/admin/student/new/{id}/save";


        String TEACHER_HOMEWORK_ADD ="/teacher/homework/{id}/add";
        String TEACHER_HOMEWORK_SAVE ="/teacher/homework/add";
        String TEACHER_ABSENCES_ADD = "/teacher/absences/add";
        String TEACHER_HOMEWORK_ALL = "/teacher/homeworks/all";
        String TEACHER_HOMEWORK = "/teacher/homeworks";
        String STUDENT_HOMEWORK_ALL = "/student/homeworks/all";
        String STUDENT_HOMEWORK = "/student/homeworks";

        String TEACHER_ABSENCES_SAVE = "/teacher/absences/save";

        String ADMIN_TIMELINE_CREATE = "/admin/timeline/create";
        String TEACHER_TIMELINE = "/teacher/timeline/planing";
        String STUDENT_TIMELINE = "/student/timeline/planing";


        String DEFAULT_ADMIN_URL="/admin/dashboard";
        String DEFAULT_TEACHER_URL="/teacher/timeline/planing";
        String DEFAULT_STUDENT_URL="/student/my-profile";
        String DEFAULT_SCOLARITY_URL="/scolarity/documents";
        String DASHBOARD = "/dashboard";
        String DASHBOARD_WITHOUT_SLASH = "dashboard";


        String ADMIN_HOMEWORK_DELETE = "/teacher/homeworks/delete/{id}";
        String TEACHER_SEANCES_ADD_URL = "/teacher/seances/add";



    }

    public interface View{
        String ADMINS_ADD ="/admins/add";
        String ADMINS_EDIT ="/admins/edit";
        String ADMINS_ALL ="/admins/all";
        String STUDENTS_NEWLY_REGISTERED ="/admins/new_students";
    }

    public interface DEMAND_DOC_STATE{
        String INITIALIZED ="en cours";
        String REFUSED ="refusé";
        String COMPLETED ="completé";
    }
    public interface DEMAND_REC_STATE{
        String INITIALIZED ="en cours";
        String REFUSED_ADMIN ="refusé par admin";
        String ACCEPTED_ADMIN ="accépté par admin";
        String REFUSED_TEACHER ="refusé par prof";
        String ACCEPTED_TEACHER ="accépté par prof";
        String ACCEPTED_UPDATED ="modifié";
    }
    public interface CONFIG{

        String START_DATE="2016-09-15";
        String END_DATE="2017-01-20";
    }
}
