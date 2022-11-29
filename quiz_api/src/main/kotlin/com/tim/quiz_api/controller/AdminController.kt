package com.tim.quiz_api.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


/*
@Controller ist eine spezielle Art um der Spring Boot Anwendung zu sagen
"hier befindet sich eine Klasse bzw. Komponente welche zu Berücksichtigen ist und die Klasse ist vom Typ Controller"
Spring Boot nennt das Component Scanning
 */
@Controller
/*
@RequestMapping definiert unter welcher URL der Controller angesprochen wird
in diesem Fall localhost:8080/admin
 */
@RequestMapping("/admin")
class AdminController {


    /*
    @GetMapping definiert das sobald ein request auf die URL /admin mit einem GET Request initiert wird
    die ressource admin.html an den client gesendet wird
     */
    @GetMapping()
    fun getAdminView () = "admin";

}