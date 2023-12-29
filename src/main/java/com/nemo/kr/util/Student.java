package com.nemo.kr.util;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;



public class Student {

	private String grade;

    private String name;

    private String id;

    private String gender;

    private Method[] methods;

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Order {
        int value();
    }

    /**
     * Sort methods as per Order Annotations
     * 
     * @return
     */
    private void sortMethods() {

        methods = Student.class.getMethods();

        Arrays.sort(methods, new Comparator<Method>() {
            public int compare(Method o1, Method o2) {
                Order or1 = o1.getAnnotation(Order.class);
                Order or2 = o2.getAnnotation(Order.class);
                if (or1 != null && or2 != null) {
                    return or1.value() - or2.value();
                }
                else if (or1 != null && or2 == null) {
                    return -1;
                }
                else if (or1 == null && or2 != null) {
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    /**
     * Read Elements
     * 
     * @return
     */
    public void readElements() {
        int pos = 0;
        /**
         * Sort Methods
         */
        if (methods == null) {
            sortMethods();
        }
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("get") && !name.equalsIgnoreCase("getClass")) {
                pos++;
                String value = "";
                try {
                    value = (String) method.invoke(this);
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " Pos: " + pos + " Value: " + value);
            }
        }
    }

    // /////////////////////// Getter and Setter Methods

    /**
     * @param grade
     * @param name
     * @param id
     * @param gender
     */
    public Student(String grade, String name, String id, String gender) {
        super();
        this.grade = grade;
        this.name = name;
        this.id = id;
        this.gender = gender;
    }

    /**
     * @return the grade
     */
    @Order(value = 4)
    public String getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * @return the name
     */
    @Order(value = 2)
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    @Order(value = 1)
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the gender
     */
    @Order(value = 3)
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Main
     * 
     * @param args
     * @throws IOException
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void main(String args[]) throws IOException, SQLException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Student student = new Student("A", "Anand", "001", "Male");
        student.readElements();
    }
  }