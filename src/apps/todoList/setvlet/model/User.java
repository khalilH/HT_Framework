package apps.todoList.setvlet.model;

import java.io.File;

/**
 * Created by ladislas on 07/03/2017.
 */
public class User {

    public static String templatePath =  "src" + File.separator + "apps" + File.separator + "todoList"+ File.separator + "templates"+ File.separator+"userTemplate.html";

    private String username;
    private String nom;
    private String prenom;
    private int age;
    private String password;
    private int id;

    public User(String username, String password, String prenom, String nom, int age, int id) {
        this.username = username;
        this.password = password;
        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
