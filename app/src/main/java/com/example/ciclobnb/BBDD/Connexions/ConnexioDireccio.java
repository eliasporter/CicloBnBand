package com.example.ciclobnb.BBDD.Connexions;

import com.example.ciclobnb.BBDD.ConnectBBdd;
import com.example.ciclobnb.Objectes.Direccio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class ConnexioDireccio {

    java.sql.Statement stm;
    ResultSet rs;
    private final ConnectBBdd conexio = new ConnectBBdd();
    private Connection cn =null;
    public ConnexioDireccio()  {
        try {
            cn=conexio.execute().get();
        }catch (ExecutionException | InterruptedException e){

        }
    }


    public boolean Actualizar(Direccio direccio) throws InterruptedException {
        final boolean[] hecho = {false};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                stm = null;
                rs = null;
                try {
                    String sql= "UPDATE direccio SET TipusVia = '"+direccio.getTipusVia()+"', " +
                            "NomCarrer = '"+direccio.getNomCarrer()+"', Numero = '"+direccio.getNumero()+"', " +
                            "Pis = '" +direccio.getPis()+"', IdCP = "+direccio.getIdCP()+" WHERE IdDireccio = " +
                            direccio.getIdDireccion()+";";
                    //cn=conexio.execute().get();
                    stm = cn.createStatement();
                    if (stm.executeUpdate(sql) > 0) hecho[0] = true;
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (stm != null) {
                        try {
                            stm.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (cn != null) {
                        try {
                            cn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
        thread.join();
        return hecho[0];
    }
    public boolean SubirDireccion(Direccio direccio) throws InterruptedException {
        final boolean[] hecho = {false};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                stm = null;
                rs = null;
                try {
                    String sql= "INSERT INTO direccio (TipusVia, NomCarrer, Numero, Pis, IdCP) VALUES (" +
                            "'"+direccio.getTipusVia()+"', '"+direccio.getNomCarrer()+"', '"+direccio.getNumero()+"', '" +
                            direccio.getPis()+"', '"+direccio.getIdCP()+"');";
                    //cn=conexio.execute().get();
                    stm = cn.createStatement();
                    if (stm.executeUpdate(sql) > 0) hecho[0] = true;
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (stm != null) {
                        try {
                            stm.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (cn != null) {
                        try {
                            cn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
        thread.join();
        return hecho[0];
    }

    public int BuscarID(String id) throws InterruptedException {
        final int[] idCp = new int[1];
        Thread fil = new Thread(new Runnable() {
            @Override
            public void run() {
                java.sql.Statement stm = null;
                ResultSet rs = null;
                int idXat,idUser1,idUser2;
                boolean actiu;
                try {
                    String sql= "SELECT * FROM `ciclobnbDB`.`codipostal` WHERE codipostal= '"+id+"';";

                    //cn=conexio.execute().get();;
                    stm = cn.createStatement();
                    rs=stm.executeQuery(sql);
                    rs.next();
                    idCp[0] =rs.getInt(1);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (stm != null) {
                        try {
                            stm.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (cn != null) {
                        try {
                            cn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        fil.start();
        fil.join();
        return idCp[0];
    }

    public String agafaUltima() throws InterruptedException {
        //
        final String[] id = new String[1];
        Thread fil = new Thread(new Runnable() {
            @Override
            public void run() {
                java.sql.Statement stm = null;
                ResultSet rs = null;
                try {
                    String sql= "SELECT * FROM `direccio` ORDER BY `IdDireccio` DESC LIMIT 1;";
                    /*conexio.execute();
                    cn=conexio.get();*/
                    stm = cn.createStatement();
                    rs=stm.executeQuery(sql);
                    rs.next();
                    id[0] =rs.getString(1);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (stm != null) {
                        try {
                            stm.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (cn != null) {
                        try {
                            cn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        fil.start();
        fil.join();
        return id[0];
    }
}
