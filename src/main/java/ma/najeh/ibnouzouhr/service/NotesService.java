package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.Note;
import ma.najeh.ibnouzouhr.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by youssef on 12/15/17.
 */
@Service("notesServices")
public class NotesService {
    private final NoteRepository noteRepository;


    @Autowired
    public NotesService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Map<String,List<Note>> getNotesByStudentDividedOverAllSemesters(long id){
        Map<String,List<Note>> notesBySemester =new LinkedHashMap<>(12);

        for (Note note:noteRepository.findAllByStudentId(id)) {
            switch (note.getModule().getSemestre().getName()){
                case "S1":
                    if (notesBySemester.get("S1") == null || notesBySemester.get("S1").isEmpty() ){
                        notesBySemester.put("S1",new ArrayList<>());
                    }
                    notesBySemester.get("S1").add(note);
                    break;
                case "S2":
                    if (notesBySemester.get("S2") == null || notesBySemester.get("S2").isEmpty() ){
                        notesBySemester.put("S2",new ArrayList<>());
                    }
                    notesBySemester.get("S2").add(note);
                    break;
                case "S3":
                    if (notesBySemester.get("S3") == null || notesBySemester.get("S3").isEmpty() ){
                        notesBySemester.put("S3",new ArrayList<>());
                    }
                    notesBySemester.get("S3").add(note);
                    break;
                case "S4":
                    if (notesBySemester.get("S4") == null || notesBySemester.get("S4").isEmpty() ){
                        notesBySemester.put("S4",new ArrayList<>());
                    }
                    notesBySemester.get("S4").add(note);
                    break;
                case "S5":
                    if (notesBySemester.get("S5") == null || notesBySemester.get("S5").isEmpty() ){
                        notesBySemester.put("S5",new ArrayList<>());
                    }
                    notesBySemester.get("S5").add(note);
                    break;
                case "S6":
                    if (notesBySemester.get("S6") == null || notesBySemester.get("S6").isEmpty() )
                        notesBySemester.put("S6",new ArrayList<>());
                    notesBySemester.get("S6").add(note);
                    break;
            }

        }
        return notesBySemester;
    }

    public Note findOne(Long noteId) {
        return noteRepository.findOne(noteId);
    }

    public Note findByModuleIdAndStudentId(Long moduleId, Long studentId) {
        return noteRepository.findByModuleIdAndStudentId(moduleId,studentId);
    }

    public Note updateNote(Note note) {
        return noteRepository.save(note);
    }
}

