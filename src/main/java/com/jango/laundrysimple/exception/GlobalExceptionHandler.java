package com.jango.laundrysimple.exception;//package com.virtualpro.contraccess.exception;
//
//import com.virtualpro.contraccess.util.MessageResponses;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.multipart.MaxUploadSizeExceededException;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@ControllerAdvice
//public class GlobalExceptionHandler extends Throwable {
//
//    public static String message = "No";
//
//    //commons-fileupload
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public String handleError2(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
//
//        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
//        return "redirect:/uploadStatus";
//
//    }
//
//    public String errorResponse(){
//        return message;
//    }
//}
