package edu.northeastern.cs5200.controllers.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    @RequestMapping("/api/hello/string")
    public String sayHello() {
        return "Hello Yifan Bao!";
    }

    @RequestMapping("/api/hello/object")
    public HelloObject sayHelloObject() {
        HelloObject obj = new HelloObject("Hello Yifan Bao!");
        return obj;
    }


    /*
     The following source creates a new instance of the HelloObject with the message property set
     to the constant literal "Hello Yifan Bao!" and then saves it to the database
    */
    @Autowired
    HelloRepository helloRepository;

    @RequestMapping("/api/hello/insert")
    public HelloObject insertHelloObject() {
        HelloObject obj = new HelloObject("Hello Yifan Bao!");
        helloRepository.save(obj);
        return obj;
    }


    /*
     create an endpoint that allows passing an arbitrary message as a parameter and then save it to the database

     The following code maps the URL pattern /api/hello/insert/{msg} to the insert Message() method.
     The URL contains path variable {msg}, a placeholder that can be any string.
     The actual string value is then mapped to method parameter message using the @PathVariable annotation.
     */
    @RequestMapping("/api/hello/insert/{msg}")
    public HelloObject insertMessage(@PathVariable("msg") String message) {
        HelloObject obj = new HelloObject(message);
        helloRepository.save(obj);
        return obj;
    }


    /*
     add a method to retrieve all the records from the database
     and return them as a List of HelloObject instances using the findAll() method.

     The following code maps the URL pattern /api/hello/select/all to the selectAllHello Objects() method.
     The method uses the helloRepository.findAll() method to retrieve all records from the hello table and converts them to a List of HelloObject instances.
     */
    @RequestMapping("/api/hello/select/all")
    public List<HelloObject> selectAllHelloObjects() {
        List<HelloObject> hellos = (List<HelloObject>)helloRepository.findAll();
        return hellos;
    }
}
