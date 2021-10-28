package com.example.misbrincosapp.BD;

import android.widget.Toast;

import com.example.misbrincosapp.CreateLessonsActivity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class BdSessions {
    private final String url = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5445661?characterEncoding=utf8";
    //Declaramos un objeto de tipo PreparedStatement el cual nos ayudara a preparar los querys que queramos hacer a la BD
    Connection connection = null;
    Toast toast;
    CreateLessonsActivity createLessonsActivity;
    public BdSessions(){
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
    public ArrayList<Integer> searchId() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Id FROM Sesion ORDER BY Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Id"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //+ sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Date> searchDate() {
        ArrayList<Date> arrayList = new ArrayList<Date>();
        String sql = "SELECT Fecha_sesion FROM Sesion ORDER BY Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getDate("Fecha_sesion"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //+ sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchCcTeacher() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Cc_profesor  FROM Sesion ORDER BY Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Cc_profesor"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //+ sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchClassroomNumber() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Numero_salon FROM Sesion ORDER BY Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Numero_salon"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //+ sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchDayOfWeek() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Dia FROM Realiza ORDER BY Id_sesion" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Dia"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //+ sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchLessonsName() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Nombre_clase FROM Realiza ORDER BY Id_sesion" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Nombre_clase"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //+ sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Time> searchHour() {
        ArrayList<Time> arrayList = new ArrayList<Time>();
        String sql = "SELECT Hora FROM Realiza ORDER BY Id_sesion" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getTime("Hora"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //+ sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchCcTeacher(int id) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Cc_profesor FROM Sesion WHERE Id="+id+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Cc_profesor"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchSessionLessonName(int id) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Nombre_clase FROM Realiza WHERE Id_sesion="+id+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Nombre"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Date> searchSessionDate(int id) {
        ArrayList<Date> arrayList = new ArrayList<Date>();
        String sql = "SELECT Fecha_sesion FROM Sesion WHERE Id_sesion="+id+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getDate("Fecha_sesion"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchSessionDayOfWeek(int id) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Dia  FROM Realiza WHERE Id_sesion="+id+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Dia"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Time> searchSessionHour(int id) {
        ArrayList<Time> arrayList = new ArrayList<Time>();
        String sql = "SELECT Hora FROM Realiza WHERE Id_sesion="+id+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getTime("Hora"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchSessionId(int id) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Id FROM Sesion WHERE Id_sesion="+id+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Id"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchSessionClassroom(int id) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Numero_salon FROM Sesion WHERE Id_sesion="+id+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Numero_salon"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public void addSession (int id, String fecha, String ccProfesor, int numeroDeSalon){
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Sesion VALUES ("+id+",'"+fecha+"','"+ccProfesor+"',"+numeroDeSalon+")")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }
    public void addRealiza (int id, String dia, String hora, String nombreClase){
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Realiza VALUES ("+id+",'"+dia+"','"+hora+"','"+nombreClase+"')")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }

    public void deleteSession (int id){
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Sesion WHERE Id="+id+"")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }
    public void deleteRealiza (int id){
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Realiza WHERE Id_sesion="+id+"")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }
    public void updateSesion(int id, String fecha, String ccProfesor, int numerSalon) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Sesion SET Fecha_sesion='"+fecha+"', Cc_profesor='"+ccProfesor+"' , Numero_salon ="+numerSalon+" WHERE Id="+id+"")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //        + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
    }
}
