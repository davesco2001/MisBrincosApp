package com.example.misbrincosapp.BD;
import android.widget.Toast;

import com.example.misbrincosapp.CreateLessonsActivity;

import java.sql.Connection;          //Aqui obtenemos el metodo conectar
import java.sql.DriverManager;       //Aqui obtenemos el manejo del driver de java a mysql
import java.sql.PreparedStatement;   //Aqui obtenemos una sintaxis facil de crear sentencias sql
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BdLessons {
    private final String url = "jdbc:mysql://sql5.freesqldatabase.com/sql5445661";
    //Declaramos un objeto de tipo PreparedStatement el cual nos ayudara a preparar los querys que queramos hacer a la BD
    Connection connection = null;
    Toast toast;
    CreateLessonsActivity createLessonsActivity;
    public BdLessons(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, "sql5445661", "af1PYByzIE");    //Apuntamos nuestro objeto con a el intento de conectarse con los parametros o las credenciales que tenemos en MYSQL
            if (connection != null) {
                toast.makeText(createLessonsActivity,"Conexión a base de datos funcionando" , Toast.LENGTH_SHORT).show();
                System.out.println("Conexión a base de datos funcionando");
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println(e);
        }
    }
    public Connection getConnection() {

        return connection;
    }

    public void dropConnection() {
        connection = null;
        toast.makeText(createLessonsActivity,"La conexion la BD se ha cerrado" , Toast.LENGTH_SHORT).show();
        System.out.println("La conexion la BD se ha cerrado");

    }

    public ArrayList<String> searchName() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Nombre FROM Clase ORDER BY Nombre" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Nombre"));
            }

        } catch (SQLException sqlException) {
            toast.makeText(createLessonsActivity,"Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchDuration() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Duracion FROM Clase ORDER BY Nombre" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Duracion"));
            }

        } catch (SQLException sqlException) {
            toast.makeText(createLessonsActivity,"Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchLimitOfDays() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT limit_timepo_cancelacion FROM Clase ORDER BY Nombre" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("limit_timepo_cancelacion"));
            }

        } catch (SQLException sqlException) {
            toast.makeText(createLessonsActivity,"Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchLessonName(String nombre) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Nombre FROM Clase WHERE Nombre="+nombre+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Nombre"));
            }

        } catch (SQLException sqlException) {
            toast.makeText(createLessonsActivity,"Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchLessonDuration(String nombre) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Duracion FROM Clase WHERE Nombre="+nombre+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Duracion"));
            }

        } catch (SQLException sqlException) {
            toast.makeText(createLessonsActivity,"Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchLessonLimitOfDays(String nombre) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT limit_timepo_cancelacion FROM Clase WHERE Nombre="+nombre+"" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("limit_timepo_cancelacion"));
            }

        } catch (SQLException sqlException) {
            toast.makeText(createLessonsActivity,"Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public void addLesson (String name, int duration, int limitOfDays){
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Clase (Nombre, Duracion, limit_timepo_cancelacion) VALUES ("+name+","+duration+","+limitOfDays+")")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            toast.makeText(createLessonsActivity,"Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }
    public void deleteLesson (String name){
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Clase WHERE Nombre="+name)) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            toast.makeText(createLessonsActivity,"Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }


}
