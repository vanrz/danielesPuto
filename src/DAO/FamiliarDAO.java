/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import negocio.Familiar;
import util.CaException;
import util.ServiceLocator;
import java.sql.*;

/**
 *
 * @author vanRz
 */
public class FamiliarDAO {

    private Familiar fam;

    public Familiar getFam() {
        return fam;
    }

    public void setFam(Familiar fam) {
        this.fam = fam;
    }

    public FamiliarDAO() {

        fam = new Familiar();
    }

    public void AñadirFAmiliar() throws CaException {
        try {
            String stringSQL = "INSERT INTO \"Familiar\" (k_familiar, o_tipoidfam, n_pnombre, n_snombre, n_papellido, n_sapellido, f_nacimiento, n_parentesco, k_persona)VALUES (?,?,?,?,?,?,?,?,?)";
            Connection conex = ServiceLocator.getInstance().tomarConexion();//conexion
            PreparedStatement prepSta = conex.prepareStatement(stringSQL);

            prepSta.setInt(1, fam.getK_familiar());
            prepSta.setString(2, fam.getO_tipoidfam());
            prepSta.setString(3, fam.getN_pnombre());
            prepSta.setString(4, fam.getN_snombre());
            prepSta.setString(5, fam.getN_papellido());
            prepSta.setString(6, fam.getN_sapellido());
            prepSta.setDate(7, java.sql.Date.valueOf(fam.getF_nacimiento()));//yyyy-mm-dd
            prepSta.setString(8, fam.getN_parentesco());
            prepSta.setInt(9, fam.getK_persona());
            prepSta.executeUpdate();
            prepSta.close();
            ServiceLocator.getInstance().commit();

        } catch (SQLException e) {
            throw new CaException("FamiliarDAO", "No se creó el familiar" + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }

    public void BuscarFAmiliar() throws CaException {
        try {
            String stringSQL = "SELECT * FROM Familiar WHERE k_familiar = ?";//busqueda en sql
            Connection conex = ServiceLocator.getInstance().tomarConexion();//conexion
            PreparedStatement prepSta = conex.prepareStatement(stringSQL);//prepara la busqueda del sql

            prepSta.setInt(1, fam.getK_familiar());//reemplaza el interrogante por el valor

            ResultSet resultado = prepSta.executeQuery();//ejecuta el query y guarda el resultado

            while (resultado.next()) {

                fam.setK_familiar(resultado.getInt(1));
                fam.setO_tipoidfam(resultado.getString(2));
                fam.setN_pnombre(resultado.getString(3));
                fam.setN_snombre(resultado.getString(4));
                fam.setN_papellido(resultado.getString(5));
                fam.setN_sapellido(resultado.getString(6));
                Date f_nacimiento = resultado.getDate(7);
                fam.setF_nacimiento(f_nacimiento.toString());//yyyy-mm-dd
                fam.setN_parentesco(resultado.getString(8));
                fam.setK_persona(resultado.getInt(9));

            }
        } catch (SQLException e) {
            throw new CaException("FamiliarDAO", "No se encontro el familiar" + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }

    public void BorrarFamiliar() throws CaException {
        try {
            String stringSQL = "DELETE FROM Familiar WHERE k_familiar = ?";//busqueda en sql
            Connection conex = ServiceLocator.getInstance().tomarConexion();//conexion
            PreparedStatement prepSta = conex.prepareStatement(stringSQL);//prepara la busqueda del sql
            
            prepSta.setInt(1, fam.getK_familiar());

            prepSta.executeUpdate();
            prepSta.close();

            ServiceLocator.getInstance().commit();

        } catch (SQLException e) {
            throw new CaException("FamiliarDAO", "No se eliminó el familiar" + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }

}
