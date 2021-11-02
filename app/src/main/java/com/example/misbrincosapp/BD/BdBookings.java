package com.example.misbrincosapp.BD;

import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class BdBookings {
    private final String url = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5445661?characterEncoding=utf8";
    //Declaramos un objeto de tipo PreparedStatement el cual nos ayudara a preparar los querys que queramos hacer a la BD
    Connection connection = null;
    Toast toast;
    //CreateLessonsActivity createLessonsActivity;
    public BdBookings(){
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
    public ArrayList<Integer> searchIds() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Id FROM Reserva ORDER BY Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Id"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchAsistances() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Asistencia FROM Reserva ORDER BY Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Asistencia"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Date> searchSessionDates() {
        ArrayList<Date> arrayList = new ArrayList<Date>();
        String sql = "SELECT Fecha_reserva  FROM Reserva ORDER BY Id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getDate("Fecha_reserva"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchIdSessions() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Id_sesion FROM Reserva ORDER BY Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Id_sesion"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //+ sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchCcStudents() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Cc_alumno FROM Reserva ORDER BY Id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Cc_alumno"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }

    public ArrayList<Integer> searchId(int idBooking) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Id FROM Reserva WHERE Id="+idBooking+"" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Id"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchClassroomNumber(int idBooking) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "Sesion.Numero_salon FROM Reserva JOIN Sesion ON  Reserva.Id_sesion = Sesion.Id WHERE Reserva.Id= "+idBooking+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Numero_salon"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Time> searchHour(int idBooking) {
        ArrayList<Time> arrayList = new ArrayList<Time>();
        String sql = "SELECT Realiza.Hora FROM Reserva JOIN Realiza ON Reserva.Id_sesion = Realiza.Id_sesion  WHERE Reserva.Id= "+idBooking+"" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getTime("Hora"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchDay(int idBooking) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Realiza.Dia FROM Reserva JOIN Realiza ON Reserva.Id_sesion = Realiza.Id_sesion  WHERE Reserva.Id= "+idBooking+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Dia"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchAsistance(int idBooking) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Asistencia FROM Reserva WHERE Id="+idBooking+"" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Asistencia"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchTeacherName(int idBooking) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Profesor.Nombre FROM (Reserva JOIN Sesion ON Reserva.Id_sesion = Sesion.Id) JOIN Profesor ON Sesion.Cc_profesor = Profesor.Cc WHERE Reserva.Id="+idBooking+"";
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
    public ArrayList<Date> searchSessionDate(int idBooking) {
        ArrayList<Date> arrayList = new ArrayList<Date>();
        String sql = "SELECT Fecha_reserva  FROM Reserva WHERE Id="+idBooking+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getDate("Fecha_reserva"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchIdSession(int idBooking) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Id_sesion FROM Reserva WHERE Id="+idBooking+"" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Id_sesion"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //+ sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchCcStudent(int idBooking) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Cc_alumno FROM Reserva WHERE Id="+idBooking+"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Cc_alumno"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }

    public ArrayList<String> searchBookingLessonName(int idBooking) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Realiza.Nombre_clase FROM Realiza JOIN Reserva ON  Reserva.Id_sesion = Realiza.Id_sesion WHERE Reserva.Id= "+idBooking+"" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Nombre_clase"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Date> searchBookingDate(String ccStudent) {
        ArrayList<Date> arrayList = new ArrayList<Date>();
        String sql = "SELECT Fecha_reserva FROM Reserva WHERE Cc_alumno='"+ccStudent+"' ORDER BY Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getDate("Fecha_reserva"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchBookingId(String ccStudent) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Id FROM Reserva WHERE Cc_alumno='"+ccStudent+"' ORDER BY Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Id"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchBookingLessonsName(String ccStudent) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Realiza.Nombre_clase FROM Realiza JOIN Reserva ON  Reserva.Id_sesion = Realiza.Id_sesion WHERE Cc_Alumno= '"+ccStudent+"' ORDER BY Reserva.Id" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Nombre_clase"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public void addBooking (int id, String asistencia, String date, int idSession, String ccStudent){
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Reserva VALUES ("+id+",'"+asistencia+"','"+date+"',"+idSession+", '"+ccStudent+"')")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }
    public void deleteBooking (int id){
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Reserva WHERE Id="+id+"")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }

    }
    public void updateBooking(int id, String asistencia) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Reserva SET Asistencia='"+asistencia+"' WHERE Id="+id+"")) {
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            //        + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
    }
    public ArrayList<Integer> searchNumber(String lessonName) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Numero FROM Salon WHERE Tipo="+lessonName+"ORDER BY Numero" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Numero"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Integer> searchCapacity(int numero) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String sql = "SELECT Aforo FROM Salon WHERE Numero="+numero ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getInt("Aforo"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<String> searchDay() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT Dia FROM Horario ORDER BY Hora" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getString("Dia"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
    public ArrayList<Time> searchHour(String day) {
        ArrayList<Time> arrayList = new ArrayList<Time>();
        String sql = "SELECT Hora FROM Horario  WHERE Dia="+day+" ORDER BY Hora" ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                arrayList.add(resultSet.getTime("Hora"));
            }

        } catch (SQLException sqlException) {
            //toast.makeText(createLessonsActivity,"Error en la ejecución:"
            // + sqlException.getErrorCode() + " " + sqlException.getMessage() , Toast.LENGTH_SHORT).show();
            System.out.println("Error en la ejecución:"
                    + sqlException.getErrorCode() + " " + sqlException.getMessage());
        }
        return arrayList;
    }
}
