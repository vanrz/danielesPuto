/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import negocio.Inscripcion;

import util.CaException;
import util.ServiceLocator;
import java.sql.*;

/**
 *
 * @author vanRz
 */
public class InscripcionDAO {

    Inscripcion ins;

    public InscripcionDAO() {

        ins = new Inscripcion();
    }

    public void AñadirInscripcion() throws CaException {
        try {
            String stringSQL = "INSERT INTO Inscripcion VALUES (?,?,?,?,?,?,?)";
            Connection conex = ServiceLocator.getInstance().tomarConexion();//conexion
            PreparedStatement prepSta = conex.prepareStatement(stringSQL);

            prepSta.setInt(1, ins.getK_ins());
            prepSta.setString(2, ins.getI_estado());
            prepSta.setString(3, ins.getF_ins());//yyyy-mm-dd
            prepSta.setInt(4, ins.getV_ins());
            prepSta.setString(5, ins.getI_asistencia());
            prepSta.setInt(6, ins.getK_persona());
            prepSta.setInt(7, ins.getK_evento());

        } catch (SQLException e) {
            throw new CaException("InscripcionDAO", "No se creó la inscripcion" + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }

    public void BuscarInscripcion() throws CaException {
        try {
            String stringSQL = "SELECT * FROM Inscripcion WHERE k_ins = ?";//busqueda en sql
            Connection conex = ServiceLocator.getInstance().tomarConexion();//conexion
            PreparedStatement prepSta = conex.prepareStatement(stringSQL);//prepara la busqueda del sql

            prepSta.setInt(1, ins.getK_ins());//reemplaza el interrogante por el valor

            ResultSet resultado = prepSta.executeQuery();//ejecuta el query y guarda el resultado

            while (resultado.next()) {
                ins.setK_ins(resultado.getInt(1));
                ins.setI_estado(resultado.getString(2));
                Date f_ins = resultado.getDate(3);
                ins.setF_ins(f_ins.toString());//yyyy-mm-dd
                ins.setV_ins(resultado.getInt(4));
                ins.setI_asistencia(resultado.getString(5));
                ins.setK_persona(resultado.getInt(6));
                ins.setK_evento(resultado.getInt(7));
            }

        } catch (SQLException e) {
            throw new CaException("InscripcionDAO", "No se encontro la inscripcion" + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }

    public void BorrarInscripcion() throws CaException {
        try {
            String stringSQL = "DELETE FROM Inscripcion WHERE k_ins = ?";//busqueda en sql
            Connection conex = ServiceLocator.getInstance().tomarConexion();//conexion
            PreparedStatement prepSta = conex.prepareStatement(stringSQL);//prepara la busqueda del sql

            prepSta.setInt(1, ins.getK_ins());

            prepSta.executeUpdate();
            prepSta.close();

            ServiceLocator.getInstance().commit();
            
        } catch (SQLException e) {
            throw new CaException("InscripcionDAO", "No se eliminó la inscripcion" + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    

}
