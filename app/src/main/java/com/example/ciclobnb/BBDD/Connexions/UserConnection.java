package com.example.ciclobnb.BBDD.Connexions;

import android.util.Log;

import com.example.ciclobnb.BBDD.ConnectBBdd;
import com.example.ciclobnb.Objectes.Direccio;
import com.example.ciclobnb.Objectes.Usser;
import com.example.ciclobnb.Objectes.Xat.Xat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class UserConnection {
    private Connection cn=null;
    private final ConnectBBdd conexio = new ConnectBBdd();
    public UserConnection() throws InterruptedException {
        try {
            if(cn!=null)
                cn=null;
            this.cn=conexio.execute().get();
        }catch (ExecutionException e){

        }

    }
    public Usser getUserPerId(int id) throws SQLException, InterruptedException {
        final Usser[] u = {null};
        Thread fil = new Thread(new Runnable() {
            @Override
            public void run() {
                java.sql.Statement stm = null;
                ResultSet rs = null;
                String nom, cognom1, cognom2,correuElectronic,login,contrasenya="";
                Date dataNaixement;
                boolean actiu;

                try {
                    String sql= "SELECT * from `usuaris` WHERE idUsuari='"+id+"';";

                    stm = cn.createStatement();
                    rs=stm.executeQuery(sql);
                    rs.next();
                    login=rs.getString(2);
                    nom=rs.getString(4);
                    cognom1=rs.getString(5);
                    cognom2=rs.getString(6);
                    dataNaixement=rs.getDate(7);
                    correuElectronic=rs.getString(8);
                    actiu=rs.getBoolean(9);
                    u[0] = new Usser(id,nom, cognom1,  cognom2, login, contrasenya, dataNaixement, correuElectronic, actiu);
                    //Log.d("userLlegit", u[0].getNom());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        cn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        fil.start();
        fil.join();
        return u[0];
    }
    public Usser Login(String user, String password) throws SQLException, InterruptedException {
        final Usser[] u = {null};
        Thread fil = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    java.sql.Statement stm = null;
                    ResultSet rs = null;
                    //cn= new ConnectBBdd().execute().get();
                    stm = cn.createStatement();
                    rs=stm.executeQuery("SELECT * FROM usuaris u " +
                            "INNER JOIN direccio d ON d.IdDireccio = u.IdDireccio " +
                            "WHERE Login='"+user+"' AND Contrasenya='"+new Usser().Hash(password)+"';");
                    rs.next();
                    u[0] = new Usser(rs.getInt("IdUsuari"),rs.getString("Nom"), rs.getString("Cognom1"),  rs.getString("Cognom2"), user,new Usser().Hash(password), rs.getDate("DataNaixement"), rs.getString("CorreuElectronic"), rs.getBoolean("CompteActiu"));
                    u[0].setDireccio(new Direccio(rs.getInt("IdDireccio"),rs.getString("TipusVia"),rs.getString("NomCarrer"),rs.getString("Numero"),rs.getString("Pis"),rs.getInt("IdCP")));
                    Log.d("userLlegit", u[0].getNom());
                }catch (Exception e){ e.printStackTrace();}
            }
        });
        fil.start();
        fil.join();
        return u[0];
    }

    public boolean insertUser(Usser user) throws InterruptedException {
        final boolean[] be = {false};
        Thread fil = new Thread(new Runnable() {
            @Override
            public void run() {
                java.sql.Statement stm = null;
                ResultSet rs = null;
                try {
                    String sql = "INSERT INTO `usuaris` (`Login`, `Contrasenya`, `Nom`, `Cognom1`, " +
                            "`Cognom2`, `DataNaixement`, `CorreuElectronic`, `CompteActiu`, `IdDireccio`) " +
                            "VALUES ('"+user.getLogin()+"', '"+user.Hash(user.getContrasenya())+"', '"+user.getNom()+"', '"+user.getCognom1()+"', '"+user.getCognom2()+"', '"+"2023-04-17"+"', '"+user.getCorreuElectronic()+"', 1, "+new Direccio().AgafaUltima()+");";

                    //cn= conexio.execute().get();
                    stm = cn.createStatement();
                    int i = stm.executeUpdate(sql);
                    if (i > 0) {
                        be[0] =true;
                        Log.d("SsQL", "insertat ");

                    } else {

                        Log.d("SsQL", "No insertat ");
                        rs.close();
                        stm.close();
                        cn.close();
                    }

                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        fil.start();
        fil.join();

        return be[0];
    }

    public boolean updateUser(Usser user) throws InterruptedException {
        final boolean[] be = {false};
        Thread fil = new Thread(new Runnable() {
            @Override
            public void run() {
                java.sql.Statement stm = null;
                ResultSet rs = null;
                try {
                    /*String sql = "UPDATE `usuaris` (`Nom`, `Cognom1`, " +
                            "`Cognom2`, `DataNaixement`, `CorreuElectronic`, `CompteActiu`, `IdDireccio`) " +
                            "SET ('"+user.getNom()+"', '"+user.getCognom1()+"', '"+user.getCognom2()+"', '"+user.getDataNaixement()+"', '"+user.getCorreuElectronic()+"', 1, "+new Direccio().AgafaUltima()+")" +
                            "WHERE login = '"+user.getLogin()+"' AND Contrasenya = '"+user.getContrasenya()+"';";*/
                    String sql = "UPDATE `usuaris` " +
                            "SET `Nom`='"+user.getNom()+"', `Cognom1`='"+user.getCognom1()+"', `Cognom2`='"+user.getCognom2()+"', `DataNaixement`='"+user.getDataNaixement()+"', `CorreuElectronic`='"+user.getCorreuElectronic()+"', `CompteActiu`= 1, `IdDireccio` = "+new Direccio().AgafaUltima()+
                            " WHERE login = '"+user.getLogin()+"' AND Contrasenya = '"+user.getContrasenya()+"';";

                    //cn= conexio.execute().get();
                    stm = cn.createStatement();
                    int i = stm.executeUpdate(sql);
                    if (i > 0) {
                        be[0] =true;
                        Log.d("SsQL", "insertat ");

                    } else {

                        Log.d("SsQL", "No insertat ");
                        rs.close();
                        stm.close();
                        cn.close();
                    }

                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        fil.start();
        fil.join();

        return be[0];
    }

    public ArrayList<String> Buscador(String query, Integer columnName) throws InterruptedException {
        ArrayList<String> result = new ArrayList<>();

       /* try {
            if(cn!=null)
                cn=null;
            cn = conexio.execute().get();

        }catch (ExecutionException e){

        }*/
        Thread fil =new Thread(new Runnable() {
            @Override
            public void run() {
                java.sql.Statement stm = null;
                ResultSet rs = null;
                try {

                    stm = cn.createStatement();
                    rs=stm.executeQuery(query);
                    while(rs.next()) {
                        String name = rs.getString(columnName);
                        result.add(name);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        cn.close();
                        rs.close();
                        stm.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.d("XatError", e.getMessage());
                    }
                }
            }
        });
        fil.start();
        fil.join();

        return result;
    }
}
