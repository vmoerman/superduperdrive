package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.forms.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static java.lang.Integer.parseInt;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

//    @GetMapping("/note")
//    public Note getNote(int noteId)
//    {
//        return noteService.getNote(noteId);
//    }

    @PostMapping("/note")
    public String addNote(NoteForm noteForm, Model model, Authentication authentication)
    {

        User currentUser = userService.getUser(authentication.getName());
        Note note = new Note();
        if(noteForm.getNoteid() != "")
        {
            note.setNoteid(parseInt(noteForm.getNoteid()));
        }
        note.setNotetitle(noteForm.getNotetitle());
        note.setNotedescription(noteForm.getNotedescription());

        int noteChange;
        System.out.println(note.getNoteid());
        if(note.getNoteid() == null){
            noteChange = noteService.addNote(note, currentUser.getUserId());
            model.addAttribute("success", true);
            model.addAttribute("message", "Note added!");
        }
        else
        {
            noteService.editNote(note, currentUser.getUserId());
            model.addAttribute("success", true);
            model.addAttribute("message", "Note edited!");
        }

//        if(noteChange < 0)
//        {
//            model.addAttribute("error", true);
//            model.addAttribute("message", "Something went wrong!");
//            return "result";
//        }

        return "result";

    }

    @GetMapping("/deletenote/{noteid}")
    public String deleteNote(@PathVariable("noteid") int noteid, Model model, Authentication authentication)
    {
        try
        {
            User currentUser = userService.getUser(authentication.getName());
            noteService.deleteNote(noteid);
            model.addAttribute("success", true);
            model.addAttribute("message", "Deleted succesfully!");
            return "result";
        }
        catch (Exception e)
        {
            model.addAttribute("error", true);
            model.addAttribute("message", "System error!" + e.getMessage());
            return "result";
        }

    }
}
