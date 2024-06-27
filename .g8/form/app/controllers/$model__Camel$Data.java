package controllers;

import play.api.data.format.package$;
import play.data.validation.Constraints;

public class $model;package$="Camel"$Data {

    @Constraints.Required String name;

    @Constraints.Required
    private Integer age;

    public $model;format="Camel"$Data() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("$model;format="Camel"$Data(%s, %s)", name, age);
    }

}
