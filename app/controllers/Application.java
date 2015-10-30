package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.Person;
import play.*;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result addPerson(){
        JsonNode json = request().body().asJson();

        Person person = Json.fromJson(json, Person.class);

        if (person.toString().equals("")){
            return badRequest("Missing parameter");
        }

        person.save();
        return ok();
    }

    public Result listPerson(){

        List<Person> persons = new Model.Finder(String.class, Person.class).all();

        return ok(toJson(persons));
    }

    public Result getPerson(int id){

        Person person = Person.find.byId(id);

        if (person == null){
            return notFound("User not found!");
        }

        return ok(toJson(person));

    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result updatePerson(int id){

        Person person = Person.find.byId(id);

        if (person == null){
            return notFound("User not found");
        }

        JsonNode json = request().body().asJson();
        Person personToBe = Json.fromJson(json, Person.class);

        person = personToBe;
        person.update();

        return ok();
    }

    public Result deletePerson(int id){

        Person person = Person.find.byId(id);

        if (person == null){
            return notFound("User not found");
        }

        person.delete();
        return ok();
    }

    public Result searchPerson(String name){
        List<Person> persons = Person.find.where()
                .like("name", "%"+name+"%")
                .findList();

        if (persons == null){
            return notFound("User not found!");
        }

        return ok(toJson(persons));

    }
}
