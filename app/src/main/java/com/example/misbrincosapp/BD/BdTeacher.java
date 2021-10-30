package com.example.misbrincosapp.BD;

import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BdTeacher {
    private final String url = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5445661?characterEncoding=utf8";
    //Declaramos un objeto de tipo PreparedStatement el cual nos ayudara a preparar los querys que queramos hacer a la BD
    Connection connection = null;
    Toast toast;
    //CreateLessonsActivity createLessonsActivity;
    public BdTeacher(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, "sql5445661", "af1PYByzIE");    //Apuntamos nuestro objeto con a el intento de conectarse con los parametros o las credenciales que tenemos en MYSQL
            if (connection != null) {
                //toast.makeText(createLessonsActivity,"Conexión a base de datos funcionando" , Toast.LENGTH_SHORT).show();
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
        //toast.makeText(createLessonsActivity,"La conexion la BD se ha cerrado" , Toast.LENGTH_SHORT).show();
        System.out.println("La conexion la BD se ha cerrado");

    }
    public ArrayList<String> searchCc(String lessonName) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Cc_profesor FROM Especializa WHERE Nombre_clase='"+lessonName+"' ORDER BY Cc" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Cc_profesor"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchTeacherName(String cc) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Nombre FROM Profesor WHERE Cc='"+cc+"'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Nombre"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //        + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchCc() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Cc FROM Profesor ORDER BY Cc" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Cc"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchName() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Nombre FROM Profesor ORDER BY Cc" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Nombre"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchEmail() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Correo FROM Profesor ORDER BY Cc" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Correo"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchTeacherCc(String cc) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Cc FROM Profesor WHERE Cc='"+cc+"'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Cc"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //        + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchTeacherNameWith(String cc) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Nombre FROM Profesor WHERE Cc='"+cc+"'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Nombre"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //        + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchTeacherEmail(String cc) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Correo FROM Profesor WHERE Cc='"+cc+"'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Correo"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //        + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public void addTeacher(String cc,String name, String email){
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Profesor VALUES ('"+cc+"' ,'"+name+"','"+email+"')")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //        + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }
    public void updateTeacher(String cc, String email) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Profesor SET Correo='"+email+"' WHERE Cc='"+cc+"'")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //        + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
    }
    public void deleteTeacher(String cc){
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Profesor WHERE Cc='"+cc+"'")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //        + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }
}
